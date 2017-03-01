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
import com.mockuai.usercenter.common.dto.UserMessageDTO;
import com.mockuai.usercenter.common.qto.UserMessageQTO;
import com.mockuai.usercenter.mop.api.util.MopApiUtil;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryMessageAction extends BaseAction {

    public MopResponse execute(Request request) {
        Long userId = (Long) request.getAttribute("user_id");
        String offsetStr = (String) request.getParam("offset");
        String countStr = (String) request.getParam("count");
        String statusStr = (String) request.getParam("status");
        String appKey = (String) request.getParam("app_key");

        Integer status = null;
        Long offset = null;
        Integer count = null;

        if (StringUtils.isNotBlank(offsetStr)) {
            offset = Long.valueOf(offsetStr);
        }

        if (StringUtils.isNotBlank(countStr)) {
            count = Integer.valueOf(countStr);
        }

        if (StringUtils.isNotBlank(statusStr)) {
            status = Integer.valueOf(statusStr);
        }

        //获取用户的注册时间
        com.mockuai.usercenter.common.api.Request req = new BaseRequest();
        req.setParam("userId", userId);
        req.setParam("appKey", appKey);
        req.setCommand(ActionEnum.GET_USER_BY_ID.getActionName());
        Response<UserDTO> reqResponse = this.getUserDispatchService().execute(req);
        if (!reqResponse.isSuccess()) {
            return MopApiUtil.transferResp(reqResponse);
        }

        Date userRegDate = reqResponse.getModule().getGmtCreated();

        UserMessageQTO messageQto = new UserMessageQTO();

        //TODO 分页参数待重构
        messageQto.setReceiverId(userId);
        messageQto.setOffset(offset);
        messageQto.setCount(count);
        messageQto.setStatus(status);
        messageQto.setUserRegDate(userRegDate);


        com.mockuai.usercenter.common.api.Request userReq = new BaseRequest();
        //QueryMessageQTO QTO= new QueryMessageQTO;
        //QTO.setReceiverId(userId)
        userReq.setParam("userMessageQTO", messageQto);
        userReq.setParam("appKey", appKey);
        userReq.setCommand(ActionEnum.QUERY_USER_MESSAGE.getActionName());
        Response<List<UserMessageDTO>> userResp = this.getUserDispatchService().execute(userReq);
        if (userResp.getCode() == ResponseCode.REQUEST_SUCCESS.getValue()) {
            List<UserMessageDTO> messageDTOs = userResp.getModule();
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("message_list", MopApiUtil.genMopMessageList(messageDTOs));
            data.put("total_count", userResp.getTotalCount());

            return new MopResponse(data);
        } else {
            return MopApiUtil.transferResp(userResp);
        }
    }

    public String getName() {
        return "/user/message/list";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }

}
