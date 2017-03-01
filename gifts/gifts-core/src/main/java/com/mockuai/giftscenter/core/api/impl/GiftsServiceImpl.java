package com.mockuai.giftscenter.core.api.impl;

import com.mockuai.giftscenter.common.api.Request;
import com.mockuai.giftscenter.common.api.Response;
import com.mockuai.giftscenter.common.api.GiftsService;
import com.mockuai.giftscenter.core.api.RequestAdapter;
import com.mockuai.giftscenter.core.service.RequestDispatcher;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GiftsServiceImpl<T> implements GiftsService<T> {

    @Resource
    private RequestDispatcher requestDispatcher;

    public Response<T> execute(Request request) {

        return this.requestDispatcher.dispatch(new RequestAdapter(request));
    }
}