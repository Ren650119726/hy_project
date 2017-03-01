package com.mockuai.toolscenter.core.service.action;

import com.mockuai.toolscenter.common.api.ToolsResponse;
import com.mockuai.toolscenter.core.exception.ToolsException;
import com.mockuai.toolscenter.core.service.RequestContext;
import org.springframework.stereotype.Service;

/**
 * 操作对象基类
 * @author wujin.zzq
 *
 */
@Service
public interface Action {
	
	public ToolsResponse execute(RequestContext context) throws ToolsException;
	
	public String getName(); 
}
