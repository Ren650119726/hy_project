package com.mockuai.distributioncenter.core.api.impl;

import com.mockuai.distributioncenter.common.api.DistributionService;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.core.api.RequestAdapter;
import com.mockuai.distributioncenter.core.service.RequestDispatcher;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * Created by duke on 15/10/28.
 */
public class DistributionServiceImpl<T> implements DistributionService<T> {

    @Autowired
    private RequestDispatcher requestDispatcher;

    public Response<T> execute(Request request) {
        return this.requestDispatcher.dispatch(request);
    }
}
