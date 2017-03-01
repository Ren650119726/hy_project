package com.mockuai.itemcenter.core.dao.impl;

import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.core.dao.ItemDAO;
import com.mockuai.itemcenter.core.domain.ItemDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemDAOImpl extends SqlMapClientDaoSupport implements ItemDAO {

	public ItemDAOImpl() {
		super();
	}

	public Long  addItem(ItemDO itemDO) {
		Long newInsertedId = (Long) getSqlMapClientTemplate().insert("ItemDAO.addItem", itemDO);
		return newInsertedId;
	}

	public ItemDO getItem(Long id, Long supplierId, String bizCode) {
		ItemDO itemDO = new ItemDO();
		itemDO.setSellerId(supplierId);
		itemDO.setId(id);
		itemDO.setBizCode(bizCode);
		ItemDO record = (ItemDO) getSqlMapClientTemplate().queryForObject("ItemDAO.getItem", itemDO);
		return record;
	}

	public int deleteItem(Long id, Long supplierId, String bizCode) {
		ItemDO itemDO = new ItemDO();
		itemDO.setId(id);
		itemDO.setSellerId(supplierId);
		itemDO.setBizCode(bizCode);

		int rows = getSqlMapClientTemplate().delete("ItemDAO.deleteItem", itemDO);
		return rows;
	}

	public int updateItem(ItemDO itemDO) {
//		WhereParms parms = new WhereParms(itemDO);
//		parms.setId(itemDO.getId());
//		parms.setSellerId(itemDO.getSellerId());
		int rows = getSqlMapClientTemplate().update("ItemDAO.updateItem", itemDO);
		return rows;
	}

	@Override
	public int removeItemFromGroup(ItemDO itemDO) {
		int rows = getSqlMapClientTemplate().update("ItemDAO.removeItemFromGroup", itemDO);
		return rows;
	}

	public int removeItemToDefaultGroup(ItemDO itemDO) {
		int rows = getSqlMapClientTemplate().update("ItemDAO.removeItemToDefaultGroup", itemDO);
		return rows;
	}

	@Override
	public long countItem(ItemQTO itemQTO) {
		Long totalCount = (Long) getSqlMapClientTemplate().queryForObject("ItemDAO.countItem", itemQTO);
		return totalCount;
	}

	public List<ItemDO> queryItem(ItemQTO itemQTO) {
		if (null != itemQTO.getNeedPaging() && itemQTO.getNeedPaging().booleanValue()) {
			Long totalCount = (Long) getSqlMapClientTemplate().queryForObject("ItemDAO.countItem", itemQTO);// 总记录数
			itemQTO.setTotalCount((int)totalCount.longValue());
			if (totalCount == 0) {
				return new ArrayList<ItemDO>();
			} else {
				itemQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
			}
		}
		List<ItemDO> list = getSqlMapClientTemplate().queryForList("ItemDAO.queryItemList", itemQTO);
		return list;
	}

	public int updateItemState(Long id, Long supplier_id, String bizCode, Integer state) {
		ItemDO itemDO = new ItemDO();
		itemDO.setItemStatus(state);
		itemDO.setId(id);
		itemDO.setSellerId(supplier_id);
		itemDO.setBizCode(bizCode);
		itemDO.setDeleteMark(0);
		
		// updated by cwr 
//		WhereParms parms = new WhereParms(itemDO);
//		parms.setIsDeleted(DBConst.UN_DELETED.getCode());
//		parms.setId(id);
//		parms.setSupplierId(supplier_id);
		
		int rows = getSqlMapClientTemplate().update("ItemDAO.updateItemState", itemDO);
		return rows;
	}

	@Override
	public int removeItemSaleEnd(Long id, Long supplier_id, String bizCode) {
		ItemDO itemDO = new ItemDO();
		itemDO.setId(id);
		itemDO.setSellerId(supplier_id);
		itemDO.setDeleteMark(0);
		itemDO.setBizCode(bizCode);

		int rows = getSqlMapClientTemplate().update("ItemDAO.removeItemSaleEndTime", itemDO);
		return rows;
	}

	//	商品上下架更新
	@Override
	public List<ItemDO> selectItemSaleStateUp(ItemQTO itemQTO) {
		if (null != itemQTO.getNeedPaging() && itemQTO.getNeedPaging().booleanValue()) {
			Long totalCount = (Long) getSqlMapClientTemplate().queryForObject("ItemDAO.countItem", itemQTO);// 总记录数
			itemQTO.setTotalCount((int)totalCount.longValue());
			if (totalCount == 0) {
				return new ArrayList<ItemDO>();
			} else {
				itemQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
			}
		}
		List<ItemDO> list = getSqlMapClientTemplate().queryForList("ItemDAO.selectItemSaleUp", itemQTO);
		return list;
	}

	@Override
	public List<ItemDO> selectItemSaleStateDown(ItemQTO itemQTO) {
		if (null != itemQTO.getNeedPaging() && itemQTO.getNeedPaging().booleanValue()) {
			Long totalCount = (Long) getSqlMapClientTemplate().queryForObject("ItemDAO.countItem", itemQTO);// 总记录数
			itemQTO.setTotalCount((int)totalCount.longValue());
			if (totalCount == 0) {
				return new ArrayList<ItemDO>();
			} else {
				itemQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
			}
		}
		List<ItemDO> list = getSqlMapClientTemplate().queryForList("ItemDAO.selectItemSaleDown", itemQTO);
		return list;
	}

	@Override
	public void updateItemSaleStateUp(ItemQTO itemQTO) {
		getSqlMapClientTemplate().update("ItemDAO.updateItemSaleUp", itemQTO);
	}

	@Override
	public void updateItemSaleStateDown(ItemQTO itemQTO) {
		getSqlMapClientTemplate().update("ItemDAO.updateItemSaleDown", itemQTO);
	}

	
	public Object isItemExist(ItemQTO itemQTO){
		Object result = this.getSqlMapClientTemplate().queryForObject("ItemDAO.isItemExist",itemQTO);
		return result;
	}

	@Override
	public int updateItemWithBlankDate(ItemDO itemDO) {
		int rows = getSqlMapClientTemplate().update("ItemDAO.updateItemWithBlankDate", itemDO);
		return rows;
	}

	@Override
	public List<ItemDO> queryDistinctSellerId() {
		List<ItemDO> itemDOs = getSqlMapClientTemplate().queryForList("ItemDO.selectDistinctSellerId");
		return itemDOs;
	}

	@Override
	public int removeItemSaleBegin(Long id, Long supplierId, String bizCode) {
		ItemDO itemDO = new ItemDO();
		itemDO.setId(id);
		itemDO.setSellerId(supplierId);
		itemDO.setBizCode(bizCode);
		itemDO.setDeleteMark(0);

		int rows = getSqlMapClientTemplate().update("ItemDAO.removeItemSaleBeginTime", itemDO);
		return rows;
	}

	@Override
	public List<ItemDO> selectItemLoseShopId(ItemQTO itemQTO) {
		if (null != itemQTO.getNeedPaging() && itemQTO.getNeedPaging().booleanValue()) {
			Long totalCount = (Long) getSqlMapClientTemplate().queryForObject("ItemDAO.countItem", itemQTO);// 总记录数
			itemQTO.setTotalCount((int) totalCount.longValue());
			if (totalCount == 0) {
				return new ArrayList<ItemDO>();
			} else {
				itemQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
			}
		}
		List<ItemDO> list = getSqlMapClientTemplate().queryForList("ItemDAO.selectItemLoseShopId", itemQTO);
		return list;
	}

	@Override
	public Long updateItemLoseShopId(ItemQTO query) {
		return Long.valueOf(getSqlMapClientTemplate().update("ItemDAO.updateItemLoseShopId", query));
	}

	@Override
	public Long trashItem(ItemDO query) {
		return Long.valueOf(getSqlMapClientTemplate().update("ItemDAO.trashItem", query));
	}

	@Override
	public Long recoveryItem(ItemDO query) {
		return Long.valueOf(getSqlMapClientTemplate().update("ItemDAO.recoveryItem", query));
	}
    @Override
    public List<Long> queryCompositeItem(List<Long> idList) {
        return getSqlMapClientTemplate().queryForList("ItemDAO.queryCompositeItem", idList);
    }

	@Override
	public Long emptyRecycleBin(ItemDO query) {
		return Long.valueOf(getSqlMapClientTemplate().update("ItemDAO.emptyRecycleBin", query));
	}

	@Override
	public void updateItemStockNum(ItemDO query) {
		getSqlMapClientTemplate().update("ItemDAO.updateItemStockNum", query);
	}



	@Override
	public Map<Long, ItemDO> queryItemMap(ItemQTO qto) {
		return  getSqlMapClientTemplate().queryForMap("ItemDAO.queryItemList",qto,"id");
	}
	//更新商品发布状态
	@Override
	public Long updateIssueStatus(Long itemId,Integer status) {
		Map map = new HashMap();
		map.put("id",itemId);
		map.put("issueStatus",status);
		return Long.valueOf(getSqlMapClientTemplate().update("ItemDAO.updateIssueStatus",map));
	}

    @Override
    public Integer selectItemStatus(Long itemId) {
        return (Integer) getSqlMapClientTemplate().queryForObject("ItemDAO.selectItemStatus",itemId);
    }

	@Override
	public Long getItemCategoryId(Long itemId) {
		return (Long) getSqlMapClientTemplate().queryForObject("ItemDAO.getItemCategoryId",itemId);
	}

	protected class WhereParms extends ItemQTO {
		private Object record;

		public WhereParms(Object record) {
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}

}