package com.mockuai.marketingcenter.core.api.impl;

import com.mockuai.marketingcenter.common.api.MarketingService;
import com.mockuai.marketingcenter.common.api.Request;
import com.mockuai.marketingcenter.common.api.Response;
import com.mockuai.marketingcenter.core.api.RequestAdapter;
import com.mockuai.marketingcenter.core.service.RequestDispatcher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MarketingServiceImpl<T> implements MarketingService<T> {

    @Resource
    private RequestDispatcher requestDispatcher;

    public Response<T> execute(Request request) {

        return this.requestDispatcher.dispatch(new RequestAdapter(request));
    }
}