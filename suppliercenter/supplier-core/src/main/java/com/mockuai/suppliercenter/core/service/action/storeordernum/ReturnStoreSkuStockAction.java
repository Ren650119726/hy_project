package com.mockuai.suppliercenter.core.service.action.storeordernum;

import com.mockuai.suppliercenter.common.action.ActionEnum;
import com.mockuai.suppliercenter.common.api.SupplierResponse;
import com.mockuai.suppliercenter.common.constant.ResponseCode;
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

@Service
public class ReturnStoreSkuStockAction extends TransAction {
    private static final Logger log = LoggerFactory
            .getLogger(ReturnStoreSkuStockAction.class);

    @Resource
    private SupplierOrderStockManager supplierOrderStockManager;

    @Override
    protected SupplierResponse doTransaction(RequestContext context)
            throws SupplierException {


        log.info("Enter Action [{}]", getName());
        SupplierRequest supplierRequest = context.getRequest();
        String appKey = (String) supplierRequest.getParam("appKey");
        SupplierOrderStockDTO orderStockDTO = (SupplierOrderStockDTO) supplierRequest
                .getParam("orderStockDTO");

        String bizCode = (String) context.get("bizCode");

        // TODO 入参校验
        if (bizCode == null) {
            log.error("bizCode is null");
            return new SupplierResponse(ResponseCode.P_PARAM_NULL,
                    "bizCode is null");
        }
        if (orderStockDTO == null) {
            log.error("orderStockDTO is null, bizCode = {}", bizCode);
            return new SupplierResponse(ResponseCode.P_PARAM_NULL,
                    " orderStockDTO is null");
        }

        orderStockDTO.setBizCode(bizCode);

        try {
            supplierOrderStockManager.returnStoreSkuStock(orderStockDTO, appKey);
            log.info(" ReturnStoreSkuStock  success, Exit Action [{}]", getName());
            return new SupplierResponse(true);
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
        return ActionEnum.RETURN_STORESKUSTOCK.getActionName();
    }
}
