package com.mockuai.itemcenter.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.domain.qto.SkuPropertyTmplQTO;
import com.mockuai.itemcenter.core.dao.SkuPropertyTmplDAO;
import com.mockuai.itemcenter.core.domain.SkuPropertyTmplDO;

@Service
public class SkuPropertyTmplDAOImpl extends SqlMapClientDaoSupport implements SkuPropertyTmplDAO {

	public SkuPropertyTmplDAOImpl() {
		super();
	}

	public Long addSkuPropertyTmpl(SkuPropertyTmplDO skuPropertyTmplDO) {
		Long newInsertedId = (Long) getSqlMapClientTemplate()
				.insert("SkuPropertyTmplDAO.addSkuPropertyTmpl", skuPropertyTmplDO);
		return newInsertedId;
	}

	public SkuPropertyTmplDO getSkuPropertyTmpl(Long id) {
		SkuPropertyTmplDO qto = new SkuPropertyTmplDO();
		qto.setId(id);
		SkuPropertyTmplDO record = (SkuPropertyTmplDO) getSqlMapClientTemplate()
				.queryForObject("SkuPropertyTmplDAO.getSkuPropertyTmpl", qto);
		return record;
	}

	public int deleteSkuPropertyTmpl(Long id) {
		SkuPropertyTmplDO skuPropertyTmplDO = new SkuPropertyTmplDO();
		skuPropertyTmplDO.setId(id);
		int rows = getSqlMapClientTemplate().update("SkuPropertyTmplDAO.deleteSkuPropertyTmpl", skuPropertyTmplDO);
		return rows;
	}

	public int updateSkuPropertyTmpl(SkuPropertyTmplDO skuPropertyTmplDO) {
		int rows = getSqlMapClientTemplate().update("SkuPropertyTmplDAO.updateSkuPropertyTmpl", skuPropertyTmplDO);
		return rows;
	}

	public List<SkuPropertyTmplDO> querySkuPropertyTmpl(SkuPropertyTmplQTO skuPropertyTmplQTO) {
		if (null != skuPropertyTmplQTO.getNeedPaging()) {
			Integer totalCount = (Integer) getSqlMapClientTemplate()
					.queryForObject("SkuPropertyTmplDAO.countSkuPropertyTmpl", skuPropertyTmplQTO);// 总记录数
			skuPropertyTmplQTO.setTotalCount(totalCount);
			if (totalCount == 0) {
				return new ArrayList<SkuPropertyTmplDO>();
			} else {
				skuPropertyTmplQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
			}
		}
		List<SkuPropertyTmplDO> list = getSqlMapClientTemplate()
				.queryForList("SkuPropertyTmplDAO.querySkuPropertyTmpl", skuPropertyTmplQTO);
		return list;
	}
	
	public List<SkuPropertyTmplDO> querySkuPropertyTmplWithValue(SkuPropertyTmplQTO skuPropertyTmplQTO){
//		if (null != skuPropertyTmplQTO.getNeedPaging()) {
//			Integer totalCount = (Integer) getSqlMapClientTemplate()
//					.queryForObject("SkuPropertyTmplDAO.countSkuPropertyTmpl", skuPropertyTmplQTO);// 总记录数
//			skuPropertyTmplQTO.setTotalCount(totalCount);
//			if (totalCount == 0) {
//				return new ArrayList<SkuPropertyTmplDO>();
//			} else {
//				skuPropertyTmplQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
//			}
//		}
		List<SkuPropertyTmplDO> list = getSqlMapClientTemplate()
				.queryForList("SkuPropertyTmplDAO.querySkuPropertyTmplWithValue", skuPropertyTmplQTO);
		return list;
	}


//	protected class WhereParms extends SkuPropertyTmplQTO {
//		private Object record;
//
//		public WhereParms(Object record) {
//			this.record = record;
//		}
//
//		public Object getRecord() {
//			return record;
//		}
//	}
}