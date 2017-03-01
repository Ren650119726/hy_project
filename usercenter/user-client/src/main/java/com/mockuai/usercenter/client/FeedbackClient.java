package com.mockuai.usercenter.client;

import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.dto.FeedbackDTO;
import com.mockuai.usercenter.common.qto.FeedbackQTO;

import java.util.Date;
import java.util.List;

/**
 * Created by duanyytop on 15/5/14.
 */
public interface FeedbackClient {
    /**
     * 查询反馈消息
     * @param feedbackQTO
     * @return
     */
    public Response<List<FeedbackDTO>> queryUserFeedback(FeedbackQTO feedbackQTO, String appKey);
}
