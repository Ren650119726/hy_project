package com.mockuai.usercenter.core.dao;


import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.qto.FeedbackQTO;
import com.mockuai.usercenter.core.domain.FeedbackDO;

/**
 * Created by duanyytop on 15/5/13.
 */
@Service
public interface FeedbackDAO {

    Long addFeedback(FeedbackDO feedbackDO);

    List<FeedbackDO> getFeedbackByDate(Date startDate, Date endDate, Long offset, Integer count, String bizCode);

    FeedbackDO getFeedbackById(Long id);
    
    List<FeedbackDO> queryFeedback(FeedbackQTO feedbackQTO);
    
    Integer getTotalCount(FeedbackQTO feedbackQTO);
}
