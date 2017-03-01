package com.mockuai.itemcenter.core.dao.impl;

import java.util.List;

import com.mockuai.itemcenter.core.dao.SpuPropertyDAO;
import com.mockuai.itemcenter.core.domain.SpuPropertyDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.domain.qto.SpuPropertyQTO;

@Service
public class SpuPropertyDAOImpl extends SqlMapClientDaoSupport implements SpuPropertyDAO {

	public SpuPropertyDAOImpl() {
		super();
	}

	public Long addSpuProperty(SpuPropertyDO spuPropertyDO) {
		spuPropertyDO.setIsDeleted(DBConst.UN_DELETED.getCode());
		Long newInsertedId = (Long) getSqlMapClientTemplate().insert("SpuPropertyDAO.addSpuProperty", spuPropertyDO);
		return newInsertedId;
	}

	public SpuPropertyDO getSpuProperty(Long id) {
		SpuPropertyDO record = (SpuPropertyDO) getSqlMapClientTemplate()
				.queryForObject("SpuPropertyDAO.getSpuProperty", id);
		return record;
	}

	public int updateSpuProperty(SpuPropertyDO spuPropertyDO) {
		int rows = getSqlMapClientTemplate().update("SpuPropertyDAO.updateSpuProperty", spuPropertyDO);
		return rows;
	}

	public List<SpuPropertyDO> querySpuProperty(SpuPropertyQTO SpuPropertyQTO) {
		List<SpuPropertyDO> list = getSqlMapClientTemplate()
				.queryForList("SpuPropertyDAO.querySpuProperty", SpuPropertyQTO);
		return list;
	}

}