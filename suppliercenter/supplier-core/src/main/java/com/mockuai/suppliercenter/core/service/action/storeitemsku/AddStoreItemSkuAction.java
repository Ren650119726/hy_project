package com.mockuai.suppliercenter.core.service.action.storeitemsku;

import com.mockuai.suppliercenter.common.action.ActionEnum;
import com.mockuai.suppliercenter.common.api.SupplierResponse;
import com.mockuai.suppliercenter.common.constant.ResponseCode;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
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
public class AddStoreItemSkuAction extends TransAction {

    private static final Logger log = LoggerFactory.getLogger(AddStoreItemSkuAction.class);

    @Resource
    private StoreItemSkuManager storeItemSkuManager;

    @Override
    protected SupplierResponse doTransaction(RequestContext context)
            throws SupplierException {
        SupplierRequest supplierRequest = context.getRequest();
        StoreItemSkuDTO storeItemSkuDTO = (StoreItemSkuDTO) supplierRequest.getParam("storeItemSkuDTO");

        String bizCode = (String) context.get("bizCode");

        if (bizCode == null) {
            bizCode = "hanshu";
//            log.error("bizCode is null");
//            return new SupplierResponse(ResponseCode.P_PARAM_NULL,
//                    "bizCode is null");
        }
        storeItemSkuDTO.setBizCode(bizCode);

        if (storeItemSkuDTO == null) {
            log.error("storeItemSkuDTO is null, bizCode = {}", bizCode);
            return new SupplierResponse(ResponseCode.P_PARAM_NULL, "storeItemSkuDTO is null");
        }


        try {
            storeItemSkuDTO = storeItemSkuManager.addStoreItemSku(storeItemSkuDTO);
            return new SupplierResponse(storeItemSkuDTO);
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
        return ActionEnum.ADD_STOREITEMSKU.getActionName();
    }
}
