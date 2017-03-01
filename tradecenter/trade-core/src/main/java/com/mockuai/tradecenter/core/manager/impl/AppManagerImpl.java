package com.mockuai.tradecenter.core.manager.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mockuai.appcenter.client.AppClient;
import com.mockuai.appcenter.common.api.Response;
import com.mockuai.appcenter.common.constant.AppTypeEnum;
import com.mockuai.appcenter.common.constant.BizPropertyKey;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.AppManager;
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
    public BizInfoDTO getBizInfo(String bizCode) throws TradeException {
        if(StringUtils.isBlank(bizCode)){
            throw new TradeException(ResponseCode.PARAM_E_PARAM_MISSING, "bizCode is null");
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
            throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
        }
    }

    @Override
    public AppInfoDTO getAppInfo(String appKey) throws TradeException {
        if(StringUtils.isBlank(appKey)){
            throw new TradeException(ResponseCode.PARAM_E_PARAM_MISSING, "appKey is null");
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
            throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
        }
    }

	@Override
	public AppInfoDTO getAppInfoByBizCode(String bizCode,int type) throws TradeException {
		if(StringUtils.isBlank(bizCode)){
            throw new TradeException(ResponseCode.PARAM_E_PARAM_MISSING, "bizCode is null");
        }
		 try{
			 AppTypeEnum appTypeEnum = null;
			 for (AppTypeEnum institution : AppTypeEnum.values()) {
		            if(institution.getValue()==type){
		            	appTypeEnum =  institution;
		            }
		        }
			 
	            Response<AppInfoDTO> getResult =
	                    appClient.getAppInfoByType(bizCode,appTypeEnum);
	            if(getResult.getCode() != com.mockuai.appcenter.common.constant.ResponseCode.RESPONSE_SUCCESS.getCode()){
	                log.error("error to get appInfo, bizCode:{}, errorCode:{}, errorMsg:{}",
	                		bizCode, getResult.getCode(), getResult.getMessage());
	                return null;
	            }

	            return getResult.getModule();
	        }catch(Exception e){
	            log.error("bizCode:{}", bizCode, e);
	            throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
	        }
		
	}

	@Override
	public Boolean getPayByMockuai(String bizCode) throws TradeException {
		if(StringUtils.isBlank(bizCode)){
            throw new TradeException(ResponseCode.PARAM_E_PARAM_MISSING, "bizCode is null");
        }
		Boolean result = false;
		try{
			
			 Response<BizInfoDTO> getResult =   appClient.getBizInfo(bizCode);
	            if(getResult.getCode() != com.mockuai.appcenter.common.constant.ResponseCode.RESPONSE_SUCCESS.getCode()){
	                log.error("error to get bizInfo, bizCode:{}, errorCode:{}, errorMsg:{}",
	                        bizCode, getResult.getCode(), getResult.getMessage());
	                return false;
	            }
	            BizInfoDTO bizInfo = getResult.getModule();
	            if(null!=bizInfo){
	            	 BizPropertyDTO isPayByMockuai = bizInfo.getBizPropertyMap()
	 						.get(BizPropertyKey.IS_PAY_BY_MOCKUAI);
	 				if(null!=isPayByMockuai){
	 					if(isPayByMockuai.getValue().equals("1")){// 代表走魔筷的帐
	 						result =  true;
	 					}
	 				}
	            }
	            return result;
	            
		}catch(Exception e){
            log.error("bizCode:{}", bizCode, e);
            throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
        }
	}

}
