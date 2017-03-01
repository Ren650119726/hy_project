package com.mockuai.itemcenter.core.dao.impl;

import java.util.List;

import com.mockuai.itemcenter.core.dao.SpuInfoDAO;
import com.mockuai.itemcenter.core.domain.SpuInfoDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.domain.qto.SpuInfoQTO;

@Service
public class SpuInfoDAOImpl extends SqlMapClientDaoSupport implements SpuInfoDAO {

	public SpuInfoDAOImpl() {
		super();
	}

	public Long addSpuInfo(SpuInfoDO spuInfoDO) {
		spuInfoDO.setIsDeleted(DBConst.UN_DELETED.getCode());
		Long newInsertedId = (Long) getSqlMapClientTemplate().insert("SpuInfoDAO.addSpuInfo", spuInfoDO);
		return newInsertedId;
	}

	public SpuInfoDO getSpuInfo(Long id) {
		SpuInfoDO record = (SpuInfoDO) getSqlMapClientTemplate().queryForObject("SpuInfoDAO.getSpuInfo", id);
		return record;
	}

	public int updateSpuInfo(SpuInfoDO spuInfoDO) {
		int rows = getSqlMapClientTemplate().update("SpuInfoDAO.updateSpuInfo", spuInfoDO);
		return rows;
	}

	public List<SpuInfoDO> querySpuInfo(SpuInfoQTO SpuInfoQTO) {
		List<SpuInfoDO> list = getSqlMapClientTemplate().queryForList("SpuInfoDAO.querySpuInfo", SpuInfoQTO);
		return list;
	}

}