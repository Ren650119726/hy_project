package com.mockuai.giftscenter.core.service.action;

import com.mockuai.giftscenter.common.api.GiftsResponse;
import com.mockuai.giftscenter.core.exception.GiftsException;
import com.mockuai.giftscenter.core.service.RequestContext;

import org.springframework.stereotype.Service;

/**
 * 操作对像基类
 *
 * @author wujin.zzq
 */
@Service
public interface Action<T> {

    GiftsResponse run(RequestContext<T> context) throws GiftsException;

    String getName();
}
