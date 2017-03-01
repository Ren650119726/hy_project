package com.mockuai.appcenter.client.impl;

import com.mockuai.appcenter.client.AppClient;
import com.mockuai.appcenter.common.api.AppService;
import com.mockuai.appcenter.common.api.BaseRequest;
import com.mockuai.appcenter.common.api.Request;
import com.mockuai.appcenter.common.api.Response;
import com.mockuai.appcenter.common.constant.ActionEnum;
import com.mockuai.appcenter.common.constant.AppTypeEnum;
import com.mockuai.appcenter.common.constant.ValueTypeEnum;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.AppPropertyDTO;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.appcenter.common.domain.BizPropertyDTO;

import javax.annotation.Resource;

/**
 * Created by zengzhangqiang on 6/8/15.
 */
public class AppClientImpl implements AppClient{
    @Resource
    private AppService appService;

    public Response<AppInfoDTO> addAppInfo(AppInfoDTO appInfoDTO) {
        Request request = new BaseRequest();

        request.setParam("appInfoDTO", appInfoDTO);
        request.setCommand(ActionEnum.ADD_APP_INFO.getActionName());
        Response<AppInfoDTO> response = appService.execute(request);
        return response;
    }

    public Response<BizInfoDTO> addBizInfo(BizInfoDTO bizInfoDTO) {
        Request request = new BaseRequest();

        request.setParam("bizInfoDTO", bizInfoDTO);
        request.setCommand(ActionEnum.ADD_BIZ_INFO.getActionName());
        Response<BizInfoDTO> response = appService.execute(request);
        return response;
    }

    public Response<AppInfoDTO> getAppInfo(String appKey) {
        Request request = new BaseRequest();

        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_APP_INFO.getActionName());
        Response<AppInfoDTO> response = appService.execute(request);
        return response;
    }

    public Response<AppInfoDTO> getAppInfoByDomain(String domainName, AppTypeEnum appTypeEnum) {
        Request request = new BaseRequest();

        request.setParam("domainName", domainName);
        request.setParam("appType", appTypeEnum.getValue());
        request.setCommand(ActionEnum.GET_APP_INFO_BY_DOMAIN.getActionName());
        Response<AppInfoDTO> response = appService.execute(request);
        return response;
    }

    public Response<AppInfoDTO> getAppInfoByType(String bizCode, AppTypeEnum appTypeEnum) {
        Request request = new BaseRequest();

        request.setParam("bizCode", bizCode);
        request.setParam("appType", appTypeEnum.getValue());
        request.setCommand(ActionEnum.GET_APP_INFO_BY_TYPE.getActionName());
        Response<AppInfoDTO> response = appService.execute(request);
        return response;
    }

    public Response<Void> updateAppInfo(AppInfoDTO appInfoDTO) {
        Request request = new BaseRequest();
        request.setParam("appInfoDTO", appInfoDTO);
        request.setCommand(ActionEnum.UPDATE_APP_INFO.getActionName());
        Response<Void> response = appService.execute(request);
        return response;
    }

    public Response<BizInfoDTO> getBizInfo(String bizCode) {
        Request request = new BaseRequest();

        request.setParam("bizCode", bizCode);
        request.setCommand(ActionEnum.GET_BIZ_INFO.getActionName());
        Response<BizInfoDTO> response = appService.execute(request);
        return response;
    }

    
    public Response<Void> updateBizInfo(BizInfoDTO bizInfoDTO) {
        Request request = new BaseRequest();
        request.setParam("bizInfoDTO", bizInfoDTO);
        request.setCommand(ActionEnum.UPDATE_BIZ_INFO.getActionName());
        Response<Void> response = appService.execute(request);
        return response;
    }

    
    public Response<BizInfoDTO> getBizInfoByAppKey(String appKey) {
        Request request = new BaseRequest();

        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_BIZ_INFO_BY_APP_KEY.getActionName());
        Response<BizInfoDTO> response = appService.execute(request);
        return response;
    }

    
    public Response<Void> addAppProperty(AppPropertyDTO appPropertyDTO) {
        Request request = new BaseRequest();

        request.setParam("appPropertyDTO", appPropertyDTO);
        request.setCommand(ActionEnum.ADD_APP_PROPERTY.getActionName());
        Response<Void> response = appService.execute(request);
        return response;
    }

    
    public Response<Void> addBizProperty(BizPropertyDTO bizPropertyDTO) {
        Request request = new BaseRequest();

        request.setParam("bizPropertyDTO", bizPropertyDTO);
        request.setCommand(ActionEnum.ADD_BIZ_PROPERTY.getActionName());
        Response<Void> response = appService.execute(request);
        return response;
    }

    
    public Response<Void> deleteAppProperty(String appKey, String pKey) {
        Request request = new BaseRequest();

        request.setParam("appKey", appKey);
        request.setParam("pKey", pKey);
        request.setCommand(ActionEnum.DELETE_APP_PROPERTY.getActionName());
        Response<Void> response = appService.execute(request);
        return response;
    }

    
    public Response<Void> deleteBizProperty(String bizCode, String pKey) {
        Request request = new BaseRequest();
        request.setParam("bizCode", bizCode);
        request.setParam("pKey", pKey);
        request.setCommand(ActionEnum.DELETE_BIZ_PROPERTY.getActionName());
        Response<Void> response = appService.execute(request);
        return response;
    }

    
    public Response<Void> updateBizProperty(String bizCode, String pKey, String value, ValueTypeEnum valueTypeEnum) {
        Request request = new BaseRequest();
        request.setParam("bizCode", bizCode);
        request.setParam("pKey", pKey);
        request.setParam("value", value);
        request.setParam("valueType", valueTypeEnum);
        request.setCommand(ActionEnum.UPDATE_BIZ_PROPERTY.getActionName());
        Response<Void> response = appService.execute(request);
        return response;
    }
}
