package com.mockuai.distributioncenter.core.manager;

import com.mockuai.headsinglecenter.common.domain.dto.HeadSingleSubDTO;

/**
 * Created by yindingyu on 16/3/16.
 */
public interface HeadsingleManager {

	HeadSingleSubDTO queryHeadSingleSubById(Long id, String appKey);
}
