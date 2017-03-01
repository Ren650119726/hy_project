package com.mockuai.usercenter.core.service.action.feedback;

import javax.annotation.Resource;

import com.mockuai.usercenter.common.constant.ResponseCode;
import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.dto.FeedbackDTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.FeedbackManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;

/**
 * Created by duanyytop on 15/5/13.
 */
@Service
public class AddFeedbackAction implements Action{

    @Resource
    private FeedbackManager feedbackManager;

    @Override
    public UserResponse execute(RequestContext context) throws UserException {
        UserRequest userRequest = context.getRequest();
        FeedbackDTO feedbackDTO = (FeedbackDTO)userRequest.getParam("feedbackDTO");
        String bizCode = (String)context.get("bizCode");

        if(feedbackDTO == null){
            return new UserResponse(ResponseCode.P_PARAM_NULL, "feedbackDTO is null");
        }

        feedbackDTO.setBizCode(bizCode);
        try {
            Long id = feedbackManager.addFeedback(feedbackDTO);
            return new UserResponse(ResponseCode.REQUEST_SUCCESS);
        }catch (UserException e){
            return new UserResponse(e.getResponseCode(), e.getMessage());
        }
    }

    @Override
    public String getName() {
        return ActionEnum.ADD_FEEDBACK.getActionName();
    }
}
