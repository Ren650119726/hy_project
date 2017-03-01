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

public class UserRegisterAction extends BaseAction {

	public MopResponse execute(Request request) {
        String mobile = (String)request.getParam("mobile");//手机号
        String verifyCode = (String)request.getParam("verifyCode");//验证码
        String password = (String)request.getParam("password");//登录密码        
        String invitationCode = (String)request.getParam("invitationCode");//邀请码
        String registerFlag = (String)request.getParam("registerFlag");//注册标示(0app注册1网页注册)
        String appKey = (String)request.getParam("app_key");//应用接入标识码
                
        if(StringUtils.isEmpty(mobile)){
        	return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "请輸入手机号");
        }
        
        if(StringUtils.isEmpty(verifyCode)){
        	return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "请輸入验证码");
        }
        
        if(StringUtils.isEmpty(registerFlag)){
        	return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "注册标示不可为空");
        }
        
        if(StringUtils.isEmpty(password)){
        	return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "请设置6-15位英文或数字的登陆密码");
        }

        com.mockuai.usercenter.common.api.Request userReq = new BaseRequest();
        userReq.setParam("mobile", mobile);
        userReq.setParam("verifyCode", verifyCode);
        userReq.setParam("password", password);
        userReq.setParam("invitationCode", invitationCode);
        userReq.setParam("registerFlag", registerFlag);
        userReq.setParam("appKey", appKey);        
        userReq.setCommand(ActionEnum.USER_REGISTER.getActionName());
        Response<Void> res = getUserDispatchService().execute(userReq);

        return MopApiUtil.transferResp(res);
    }

    public String getName() {
        return "/user/mopApi/register";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}
