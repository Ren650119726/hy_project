package com.mockuai.headsinglecenter.client;

import com.mockuai.headsinglecenter.common.api.Response;
import com.mockuai.headsinglecenter.common.domain.dto.HeadSingleSubDTO;

/**
 * Created by csy on 12/2/15.
 */
public interface HeadSingleSubClient {	
	Response<HeadSingleSubDTO> queryHeadSingleSub(Long id, String appKey);
	
	Response<HeadSingleSubDTO> addHeadSingleSub(HeadSingleSubDTO headSingleSubDTO, String appKey);

	Response<HeadSingleSubDTO> modifyHeadSingleSub(HeadSingleSubDTO headSingleSubDTO, String appKey);
	
	Response<HeadSingleSubDTO> queryHeadSingleSubById(Long id, String appKey);
}