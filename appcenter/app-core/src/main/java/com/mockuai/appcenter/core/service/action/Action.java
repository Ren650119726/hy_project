package com.mockuai.appcenter.core.service.action;

import com.mockuai.appcenter.common.api.AppResponse;
import com.mockuai.appcenter.core.exception.AppException;
import com.mockuai.appcenter.core.service.RequestContext;
import org.springframework.stereotype.Service;

/**
 * 操作对象基类
 * @author wujin.zzq
 *
 */
@Service
public interface Action {
	
	public AppResponse execute(RequestContext context);
	
	public String getName(); 
}
