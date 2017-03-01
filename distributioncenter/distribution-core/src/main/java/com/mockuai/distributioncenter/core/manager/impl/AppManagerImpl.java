package com.mockuai.distributioncenter.core.manager.impl;

import com.mockuai.appcenter.client.AppClient;
import com.mockuai.appcenter.common.api.Response;
import com.mockuai.appcenter.common.constant.AppTypeEnum;
import com.mockuai.appcenter.common.constant.ValueTypeEnum;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.AppManager;
import com.mockuai.distributioncenter.core.util.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by duke on 15/10/28.
 */
@Component
public class AppManagerImpl implements AppManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppManagerImpl.class);

    @Autowired
    private AppClient appClient;

    public BizInfoDTO getBizInfo(String bizCode) throws DistributionException {

        if (StringUtils.isBlank(bizCode)) {
            throw new DistributionException(ResponseCode.PARAMETER_NULL.getCode(), "bizCode is null");
        }

        try {
            Response<BizInfoDTO> getResult = appClient.getBizInfo(bizCode);
            if (getResult.getCode() != com.mockuai.appcenter.common.constant.ResponseCode.RESPONSE_SUCCESS.getCode()) {
                LOGGER.error("error to get bizInfo, bizCode:{}, errorCode:{}, errorMsg:{}",
                        bizCode, getResult.getCode(), getResult.getMessage());
                return null;
            }

            return getResult.getModule();
        } catch (Exception e) {
            LOGGER.error("failed when getting bizCode : {}", bizCode, e);
            throw new DistributionException(ResponseCode.SERVICE_EXCEPTION);
        }
    }

    public AppInfoDTO getAppInfo(String appKey) throws DistributionException {

        if (StringUtils.isBlank(appKey)) {
            throw new DistributionException(ResponseCode.PARAMETER_NULL.getCode(), "appKey is null");
        }

        try {
            Response<AppInfoDTO> getResult = appClient.getAppInfo(appKey);
            if (getResult.getCode() != com.mockuai.appcenter.common.constant.ResponseCode.RESPONSE_SUCCESS.getCode()) {
                LOGGER.error("error to get appInfo, appKey:{}, errorCode:{}, errorMsg:{}",
                        appKey, getResult.getCode(), getResult.getMessage());
                return null;
            }

            return getResult.getModule();
        } catch (Exception e) {
            LOGGER.error("failed when getting appInfo, appKey:{}", appKey, e);
            throw new DistributionException(ResponseCode.SERVICE_EXCEPTION);
        }
    }

    public String getAppKeyByBizCode(String bizCode) throws DistributionException {
        if (StringUtils.isBlank(bizCode)) {
            throw new DistributionException(ResponseCode.PARAMETER_NULL, "bizCode is null");
        }
        try {
            Response<AppInfoDTO> response = appClient.getAppInfoByType(bizCode, AppTypeEnum.APP_WAP);
            if (response.isSuccess()) {
                if (response.getModule() != null) {
                    return response.getModule().getAppKey();
                } else {
                    return null;
                }
            } else {
                LOGGER.error("error to get appInfo, bizCode : {}, errorCode {}, errorMsg : {}",
                        bizCode, response.getCode(), response.getMessage());
                throw new DistributionException(ResponseCode.SERVICE_EXCEPTION);
            }
        } catch (DistributionException e) {
            throw new DistributionException(ResponseCode.SERVICE_EXCEPTION);
        }
    }

    @Override
    public void addBizProperty(BizPropertyDTO bizPropertyDTO) throws DistributionException {
        Response<Void> response = appClient.addBizProperty(bizPropertyDTO);
        if (!response.isSuccess()) {
            LOGGER.error("error to add biz property, errMsg: {} bizProperty: {}",
                    response.getMessage(), JsonUtil.toJson(bizPropertyDTO));
            throw new DistributionException(ResponseCode.SERVICE_EXCEPTION);
        }
    }

    @Override
    public void updateBizProperty(String bizCode, String pKey, String value, ValueTypeEnum valueTypeEnum)
            throws DistributionException {
        Response<Void> response = appClient.updateBizProperty(bizCode, pKey, value, valueTypeEnum);
        if (!response.isSuccess()) {
            LOGGER.error("update biz property error, errMsg: {}, bizCode: {}, pKey: {}, value: {}, valueType: {}",
                    response.getMessage(), bizCode, pKey, value, valueTypeEnum);
            throw new DistributionException(ResponseCode.SERVICE_EXCEPTION);
        }
    }
}
