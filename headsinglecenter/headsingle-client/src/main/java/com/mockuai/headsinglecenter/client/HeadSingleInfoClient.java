package com.mockuai.headsinglecenter.client;

import java.util.Date;
import java.util.List;

import com.mockuai.headsinglecenter.common.api.Response;

/**
 * Created by csy on 12/2/15.
 */
public interface HeadSingleInfoClient {
	Response<List<Long>> queryHeadSingInfo(String terminalType,	Date payTimeStart, Date payTimeEnd, String appKey);
}