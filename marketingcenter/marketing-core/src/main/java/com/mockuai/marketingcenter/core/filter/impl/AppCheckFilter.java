package com.mockuai.marketingcenter.core.filter.impl;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.filter.Filter;
import com.mockuai.marketingcenter.core.manager.AppManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
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
    public MarketingResponse before(RequestContext ctx) throws MarketingException {
        String appKey = (String) ctx.getRequest().getParam("appKey");
        if (StringUtils.isBlank(appKey)) {
            LOGGER.error("the appKey is null");
            return new MarketingResponse(ResponseCode.PARAMETER_NULL, "appKey is null");
        }

        AppManager appManager = (AppManager) ctx.getAppContext().getBean("appManager");
        AppInfoDTO appInfoDTO = appManager.getAppInfo(appKey);
        if (appInfoDTO == null) {
            LOGGER.error("the appKey is invalid, {}", appKey);
            return new MarketingResponse(ResponseCode.BIZ_E_APP_NOT_EXIST, "appKey is invalid");
        }

        //将app所属的bizCode塞到context中
        ctx.put("bizCode", appInfoDTO.getBizCode());
        ctx.put("appKey", appInfoDTO.getAppKey());

        BizInfoDTO bizInfo = appManager.getBizInfo(appInfoDTO.getBizCode());
        ctx.put("bizInfo", bizInfo);

        return new MarketingResponse(ResponseCode.SUCCESS);
    }

    @Override
    public MarketingResponse after(RequestContext ctx) throws MarketingException {
        return new MarketingResponse(ResponseCode.SUCCESS);
    }
}