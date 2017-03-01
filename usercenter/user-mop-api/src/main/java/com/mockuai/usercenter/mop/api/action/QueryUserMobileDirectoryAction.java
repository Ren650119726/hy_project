package com.mockuai.usercenter.mop.api.action;

import java.util.List;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.mop.api.util.MopApiUtil;

public class QueryUserMobileDirectoryAction extends BaseAction {

    @SuppressWarnings({ "rawtypes", "unchecked" })	
	public MopResponse execute(Request request) {
    	List<String> userMobileDirJsonList = (List<String>)request.getParam("user_mobile_list");//用户通讯录手机号
        String appKey = (String)request.getParam("app_key");//应用接入标识码
        
        if(null == userMobileDirJsonList){
        	return new MopResponse(30017,"您的手机通讯录中没有找到嗨云用户");
        }
	
        com.mockuai.usercenter.common.api.Request userReq = new BaseRequest();
        userReq.setParam("userMobileDirJsonList", userMobileDirJsonList);
        userReq.setParam("appKey", appKey);        
        userReq.setCommand(ActionEnum.QUERY_USERMOBILEDIRECTORY.getActionName());
        Response<UserDTO> res = getUserDispatchService().execute(userReq);

        return MopApiUtil.transferResp(res);
    }

    public String getName() {
        return "/user/userMobileDirectory/query";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
