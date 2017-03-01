package com.mockuai.tradecenter.core.filter.impl;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.filter.Filter;
import com.mockuai.tradecenter.core.manager.AppManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;


public class TestFilter implements Filter {
	private List<String> freeActions;

	public TestFilter(){
		//以下几个支付回调，直接放行
		freeActions = new ArrayList<String>();
		freeActions.add(ActionEnum.ALIPAY_CALLBACK.getActionName());
		freeActions.add(ActionEnum.UNION_PAY_CALLBACK.getActionName());
		freeActions.add(ActionEnum.UNION_PAY_CALLBACK_FOR_OLD_VERSION.getActionName());
		freeActions.add(ActionEnum.WECHAT_PAY_CALLBACK.getActionName());
	}

	@Override
	public boolean isAccept(RequestContext ctx) {
		return true;
	}

	@Override
	public TradeResponse before(RequestContext ctx) throws TradeException {
		//对于指定无需进行appKey校验的action，直接放行
		if(freeActions.contains(ctx.getRequest().getCommand())){
			return new TradeResponse(ResponseCode.RESPONSE_SUCCESS);
		}
		String appKey = (String)ctx.getRequest().getParam("appKey");
		if(StringUtils.isBlank(appKey)){
			return new TradeResponse(ResponseCode.PARAM_E_PARAM_MISSING, "appKey is null");
		}

		AppManager appManager = (AppManager)ctx.getAppContext().getBean("appManager");
		AppInfoDTO appInfoDTO = appManager.getAppInfo(appKey);
		if(appInfoDTO == null){
			return new TradeResponse(ResponseCode.BIZ_E_APP_NOT_EXIST, "appKey is invalid");
		}

		//将app所属的bizCode塞到context中
		ctx.put("bizCode", appInfoDTO.getBizCode());
		ctx.put("appKey", appInfoDTO.getAppKey());

		return new TradeResponse(ResponseCode.RESPONSE_SUCCESS);
	}

	@Override
	public TradeResponse after(RequestContext ctx) throws TradeException {
		return new TradeResponse(ResponseCode.RESPONSE_SUCCESS);
	}
}
