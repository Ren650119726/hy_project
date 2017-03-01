package com.mockuai.headsinglecenter.core.api.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mockuai.headsinglecenter.common.api.HeadSingleService;
import com.mockuai.headsinglecenter.common.api.Request;
import com.mockuai.headsinglecenter.common.api.Response;
import com.mockuai.headsinglecenter.core.api.RequestAdapter;
import com.mockuai.headsinglecenter.core.service.RequestDispatcher;

@Service
public class HeadSingleServiceImpl<T> implements HeadSingleService<T> {

    @Resource
    private RequestDispatcher requestDispatcher;

    public Response<T> execute(Request request) {

        return this.requestDispatcher.dispatch(new RequestAdapter(request));
    }
}