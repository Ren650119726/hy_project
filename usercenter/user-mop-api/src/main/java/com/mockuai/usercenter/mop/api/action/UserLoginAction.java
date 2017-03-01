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

public class UserLoginAction extends BaseAction {

	public MopResponse execute(Request request) {
    	String loginName = (String) request.getParam("loginName");//手机号
		String loginPwd = (String) request.getParam("loginPwd");//登陆的密码
		String loginVerifyCode = (String) request.getParam("loginVerifyCode");//手机验证码
		String loginFlag = (String) request.getParam("loginFlag");//登录标识(0代表密码验证1代码验证码登录)
		String loginSource = (String) request.getParam("loginSource");//登录类型
        String appKey = (String)request.getParam("app_key");//应用接入标识码
                
        if(StringUtils.isEmpty(loginName)){
        	return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "手机号为空,请输入正确的手机号进行登录");
        }
        
        if(StringUtils.isEmpty(loginFlag)){
        	return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "登录失败,请重新操作");
        }

        com.mockuai.usercenter.common.api.Request userReq = new BaseRequest();
        userReq.setParam("loginName", loginName);
        userReq.setParam("loginPwd", loginPwd);
        userReq.setParam("loginVerifyCode", loginVerifyCode);
        userReq.setParam("loginFlag", loginFlag);
        userReq.setParam("loginSource", loginSource);
        userReq.setParam("appKey", appKey);        
        userReq.setCommand(ActionEnum.USER_LOGIN.getActionName());
        Response<Void> res = getUserDispatchService().execute(userReq);

        return MopApiUtil.transferResp(res);
    }

    public String getName() {
        return "/user/mopApi/login";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}
