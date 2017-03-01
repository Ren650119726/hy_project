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

public class ResetUserPayPwdAction extends BaseAction {

    @SuppressWarnings({ "rawtypes", "unchecked" })	
	public MopResponse execute(Request request) {
    	Long userId = (Long) request.getAttribute("user_id");//用户id
		String pay_pwd = (String) request.getParam("pay_pwd");//支付密码		
        String once_pay_pwd = (String)request.getParam("once_pay_pwd");//确认支付密码
        String id_card = (String)request.getParam("id_card");//身份证号
        String payFlag = (String)request.getParam("payFlag");//支付方法标识
        String appKey = (String)request.getParam("app_key");//应用接入标识码
        
        if(StringUtils.isEmpty(payFlag)){
        	return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "支付方法标识不能为空");
        }
        
        if(StringUtils.isEmpty(pay_pwd)){
        	return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "支付密码不可为空");
        }
        
        if(StringUtils.isEmpty(once_pay_pwd)){
        	return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "确认支付密码不可为空");
        }

        com.mockuai.usercenter.common.api.Request userReq = new BaseRequest();
        userReq.setParam("user_id", userId);
        userReq.setParam("pay_pwd", pay_pwd);
        userReq.setParam("once_pay_pwd", once_pay_pwd);
        userReq.setParam("id_card", id_card);        
        userReq.setParam("payFlag", payFlag);        
        userReq.setParam("appKey", appKey);        
        userReq.setCommand(ActionEnum.RESETUSERPAYPWD.getActionName());
        Response<Void> res = getUserDispatchService().execute(userReq);

        return MopApiUtil.transferResp(res);
    }

    public String getName() {
        return "/user/userPayPwd/reset";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}
