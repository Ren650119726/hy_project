package com.mockuai.rainbowcenter.core.service.action;

import com.mockuai.rainbowcenter.common.api.RainbowResponse;
import com.mockuai.rainbowcenter.core.service.RequestContext;
import org.springframework.stereotype.Service;

/**
 * 操作对象基类
 * @author wujin.zzq
 *
 */
@Service
public interface Action {
	
	public RainbowResponse execute(RequestContext context);
	
	public String getName(); 
}
