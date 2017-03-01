package com.mockuai.usercenter.core.service.action.feedback;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.dto.FeedbackDTO;
import com.mockuai.usercenter.common.qto.FeedbackQTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.FeedbackManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by duanyytop on 15/5/13.
 */
@Service
public class QueryFeedbackAction implements Action {

    @Resource
    private FeedbackManager feedbackManager;

    @Override
    public UserResponse execute(RequestContext context) throws UserException {

        List<FeedbackDTO> feedbackDTOList = null;
        UserRequest request = context.getRequest();
        FeedbackQTO feedbackQTO = (FeedbackQTO)request.getParam("feedbackQTO");
        String bizCode = (String)context.get("bizCode");

        Long startRow = (feedbackQTO.getPageNum().longValue()-1) * ( feedbackQTO.getPageSize().intValue());
        feedbackQTO .setCount(feedbackQTO.getPageSize());
        feedbackQTO.setOffset(startRow);
        feedbackQTO.setBizCode(bizCode);

        Date startDate = (Date) feedbackQTO.getStartDate();
        Date endDate   = (Date) feedbackQTO.getEndDate();
        Long offset    = (Long) feedbackQTO.getOffset();
        Integer count  = (Integer) feedbackQTO.getCount();

        try {
             feedbackDTOList = feedbackManager.getFeedbackByDate(startDate, endDate, offset, count, bizCode);
            long total=feedbackManager.getTotalCount(feedbackQTO);
            return new UserResponse(feedbackDTOList,total);
            //return new UserResponse(feedbackDTOList);
        }catch (UserException e){
            return new UserResponse(e.getResponseCode(), e.getMessage());
        }

    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_USER_FEEDBACK.getActionName();
    }

}
