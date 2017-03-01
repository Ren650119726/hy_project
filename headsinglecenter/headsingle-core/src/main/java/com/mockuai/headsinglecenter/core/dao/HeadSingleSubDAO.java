package com.mockuai.headsinglecenter.core.dao;

import org.springframework.stereotype.Service;

import com.mockuai.headsinglecenter.core.domain.HeadSingleSubDO;

/**
 * Created by csy on 12/4/15.
 */
@Service
public interface HeadSingleSubDAO {
	HeadSingleSubDO queryHeadSingleSub(Long id);

	Long addHeadSingleSub(HeadSingleSubDO headSingleSubDO);

	int modifyHeadSingleSub(HeadSingleSubDO headSingleSubDO);

	Integer updateHeadSingleDeleteMark();

	HeadSingleSubDO queryHeadSingleSubById(Long id);   
}