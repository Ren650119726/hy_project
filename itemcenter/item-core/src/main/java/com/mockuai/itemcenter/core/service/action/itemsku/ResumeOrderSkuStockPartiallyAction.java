package com.mockuai.itemcenter.core.service.action.itemsku;

import com.google.common.base.Strings;
import com.google.common.collect.*;
import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.HookEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.constant.StockFrozenRecordStatusEnum;
import com.mockuai.itemcenter.common.domain.dto.OrderStockDTO;
import com.mockuai.itemcenter.common.domain.dto.StockFrozenRecordDTO;
import com.mockuai.itemcenter.common.domain.qto.StockFrozenRecordQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemSkuManager;
import com.mockuai.itemcenter.core.manager.StockFrozenRecordManager;
import com.mockuai.itemcenter.core.manager.StoreStockManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import com.mockuai.suppliercenter.common.dto.SupplierOrderStockDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 以订单为单位减去sku库存
 */

@Service
public class ResumeOrderSkuStockPartiallyAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(ResumeOrderSkuStockPartiallyAction.class);
    @Resource
    private ItemSkuManager itemSkuManager;

    @Resource
    private StoreStockManager storeStockManager;

    @Resource
    private StockFrozenRecordManager stockFrozenRecordManager;

    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {
        ItemResponse response = null;
        ItemRequest request = context.getRequest();

        if (request.getObject("orderStockDTO", OrderStockDTO.class) == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "orderStockDTO is missing");
        }


        String bizCode = (String) context.get("bizCode");

        String appKey = request.getString("appKey");

        OrderStockDTO orderStockDTO = request.getObject("orderStockDTO", OrderStockDTO.class);

        Long sellerId = orderStockDTO.getSellerId();

        if (sellerId == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "orderStockDTO.sellerId不能为空");
        }


        String orderSn = orderStockDTO.getOrderSn();

        if (Strings.isNullOrEmpty(orderSn)) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "orderStockDTO.orderSn不能为空");
        }


        StockFrozenRecordQTO query = new StockFrozenRecordQTO();
        query.setBizCode(bizCode);
        query.setOrderSn(orderStockDTO.getOrderSn());
        query.setStatus(StockFrozenRecordStatusEnum.CRUSHED.getStatus());

        List<StockFrozenRecordDTO> recordDTOList = stockFrozenRecordManager.queryStockFrozenRecord(query);


        Multimap<Long, StockFrozenRecordDTO> recordSkuMap = LinkedListMultimap.create();

        for (StockFrozenRecordDTO recordDTO : recordDTOList) {
            recordSkuMap.put(recordDTO.getSkuId(), recordDTO);
        }

        List<Long> skuIdList = Lists.newArrayList();

        List<SupplierOrderStockDTO.OrderSku> skuList = Lists.newArrayList();

        Collections.sort(orderStockDTO.getOrderSkuList(), new Comparator<OrderStockDTO.OrderSku>() {
            @Override
            public int compare(OrderStockDTO.OrderSku o1, OrderStockDTO.OrderSku o2) {

                if (o1.getSkuId() > o2.getSkuId()) {
                    return 1;
                } else {
                    return -1;
                }

                   /* if (o1.getSkuId() > o2.getSkuId()) {
                        return 1;
                    } else if (o1.getSkuId() < o2.getSkuId()) {
                        return -1;
                    } else {
                        if (o1.getDistributorId() > o2.getDistributorId()) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }*/
            }
        });

        for (OrderStockDTO.OrderSku orderSku : orderStockDTO.getOrderSkuList()) {


            Long skuId = orderSku.getSkuId();

            if (!recordSkuMap.keySet().contains(skuId)) {

                OrderStockDTO failure = new OrderStockDTO();

                failure.setOrderSn(orderSn);
                failure.setSellerId(sellerId);

                failure.setFailedSkuList(Lists.newArrayList(orderSku));

                return ResponseUtil.getErrorResponse(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "sku" + skuId + "对应的库存减扣记录不存在", failure);
            }

            Collection<StockFrozenRecordDTO> records = recordSkuMap.get(skuId);


            if(records != null) {
                for (StockFrozenRecordDTO record : records) {
                    //V1.2.2 移除店铺概念 distributorId
                    //if (record.getDistributorId().longValue() == orderSku.getDistributorId().longValue()) {
                        Integer number = record.getNumber();

                        skuIdList.add(skuId);

                        itemSkuManager.resumeCrushedItemSkuStock(skuId, sellerId, number, bizCode);

                        SupplierOrderStockDTO.OrderSku sku = new SupplierOrderStockDTO.OrderSku();

                        sku.setSkuId(skuId);
                        sku.setNumber(number);
                       //sku.setDistributorId(record.getDistributorId());
                        sku.setStoreId(orderSku.getStoreId());
                        skuList.add(sku);
                    //}
                }
            }


        }


        //冻结仓库库存,如果在这里发生异常,前面的数据库操作将回滚
        SupplierOrderStockDTO supplierOrderStockDTO = new SupplierOrderStockDTO();
        supplierOrderStockDTO.setOrderSn(orderSn);
        supplierOrderStockDTO.setOrderSkuList(skuList);
        storeStockManager.resumeStoreSkuStockBySku(supplierOrderStockDTO, appKey);


        //如果前两部操作都成功,则修改冻结记录为回复状态;

        stockFrozenRecordManager.updateStockFrozenRecordStatus(orderSn, skuIdList, sellerId, StockFrozenRecordStatusEnum.RESUME.getStatus(), bizCode);

        context.put(HookEnum.BATCH_STOCK_CHANGE_HOOK.getHookName(), "");
        context.put("skuIdList", skuIdList);
        context.put("sellerId", sellerId);

        response = ResponseUtil.getSuccessResponse(true);
        return response;

    }

    @Override
    public String getName() {
        return ActionEnum.INCREASE_ORDER_SKU_STOCK_PARTIALLY.getActionName();
    }
}
