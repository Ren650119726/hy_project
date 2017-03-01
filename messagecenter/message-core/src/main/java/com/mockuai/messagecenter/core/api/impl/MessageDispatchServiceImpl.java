package com.mockuai.messagecenter.core.api.impl;

import com.mockuai.messagecenter.common.api.MessageDispatchService;
import com.mockuai.messagecenter.common.api.Request;
import com.mockuai.messagecenter.common.api.MessageResponse;
import com.mockuai.messagecenter.core.service.RequestDispatcher;

import javax.annotation.Resource;

/**
 * Created by idoud on 4/24/15.
 */
public class MessageDispatchServiceImpl implements MessageDispatchService {
    @Resource
    private RequestDispatcher requestDispatcher;

    public MessageResponse execute(Request request) {
        MessageResponse res = requestDispatcher.dispatch(new RequestAdapter(request));
        return res;
    }
}
