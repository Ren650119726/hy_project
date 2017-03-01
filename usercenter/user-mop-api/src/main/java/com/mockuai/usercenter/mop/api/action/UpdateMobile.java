package com.mockuai.usercenter.mop.api.action;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.mop.api.util.MopApiUtil;

import org.apache.commons.lang.StringUtils;

public class UpdateMobile extends BaseAction {
    @SuppressWarnings({ "rawtypes", "unchecked" })	
	public MopResponse execute(Request request) {
    	Long userId = (Long)request.getAttribute("user_id");
        String mobile = (String)request.getParam("mobile");//用户手机 
        String verify_code = (String)request.getParam("verify_code");//验证码
        String appKey = (String)request.getParam("app_key");//应用接入标识码
        String mFlag = (String)request.getParam("mFlag");
        
        if(StringUtils.isEmpty(mobile)){
        	return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "mobile is null");
        }
        
        if(StringUtils.isEmpty(verify_code)){
        	return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "verifyCode is null");
        }
        
        if(StringUtils.isEmpty(mFlag)){
        	return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "mFlag is null");
        }

        com.mockuai.usercenter.common.api.Request userReq = new BaseRequest();
        userReq.setParam("user_id", userId);
        userReq.setParam("mobile", mobile);
        userReq.setParam("verify_code", verify_code);
        userReq.setParam("mFlag", mFlag);
        userReq.setParam("appKey", appKey);        
        userReq.setCommand(ActionEnum.UPDATE_MOBILE.getActionName());
        Response<Void> res = getUserDispatchService().execute(userReq);

        return MopApiUtil.transferResp(res);
    }

    public String getName() {
        return "/user/mobile/update";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}
