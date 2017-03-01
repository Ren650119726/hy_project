package com.mockuai.suppliercenter.core.filter.impl;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.suppliercenter.common.api.SupplierResponse;
import com.mockuai.suppliercenter.common.constant.ResponseCode;
import com.mockuai.suppliercenter.core.exception.SupplierException;
import com.mockuai.suppliercenter.core.filter.Filter;
import com.mockuai.suppliercenter.core.manager.AppManager;
import com.mockuai.suppliercenter.core.service.RequestContext;
import org.apache.commons.lang.StringUtils;


public class AppCheckFilter implements Filter {

    @Override
    public boolean isAccept(RequestContext ctx) {
        return true;
    }

    @Override
    public SupplierResponse before(RequestContext ctx) throws SupplierException {
        String appKey = (String) ctx.getRequest().getParam("appKey");
        if (StringUtils.isBlank(appKey)) {
            return new SupplierResponse(ResponseCode.P_PARAM_NULL, "appKey is null");
        }

        AppManager appManager = (AppManager) ctx.getAppContext().getBean("appManager");
        AppInfoDTO appInfoDTO = appManager.getAppInfo(appKey);
        if (appInfoDTO == null) {
            return new SupplierResponse(ResponseCode.B_APP_NOT_EXIST, "appKey is invalid");
        }

        //将app所属的bizCode塞到context中
        ctx.put("bizCode", appInfoDTO.getBizCode());
        ctx.put("appType", appInfoDTO.getAppType());
        ctx.put("appKey", appInfoDTO.getAppKey());

        return new SupplierResponse(ResponseCode.REQUEST_SUCCESS);
    }

    @Override
    public SupplierResponse after(RequestContext ctx) throws SupplierException {
        return new SupplierResponse(ResponseCode.REQUEST_SUCCESS);
    }
}
