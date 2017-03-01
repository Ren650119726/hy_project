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

/**
 * Created by zengzhangqiang on 7/6/15.
 */
public class ReleaseUserOpenInfo extends BaseAction {

    public MopResponse execute(Request request) {
        Long userId = (Long)request.getAttribute("user_id");
        String openTypeStr = (String)request.getParam("open_type");
        String appKey = (String)request.getParam("app_key");

        if(StringUtils.isEmpty(openTypeStr)) {
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "open_type is null");
        }

        Integer openType = null;
        try{
            openType = Integer.valueOf(openTypeStr);
        }catch (Exception e){
            return new MopResponse(MopRespCode.P_E_PARAM_FORMAT_INVALID, "the format of open_type is invalid");
        }

        com.mockuai.usercenter.common.api.Request userReq = new BaseRequest();
        userReq.setParam("userId", userId);
        userReq.setParam("openType", openType);
        userReq.setParam("appKey", appKey);
        userReq.setCommand(ActionEnum.RELEASE_USER_OPEN_INFO.getActionName());
        Response<Void> res = getUserDispatchService().execute(userReq);

        return MopApiUtil.transferResp(res);
    }

    public String getName() {
        return "/user/open_info/release";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }

    public static void main(String[] args){
        Long inviterId = Long.valueOf(null);
        System.out.println("inviterId:"+inviterId);
    }
}
