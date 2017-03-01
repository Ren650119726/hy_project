package com.mockuai.headsinglecenter.core.dao;

import java.util.Date;
import java.util.List;

import com.mockuai.headsinglecenter.core.domain.HeadSingleInfoDO;

/**
 * Created by csy on 12/4/15.
 */
public interface HeadSingleInfoDAO {
	List<HeadSingleInfoDO> queryHeadSingleInfos(Integer terminalType, Date payTimeStart, Date payTimeEnd);

	HeadSingleInfoDO queryInfoDtoByOrderId(Long orderId);

	Long addHeadSingleInfo(HeadSingleInfoDO headSingleInfoDO);
}