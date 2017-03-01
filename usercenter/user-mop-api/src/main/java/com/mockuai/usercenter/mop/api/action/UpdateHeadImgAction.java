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

public class UpdateHeadImgAction extends BaseAction {

    @SuppressWarnings({ "rawtypes", "unchecked" })	
	public MopResponse execute(Request request) {
    	Long userId = (Long)request.getAttribute("user_id");
        String headImg = (String)request.getParam("headImg");//图片地址
        String appKey = (String)request.getParam("app_key");//应用接入标识码
                
        if(StringUtils.isEmpty(headImg)){
        	return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "传入图片为空,请重新选择");
        }

        com.mockuai.usercenter.common.api.Request userReq = new BaseRequest();
        userReq.setParam("user_id", userId);
        userReq.setParam("headImg", headImg);
        userReq.setParam("appKey", appKey);        
        userReq.setCommand(ActionEnum.UPDATE_HEADIMG.getActionName());
        Response<Void> res = getUserDispatchService().execute(userReq);

        return new MopResponse(res.getModule());
    }

    public String getName() {
        return "/user/headImg/update";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}
