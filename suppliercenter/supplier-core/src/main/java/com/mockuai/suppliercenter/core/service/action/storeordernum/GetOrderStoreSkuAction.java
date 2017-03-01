package com.mockuai.suppliercenter.core.service.action.storeordernum;

import com.mockuai.suppliercenter.common.action.ActionEnum;
import com.mockuai.suppliercenter.common.api.SupplierResponse;
import com.mockuai.suppliercenter.common.constant.ResponseCode;
import com.mockuai.suppliercenter.common.dto.SupplierOrderStockDTO;
import com.mockuai.suppliercenter.core.exception.SupplierException;
import com.mockuai.suppliercenter.core.manager.SupplierOrderStockManager;
import com.mockuai.suppliercenter.core.service.RequestContext;
import com.mockuai.suppliercenter.core.service.SupplierRequest;
import com.mockuai.suppliercenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 查询供应商接口
 */
@Service
public class GetOrderStoreSkuAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(GetOrderStoreSkuAction.class);

    @Resource
    private SupplierOrderStockManager supplierOrderStockManager;

    @Override
    public SupplierResponse execute(RequestContext context)
            throws SupplierException {

        SupplierRequest supplierRequest = context.getRequest();
        String orderSn = (String) supplierRequest.getParam("orderSn");
        String appKey = (String) supplierRequest.getParam("appKey");


        String bizCode = (String) context.get("bizCode");

        // TODO 入参校验
        if (bizCode == null) {
            log.error("bizCode is null");
            return new SupplierResponse(ResponseCode.P_PARAM_NULL,
                    "bizCode is null");
        }

        if (orderSn == null) {
            log.error("get OrderStoreSku info orderSn is null ");
        }


        try {
            SupplierOrderStockDTO supplierOrderStockDTO = supplierOrderStockManager
                    .getOrderStoreSkuByOrderSn(orderSn, bizCode);
            log.info("Exit Action [{}]", getName());
            return new SupplierResponse(supplierOrderStockDTO);

        } catch (SupplierException e) {
            // TODO Auto-generated catch block
            log.error("do action:" + supplierRequest.getCommand()
                    + " occur Exception:" + e.getMessage(), e);
            return new SupplierResponse(e.getResponseCode(), e.getMessage());
        } catch (Exception e) {
            log.error("do action:" + supplierRequest.getCommand()
                    + " occur Exception:" + e.getMessage(), e);
            return new SupplierResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION,
                    e.getMessage());

        }


    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return ActionEnum.GET_ORDERSTORESKU.getActionName();
    }

}
