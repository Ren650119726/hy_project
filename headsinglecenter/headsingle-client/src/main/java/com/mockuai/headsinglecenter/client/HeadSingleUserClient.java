package com.mockuai.headsinglecenter.client;

import java.util.List;

import com.mockuai.headsinglecenter.common.api.Response;
import com.mockuai.headsinglecenter.common.domain.dto.HeadSingleSubDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;

/**
 * Created by csy on 12/2/15.
 */
public interface HeadSingleUserClient {
	Response<HeadSingleSubDTO> queryJudgeHeadSingleUser(List<MarketItemDTO> marketItems, Long userId, String appKey);
}