package com.mockuai.usercenter.mop.api.action;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.common.qto.UserQTO;
import com.mockuai.usercenter.mop.api.util.MopApiUtil;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 6/2/15.
 */
public class UserInviteeList extends BaseAction{

    public MopResponse execute(Request request) {
        String offsetStr = (String)request.getParam("offset");
        String countStr = (String)request.getParam("count");
        String appKey = (String)request.getParam("app_key");

        Long offset = null;
        Integer count = null;

        if(StringUtils.isNotBlank(offsetStr)){
            try{
                offset = Long.valueOf(offsetStr);
            }catch(Exception e){
                //TODO error handle
            }
        }

        if(StringUtils.isNotBlank(countStr)){
            try{
                count = Integer.valueOf(countStr);
            }catch(Exception e){
                //TODO error handle
            }
        }
        Long userId  = (Long)request.getAttribute("user_id");
        com.mockuai.usercenter.common.api.Request userReq = new BaseRequest();
        UserQTO userQTO = new UserQTO();
        userQTO.setOffset(offset);
        userQTO.setCount(count);
        userQTO.setInviterId(userId);
        userReq.setParam("userQTO", userQTO);
        userReq.setParam("appKey", appKey);
        userReq.setCommand(ActionEnum.QUERY_USER.getActionName());
        Response<List<UserDTO>> userResp = this.getUserDispatchService().execute(userReq);
        if(userResp.getCode() == ResponseCode.REQUEST_SUCCESS.getValue()){
            Map<String,Object> data = new HashMap<String,Object>();
            data.put("invitee_list", MopApiUtil.genMopInviteeList(userResp.getModule()));
            data.put("total_count", userResp.getTotalCount());
            return new MopResponse(data);
        }else{
            return MopApiUtil.transferResp(userResp);
        }
    }

    public String getName() {
        return "/user/invitee/list";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }

}
