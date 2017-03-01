package com.mockuai.itemcenter.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.mockuai.itemcenter.core.dao.ItemImageDAO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.domain.qto.ItemImageQTO;
import com.mockuai.itemcenter.core.domain.ItemImageDO;

@Service
public class ItemImageDAOImpl extends SqlMapClientDaoSupport implements ItemImageDAO {

	public ItemImageDAOImpl() {
		super();
	}

	public Long addItemImage(ItemImageDO itemImageDO) {
		Long newInsertedId = (Long) getSqlMapClientTemplate().insert("ItemImageDAO.addItemImage", itemImageDO);
		return newInsertedId;
	}

	public ItemImageDO getItemImage(Long id, Long sellerId) {
		ItemImageQTO qto = new ItemImageQTO();
		qto.setSellerId(sellerId);
		qto.setId(id);
		ItemImageDO record = (ItemImageDO) getSqlMapClientTemplate()
				.queryForObject("ItemImageDAO.getItemImage", qto);
		return record;
	}

	public int deleteItemImage(Long id, Long sellerId) {
		ItemImageDO itemImageDO = new ItemImageDO();
		itemImageDO.setId(id);
		itemImageDO.setSellerId(sellerId);
		int rows = getSqlMapClientTemplate().update("ItemImageDAO.deleteItemImage", itemImageDO);
		return rows;
	}

	public int updateItemImage(ItemImageDO itemImageDO) {
		int rows = getSqlMapClientTemplate().update("ItemImageDAO.updateItemImage", itemImageDO);
		return rows;
	}

	public List<ItemImageDO> queryItemImage(ItemImageQTO itemImageQTO) {
		if (null != itemImageQTO.getNeedPaging()) {
			Integer totalCount = (Integer) getSqlMapClientTemplate()
					.queryForObject("ItemDAO.countItem", itemImageQTO);// 总记录数
			itemImageQTO.setTotalCount(totalCount);
			if (totalCount == 0) {
				return new ArrayList<ItemImageDO>();
			} else {
				itemImageQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
			}
		}
		List<ItemImageDO> list = getSqlMapClientTemplate()
				.queryForList("ItemImageDAO.queryItemImage", itemImageQTO);
		return list;
	}

	@Override
	public Long trashItemImageListByItemId(ItemImageDO query) {
		return Long.valueOf(getSqlMapClientTemplate().update("ItemImageDAO.trashItemImage", query));
	}

	@Override
	public Long recoveryItemImageListByItemId(ItemImageDO query) {
		return Long.valueOf(getSqlMapClientTemplate().update("ItemImageDAO.recoveryItemImage", query));
	}

	@Override
	public Long emptyRecycleBin(ItemImageDO query) {
		return Long.valueOf(getSqlMapClientTemplate().update("ItemImageDAO.emptyRecycleBin", query));
	}

	public int deleteItemImageListByItemId(Long itemId, Long sellerId) {
		ItemImageDO itemImageDO = new ItemImageDO();
		itemImageDO.setItemId(itemId);
		itemImageDO.setSellerId(sellerId);
		int rows = getSqlMapClientTemplate().update("ItemImageDAO.deleteItemImage", itemImageDO);
		return rows;
	}

//	protected class WhereParms extends ItemImageQTO {
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