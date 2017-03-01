package com.mockuai.suppliercenter.core.api.impl;

import com.mockuai.suppliercenter.common.api.SupplierService;
import com.mockuai.suppliercenter.core.service.RequestDispatcher;

import javax.annotation.Resource;


public class SupplierServiceImpl implements SupplierService {

    @Resource
    private RequestDispatcher requestDispatcher;


}
