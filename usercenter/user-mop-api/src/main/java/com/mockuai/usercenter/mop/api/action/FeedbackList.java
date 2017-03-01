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
import com.mockuai.usercenter.common.dto.FeedbackDTO;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class FeedbackList extends BaseAction {

    public MopResponse execute(Request request) {
        Date startDate  = (Date)request.getParam("start_time");
        Date endDate    = (Date)request.getParam("end_time");
        Long offset     = (Long)request.getParam("offset");
        Integer count   = (Integer)request.getParam("count");
        String appKey = (String)request.getParam("app_key");

        com.mockuai.usercenter.common.api.Request userReq = new BaseRequest();
        userReq.setParam("startDate", startDate);
        userReq.setParam("endDate",   endDate);
        userReq.setParam("offset", offset);
        userReq.setParam("count", count);
        userReq.setParam("appKey", appKey);
        userReq.setCommand(ActionEnum.QUERY_USER_FEEDBACK.getActionName());

        Response<List<FeedbackDTO>> userResp = this.getUserDispatchService().execute(userReq);

        if(userResp.getCode() != ResponseCode.REQUEST_SUCCESS.getValue()){
            return new MopResponse(MopRespCode.S_E_SERVICE_ERROR);
        }else{
            Map<String,Object> data = new HashMap<String, Object>();
            List<FeedbackDTO> list = new ArrayList<FeedbackDTO>();
            if(!CollectionUtils.isEmpty(userResp.getModule())){
                for (int i = 0; i < userResp.getModule().size(); i++) {
                    FeedbackDTO feedbackDTO  = userResp.getModule().get(i);
                    list.add(feedbackDTO);
                }
            }
            data.put("feedback_list", list);
            data.put("total_count", userResp.getTotalCount());
            return new MopResponse(data);
        }
    }

    public String getName() {
        return "/user/feedback/list";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }


}
