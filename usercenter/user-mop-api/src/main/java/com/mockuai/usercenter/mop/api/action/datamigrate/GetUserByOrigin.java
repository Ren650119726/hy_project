package com.mockuai.usercenter.mop.api.action.datamigrate;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.FeedbackDTO;
import com.mockuai.usercenter.common.dto.datamigrate.MigrateUserDTO;
import com.mockuai.usercenter.mop.api.action.BaseAction;
import com.mockuai.usercenter.mop.api.util.JsonUtil;
import com.mockuai.usercenter.mop.api.util.MopApiUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zengzhangqiang on 6/17/15.
 */
public class GetUserByOrigin extends BaseAction {

    public MopResponse execute(Request request) {
        String originUserIdStr = (String)request.getParam("origin_user_id");
        String appKey = (String)request.getParam("app_key");

        Long originUserId = null;
        try{
            originUserId = Long.valueOf(originUserIdStr);
        }catch(Exception e){
            return new MopResponse(MopRespCode.P_E_PARAM_FORMAT_INVALID);
        }


        com.mockuai.usercenter.common.api.Request userReq = new BaseRequest();
        userReq.setParam("originUserId", originUserId);
        userReq.setParam("appKey", appKey);
        userReq.setCommand(ActionEnum.GET_USER_BY_ORIGIN.getActionName());
        Response<MigrateUserDTO> userResp = this.getUserDispatchService().execute(userReq);
        Map<String,Object> data = new HashMap<String, Object>();
        if(userResp.getCode() == ResponseCode.REQUEST_SUCCESS.getValue()){
            data.put("migrate_user", userResp.getModule());
            return new MopResponse(data);
        }else{
            return new MopResponse(userResp.getCode(), userResp.getMessage());
        }
    }

    public String getName() {
        return "/data_migrate/user/get_by_origin";
    }

    public ActionAuthLevel getAuthLevel() {
        //TODO 这里要加个授权级别类型，叫特殊的APPKEY
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
