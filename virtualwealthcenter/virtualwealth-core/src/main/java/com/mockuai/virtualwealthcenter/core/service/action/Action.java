package com.mockuai.virtualwealthcenter.core.service.action;

import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import org.springframework.stereotype.Service;

/**
 * 操作对像基类
 *
 * @author wujin.zzq
 */
@Service
public interface Action<T> {

    @SuppressWarnings("rawtypes")
    public VirtualWealthResponse execute(RequestContext<T> context) throws VirtualWealthException;

    public String getName();
}
