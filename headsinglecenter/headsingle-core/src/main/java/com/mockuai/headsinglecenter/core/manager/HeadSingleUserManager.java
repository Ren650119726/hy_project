package com.mockuai.headsinglecenter.core.manager;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mockuai.headsinglecenter.common.domain.dto.HeadSingleSubDTO;
import com.mockuai.headsinglecenter.common.domain.dto.HeadSingleUserDTO;
import com.mockuai.headsinglecenter.core.exception.HeadSingleException;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;


/**
 * Created by csy 2016-07-13
 */
@Service
public interface HeadSingleUserManager {
	HeadSingleSubDTO queryJudgeHeadSingleUser(Long userId,	List<MarketItemDTO> marketItems, String appKey) throws HeadSingleException;
	
	Long addHeadSingleUser(HeadSingleUserDTO headSingleUserDTO) throws HeadSingleException;

	Integer updateHeadSingleOrderCount(HeadSingleUserDTO headSingleUserDTO) throws HeadSingleException;
}
