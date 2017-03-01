package com.mockuai.mainweb.core.service.action;

import com.mockuai.mainweb.common.api.action.MainWebResponse;
import com.mockuai.mainweb.core.exception.ImageException;
import com.mockuai.mainweb.core.exception.MainWebException;
import org.springframework.stereotype.Service;

/**
 * 操作对像基类
 * 
 * @author luliang
 *
 */
@Service
public interface Action {

	public MainWebResponse execute(RequestContext context) throws  MainWebException;

	public String getName();
}
