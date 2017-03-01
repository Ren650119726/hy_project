package com.mockuai.headsinglecenter.core.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.mockuai.headsinglecenter.core.dao.HeadSingleSubDAO;
import com.mockuai.headsinglecenter.core.domain.HeadSingleSubDO;

/**
 * Created by csy on 12/4/15.
 */
@Service
public class HeadSingleSubDaoImpl extends SqlMapClientDaoSupport implements HeadSingleSubDAO {

	@Override
	public HeadSingleSubDO queryHeadSingleSub(Long id) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("id", id);
		HeadSingleSubDO headSingleSubDO = (HeadSingleSubDO) getSqlMapClientTemplate().queryForObject("headsingle_sub.queryHeadSingleSub",map);
		return headSingleSubDO;
	}

	@Override
	public Long addHeadSingleSub(HeadSingleSubDO headSingleSubDO) {
		Long subId = (Long) getSqlMapClientTemplate().insert("headsingle_sub.addHeadSingleSub",headSingleSubDO);
		return subId;
	}

	@Override
	public int modifyHeadSingleSub(HeadSingleSubDO headSingleSubDO) {
		int result = (int) getSqlMapClientTemplate().update("headsingle_sub.modifyHeadSingleSub",headSingleSubDO);
		return result;
	}

	@Override
	public Integer updateHeadSingleDeleteMark() {
		Integer result = (Integer) getSqlMapClientTemplate().update("headsingle_sub.updateHeadSingleDeleteMark");
		return result;
	}

	@Override
	public HeadSingleSubDO queryHeadSingleSubById(Long id) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("id", id);
		HeadSingleSubDO headSingleSubDO = (HeadSingleSubDO) getSqlMapClientTemplate().queryForObject("headsingle_sub.queryHeadSingleSubById",map);
		return headSingleSubDO;
	}
}