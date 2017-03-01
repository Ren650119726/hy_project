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

public class UpdateUserLoginInfoMissAction extends BaseAction {

    @SuppressWarnings({ "rawtypes", "unchecked" })	
	public MopResponse execute(Request request) {
    	Long userId = (Long)request.getAttribute("user_id");
        String mobile = (String)request.getParam("mobile");//手机号
        String password = (String)request.getParam("password");//密码
        String verifyCode = (String)request.getParam("verify_code");//邀请码
        String invitationCode = (String)request.getParam("invitation_code");
        String appKey = (String)request.getParam("app_key");//应用接入标识码
                
        if(null == userId){
        	return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "更新用户信息传入参数不可为空");
        }

        com.mockuai.usercenter.common.api.Request userReq = new BaseRequest();
        userReq.setParam("user_id", userId);
        userReq.setParam("mobile", mobile);
        userReq.setParam("password", password);        
        userReq.setParam("verifyCode", verifyCode);        
        userReq.setParam("invitationCode", invitationCode);        
        userReq.setParam("appKey", appKey);
        userReq.setCommand(ActionEnum.UPDATEUSERLOGININFOMISS.getActionName());
        Response<Void> res = getUserDispatchService().execute(userReq);

        return MopApiUtil.transferResp(res);
    }

    public String getName() {
        return "/user/loginInfoMiss/mopap/update";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}
