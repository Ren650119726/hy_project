package com.mockuai.usercenter.mop.api.action;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.dto.UserAuthInfoDTO;
import com.mockuai.usercenter.mop.api.util.JsonUtil;
import com.mockuai.usercenter.mop.api.util.MopApiUtil;

/**
 * Created by duanyytop on 15/5/14.
 */
public class AddUserAuthAction extends BaseAction{

    public MopResponse execute(Request request) {
        Long userId = (Long)request.getAttribute("user_id");
        String src = (String)request.getParam("auth_info");
        String appKey = (String)request.getParam("app_key");
        UserAuthInfoDTO userAuthInfoDTO = JsonUtil.parseJson(src, UserAuthInfoDTO.class);
        userAuthInfoDTO.setUserId(userId);
        com.mockuai.usercenter.common.api.Request userReq = new BaseRequest();
        userReq.setParam("userAuthInfoDTO", userAuthInfoDTO);
        userReq.setParam("appKey", appKey);
        userReq.setCommand(ActionEnum.ADD_USER_AUTH_INFO.getActionName());
        Response<UserAuthInfoDTO> userResp = this.getUserDispatchService().execute(userReq);
        return MopApiUtil.transferResp(userResp);
    }

    public String getName() {
        return "/user/auth/add";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    /**
     * FIXME 这里由于之前暴露出去的时候是NO_LIMIT,导致现在洋东西android客户端使用了post方式，
     * FIXME 所以这里只能保持NO_LIMIT
     * @return
     */
    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.NO_LIMIT;
    }

}
