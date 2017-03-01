package com.mockuai.suppliercenter.core.service.action;

import com.mockuai.suppliercenter.common.api.SupplierResponse;
import com.mockuai.suppliercenter.core.exception.SupplierException;
import com.mockuai.suppliercenter.core.service.RequestContext;
import org.springframework.stereotype.Service;

/**
 * 操作对像基类
 *
 * @author wujin.zzq
 */
@Service
public interface Action {

    @SuppressWarnings("rawtypes")
    public SupplierResponse execute(RequestContext context) throws SupplierException;

    public String getName();
}
