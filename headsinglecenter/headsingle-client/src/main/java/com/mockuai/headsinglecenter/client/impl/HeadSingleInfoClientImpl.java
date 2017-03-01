package com.mockuai.headsinglecenter.client.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.mockuai.headsinglecenter.client.HeadSingleInfoClient;
import com.mockuai.headsinglecenter.common.api.BaseRequest;
import com.mockuai.headsinglecenter.common.api.HeadSingleService;
import com.mockuai.headsinglecenter.common.api.Request;
import com.mockuai.headsinglecenter.common.api.Response;
import com.mockuai.headsinglecenter.common.constant.ActionEnum;

/**
 * Created by csy on 12/2/15.
 */
public class HeadSingleInfoClientImpl implements HeadSingleInfoClient {
	@Resource
	private HeadSingleService headSingleService;	
	
	/**
	 * 查询符合订单的信息
	 * 
	 * @author csy
	 * @Date 2016-07-20
	 */
	@SuppressWarnings("unchecked")
	public Response<List<Long>> queryHeadSingInfo(String terminalType, Date payTimeStart, Date payTimeEnd, String appKey) {
		Request request = new BaseRequest();
        request.setParam("terminalType", terminalType);
        request.setParam("payTimeStart", payTimeStart);
        request.setParam("payTimeEnd", payTimeEnd);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_HEADSINGLE_INFO.getActionName());
        Response<List<Long>> response = headSingleService.execute(request);
        return response;
	}	
}