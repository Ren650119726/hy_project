package com.mockuai.itemcenter.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.mockuai.itemcenter.core.dao.SalesFieldDAO;
import com.mockuai.itemcenter.core.domain.SalesFieldDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.domain.qto.SalesFieldQTO;

@Service
public class SalesFieldDAOImpl extends SqlMapClientDaoSupport implements SalesFieldDAO {

	public SalesFieldDAOImpl() {
		super();
	}

	public Integer addSalesField(SalesFieldDO salesFieldDO) {
		salesFieldDO.setIsDeleted(DBConst.UN_DELETED.getCode());
		Integer newInsertedId = (Integer) getSqlMapClientTemplate()
				.insert("SalesFieldDAO.addSalesField", salesFieldDO);
		return newInsertedId;
	}

	public SalesFieldDO getSalesField(Integer id) {
		SalesFieldQTO qto = new SalesFieldQTO();
		qto.setIsDeleted(DBConst.UN_DELETED.getCode());
		qto.setId(id);
		SalesFieldDO record = (SalesFieldDO) getSqlMapClientTemplate()
				.queryForObject("SalesFieldDAO.getSalesField", qto);
		return record;
	}

	public int deleteSalesField(Integer id) {
		SalesFieldDO salesFieldDO = new SalesFieldDO();
		salesFieldDO.setIsDeleted(DBConst.DELETED.getCode());
		WhereParms parms = new WhereParms(salesFieldDO);
		parms.setIsDeleted(DBConst.UN_DELETED.getCode());
		parms.setId(id);
		int rows = getSqlMapClientTemplate().update("SalesFieldDAO.deleteSalesField", parms);
		return rows;
	}

	public int updateSalesField(SalesFieldDO salesFieldDO) {
		WhereParms parms = new WhereParms(salesFieldDO);
		parms.setIsDeleted(DBConst.UN_DELETED.getCode());
		parms.setId(salesFieldDO.getId());
		int rows = getSqlMapClientTemplate().update("SalesFieldDAO.updateSalesField", parms);
		return rows;
	}

	public List<SalesFieldDO> querySalesField(SalesFieldQTO salesFieldQTO) {
		salesFieldQTO.setIsDeleted(DBConst.UN_DELETED.getCode());
		if (null != salesFieldQTO.getNeedPaging()) {
			Integer totalCount = (Integer) getSqlMapClientTemplate()
					.queryForObject("SalesFieldDAO.countSalesField", salesFieldQTO);// 总记录数
			salesFieldQTO.setTotalCount(totalCount);
			if (totalCount == 0) {
				return new ArrayList<SalesFieldDO>();
			} else {
				salesFieldQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
			}
			totalCount = null;
		}
		List<SalesFieldDO> list = getSqlMapClientTemplate()
				.queryForList("SalesFieldDAO.querySalesField", salesFieldQTO);
		return list;
	}

	protected class WhereParms extends SalesFieldQTO {
		private Object record;

		public WhereParms(Object record) {
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}

}