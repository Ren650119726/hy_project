package com.mockuai.headsinglecenter.core.service.action;

import org.springframework.stereotype.Service;

import com.mockuai.headsinglecenter.common.api.HeadSingleResponse;
import com.mockuai.headsinglecenter.core.exception.HeadSingleException;
import com.mockuai.headsinglecenter.core.service.RequestContext;

/**
 * 操作对像基类
 *
 * @author wujin.zzq
 */
@Service
public interface Action {

	@SuppressWarnings("rawtypes")
	HeadSingleResponse execute(RequestContext context) throws HeadSingleException;

    String getName();
}
