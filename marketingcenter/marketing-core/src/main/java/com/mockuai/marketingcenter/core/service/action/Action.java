package com.mockuai.marketingcenter.core.service.action;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.service.RequestContext;
import org.springframework.stereotype.Service;

/**
 * 操作对像基类
 *
 * @author wujin.zzq
 */
@Service
public interface Action<T> {

    @SuppressWarnings("rawtypes")
    public MarketingResponse execute(RequestContext<T> context) throws MarketingException;

    public String getName();
}
