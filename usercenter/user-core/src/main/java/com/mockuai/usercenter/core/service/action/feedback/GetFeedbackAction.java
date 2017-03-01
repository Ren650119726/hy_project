package com.mockuai.usercenter.core.service.action.feedback;

/**
 * Created by duanyytop on 15/5/13.
 */


import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.dto.FeedbackDTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.FeedbackManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 根据id查找反馈意见
 * */
@Service
public class GetFeedbackAction implements Action{

    @Resource
    private FeedbackManager feedbackManager;

    @Override
    public UserResponse execute(RequestContext context) throws UserException {

        FeedbackDTO feedbackDTO = null;
        UserRequest request = context.getRequest();
        Long id = (Long) request.getParam("id");

        try {
            feedbackDTO = feedbackManager.getFeedbackById(id);
            return new UserResponse(feedbackDTO);
        }catch (UserException e){
            return new UserResponse(e.getResponseCode(), e.getMessage());
        }

    }

    @Override
    public String getName() {
        return ActionEnum.GET_FEEDBACK.getActionName();
    }
}
