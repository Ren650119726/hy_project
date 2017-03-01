package com.mockuai.virtualwealthcenter.core.api.impl;

import com.mockuai.virtualwealthcenter.common.api.VirtualWealthService;
import com.mockuai.virtualwealthcenter.common.api.Request;
import com.mockuai.virtualwealthcenter.common.api.Response;
import com.mockuai.virtualwealthcenter.core.api.RequestAdapter;
import com.mockuai.virtualwealthcenter.core.service.RequestDispatcher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class VirtualWealthServiceImpl<T> implements VirtualWealthService<T> {

    @Resource
    private RequestDispatcher requestDispatcher;

    public Response<T> execute(Request request) {

        return this.requestDispatcher.dispatch(new RequestAdapter(request));
    }
}