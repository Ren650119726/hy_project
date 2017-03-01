package com.mockuai.headsinglecenter.core.filter.impl;

import org.apache.commons.lang.StringUtils;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.headsinglecenter.common.api.HeadSingleResponse;
import com.mockuai.headsinglecenter.common.constant.ResponseCode;
import com.mockuai.headsinglecenter.core.exception.HeadSingleException;
import com.mockuai.headsinglecenter.core.filter.Filter;
import com.mockuai.headsinglecenter.core.manager.AppManager;
import com.mockuai.headsinglecenter.core.service.RequestContext;

public class AppCheckFilter implements Filter {

    @SuppressWarnings("rawtypes")
	@Override
    public boolean isAccept(RequestContext ctx) {
        return true;
    }

    @SuppressWarnings("rawtypes")
	@Override
    public HeadSingleResponse before(RequestContext ctx) throws HeadSingleException {
        String appKey = (String) ctx.getRequest().getParam("appKey");
        if (StringUtils.isBlank(appKey)) {
            return new HeadSingleResponse(ResponseCode.PARAMETER_NULL, "appKey is null");
        }

        AppManager appManager = (AppManager) ctx.getAppContext().getBean("appManager");
        AppInfoDTO appInfoDTO = appManager.getAppInfo(appKey);
        if (appInfoDTO == null) {
            return new HeadSingleResponse(ResponseCode.BIZ_E_APP_NOT_EXIST, "appKey is invalid");
        }

        //将app所属的bizCode塞到context中
        ctx.put("bizCode", appInfoDTO.getBizCode());
        ctx.put("appKey", appInfoDTO.getAppKey());

        return new HeadSingleResponse(ResponseCode.SUCCESS);
    }

    @SuppressWarnings("rawtypes")
	@Override
    public HeadSingleResponse after(RequestContext ctx) throws HeadSingleException {
        return new HeadSingleResponse(ResponseCode.SUCCESS);
    }
}