package com.mockuai.itemcenter.core.filter.impl;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.filter.Filter;
import com.mockuai.itemcenter.core.manager.AppManager;
import com.mockuai.itemcenter.core.service.RequestContext;
import org.apache.commons.lang.StringUtils;


public class AppCheckFilter implements Filter {

	@Override
	public boolean isAccept(RequestContext ctx) {
		return true;
	}

	@Override
	public ItemResponse before(RequestContext ctx) throws ItemException {

		String appKey = (String)ctx.getRequest().getParam("appKey");
		if(StringUtils.isBlank(appKey)){
			return new ItemResponse(ResponseCode.PARAM_E_MISSING, "appKey is null");
		}

		AppManager appManager = (AppManager)ctx.getAppContext().getBean("appManager");
		AppInfoDTO appInfoDTO = appManager.getAppInfo(appKey);
		if(appInfoDTO == null){
			return new ItemResponse(ResponseCode.BASE_APP_NOT_EXIST, "appKey is invalid");
		}

		//将app所属的bizCode塞到context中
		ctx.put("bizCode", appInfoDTO.getBizCode());
		ctx.put("appKey", appKey);

		return new ItemResponse(ResponseCode.SUCCESS);
	}

	@Override
	public ItemResponse after(RequestContext ctx) throws ItemException {
		return new ItemResponse(ResponseCode.SUCCESS);
	}
}
