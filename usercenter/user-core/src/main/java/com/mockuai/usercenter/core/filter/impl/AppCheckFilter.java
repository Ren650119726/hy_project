package com.mockuai.usercenter.core.filter.impl;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.filter.Filter;
import com.mockuai.usercenter.core.manager.AppManager;
import com.mockuai.usercenter.core.service.RequestContext;
import org.apache.commons.lang.StringUtils;


public class AppCheckFilter implements Filter{

	@Override
	public boolean isAccept(RequestContext ctx) {
		return true;
	}

	@Override
	public UserResponse before(RequestContext ctx) throws UserException {
		String appKey = (String)ctx.getRequest().getParam("appKey");
		if(StringUtils.isBlank(appKey)){
			return new UserResponse(ResponseCode.P_PARAM_NULL, "appKey is null");
		}

		AppManager appManager = (AppManager)ctx.getAppContext().getBean("appManager");
		AppInfoDTO appInfoDTO = appManager.getAppInfo(appKey);
		if(appInfoDTO == null){
			return new UserResponse(ResponseCode.B_APP_NOT_EXIST, "appKey is invalid");
		}

		//将app所属的bizCode塞到context中
		ctx.put("bizCode", appInfoDTO.getBizCode());
		ctx.put("appType", appInfoDTO.getAppType());
		ctx.put("appKey", appInfoDTO.getAppKey());

		return new UserResponse(ResponseCode.REQUEST_SUCCESS);
	}

	@Override
	public UserResponse after(RequestContext ctx) throws UserException {
		return new UserResponse(ResponseCode.REQUEST_SUCCESS);
	}
}
