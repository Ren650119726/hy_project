package com.mockuai.deliverycenter.core.filter.impl;

import org.apache.commons.lang.StringUtils;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.deliverycenter.core.exception.DeliveryException;
import com.mockuai.deliverycenter.core.filter.Filter;
import com.mockuai.deliverycenter.core.manager.AppManager;
import com.mockuai.deliverycenter.core.service.RequestContext;


public class AppCheckFilter implements Filter{

	@Override
	public boolean isAccept(RequestContext ctx) {
		return true;
	}

	@Override
	public boolean before(RequestContext ctx) throws DeliveryException {
		String appKey = (String)ctx.getRequest().getParam("appKey");
		if(StringUtils.isBlank(appKey)){
			throw new DeliveryException("appKey is null");
		}

		AppManager appManager = (AppManager)ctx.getAppContext().getBean("appManager");
		AppInfoDTO appInfoDTO = appManager.getAppInfo(appKey);
		if(appInfoDTO == null){
			throw new DeliveryException("appKey is invalid");
		}

		//将app所属的bizCode塞到context中
		ctx.put("bizCode", appInfoDTO.getBizCode());
		ctx.put("appType", appInfoDTO.getAppType());
		ctx.put("appKey", appInfoDTO.getAppKey());
		return true;
	}

	@Override
	public boolean after(RequestContext ctx) throws DeliveryException {
		return true;
	}
}
