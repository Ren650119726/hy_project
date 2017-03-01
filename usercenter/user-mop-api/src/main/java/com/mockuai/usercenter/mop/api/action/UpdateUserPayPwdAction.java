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

public class UpdateUserPayPwdAction extends BaseAction {

    @SuppressWarnings({ "rawtypes", "unchecked" })	
	public MopResponse execute(Request request) {
    	Long userId = (Long)request.getAttribute("user_id");
		String pay_pwd = (String) request.getParam("pay_pwd");//原支付密码		
        String new_pay_pwd = (String)request.getParam("new_pay_pwd");//新密码
        String once_pay_pwd = (String)request.getParam("once_pay_pwd");//确认新密码
        String appKey = (String)request.getParam("app_key");//应用接入标识码
        
        if(StringUtils.isEmpty(pay_pwd)){
        	return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "支付密码不可为空");
        }
        
        if(StringUtils.isEmpty(once_pay_pwd)){
        	return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "确认支付密码不可为空");
        }
        
        if(StringUtils.isEmpty(new_pay_pwd)){
        	return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "新支付密码不可为空");
        }

        com.mockuai.usercenter.common.api.Request userReq = new BaseRequest();
        userReq.setParam("user_id", userId);
        userReq.setParam("pay_pwd", pay_pwd);
        userReq.setParam("new_pay_pwd", new_pay_pwd);
        userReq.setParam("once_pay_pwd", once_pay_pwd);        
        userReq.setParam("appKey", appKey);        
        userReq.setCommand(ActionEnum.UPDATEUSERPAYPWD.getActionName());
        Response<Void> res = getUserDispatchService().execute(userReq);

        return MopApiUtil.transferResp(res);
    }

    public String getName() {
        return "/user/userPayPwd/update";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}
