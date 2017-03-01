package com.mockuai.itemcenter.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.mockuai.itemcenter.core.domain.GlobalPropertyValueDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.domain.qto.GlobalPropertyValueQTO;
import com.mockuai.itemcenter.core.dao.GlobalPropertyValueDAO;
import com.mockuai.itemcenter.core.exception.ItemException;

@Service
public class GlobalPropertyValueDAOImpl extends SqlMapClientDaoSupport implements GlobalPropertyValueDAO {

	public GlobalPropertyValueDAOImpl() {
		super();
	}

	public Long addGlobalPropertyValue(GlobalPropertyValueDO globalPropertyValueDO) {
		globalPropertyValueDO.setIsDeleted(DBConst.UN_DELETED.getCode());
		Long newInsertedId = (Long) getSqlMapClientTemplate()
				.insert("GlobalPropertyValueDAO.addGlobalPropertyValue", globalPropertyValueDO);
		return newInsertedId;
	}

	public GlobalPropertyValueDO getGlobalPropertyValue(Long id) {
		GlobalPropertyValueQTO qto = new GlobalPropertyValueQTO();
		qto.setIsDeleted(DBConst.UN_DELETED.getCode());
		qto.setId(id);
		GlobalPropertyValueDO record = (GlobalPropertyValueDO) getSqlMapClientTemplate()
				.queryForObject("GlobalPropertyValueDAO.getGlobalPropertyValue", qto);
		return record;
	}

	public int deleteGlobalPropertyValue(Long id) {
		GlobalPropertyValueDO globalPropertyValueDO = new GlobalPropertyValueDO();
		globalPropertyValueDO.setIsDeleted(DBConst.DELETED.getCode());
		WhereParms parms = new WhereParms(globalPropertyValueDO);
		parms.setIsDeleted(DBConst.UN_DELETED.getCode());
		parms.setId(id);
		int rows = getSqlMapClientTemplate().update("GlobalPropertyValueDAO.deleteGlobalPropertyValue", parms);
		return rows;
	}

	public int deleteGlobalPropertyValueListByGlobalPropertyId(Long globalPropertyId) throws ItemException {
		GlobalPropertyValueDO globalPropertyValueDO = new GlobalPropertyValueDO();
		globalPropertyValueDO.setIsDeleted(DBConst.DELETED.getCode());
		WhereParms parms = new WhereParms(globalPropertyValueDO);
		parms.setIsDeleted(DBConst.UN_DELETED.getCode());
		parms.setGlobalPropertyId(globalPropertyId);
		int rows = getSqlMapClientTemplate().update("GlobalPropertyValueDAO.deleteGlobalPropertyValue", parms);
		return rows;
	}

	public int updateGlobalPropertyValue(GlobalPropertyValueDO globalPropertyValueDO) {
		WhereParms parms = new WhereParms(globalPropertyValueDO);
		parms.setIsDeleted(DBConst.UN_DELETED.getCode());
		parms.setId(globalPropertyValueDO.getId());
		int rows = getSqlMapClientTemplate().update("GlobalPropertyValueDAO.updateGlobalPropertyValue", parms);
		return rows;
	}

	public List<GlobalPropertyValueDO> queryGlobalPropertyValue(GlobalPropertyValueQTO globalPropertyValueQTO) {
		globalPropertyValueQTO.setIsDeleted(DBConst.UN_DELETED.getCode());
		if (null != globalPropertyValueQTO.getNeedPaging()) {
			Integer totalCount = (Integer) getSqlMapClientTemplate()
					.queryForObject("GlobalPropertyValueDAO.countGlobalPropertyValue", globalPropertyValueQTO);// 总记录数
			globalPropertyValueQTO.setTotalCount(totalCount);
			if (totalCount == 0) {
				return new ArrayList<GlobalPropertyValueDO>();
			} else {
				globalPropertyValueQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
			}
			totalCount = null;
		}
		List<GlobalPropertyValueDO> list = getSqlMapClientTemplate()
				.queryForList("GlobalPropertyValueDAO.queryGlobalPropertyValue", globalPropertyValueQTO);
		return list;
	}

	protected class WhereParms extends GlobalPropertyValueQTO {
		private Object record;

		public WhereParms(Object record) {
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}

}