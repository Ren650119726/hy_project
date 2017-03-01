package com.mockuai.suppliercenter.core.service.action.store;

import com.mockuai.suppliercenter.common.action.ActionEnum;
import com.mockuai.suppliercenter.common.api.SupplierResponse;
import com.mockuai.suppliercenter.common.constant.ResponseCode;
import com.mockuai.suppliercenter.common.dto.StoreDTO;
import com.mockuai.suppliercenter.common.qto.StoreQTO;
import com.mockuai.suppliercenter.core.exception.SupplierException;
import com.mockuai.suppliercenter.core.manager.StoreManager;
import com.mockuai.suppliercenter.core.service.RequestContext;
import com.mockuai.suppliercenter.core.service.SupplierRequest;
import com.mockuai.suppliercenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QueryStoreAction implements Action {
    private static final Logger log = LoggerFactory
            .getLogger(QueryStoreForOrderAction.class);

    @Resource
    private StoreManager storeManager;

    @Override
    public SupplierResponse execute(RequestContext context)
            throws SupplierException {
        log.info("Enter Action [{}]", getName());

        SupplierRequest supplierRequest = context.getRequest();
        StoreQTO storeQTO = (StoreQTO) supplierRequest.getParam("storeQTO");
        String appKey = (String) supplierRequest.getParam("appKey");

        String bizCode = (String) context.get("bizCode");

        // TODO 入参校验
        if (bizCode == null) {
            bizCode = "hanshu";
//            log.error("bizCode is null");
//            return new SupplierResponse(ResponseCode.P_PARAM_NULL,
//                    "bizCode is null");
        }
        storeQTO.setBizCode(bizCode);
        if (storeQTO == null) {
            log.error("store info qto is null when query store by storeQto");
        }
        try {
            List<StoreDTO> stores = storeManager.queryStore(storeQTO, appKey);
            Long total = storeManager.getTotalCount(storeQTO);

            return new SupplierResponse(stores, total);
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
        return ActionEnum.QUERY_STORE.getActionName();
    }
}
