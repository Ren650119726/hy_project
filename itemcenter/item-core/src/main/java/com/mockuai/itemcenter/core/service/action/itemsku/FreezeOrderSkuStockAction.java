package com.mockuai.itemcenter.core.service.action.itemsku;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.HookEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.constant.StockFrozenRecordStatusEnum;
import com.mockuai.itemcenter.common.domain.dto.OrderStockDTO;
import com.mockuai.itemcenter.common.domain.dto.StockFrozenRecordDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemSkuManager;
import com.mockuai.itemcenter.core.manager.StockFrozenRecordManager;
import com.mockuai.itemcenter.core.manager.StoreStockManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.JsonUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import com.mockuai.suppliercenter.common.api.Response;
import com.mockuai.suppliercenter.common.dto.SupplierOrderStockDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 以订单为单位减去sku库存
 */

@Service
public class FreezeOrderSkuStockAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(FreezeOrderSkuStockAction.class);
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

        if (request.getObject("orderStockDTO") == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "orderStockDTO is missing");
        }

        OrderStockDTO orderStockDTO = request.getObject("orderStockDTO", OrderStockDTO.class);
        log.info("冻结库存 freezeOrderSkuStock orderStockDTO:{} ", JSON.toJSON(orderStockDTO));
        String bizCode = (String) context.get("bizCode");

        String appKey = request.getString("appKey");

        String orderSn = orderStockDTO.getOrderSn();

        Long sellerId = orderStockDTO.getSellerId();

        List<Long> skuIdList = Lists.newArrayList();

        List<SupplierOrderStockDTO.OrderSku> skuList = Lists.newArrayList();

        for (OrderStockDTO.OrderSku orderSku : orderStockDTO.getOrderSkuList()) {

            Long skuId = orderSku.getSkuId();
            Integer number = orderSku.getNumber();
           // Long distributorId = orderSku.getDistributorId();
            skuIdList.add(skuId);
            SupplierOrderStockDTO.OrderSku sku = new SupplierOrderStockDTO.OrderSku();

            sku.setSkuId(skuId);
            sku.setNumber(number);
            //sku.setDistributorId(distributorId);
            skuList.add(sku);
        }


        //冻结仓库库存,如果在这里发生异常,前面的数据库操作将回滚
        SupplierOrderStockDTO supplierOrderStockDTO = new SupplierOrderStockDTO();
        supplierOrderStockDTO.setOrderSn(orderSn);
        supplierOrderStockDTO.setBizCode(bizCode);
        //supplierOrderStockDTO.setSellerId(sellerId);
        supplierOrderStockDTO.setOrderSkuList(skuList);
        Response xx = storeStockManager.lockStoreSkuStock(supplierOrderStockDTO, appKey);

        if (xx.isSuccess()) {
            supplierOrderStockDTO = (SupplierOrderStockDTO) xx.getModule();


            Multimap<Long, SupplierOrderStockDTO.OrderSku> storedIdMap = LinkedListMultimap.create();

            for (SupplierOrderStockDTO.OrderSku orderSku : supplierOrderStockDTO.getOrderSkuList()) {
                storedIdMap.put(orderSku.getSkuId(), orderSku);
            }

            log.info("freeze order stock params {} store returns {}", JsonUtil.toJson(orderStockDTO),JsonUtil.toJson(supplierOrderStockDTO));

            //如果前两部操作都成功,则添加冻结记录;
            for (OrderStockDTO.OrderSku orderSku : orderStockDTO.getOrderSkuList()) {

                Collection<SupplierOrderStockDTO.OrderSku> skus = storedIdMap.get(orderSku.getSkuId());

                if (skus != null) {

                    for (SupplierOrderStockDTO.OrderSku sku : skus) {

                        StockFrozenRecordDTO stockFrozenRecordDTO = new StockFrozenRecordDTO();
                        stockFrozenRecordDTO.setSellerId(sellerId);
                        stockFrozenRecordDTO.setBizCode(bizCode);
                        stockFrozenRecordDTO.setOrderSn(orderSn);
                        stockFrozenRecordDTO.setItemId(0L);

                        //多店下单情况下,要保证distributor_id相同
                        //updated by jiguansheng V1.2.2版本 移除店铺概念 移除 distributorId内容
                       /* if (sku.getDistributorId().longValue() == orderSku.getDistributorId().longValue()) {
                            orderSku.setStoreId(sku.getStoreId());
                            orderSku.setSupplierId(sku.getSupplierId());
                            stockFrozenRecordDTO.setStoreId(sku.getStoreId());
                            stockFrozenRecordDTO.setSupplierId(sku.getSupplierId());
                            stockFrozenRecordDTO.setDistributorId(sku.getDistributorId());
                            stockFrozenRecordDTO.setSkuId(orderSku.getSkuId());
                            stockFrozenRecordDTO.setNumber(orderSku.getNumber());
                            stockFrozenRecordDTO.setStatus(StockFrozenRecordStatusEnum.FROZEN.getStatus());
                            stockFrozenRecordManager.addStockFrozenRecord(stockFrozenRecordDTO);
                        }*/
                        orderSku.setStoreId(sku.getStoreId());
                        orderSku.setSupplierId(sku.getSupplierId());
                        stockFrozenRecordDTO.setStoreId(sku.getStoreId());
                        stockFrozenRecordDTO.setSupplierId(sku.getSupplierId());
                        stockFrozenRecordDTO.setSkuId(orderSku.getSkuId());
                        stockFrozenRecordDTO.setNumber(orderSku.getNumber());
                        stockFrozenRecordDTO.setStatus(StockFrozenRecordStatusEnum.FROZEN.getStatus());
                        stockFrozenRecordManager.addStockFrozenRecord(stockFrozenRecordDTO);

                    }
                }

            }


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
                Integer number = orderSku.getNumber();
               // Long distributorId = orderSku.getDistributorId();
                skuIdList.add(skuId);

                try {

                    itemSkuManager.freezeItemSkuStock(skuId, sellerId, number, bizCode);
                } catch (ItemException e) {

                    if (e.getCode() == ResponseCode.SYS_E_DB_UPDATE.getCode() || e.getCode() == ResponseCode.PARAM_E_INVALID.getCode()) {

                        OrderStockDTO.OrderSku failure = new OrderStockDTO.OrderSku();

                        failure.setSkuId(skuId);

                        orderStockDTO.setFailedSkuList(Lists.newArrayList(failure));

                        return ResponseUtil.getErrorResponse(ResponseCode.BASE_STATE_E_STOCK_SHORT, sellerId + "_" + skuId + "库存不足", orderStockDTO);
                    }
                }
            }

        } else if (xx.getCode() == com.mockuai.suppliercenter.common.constant.ResponseCode.B_ORDERNUMBER_ERROR.getValue()) {

            OrderStockDTO.OrderSku orderSku = new OrderStockDTO.OrderSku();
            orderSku.setSkuId((Long) xx.getModule());

            orderStockDTO.setFailedSkuList(Lists.newArrayList(orderSku));

            return ResponseUtil.getErrorResponse(ResponseCode.BASE_STATE_E_STOCK_SHORT, "库存" + xx.getModule() + "不足", orderStockDTO);
        } else {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEPENDENT_SERVICE_EXCEPTION, xx.getMessage());
        }


        context.put(HookEnum.BATCH_STOCK_CHANGE_HOOK.getHookName(), "");
        context.put("skuIdList", skuIdList);
        context.put("sellerId", sellerId);


        response = ResponseUtil.getSuccessResponse(orderStockDTO);
        return response;

    }

    @Override
    public String getName() {
        return ActionEnum.FREEZE_ORDER_SKU_STOCK.getActionName();
    }
}
