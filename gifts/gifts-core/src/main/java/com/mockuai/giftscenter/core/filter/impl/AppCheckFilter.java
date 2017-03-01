package com.mockuai.giftscenter.core.filter.impl;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.giftscenter.core.exception.GiftsException;
import com.mockuai.giftscenter.core.filter.Filter;
import com.mockuai.giftscenter.core.manager.AppManager;
import com.mockuai.giftscenter.core.service.RequestContext;
import com.mockuai.giftscenter.common.api.GiftsResponse;
import com.mockuai.giftscenter.common.constant.ResponseCode;

import org.apache.commons.lang.StringUtils;

public class AppCheckFilter implements Filter {

    @Override
    public boolean isAccept(RequestContext ctx) {
        return true;
    }

    @Override
    public GiftsResponse before(RequestContext ctx) throws GiftsException {
        String appKey = (String) ctx.getRequest().getParam("appKey");
        if (StringUtils.isBlank(appKey)) {
            return new GiftsResponse(ResponseCode.PARAMETER_NULL, "appKey is null");
        }

        AppManager appManager = (AppManager) ctx.getAppContext().getBean("appManager");
        AppInfoDTO appInfoDTO = appManager.getAppInfo(appKey);
        if (appInfoDTO == null) {
            return new GiftsResponse(ResponseCode.BIZ_E_APP_NOT_EXIST, "appKey is invalid");
        }

        //将app所属的bizCode塞到context中
        ctx.put("bizCode", appInfoDTO.getBizCode());
        ctx.put("appKey", appInfoDTO.getAppKey());

        return new GiftsResponse(ResponseCode.SUCCESS);
    }

    @Override
    public GiftsResponse after(RequestContext ctx) throws GiftsException {
        return new GiftsResponse(ResponseCode.SUCCESS);
    }
}