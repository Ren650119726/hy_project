package com.mockuai.itemcenter.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.domain.qto.ItemCategoryQTO;
import com.mockuai.itemcenter.core.dao.ItemCategoryDAO;
import com.mockuai.itemcenter.core.domain.ItemCategoryDO;

@Service
public class ItemCategoryDAOImpl extends SqlMapClientDaoSupport implements ItemCategoryDAO {

	public ItemCategoryDAOImpl() {
		super();
	}

	public Long addItemCategory(ItemCategoryDO itemCategoryDO) {
		Long newInsertedId = (Long) getSqlMapClientTemplate()
				.insert("ItemCategoryDAO.addItemCategory", itemCategoryDO);
		return newInsertedId;
	}

	public ItemCategoryDO getItemCategory(Long id) {
		ItemCategoryQTO qto = new ItemCategoryQTO();
		qto.setId(id);
		ItemCategoryDO record = (ItemCategoryDO) getSqlMapClientTemplate()
				.queryForObject("ItemCategoryDAO.getItemCategory", qto);
		return record;
	}

	public int deleteItemCategory(Long id) {
		ItemCategoryDO itemCategoryDO = new ItemCategoryDO();
		itemCategoryDO.setId(id);
		int rows = getSqlMapClientTemplate().update("ItemCategoryDAO.deleteItemCategory", itemCategoryDO);
		return rows;
	}

	public int updateItemCategory(ItemCategoryDO itemCategoryDO) {
		int rows = getSqlMapClientTemplate().update("ItemCategoryDAO.updateItemCategory", itemCategoryDO);
		return rows;
	}

	public List<ItemCategoryDO> queryItemCategory(ItemCategoryQTO ItemCategoryQTO) {
		Boolean needPaging = ItemCategoryQTO.getNeedPaging();
		if (null != needPaging && needPaging.booleanValue()) {
			Integer totalCount = (Integer) getSqlMapClientTemplate()
					.queryForObject("ItemCategoryDAO.countItemCategory", ItemCategoryQTO);// 总记录数
			ItemCategoryQTO.setTotalCount(totalCount);
			if (totalCount == 0) {
				return new ArrayList<ItemCategoryDO>();
			} else {
				ItemCategoryQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
			}
			totalCount = null;
		}
		List<ItemCategoryDO> list = getSqlMapClientTemplate()
				.queryForList("ItemCategoryDAO.queryItemCategory", ItemCategoryQTO);
		return list;
	}

	@Override
	public List<ItemCategoryDO> queryItemLeafCategory(String bizCode) {
		List<ItemCategoryDO> list = getSqlMapClientTemplate().queryForList(
				"ItemCategoryDAO.queryItemLeafCategory", bizCode);
		return list;
	}

	@Override
	public void updateGmtUsed(ItemCategoryDO itemCategoryDO) {
		getSqlMapClientTemplate().update("ItemCategoryDAO.updateGmtUsed", itemCategoryDO);
	}

//	protected class WhereParms extends ItemCategoryQTO {
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