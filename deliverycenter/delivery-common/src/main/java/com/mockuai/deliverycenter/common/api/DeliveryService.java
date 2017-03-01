package com.mockuai.deliverycenter.common.api;

import com.mockuai.deliverycenter.common.api.Request;
import com.mockuai.deliverycenter.common.api.Response;

/**
 * @author zhangqiang.zeng
 */
public interface DeliveryService {
	@SuppressWarnings("unchecked")
	public Response execute(Request request);
}
