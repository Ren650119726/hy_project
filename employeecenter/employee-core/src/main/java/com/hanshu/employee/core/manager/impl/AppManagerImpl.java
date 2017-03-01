package com.hanshu.employee.core.manager.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.hanshu.employee.core.manager.AppManager;
import com.mockuai.appcenter.client.AppClient;
import com.mockuai.appcenter.common.api.Response;
import com.mockuai.appcenter.common.constant.AppTypeEnum;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.hanshu.employee.common.constant.ResponseCode;
import com.hanshu.employee.core.exception.EmployeeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by zengzhangqiang on 6/28/15.
 */
@Component("appManager")
public class AppManagerImpl implements AppManager {
    private static final Logger log = LoggerFactory.getLogger(AppManagerImpl.class);

    @Resource
    private AppClient appClient;

    @Override
    public BizInfoDTO getBizInfo(String bizCode) throws EmployeeException {
        if (StringUtils.isBlank(bizCode)) {
            throw new EmployeeException(ResponseCode.P_PARAM_NULL, "bizCode is null");
        }

        try {
            com.mockuai.appcenter.common.api.Response<BizInfoDTO> getResult =
                    appClient.getBizInfo(bizCode);
            if (getResult.getCode() != com.mockuai.appcenter.common.constant.ResponseCode.RESPONSE_SUCCESS.getCode()) {
                log.error("error to get bizInfo, bizCode:{}, errorCode:{}, errorMsg:{}",
                        bizCode, getResult.getCode(), getResult.getMessage());
                return null;
            }

            return getResult.getModule();
        } catch (Exception e) {
            log.error("bizCode:{}", bizCode, e);
            throw new EmployeeException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
        }
    }

    @Override
    public AppInfoDTO getAppInfo(String appKey) throws EmployeeException {
        if (StringUtils.isBlank(appKey)) {
            throw new EmployeeException(ResponseCode.P_PARAM_NULL, "appKey is null");
        }

        try {
            com.mockuai.appcenter.common.api.Response<AppInfoDTO> getResult =
                    appClient.getAppInfo(appKey);
            if (getResult.getCode() != com.mockuai.appcenter.common.constant.ResponseCode.RESPONSE_SUCCESS.getCode()) {
                log.error("error to get appInfo, appKey:{}, errorCode:{}, errorMsg:{}",
                        appKey, getResult.getCode(), getResult.getMessage());
                return null;
            }

            return getResult.getModule();
        } catch (Exception e) {
            log.error("appKey:{}", appKey, e);
            throw new EmployeeException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
        }
    }

    public String getAppKeyByBizCode(String bizCode) throws EmployeeException {
        if (StringUtils.isBlank(bizCode)) {
            throw new EmployeeException(ResponseCode.P_PARAM_NULL, "bizCode is null");
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
                log.error("error to get appInfo, bizCode : {}, errorCode {}, errorMsg : {}",
                        bizCode, response.getCode(), response.getMessage());
                throw new EmployeeException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
            }
        } catch (EmployeeException e) {
            throw new EmployeeException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
        }
    }
}
