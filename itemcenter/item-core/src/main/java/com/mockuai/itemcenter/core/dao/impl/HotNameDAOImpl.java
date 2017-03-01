package com.mockuai.itemcenter.core.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.dto.HotNameDTO;
import com.mockuai.itemcenter.core.dao.HotNameDAO;
import com.mockuai.itemcenter.core.domain.HotNameDO;

/**
 *   
 * @author huangsiqian
 * @version 2016年9月19日 下午12:17:10 
 */
@Service
public class HotNameDAOImpl extends SqlMapClientDaoSupport implements HotNameDAO{

	@Override
	public Long addHotName(HotNameDO hotNameDO) {
		Long newInserId = (Long)getSqlMapClientTemplate().insert("sku_hotname.addHotName", hotNameDO);
		return newInserId;
	}

	@Override
	public Integer updateHotName(HotNameDO hotNameDO) {
		Integer rows = getSqlMapClientTemplate().update("sku_hotname.updateHotName", hotNameDO);
		return rows;
	}

	@Override
	public List<HotNameDO> selectHotName(HotNameDTO hotNameDTO) {
		List<HotNameDO> list = getSqlMapClientTemplate().queryForList("sku_hotname.hotNameList");
		return list;
	}

	@Override
	public HotNameDO selectHotNameById(Long id) {
		Map map = new HashMap();
		map.put("id", id);
		HotNameDO hotNameDO = (HotNameDO) getSqlMapClientTemplate().queryForObject("sku_hotname.hotNameById", map);
		return hotNameDO;
	}

	@Override
	public Integer clickRateHotName(HotNameDO hotNameDO) {
		Integer rows = getSqlMapClientTemplate().update("sku_hotname.clickRateHotName", hotNameDO);
		return rows;
	}
	@Override
	public HotNameDO queryClimbObj(String climb ,Long indexSort ){
		Map paramMap = Maps.newHashMap();
		paramMap.put("climb",climb);
		paramMap.put("indexSort",indexSort);
		return (HotNameDO) getSqlMapClientTemplate().queryForObject("sku_hotname.queryClimbObj",paramMap);
	}

	@Override
	public Long hotNameCount() {
		return (Long) getSqlMapClientTemplate().queryForObject("sku_hotname.hotNameCount");
	}

	@Override
	public HotNameDO selectHotNameByName(String name) {
		Map map = new HashMap();
		map.put("hotName", name);
		HotNameDO hotNameDO = (HotNameDO) getSqlMapClientTemplate().queryForObject("sku_hotname.hotNameByName", map);
		return hotNameDO;
	}


}
