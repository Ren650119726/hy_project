package com.mockuai.distributioncenter.core.filter.impl;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.core.manager.AppManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.filter.Filter;
import org.apache.commons.lang.StringUtils;

public class AppCheckFilter implements Filter {

    public boolean isAccept(RequestContext ctx) {
        return true;
    }

    public DistributionResponse before(RequestContext ctx) throws DistributionException {
        String appKey = (String) ctx.getRequest().getParam("appKey");
        if (StringUtils.isBlank(appKey)) {
            return new DistributionResponse(ResponseCode.PARAMETER_NULL, "appKey is null");
        }

        AppManager appManager = (AppManager) ctx.getAppContext().getBean("appManagerImpl");
        AppInfoDTO appInfoDTO = appManager.getAppInfo(appKey);
        if (appInfoDTO == null) {
            return new DistributionResponse(ResponseCode.BIZ_E_APP_NOT_EXIST, "appKey is invalid");
        }

        //将app所属的bizCode塞到context中
        ctx.put("bizCode", appInfoDTO.getBizCode());
        ctx.put("appKey", appInfoDTO.getAppKey());
        ctx.put("appType", appInfoDTO.getAppType());
        return new DistributionResponse(ResponseCode.SUCCESS);
    }

    public DistributionResponse after(RequestContext ctx) throws DistributionException {
        return new DistributionResponse(ResponseCode.SUCCESS);
    }
}