package com.mockuai.usercenter.core.manager;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.dto.FeedbackDTO;
import com.mockuai.usercenter.common.qto.FeedbackQTO;
import com.mockuai.usercenter.core.exception.UserException;

/**
 * Created by duanyytop on 15/5/13.
 */
@Service
public interface FeedbackManager {

    /**
     * 添加意见反馈
     */
    Long addFeedback(FeedbackDTO feedbackDTO) throws UserException;
    
    /**
     * 符合条件查询
     * @param feedbackQTO
     * @return
     * @throws UserException
     */
    List<FeedbackDTO> queryFeeback(FeedbackQTO feedbackQTO)throws UserException;
    
    /**
     * 符合条件查询的总条数
     * @param feedbackQTO
     * @return
     * @throws UserException
     */
    Integer getTotalCount(FeedbackQTO feedbackQTO)throws UserException;

    /**
     * 根据时间段查找意见反馈
     */
    List<FeedbackDTO> getFeedbackByDate(Date startDate, Date endDate, Long offset, Integer count, String bizCode) throws UserException;

    /**
     * 根据id查找意见反馈
     */
    FeedbackDTO getFeedbackById(Long id) throws UserException;
    
    
    
}
