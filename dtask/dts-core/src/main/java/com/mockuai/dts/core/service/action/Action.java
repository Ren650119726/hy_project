package com.mockuai.dts.core.service.action;

import com.mockuai.dts.common.api.action.DtsResponse;
import com.mockuai.dts.core.exception.DtsException;
import com.mockuai.dts.core.service.RequestContext;
import org.springframework.stereotype.Service;

/**
 * 操作对像基类
 * 
 * @author luliang
 *
 */
@Service
public interface Action {

	public DtsResponse execute(RequestContext context) throws DtsException;

	public String getName();
}
