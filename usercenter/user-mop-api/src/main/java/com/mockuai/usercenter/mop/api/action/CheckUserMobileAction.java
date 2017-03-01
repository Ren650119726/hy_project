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

public class CheckUserMobileAction extends BaseAction {

    @SuppressWarnings({ "rawtypes", "unchecked" })	
	public MopResponse execute(Request request) {
    	Long userId = (Long) request.getAttribute("user_id");
        String mobile = (String)request.getParam("mobile");//用户手机 
        String verify_code = (String)request.getParam("verify_code");//验证码
        String bank_personal_id = (String)request.getParam("bank_personal_id");//用户身份证
        String mFlag = (String)request.getParam("mFlag");//接口标示("0"原密码校验,"1"身份证校验)
        String appKey = (String)request.getParam("app_key");//应用接入标识码     
        
        if(StringUtils.isEmpty(mFlag)){
        	return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "mFlag is null");
        }

        com.mockuai.usercenter.common.api.Request userReq = new BaseRequest();
        userReq.setParam("user_id", userId);
        userReq.setParam("mobile", mobile);
        userReq.setParam("verify_code", verify_code);
        userReq.setParam("bank_personal_id", bank_personal_id);
        userReq.setParam("mFlag", mFlag);
        userReq.setParam("appKey", appKey);        
        userReq.setCommand(ActionEnum.CHECK_USERMOBILE.getActionName());
        Response<Void> res = getUserDispatchService().execute(userReq);

        return MopApiUtil.transferResp(res);
    }

    public String getName() {
        return "/user/modifyUserMobile/check";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
