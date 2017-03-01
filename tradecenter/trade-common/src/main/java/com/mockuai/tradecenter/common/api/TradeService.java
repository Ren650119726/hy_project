package com.mockuai.tradecenter.common.api;

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.Response;

/**
 * @author zhangqiang.zeng
 */
public interface TradeService {
	@SuppressWarnings("unchecked")
	public Response execute(Request request);
}
