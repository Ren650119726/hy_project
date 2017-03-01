package com.mockuai.usercenter.mop.api.action;

import org.apache.commons.lang.StringUtils;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.constant.ResponseCode;

public class UpdatePwd extends BaseAction {

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public MopResponse execute(Request request) {
    	Long userId = (Long)request.getAttribute("user_id");
        String password = (String)request.getParam("password");
        String verify_code = (String)request.getParam("verify_code");
        String mobile = (String)request.getParam("mobile");        
        String appKey = (String)request.getParam("app_key");
        String mFlag = (String)request.getParam("mFlag");

        if(StringUtils.isEmpty(password)) {
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "password is null");
        }
        
        if(StringUtils.isEmpty(verify_code)){
        	return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "verify_code is null");
        }
        
        if(StringUtils.isEmpty(mobile)){
        	return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "mobile is null");
        }
        
        if(StringUtils.isEmpty(mFlag)){
        	return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "mFlag is null");
        } 

        com.mockuai.usercenter.common.api.Request userReq = new BaseRequest();
        userReq.setParam("user_id", userId);
        userReq.setParam("newPwd", password);
        userReq.setParam("mobile", mobile);
        userReq.setParam("verify_code", verify_code);
        userReq.setParam("mFlag", mFlag);
        userReq.setParam("appKey", appKey);        
        userReq.setCommand(ActionEnum.UPDATE_PWD.getActionName());
        Response<Void> res = getUserDispatchService().execute(userReq);
        
        if (ResponseCode.REQUEST_SUCCESS.getValue() != res.getCode()) {
            return new MopResponse(res.getCode(), res.getMessage());
        }

        return new MopResponse(res);
    }

    public String getName() {
        return "/user/password/update";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}
