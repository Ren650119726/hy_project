package com.mockuai.suppliercenter.core.api.impl;

import com.mockuai.suppliercenter.common.api.Request;
import com.mockuai.suppliercenter.common.api.SupplierDispatchService;
import com.mockuai.suppliercenter.common.api.SupplierResponse;
import com.mockuai.suppliercenter.core.service.RequestDispatcher;

import javax.annotation.Resource;

/**
 * Created by idoud on 4/24/15.
 */
public class SupplierDispatchServiceImpl implements SupplierDispatchService {
    @Resource
    private RequestDispatcher requestDispatcher;

    public SupplierResponse execute(Request request) {
        SupplierResponse res = requestDispatcher.dispatch(new RequestAdapter(request));
        return res;
    }
}
