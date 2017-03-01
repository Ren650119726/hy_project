package com.mockuai.suppliercenter.core.service.action.stockitemsku;

import com.google.common.base.Strings;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.mockuai.itemcenter.common.constant.HookEnum;
import com.mockuai.suppliercenter.common.action.ActionEnum;
import com.mockuai.suppliercenter.common.api.SupplierResponse;
import com.mockuai.suppliercenter.common.constant.ResponseCode;
import com.mockuai.suppliercenter.common.constant.SupplierOrderStockStatusEnum;
import com.mockuai.suppliercenter.common.dto.OrderStockDTO;
import com.mockuai.suppliercenter.common.dto.SupplierNewOrderStockDTO;
import com.mockuai.suppliercenter.common.dto.SupplierOrderStockDTO;
import com.mockuai.suppliercenter.common.qto.SupplierOrderStockQTO;
import com.mockuai.suppliercenter.core.exception.SupplierException;
import com.mockuai.suppliercenter.core.manager.SupplierOrderStockManager;
import com.mockuai.suppliercenter.core.service.RequestContext;
import com.mockuai.suppliercenter.core.service.SupplierRequest;
import com.mockuai.suppliercenter.core.service.action.TransAction;
import com.mockuai.suppliercenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by lizg on 2016/9/24.
 */

@Service
public class ThawOrderSkuStockAction extends TransAction{

    private static final Logger log = LoggerFactory.getLogger(ThawOrderSkuStockAction.class);

    @Resource
    private SupplierOrderStockManager supplierOrderStockManager;


    @Override
    protected SupplierResponse doTransaction(RequestContext context) throws SupplierException {

        SupplierRequest supplierRequest = context.getRequest();

        OrderStockDTO orderStockDTO = (OrderStockDTO)supplierRequest.getParam("orderStockDTO");

        log.info("[{}] orderStockDTO:{}", JsonUtil.toJson(orderStockDTO));

        if (null == orderStockDTO) {
            return new SupplierResponse(ResponseCode.P_PARAM_NULL, "orderStockDTO is missing");
        }

        String bizCode = (String) context.get("bizCode");
        log.info("[{}] bizCode:{}",bizCode);
        Long sellerId = orderStockDTO.getSellerId();

        if (null == sellerId) {
            return new SupplierResponse(ResponseCode.P_PARAM_NULL, "sellerId is not null");
        }

        String orderSn = orderStockDTO.getOrderSn();

        if (Strings.isNullOrEmpty(orderSn)) {
            return new SupplierResponse(ResponseCode.P_PARAM_NULL, "orderSn is not null");
        }

        SupplierOrderStockQTO supplierOrderStockQTO = new SupplierOrderStockQTO();
        supplierOrderStockQTO.setOrderSn(orderSn);
        supplierOrderStockQTO.setStatus(SupplierOrderStockStatusEnum.LOCK.getStatus());

        List<SupplierNewOrderStockDTO> newOrderStockDTOList = supplierOrderStockManager.queryOrderSkuByOrderSn(supplierOrderStockQTO);

        if (newOrderStockDTOList.size() == 0) {
          return  new SupplierResponse(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST,"商品已售罄!");
        }

        Multimap<Long,SupplierNewOrderStockDTO>  recordSkuMap = LinkedListMultimap.create();

        for  (SupplierNewOrderStockDTO recordSkuDTO : newOrderStockDTOList) {
            recordSkuMap.put(recordSkuDTO.getItemSkuId(),recordSkuDTO);
        }

         List<Long> skuIdList = Lists.newArrayList();

        List<SupplierOrderStockDTO.OrderSku> skuList = Lists.newArrayList();

        Collections.sort(orderStockDTO.getOrderSkuList(), new Comparator<OrderStockDTO.OrderSku>() {
            @Override
            public int compare(OrderStockDTO.OrderSku o1, OrderStockDTO.OrderSku o2) {
                if (o1.getSkuId() > o2.getSkuId()) {
                 return  1;
                }else {
                    return -1;
                }

            }
        });

        for (OrderStockDTO.OrderSku orderSku : orderStockDTO.getOrderSkuList()) {
             Long skuId = orderSku.getSkuId();
             if (!recordSkuMap.keySet().contains(skuId)) {
                 OrderStockDTO failOrderStock = new OrderStockDTO();
                 failOrderStock.setOrderSn(orderSn);
                 failOrderStock.setSellerId(sellerId);
                 failOrderStock.setFailedSkuList(Lists.newArrayList(orderSku));
                 log.info("对应的库存冻结记录不存在 skuId:{}",skuId);
                 return  new SupplierResponse(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST,"商品已售罄！");

             }

            Collection<SupplierNewOrderStockDTO> records = recordSkuMap.get(skuId);


            if(records != null) {
                for (SupplierNewOrderStockDTO record : records) {
                    Long number = record.getNum();
                    skuIdList.add(skuId);

                    SupplierOrderStockDTO.OrderSku sku = new SupplierOrderStockDTO.OrderSku();
                    sku.setSkuId(skuId);
                    sku.setNumber(number.intValue());
                    sku.setStoreId(orderSku.getStoreId());
                    skuList.add(sku);
                }
            }
        }


        //冻结仓库库存,如果在这里发生异常,前面的数据库操作将回滚

        SupplierOrderStockDTO supplierOrderStockDTO = new SupplierOrderStockDTO();
        supplierOrderStockDTO.setOrderSn(orderSn);
        supplierOrderStockDTO.setSellerId(sellerId);
        supplierOrderStockDTO.setOrderSkuList(skuList);
        supplierOrderStockDTO.setBizCode(bizCode);
        supplierOrderStockManager.unlockSupplierOrderNum(supplierOrderStockDTO);

        context.put(HookEnum.BATCH_STOCK_CHANGE_HOOK.getHookName(),"");
        context.put("skuIdList",skuIdList);
        context.put("sellerId",sellerId);
        return new SupplierResponse(true);
    }

    @Override
    public String getName() {
        return ActionEnum.THAW_ORDER_SKU_STOCK.getActionName();
    }
}
