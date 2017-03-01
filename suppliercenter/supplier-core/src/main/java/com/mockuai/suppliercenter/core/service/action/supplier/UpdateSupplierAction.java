package com.mockuai.suppliercenter.core.service.action.supplier;

import com.mockuai.suppliercenter.common.action.ActionEnum;
import com.mockuai.suppliercenter.common.api.SupplierResponse;
import com.mockuai.suppliercenter.common.constant.ResponseCode;
import com.mockuai.suppliercenter.common.dto.SupplierDTO;
import com.mockuai.suppliercenter.core.exception.SupplierException;
import com.mockuai.suppliercenter.core.manager.SupplierManager;
import com.mockuai.suppliercenter.core.service.RequestContext;
import com.mockuai.suppliercenter.core.service.SupplierRequest;
import com.mockuai.suppliercenter.core.service.action.TransAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UpdateSupplierAction extends TransAction {
    private static final Logger log = LoggerFactory
            .getLogger(UpdateSupplierAction.class);
    @Resource
    private SupplierManager supplierManager;

    @Override
    public SupplierResponse doTransaction(RequestContext context)
            throws SupplierException {
        log.info("Enter Action [{}]", getName());
        SupplierRequest supplierRequest = context.getRequest();
        SupplierDTO supplierDTO = (SupplierDTO) supplierRequest.getParam("supplierDTO");

        String bizCode = (String) context.get("bizCode");

        // TODO 入参校验
        if (bizCode == null) {
            bizCode = "hanshu";
//            log.error("bizCode is null");
//            return new SupplierResponse(ResponseCode.P_PARAM_NULL,
//                    "bizCode is null");
        }
        supplierDTO.setBizCode(bizCode);

        if (supplierDTO == null) {
            log.error("supplierDTO is null when update supplier, bizCode = {}", bizCode);
            return new SupplierResponse(ResponseCode.P_PARAM_NULL,
                    "supplierDTO is null");
        }
        if (supplierDTO.getId() == null) {
            log.error("supplierDTO is null when update supplier, bizCode = {}", bizCode);
            return new SupplierResponse(ResponseCode.P_PARAM_NULL,
                    "id is null");
        }

        try {
            supplierManager.updateSupplier(supplierDTO);
            log.info("update supplier success,Exit Action [{}]", getName());
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
        return ActionEnum.UPDATE_SUPPLIER.getActionName();
    }


}
