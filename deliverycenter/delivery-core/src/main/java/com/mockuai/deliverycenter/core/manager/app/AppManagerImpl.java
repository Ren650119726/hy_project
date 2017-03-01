package com.mockuai.deliverycenter.core.manager.app;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mockuai.appcenter.client.AppClient;
import com.mockuai.appcenter.common.api.Response;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.deliverycenter.core.exception.DeliveryException;
import com.mockuai.deliverycenter.core.manager.AppManager;

/**
 * Created by zengzhangqiang on 6/28/15.
 */
@Component("appManager")
public class AppManagerImpl implements AppManager {
    private static final Logger log = LoggerFactory.getLogger(AppManagerImpl.class);

    @Resource
    private AppClient appClient;

    @Override
    public BizInfoDTO getBizInfo(String bizCode) throws DeliveryException {
        if(StringUtils.isBlank(bizCode)){
            throw new DeliveryException("bizCode is null");
        }

        try{
            Response<BizInfoDTO> getResult =
                    appClient.getBizInfo(bizCode);
            if(getResult.getCode() != com.mockuai.appcenter.common.constant.ResponseCode.RESPONSE_SUCCESS.getCode()){
                log.error("error to get bizInfo, bizCode:{}, errorCode:{}, errorMsg:{}",
                        bizCode, getResult.getCode(), getResult.getMessage());
                return null;
            }

            return getResult.getModule();
        }catch(Exception e){
            log.error("bizCode:{}", bizCode, e);
            throw new DeliveryException(e.getMessage());
        }
    }

    @Override
    public AppInfoDTO getAppInfo(String appKey) throws DeliveryException {
        if(StringUtils.isBlank(appKey)){
            throw new DeliveryException("appKey is null");
        }

        try{
            Response<AppInfoDTO> getResult =
                    appClient.getAppInfo(appKey);
            if(getResult.getCode() != com.mockuai.appcenter.common.constant.ResponseCode.RESPONSE_SUCCESS.getCode()){
                log.error("error to get appInfo, appKey:{}, errorCode:{}, errorMsg:{}",
                        appKey, getResult.getCode(), getResult.getMessage());
                return null;
            }

            return getResult.getModule();
        }catch(Exception e){
            log.error("appKey:{}", appKey, e);
            throw new DeliveryException(e.getMessage());
        }
    }
}
