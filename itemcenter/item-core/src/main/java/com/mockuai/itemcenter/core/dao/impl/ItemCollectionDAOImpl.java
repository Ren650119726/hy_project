package com.mockuai.itemcenter.core.dao.impl;

import com.mockuai.itemcenter.common.domain.qto.ItemCollectionQTO;
import com.mockuai.itemcenter.core.dao.ItemCollectionDAO;
import com.mockuai.itemcenter.core.domain.ItemCollectionDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemCollectionDAOImpl extends SqlMapClientDaoSupport implements ItemCollectionDAO {

	public ItemCollectionDAOImpl() {
		super();
	}

	public Long addItemCollection(ItemCollectionDO itemCollectionDO) {
		Long newInsertedId = (Long) getSqlMapClientTemplate().insert("ItemCollectionDAO.addItemCollection", itemCollectionDO);
		return newInsertedId;
	}

	public int deleteItemCollectionByItemId(Long itemId,Long userId,Long shareUserId) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("itemId", itemId);
		params.put("shareUserId",shareUserId);
		int rows = getSqlMapClientTemplate().delete("ItemCollectionDAO.deleteItemCollectionByItemId", params);
		return rows;
	}

	public List<ItemCollectionDO> queryItemCollection(ItemCollectionQTO itemCollectionQTO) {
		//TODO 以下代码待重构，去掉totalCount查询逻辑
		if (null != itemCollectionQTO.getNeedPaging()) {
			Integer totalCount = (Integer) getSqlMapClientTemplate()
					.queryForObject("ItemCollectionDAO.countCollection", itemCollectionQTO);// 总记录数
			itemCollectionQTO.setOffsetAndTotalPage();
			itemCollectionQTO.setTotalCount(totalCount);
			if (totalCount == 0) {
				return new ArrayList<ItemCollectionDO>();
			}
		}
		List<ItemCollectionDO> list = getSqlMapClientTemplate()
				.queryForList("ItemCollectionDAO.queryItemCollectionList", itemCollectionQTO);
		return list;
	}

	protected class WhereParms extends ItemCollectionQTO {
		private Object record;

		public WhereParms(Object record) {
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}

	@Override
	public ItemCollectionDO getItemCollection(Long id, Long userId) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("id", id);
		ItemCollectionDO itemCollectionDO = (ItemCollectionDO)getSqlMapClientTemplate()
				.queryForObject("ItemCollectionDAO.getItemCollection", params);

		return itemCollectionDO;
	}

}