package com.mockuai.headsinglecenter.core.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.mockuai.headsinglecenter.core.dao.HeadSingleUserDAO;
import com.mockuai.headsinglecenter.core.domain.HeadSingleSubDO;
import com.mockuai.headsinglecenter.core.domain.HeadSingleUserDO;

/**
 * Created by edgar.zr on 12/4/15.
 */
@Service
public class HeadSingleUserDaoImpl extends SqlMapClientDaoSupport implements HeadSingleUserDAO {

	@Override
	public HeadSingleUserDO queryJudgeHeadSingleUser(Long userId) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("userId", userId);
		HeadSingleUserDO headSingleUserDO = (HeadSingleUserDO) getSqlMapClientTemplate().queryForObject("headsingle_user.queryJudgeHeadSingleUser",map);
		return headSingleUserDO;
	}

	@Override
	public Long addHeadSingleUser(HeadSingleUserDO headSingleDo) {
		Long hsUserId = (Long) getSqlMapClientTemplate().insert("headsingle_user.addHeadSingleUser",headSingleDo);
		return hsUserId;
	}

	@Override
	public Integer updateHeadSingleOrderCount(Map<Object, Object> map) {		
		Integer result = (Integer) getSqlMapClientTemplate().update("headsingle_user.updateHeadSingleOrderCount",map);
		return result;
	}

	@Override
	public HeadSingleUserDO queryJudgeHeadSingle(Long userId) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("userId", userId);
		HeadSingleUserDO headSingleUserDO = (HeadSingleUserDO) getSqlMapClientTemplate().queryForObject("headsingle_user.queryJudgeHeadSingle",map);
		return headSingleUserDO;
	}
}