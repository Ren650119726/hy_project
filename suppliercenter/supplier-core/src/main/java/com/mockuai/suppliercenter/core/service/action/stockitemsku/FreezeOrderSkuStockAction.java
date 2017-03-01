package com.mockuai.suppliercenter.core.service.action.stockitemsku;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.mockuai.suppliercenter.common.action.ActionEnum;
import com.mockuai.suppliercenter.common.api.SupplierResponse;
import com.mockuai.suppliercenter.common.constant.ResponseCode;
import com.mockuai.suppliercenter.common.dto.OrderStockDTO;
import com.mockuai.suppliercenter.common.dto.SupplierOrderStockDTO;
import com.mockuai.suppliercenter.core.exception.SupplierException;
import com.mockuai.suppliercenter.core.manager.SupplierOrderStockManager;
import com.mockuai.suppliercenter.core.service.RequestContext;
import com.mockuai.suppliercenter.core.service.SupplierRequest;
import com.mockuai.suppliercenter.core.service.action.TransAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lizg on 2016/9/23.
 * 以订单为单位锁定sku库存
 */

@Service
public class FreezeOrderSkuStockAction extends TransAction {

    private static final Logger log = LoggerFactory.getLogger(FreezeOrderSkuStockAction.class);

    @Resource
    private SupplierOrderStockManager supplierOrderStockManager;

    @Override
    protected SupplierResponse doTransaction(RequestContext context) throws SupplierException {
        SupplierRequest supplierRequest = context.getRequest();

        OrderStockDTO orderStockDTO = (OrderStockDTO)supplierRequest.getParam("orderStockDTO");
        log.info("[{}] orderStockDTO:{}",orderStockDTO);
        if (null == orderStockDTO) {
            return new SupplierResponse(ResponseCode.P_PARAM_NULL, "orderStockDTO is missing");
        }

        log.info("冻结库存 freezeOrderSkuStock orderStockDTO:{} ", JSON.toJSON(orderStockDTO));

        String bizCode = (String) context.get("bizCode");
        log.info("[{}] bizCode :{}",bizCode);
        try {
            orderStockDTO = supplierOrderStockManager.lockSkuOrderNum(orderStockDTO,bizCode);

            return new SupplierResponse(orderStockDTO);
        }catch (SupplierException e) {
            log.error("do action:" + supplierRequest.getCommand()+"occur Exception:"+e.getMessage(), e);
            return new SupplierResponse(e.getResponseCode(), e.getMessage());

        }
    }

    @Override
    public String getName() {
        return ActionEnum.FREEZE_ORDER_SKU_STOCK.getActionName();
    }
}
