package com.mockuai.messagecenter.core.filter.impl;

import org.apache.commons.lang.StringUtils;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.messagecenter.common.api.MessageResponse;
import com.mockuai.messagecenter.common.constant.ResponseCode;
import com.mockuai.messagecenter.core.exception.MessageException;
import com.mockuai.messagecenter.core.filter.Filter;
import com.mockuai.messagecenter.core.manager.AppManager;
import com.mockuai.messagecenter.core.service.RequestContext;


public class AppCheckFilter implements Filter{

	@Override
	public boolean isAccept(RequestContext ctx) {
		return true;
	}

	@Override
	public MessageResponse before(RequestContext ctx) throws MessageException {
		String appKey = (String)ctx.getRequest().getParam("appKey");
		if(StringUtils.isBlank(appKey)){
			return new MessageResponse(ResponseCode.P_PARAM_NULL, "appKey is null");
		}

		AppManager appManager = (AppManager)ctx.getAppContext().getBean("appManager");
		AppInfoDTO appInfoDTO = appManager.getAppInfo(appKey);
		if(appInfoDTO == null){
			return new MessageResponse(ResponseCode.B_APP_NOT_EXIST, "appKey is invalid");
		}

		//将app所属的bizCode塞到context中
		ctx.put("bizCode", appInfoDTO.getBizCode());
		ctx.put("appType", appInfoDTO.getAppType());
		ctx.put("appKey", appInfoDTO.getAppKey());

		return new MessageResponse(ResponseCode.REQUEST_SUCCESS);
	}

	@Override
	public MessageResponse after(RequestContext ctx) throws MessageException {
		return new MessageResponse(ResponseCode.REQUEST_SUCCESS);
	}
}
