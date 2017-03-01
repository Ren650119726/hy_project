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
import com.mockuai.usercenter.common.dto.UserOpenInfoDTO;
import com.mockuai.usercenter.common.dto.datamigrate.MigrateUserOpenInfoDTO;
import com.mockuai.usercenter.mop.api.action.BaseAction;
import com.mockuai.usercenter.mop.api.util.JsonUtil;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zengzhangqiang on 6/18/15.
 */
public class GetUserOpenInfo extends BaseAction {

    public MopResponse execute(Request request) {
        String openTypeStr = (String) request.getParam("open_type");
        String openUid = (String) request.getParam("open_uid");
        String appKey = (String)request.getParam("app_key");

        if(StringUtils.isBlank(openTypeStr)){
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "openType is null");
        }

        Integer openType = null;
        try{
            openType = Integer.valueOf(openTypeStr);
        }catch(Exception e){
            return new MopResponse(MopRespCode.P_E_PARAM_FORMAT_INVALID);
        }

        com.mockuai.usercenter.common.api.Request userReq = new BaseRequest();
        userReq.setParam("openType", openType);
        userReq.setParam("openUid", openUid);
        userReq.setParam("appKey", appKey);
        userReq.setCommand(ActionEnum.GET_MIGRATE_USER_OPEN_INFO.getActionName());
        Response<Long> userResp = this.getUserDispatchService().execute(userReq);
        Map<String,Object> data = new HashMap<String, Object>();
        if(userResp.getCode() == ResponseCode.REQUEST_SUCCESS.getValue()){
            if(userResp.getModule() != null){
                data.put("user_open_info", userResp.getModule());
                return new MopResponse(data);
            }else{
                return new MopResponse(MopRespCode.B_E_RECORD_NOT_EXIST);
            }
        }else{
            return new MopResponse(userResp.getCode(), userResp.getMessage());
        }
    }

    public String getName() {
        return "/data_migrate/user_open_info/get";
    }

    public ActionAuthLevel getAuthLevel() {
        //TODO 这里要加个授权级别类型，叫特殊的APPKEY
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}