package com.mockuai.headsinglecenter.core.dao;

import java.util.Map;

import com.mockuai.headsinglecenter.core.domain.HeadSingleUserDO;

/**
 * Created by csy on 12/4/15.
 */
public interface HeadSingleUserDAO {
	HeadSingleUserDO queryJudgeHeadSingleUser(Long userId);

	Long addHeadSingleUser(HeadSingleUserDO headSingleDo);

	Integer updateHeadSingleOrderCount(Map<Object, Object> map);
	
	HeadSingleUserDO queryJudgeHeadSingle(Long userId);
}