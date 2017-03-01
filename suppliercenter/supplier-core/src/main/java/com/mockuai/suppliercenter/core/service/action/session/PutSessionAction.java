package com.mockuai.suppliercenter.core.service.action.session;

import com.mockuai.suppliercenter.common.action.ActionEnum;
import com.mockuai.suppliercenter.common.api.SupplierResponse;
import com.mockuai.suppliercenter.common.constant.ResponseCode;
import com.mockuai.suppliercenter.core.exception.SupplierException;
import com.mockuai.suppliercenter.core.manager.CacheManager;
import com.mockuai.suppliercenter.core.service.RequestContext;
import com.mockuai.suppliercenter.core.service.SupplierRequest;
import com.mockuai.suppliercenter.core.service.action.TransAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * put session接口
 */
@Service
public class PutSessionAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(PutSessionAction.class);

    @Resource
    private CacheManager cacheManager;

    @Override
    public SupplierResponse doTransaction(RequestContext context) throws SupplierException {
        SupplierRequest supplierResponse = context.getRequest();
        String sessionKey = (String) supplierResponse.getParam("sessionKey");
        Object sessionObject = supplierResponse.getParam("sessionObject");
        cacheManager.set(sessionKey, 60 * 30, sessionObject);
        return new SupplierResponse(ResponseCode.REQUEST_SUCCESS);
    }

    @Override
    public String getName() {
        return ActionEnum.PUT_SESSION.getActionName();
    }
}
