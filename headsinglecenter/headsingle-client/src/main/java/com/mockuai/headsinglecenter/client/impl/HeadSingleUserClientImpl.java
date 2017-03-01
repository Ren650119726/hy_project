package com.mockuai.headsinglecenter.client.impl;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.headsinglecenter.client.HeadSingleUserClient;
import com.mockuai.headsinglecenter.common.api.BaseRequest;
import com.mockuai.headsinglecenter.common.api.HeadSingleService;
import com.mockuai.headsinglecenter.common.api.Request;
import com.mockuai.headsinglecenter.common.api.Response;
import com.mockuai.headsinglecenter.common.constant.ActionEnum;
import com.mockuai.headsinglecenter.common.domain.dto.HeadSingleSubDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;

/**
 * Created by csy on 12/2/15.
 */
public class HeadSingleUserClientImpl implements HeadSingleUserClient {
	@Resource
	private HeadSingleService headSingleService;
	
	/**
	 * 校验并查询是否符合首单用户
	 * 
	 * @author csy
	 * @Date 2016-07-20
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Response<HeadSingleSubDTO> queryJudgeHeadSingleUser(List<MarketItemDTO> marketItems, Long userId, String appKey) {
		Request request = new BaseRequest();
        request.setParam("marketItemList", marketItems);
        request.setParam("userId", userId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_HEADSINGLE_JUDGE_USER.getActionName());
        Response<HeadSingleSubDTO> response = headSingleService.execute(request);
        return response;
	}	
}