package com.mockuai.suppliercenter.core.service.action.session;

import com.mockuai.suppliercenter.common.action.ActionEnum;
import com.mockuai.suppliercenter.common.api.SupplierResponse;
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
 * get session接口
 */
@Service
public class GetSessionAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(GetSessionAction.class);

    @Resource
    private CacheManager cacheManager;

    @Override
    public SupplierResponse doTransaction(RequestContext context) throws SupplierException {
        SupplierRequest supplierResponse = context.getRequest();
        String sessionKey = (String) supplierResponse.getParam("sessionKey");
        Object obj = cacheManager.get(sessionKey);
        return new SupplierResponse(obj);
    }

    @Override
    public String getName() {
        return ActionEnum.GET_SESSION.getActionName();
    }
}
