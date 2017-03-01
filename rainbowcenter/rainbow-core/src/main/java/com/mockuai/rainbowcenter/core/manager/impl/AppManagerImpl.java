package com.mockuai.rainbowcenter.core.manager.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mockuai.appcenter.client.AppClient;
import com.mockuai.appcenter.common.api.Response;
import com.mockuai.appcenter.common.constant.AppTypeEnum;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.rainbowcenter.common.constant.ResponseCode;
import com.mockuai.rainbowcenter.core.exception.RainbowException;
import com.mockuai.rainbowcenter.core.manager.AppManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by duke on 15/12/11.
 */
public class AppManagerImpl implements AppManager {
    private static final Logger log = LoggerFactory.getLogger(AppManagerImpl.class);

    @Resource
    private AppClient appClient;

    public BizInfoDTO getBizInfo(String bizCode) throws RainbowException {
        if (StringUtils.isBlank(bizCode)) {
            log.info("bizCode is null");
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL, "bizCode is null");
        }

        try {
            Response<BizInfoDTO> getResult = appClient.getBizInfo(bizCode);
            if (getResult.getCode() != com.mockuai.appcenter.common.constant.ResponseCode.RESPONSE_SUCCESS.getCode()) {
                log.error("error to get bizInfo, bizCode:{}, errorCode:{}, errorMsg:{}",
                        bizCode, getResult.getCode(), getResult.getMessage());
                return null;
            }

            return getResult.getModule();
        } catch (Exception e) {
            log.error("bizCode:{}", bizCode, e);
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL);
        }
    }

    public AppInfoDTO getAppInfo(String appKey) throws RainbowException {
        if (StringUtils.isBlank(appKey)) {
            log.error("appKey is null");
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL, "appKey is null");
        }

        try {
            Response<AppInfoDTO> getResult =
                    appClient.getAppInfo(appKey);
            if (getResult.getCode() != com.mockuai.appcenter.common.constant.ResponseCode.RESPONSE_SUCCESS.getCode()) {
                log.error("error to get appInfo, appKey:{}, errorCode:{}, errorMsg:{}",
                        appKey, getResult.getCode(), getResult.getMessage());
                return null;
            }

            return getResult.getModule();
        } catch (Exception e) {
            log.error("appKey:{}", appKey, e);
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL);
        }
    }

    @Override
    public String getAppKeyByBizCode(String bizCode) throws RainbowException {
        if (StringUtils.isBlank(bizCode)) {
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL, "bizCode is null");
        }
        try {
            Response<AppInfoDTO> response = appClient.getAppInfoByType(bizCode, AppTypeEnum.APP_RAINBOW);
            if (response.isSuccess()) {
                if (response.getModule() != null) {
                    return response.getModule().getAppKey();
                } else {
                    return null;
                }
            } else {
                log.error("error to get appInfo, bizCode : {}, errorCode {}, errorMsg : {}",
                        bizCode, response.getCode(), response.getMessage());
                throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL);
            }
        } catch (RainbowException e) {
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL);
        }
    }
}
