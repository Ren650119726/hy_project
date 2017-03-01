package com.mockuai.dts.common.api;


import com.mockuai.dts.common.api.action.Request;
import com.mockuai.dts.common.api.action.Response;

/**
 * @author luliang
 */
public interface DtsService {

	public Response execute(Request request);

}
