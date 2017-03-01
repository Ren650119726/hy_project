package com.mockuai.virtualwealthcenter.core.filter.impl;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.filter.Filter;
import com.mockuai.virtualwealthcenter.core.manager.AppManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppCheckFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppCheckFilter.class);

    @Override
    public boolean isAccept(RequestContext ctx) {
        return true;
    }

    @Override
    public VirtualWealthResponse before(RequestContext ctx) throws VirtualWealthException {
        String appKey = (String) ctx.getRequest().getParam("appKey");
        if (StringUtils.isBlank(appKey)) {
            LOGGER.error("the appKey is null");
            return new VirtualWealthResponse(ResponseCode.PARAMETER_NULL, "appKey is null");
        }

        AppManager appManager = (AppManager) ctx.getAppContext().getBean("appManager");
        AppInfoDTO appInfoDTO = appManager.getAppInfo(appKey);
        if (appInfoDTO == null) {
            LOGGER.error("the appKey is invalid, {}", appKey);
            return new VirtualWealthResponse(ResponseCode.BIZ_E_APP_NOT_EXIST, "appKey is invalid");
        }

        BizInfoDTO bizInfo = appManager.getBizInfo(appInfoDTO.getBizCode());
        ctx.put("appInfo", appInfoDTO);
        ctx.put("bizInfo", bizInfo);

        return new VirtualWealthResponse(ResponseCode.SUCCESS);
    }

    @Override
    public VirtualWealthResponse after(RequestContext ctx) throws VirtualWealthException {
        return new VirtualWealthResponse(ResponseCode.SUCCESS);
    }
}