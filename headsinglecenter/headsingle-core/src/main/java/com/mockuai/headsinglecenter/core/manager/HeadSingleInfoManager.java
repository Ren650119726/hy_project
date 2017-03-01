package com.mockuai.headsinglecenter.core.manager;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mockuai.headsinglecenter.common.domain.dto.HeadSingleInfoDTO;
import com.mockuai.headsinglecenter.core.exception.HeadSingleException;

/**
 * Created by csy 2016-07-13
 */
@Service
public interface HeadSingleInfoManager {
	List<Long> queryOrderIdList(String terminalType, Date payTimeStart,	Date payTimeEnd) throws HeadSingleException;

	Long addHeadSingleInfo(HeadSingleInfoDTO headSingleInfoDTO) throws HeadSingleException;
}
