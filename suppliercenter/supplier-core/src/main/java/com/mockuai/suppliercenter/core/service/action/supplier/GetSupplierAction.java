package com.mockuai.suppliercenter.core.service.action.supplier;

import com.mockuai.suppliercenter.common.action.ActionEnum;
import com.mockuai.suppliercenter.common.api.SupplierResponse;
import com.mockuai.suppliercenter.common.constant.ResponseCode;
import com.mockuai.suppliercenter.common.dto.SupplierDTO;
import com.mockuai.suppliercenter.core.exception.SupplierException;
import com.mockuai.suppliercenter.core.manager.SupplierManager;
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
public class GetSupplierAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(AddSupplierAction.class);

    @Resource
    private SupplierManager supplierManager;

    @Override
    public SupplierResponse execute(RequestContext context)
            throws SupplierException {

        SupplierRequest supplierRequest = context.getRequest();
        Long id = (Long) supplierRequest.getParam("id");
        String appKey = (String) supplierRequest.getParam("appKey");


        String bizCode = (String) context.get("bizCode");

        if (bizCode == null) {
            bizCode = "hanshu";
//            log.error("bizCode is null");
//            return new SupplierResponse(ResponseCode.P_PARAM_NULL,
//                    "bizCode is null");
        }

        if (id == null) {
            log.error("supplier info qto is null when query supplier by if");
        }

        try {
            SupplierDTO suppliers = supplierManager.getSupplierById(id);
            log.info("Exit Action [{}]", getName());
            return new SupplierResponse(suppliers);

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
        return ActionEnum.GET_SUPPLIER.getActionName();
    }

}
