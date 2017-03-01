package com.mockuai.itemcenter.core.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.domain.qto.SpuPropertyTmplQTO;
import com.mockuai.itemcenter.core.dao.SpuPropertyTmplDAO;
import com.mockuai.itemcenter.core.domain.SpuPropertyTmplDO;

@Service
public class SpuPropertyTmplDAOImpl extends SqlMapClientDaoSupport implements SpuPropertyTmplDAO {

	public SpuPropertyTmplDAOImpl() {
		super();
	}

	public Long addSpuPropertyTmpl(SpuPropertyTmplDO spuPropertyTmplDO) {
		spuPropertyTmplDO.setIsDeleted(DBConst.UN_DELETED.getCode());
		Long newInsertedId = (Long) getSqlMapClientTemplate()
				.insert("SpuPropertyTmplDAO.addSpuPropertyTmpl", spuPropertyTmplDO);
		return newInsertedId;
	}

	public SpuPropertyTmplDO getSpuPropertyTmpl(Long id) {
		SpuPropertyTmplDO record = (SpuPropertyTmplDO) getSqlMapClientTemplate()
				.queryForObject("SpuPropertyTmplDAO.getSpuPropertyTmpl", id);
		return record;
	}

	public int updateSpuPropertyTmpl(SpuPropertyTmplDO spuPropertyTmplDO) {
		int rows = getSqlMapClientTemplate().update("SpuPropertyTmplDAO.updateSpuPropertyTmpl", spuPropertyTmplDO);
		return rows;
	}

	public List<SpuPropertyTmplDO> querySpuPropertyTmpl(SpuPropertyTmplQTO spuPropertyTmplQTO) {
		List<SpuPropertyTmplDO> list = getSqlMapClientTemplate()
				.queryForList("SpuPropertyTmplDAO.querySpuPropertyTmpl", spuPropertyTmplQTO);
		return list;
	}

}