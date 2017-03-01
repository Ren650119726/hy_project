package com.mockuai.tradecenter.core.service.action;

import org.springframework.stereotype.Service;

import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.core.service.RequestContext;

/**
 * 操作对像基类
 * @author wujin.zzq
 *
 */
@Service
public interface Action {
	
	@SuppressWarnings("rawtypes")
	public TradeResponse execute(RequestContext context) throws TradeException;
	
	public String getName(); 
}
