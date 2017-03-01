package com.mockuai.suppliercenter.core.service.action.storeitemsku;

import com.mockuai.suppliercenter.common.action.ActionEnum;
import com.mockuai.suppliercenter.common.api.SupplierResponse;
import com.mockuai.suppliercenter.common.constant.ResponseCode;
import com.mockuai.suppliercenter.core.exception.SupplierException;
import com.mockuai.suppliercenter.core.manager.StoreItemSkuManager;
import com.mockuai.suppliercenter.core.service.RequestContext;
import com.mockuai.suppliercenter.core.service.SupplierRequest;
import com.mockuai.suppliercenter.core.service.action.TransAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CopySkuStockAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(CopySkuStockAction.class);

    @Resource
    private StoreItemSkuManager storeItemSkuManager;

    @Override
    protected SupplierResponse doTransaction(RequestContext context) throws SupplierException {
        log.info("Enter Action [{}]", getName());
        SupplierRequest supplierRequest = context.getRequest();

        Long itemSkuId = (Long) supplierRequest.getParam("itemSkuId");
        Long itemSkuIdNew = (Long) supplierRequest.getParam("itemSkuIdNew");
        Long stock = (Long) supplierRequest.getParam("stock");

        String bizCode = (String) context.get("bizCode");
        String appKey = (String) supplierRequest.getParam("appKey");

//	    Integer appType = (Integer)context.get("appType");

        // TODO 入参校验
        if (bizCode == null) {
            bizCode = "hanshu";
//            log.error("bizCode is null");
//            return new SupplierResponse(ResponseCode.P_PARAM_NULL,
//                    "bizCode is null");
        }


        try {
            storeItemSkuManager.copySkuStock(itemSkuId, itemSkuIdNew, stock, appKey);
            log.info("Exit Action [{}]", getName());
            return new SupplierResponse(true);
        } catch (SupplierException e) {
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
        return ActionEnum.COPY_SKUSTOCK.getActionName();
    }
}
