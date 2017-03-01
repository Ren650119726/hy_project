package com.mockuai.suppliercenter.core.service.action.store;

import com.mockuai.suppliercenter.common.action.ActionEnum;
import com.mockuai.suppliercenter.common.api.SupplierResponse;
import com.mockuai.suppliercenter.common.constant.ResponseCode;
import com.mockuai.suppliercenter.common.dto.StoreDTO;
import com.mockuai.suppliercenter.core.exception.SupplierException;
import com.mockuai.suppliercenter.core.manager.StoreManager;
import com.mockuai.suppliercenter.core.service.RequestContext;
import com.mockuai.suppliercenter.core.service.SupplierRequest;
import com.mockuai.suppliercenter.core.service.action.TransAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AddStoreAction extends TransAction {
    private static final Logger log = LoggerFactory
            .getLogger(AddStoreAction.class);

    @Resource
    private StoreManager storeManager;

    @Override
    protected SupplierResponse doTransaction(RequestContext context)
            throws SupplierException {

        log.info("Enter Action [{}]", getName());
        SupplierRequest supplierRequest = context.getRequest();
        StoreDTO storeDTO = (StoreDTO) supplierRequest.getParam("storeDTO");

        String bizCode = (String) context.get("bizCode");

        // TODO 入参校验
        if (bizCode == null) {
            bizCode = "hanshu";
//            log.error("bizCode is null");
//            return new SupplierResponse(ResponseCode.P_PARAM_NULL,
//                    "bizCode is null");
        }
        storeDTO.setBizCode(bizCode);

        if (storeDTO == null) {
            log.error("storeDTO is null, bizCode = {}", bizCode);
            return new SupplierResponse(ResponseCode.P_PARAM_NULL, "storeDTO is null");
        }


        try {
            storeDTO = storeManager.addStore(storeDTO);
            log.info("Exit Action [{}]", getName());
            return new SupplierResponse(storeDTO);
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
        return ActionEnum.ADD_STORE.getActionName();
    }
}
