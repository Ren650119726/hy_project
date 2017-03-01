package com.mockuai.tradecenter.core.filter.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.mockuai.appcenter.common.constant.BizPropertyKey;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.core.config.MockuaiConfig;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.filter.Filter;
import com.mockuai.tradecenter.core.manager.AppManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.unionpay.acp.sdk.SDKConfig;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;


public class AppCheckFilter implements Filter {
	private List<String> freeActions;

	public AppCheckFilter(){
		//以下几个支付回调，直接放行
		freeActions = new ArrayList<String>();
		freeActions.add(ActionEnum.ALIPAY_CALLBACK.getActionName());
		freeActions.add(ActionEnum.UNION_PAY_CALLBACK.getActionName());
		freeActions.add(ActionEnum.UNION_PAY_CALLBACK_FOR_OLD_VERSION.getActionName());
		freeActions.add(ActionEnum.WECHAT_PAY_CALLBACK.getActionName());
		freeActions.add(ActionEnum.PROCESS_WITHDRAW.getActionName());
		freeActions.add(ActionEnum.QUERY_WITHDRAW.getActionName());
		freeActions.add(ActionEnum.NOTIFY_WITHDRAW_RESULT.getActionName());
		freeActions.add(ActionEnum.SUM_PAY_CALLBACK.getActionName());
		freeActions.add(ActionEnum.SUMPAY_REFUND_CALLBACK.getActionName());
		// 新增连连支付
		freeActions.add(ActionEnum.LIANLIAN_PAY_CALLBACK.getActionName());
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
		appInfoDTO.getAppPropertyMap();
		//TODO 根据bizCode 查找 domain  先写死。。。。。
		ctx.put("appDomain",MockuaiConfig.DEFAULT_DOMAIN);

		//将app所属的bizCode塞到context中
		ctx.put("bizCode", appInfoDTO.getBizCode());
		ctx.put("appKey", appInfoDTO.getAppKey());
		ctx.put("appType", appInfoDTO.getAppType());
		
		//getBizInfo
		BizInfoDTO bizInfo = appManager.getBizInfo(appInfoDTO.getBizCode());
		if(null!=bizInfo&&null!=bizInfo.getBizPropertyMap()){
			ctx.put("bizInfo", bizInfo);
			BizPropertyDTO isPayByMockuai = bizInfo.getBizPropertyMap()
					.get(BizPropertyKey.IS_PAY_BY_MOCKUAI);
			ctx.put("bizPropertyMap", bizInfo.getBizPropertyMap());
			if(null!=isPayByMockuai&&isPayByMockuai.getValue().equals("1")){
					BizInfoDTO mockuaiBizInfo = appManager.getBizInfo("mockuai_demo");
					
					Map<String,BizPropertyDTO> bizPropertyMap = mockuaiBizInfo.getBizPropertyMap();
					
					bizPropertyMap.put(com.mockuai.tradecenter.core.util.BizPropertyKey.ALIPAY_RETURN_URL, 
							bizInfo.getBizPropertyMap().get(BizPropertyKey.ALIPAY_RETURN_URL));
					bizPropertyMap.put(com.mockuai.tradecenter.core.util.BizPropertyKey.WECHAT_RETURN_URL, 
							bizInfo.getBizPropertyMap().get(BizPropertyKey.WECHAT_RETURN_URL));
					
					bizPropertyMap.put(com.mockuai.tradecenter.core.util.BizPropertyKey.UNIONPAY_RETURN_URL, 
							bizInfo.getBizPropertyMap().get(BizPropertyKey.UNIONPAY_RETURN_URL));
					
					bizPropertyMap.put(com.mockuai.tradecenter.core.util.BizPropertyKey.SUMPAY_RETURN_URL, 
							bizInfo.getBizPropertyMap().get(BizPropertyKey.SUMPAY_RETURN_URL));
					
					ctx.put("bizPropertyMap", bizPropertyMap);
			}
			
			ctx.put("bizName", bizInfo.getBizName());
		}
		
		return new TradeResponse(ResponseCode.RESPONSE_SUCCESS);
	}

	@Override
	public TradeResponse after(RequestContext ctx) throws TradeException {
		return new TradeResponse(ResponseCode.RESPONSE_SUCCESS);
	}
}
