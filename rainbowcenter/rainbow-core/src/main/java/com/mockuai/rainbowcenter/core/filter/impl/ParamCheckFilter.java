package com.mockuai.rainbowcenter.core.filter.impl;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.rainbowcenter.common.api.Request;
import com.mockuai.rainbowcenter.core.exception.RainbowException;
import com.mockuai.rainbowcenter.core.filter.Filter;
import com.mockuai.rainbowcenter.core.manager.AppManager;
import com.mockuai.rainbowcenter.core.service.RequestContext;

import javax.annotation.Resource;

public class ParamCheckFilter implements Filter {

	@Resource
	private AppManager appManager;

	@Override
	public boolean isAccept(RequestContext ctx) {
		return true;
	}

	@Override
	public boolean before(RequestContext ctx) throws RainbowException {
		Request request = ctx.getRequest();
		String appKey = (String) request.getParam("app_key");
		String appPwd = (String) request.getParam("secrets");
		if(appKey == null){
			return false;
		}
		if(appPwd == null){
			return false;
		}
		AppInfoDTO appInfoDTO = appManager.getAppInfo(appKey);
		if(!appInfoDTO.getAppPwd().equals(appPwd)){
			return false;
		}
		ctx.put("bizCode",appInfoDTO.getBizCode());
		ctx.put("appKey",appKey);
		return true;
	}

	@Override
	public boolean after(RequestContext ctx) throws RainbowException {
		return false;
	}
}
