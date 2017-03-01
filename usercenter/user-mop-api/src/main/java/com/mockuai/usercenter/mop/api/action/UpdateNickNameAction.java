package com.mockuai.usercenter.mop.api.action;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.mop.api.util.MopApiUtil;

import org.apache.commons.lang.StringUtils;

public class UpdateNickNameAction extends BaseAction {
 
    @SuppressWarnings({ "rawtypes", "unchecked" })	
	public MopResponse execute(Request request) {
    	Long userId = (Long) request.getAttribute("user_id");
        String nick_name = (String)request.getParam("nick_name");//昵称
        String appKey = (String)request.getParam("app_key");//应用接入标识码
                
        if(StringUtils.isEmpty(nick_name)){
        	return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "请输入2-15个由汉字、英文或数字组成的昵称");
        }

        com.mockuai.usercenter.common.api.Request userReq = new BaseRequest();
        userReq.setParam("user_id", userId);
        userReq.setParam("nick_name", nick_name);
        userReq.setParam("appKey", appKey);        
        userReq.setCommand(ActionEnum.UPDATE_NICKNAME.getActionName());
        Response<Void> res = getUserDispatchService().execute(userReq);
        
        if (ResponseCode.REQUEST_SUCCESS.getValue() != res.getCode()) {
            return new MopResponse(res.getCode(), res.getMessage());
        }
        
        return MopApiUtil.transferResp(res);
    }

    public String getName() {
        return "/user/nick_name/update";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}
