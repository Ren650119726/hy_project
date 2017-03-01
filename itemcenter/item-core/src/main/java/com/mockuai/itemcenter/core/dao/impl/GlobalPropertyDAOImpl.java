package com.mockuai.itemcenter.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.domain.qto.GlobalPropertyQTO;
import com.mockuai.itemcenter.core.dao.GlobalPropertyDAO;
import com.mockuai.itemcenter.core.domain.GlobalPropertyDO;

@Service
public class GlobalPropertyDAOImpl extends SqlMapClientDaoSupport implements GlobalPropertyDAO {

	public GlobalPropertyDAOImpl() {
		super();
	}

	public Long addGlobalProperty(GlobalPropertyDO globalPropertyDO) {
		globalPropertyDO.setIsDeleted(DBConst.UN_DELETED.getCode());
		Long newInsertedId = (Long) getSqlMapClientTemplate()
				.insert("GlobalPropertyDAO.addGlobalProperty", globalPropertyDO);
		return newInsertedId;
	}

	public GlobalPropertyDO getGlobalProperty(Long id) {
		GlobalPropertyQTO qto = new GlobalPropertyQTO();
		qto.setIsDeleted(DBConst.UN_DELETED.getCode());
		qto.setId(id);
		GlobalPropertyDO record = (GlobalPropertyDO) getSqlMapClientTemplate()
				.queryForObject("GlobalPropertyDAO.getGlobalProperty", qto);
		return record;
	}

	public int deleteGlobalProperty(Long id) {
		GlobalPropertyDO globalPropertyDO = new GlobalPropertyDO();
		globalPropertyDO.setIsDeleted(DBConst.DELETED.getCode());
		WhereParms parms = new WhereParms(globalPropertyDO);
		parms.setIsDeleted(DBConst.UN_DELETED.getCode());
		parms.setId(id);
		int rows = getSqlMapClientTemplate().update("GlobalPropertyDAO.deleteGlobalProperty", parms);
		return rows;
	}

	public int updateGlobalProperty(GlobalPropertyDO globalPropertyDO) {
		WhereParms parms = new WhereParms(globalPropertyDO);
		parms.setIsDeleted(DBConst.UN_DELETED.getCode());
		parms.setId(globalPropertyDO.getId());
		int rows = getSqlMapClientTemplate().update("GlobalPropertyDAO.updateGlobalProperty", parms);
		return rows;
	}

	public List<GlobalPropertyDO> queryGlobalProperty(GlobalPropertyQTO globalPropertyQTO) {
		globalPropertyQTO.setIsDeleted(DBConst.UN_DELETED.getCode());
		if (null != globalPropertyQTO.getNeedPaging()) {
			Integer totalCount = (Integer) getSqlMapClientTemplate()
					.queryForObject("GlobalPropertyDAO.countGlobalProperty", globalPropertyQTO);// 总记录数
			globalPropertyQTO.setTotalCount(totalCount);
			if (totalCount == 0) {
				return new ArrayList<GlobalPropertyDO>();
			} else {
				globalPropertyQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
			}
			totalCount = null;
		}
		List<GlobalPropertyDO> list = getSqlMapClientTemplate()
				.queryForList("GlobalPropertyDAO.queryGlobalProperty", globalPropertyQTO);
		return list;
	}

	protected class WhereParms extends GlobalPropertyQTO {
		private Object record;

		public WhereParms(Object record) {
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}

}