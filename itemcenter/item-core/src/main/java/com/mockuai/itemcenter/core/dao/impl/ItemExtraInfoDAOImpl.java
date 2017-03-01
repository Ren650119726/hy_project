package com.mockuai.itemcenter.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.domain.qto.ItemExtraInfoQTO;
import com.mockuai.itemcenter.core.dao.ItemExtraInfoDAO;
import com.mockuai.itemcenter.core.domain.ItemExtraInfoDO;

@Service
public class ItemExtraInfoDAOImpl extends SqlMapClientDaoSupport implements ItemExtraInfoDAO {

	public ItemExtraInfoDAOImpl() {
		super();
	}

	public Long addItemExtraInfo(ItemExtraInfoDO itemExtraInfoDO) {
		itemExtraInfoDO.setIsDeleted(DBConst.UN_DELETED.getCode());
		Long newInsertedId = (Long) getSqlMapClientTemplate()
				.insert("ItemExtraInfoDAO.addItemExtraInfo", itemExtraInfoDO);
		return newInsertedId;
	}

	public ItemExtraInfoDO getItemExtraInfo(Long id, Long sellerId) {
		ItemExtraInfoQTO qto = new ItemExtraInfoQTO();
		qto.setIsDeleted(DBConst.UN_DELETED.getCode());
		qto.setSupplierId(sellerId);
		qto.setId(id);
		ItemExtraInfoDO record = (ItemExtraInfoDO) getSqlMapClientTemplate()
				.queryForObject("ItemExtraInfoDAO.getItemExtraInfo", qto);
		return record;
	}

	public int updateItemExtraInfo(ItemExtraInfoDO itemExtraInfoDO) {
		WhereParms parms = new WhereParms(itemExtraInfoDO);
		parms.setIsDeleted(DBConst.UN_DELETED.getCode());
		parms.setId(itemExtraInfoDO.getId());
		parms.setSupplierId(itemExtraInfoDO.getSupplierId());
		int rows = getSqlMapClientTemplate().update("ItemExtraInfoDAO.updateItemExtraInfo", parms);
		return rows;
	}

	public List<ItemExtraInfoDO> queryItemExtraInfo(ItemExtraInfoQTO itemExtraInfoQTO) {
		itemExtraInfoQTO.setIsDeleted(DBConst.UN_DELETED.getCode());
		if (null != itemExtraInfoQTO.getNeedPaging()) {
			Integer totalCount = (Integer) getSqlMapClientTemplate()
					.queryForObject("ItemExtraInfoDAO.countItemExtraInfo", itemExtraInfoQTO);// 总记录数
			itemExtraInfoQTO.setTotalCount(totalCount);
			if (totalCount == 0) {
				return new ArrayList<ItemExtraInfoDO>();
			} else {
				itemExtraInfoQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
			}
		}
		List<ItemExtraInfoDO> list = getSqlMapClientTemplate()
				.queryForList("ItemExtraInfoDAO.queryItemExtraInfoList", itemExtraInfoQTO);
		return list;
	}

	public int deleteItemExtraInfo(Long id, Long sellerId) {
		ItemExtraInfoDO itemExtraInfoDO = new ItemExtraInfoDO();
		itemExtraInfoDO.setIsDeleted(DBConst.DELETED.getCode());
		WhereParms parms = new WhereParms(itemExtraInfoDO);
		parms.setIsDeleted(DBConst.UN_DELETED.getCode());
		parms.setId(id);
		parms.setSupplierId(sellerId);
		int rows = getSqlMapClientTemplate().delete("ItemExtraInfoDAO.deleteItemExtraInfo", parms);
		return rows;
	}

	protected class WhereParms extends ItemExtraInfoQTO {
		private Object record;

		public WhereParms(Object record) {
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}

}