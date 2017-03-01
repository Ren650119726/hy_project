package com.mockuai.itemcenter.core.dao.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.mockuai.itemcenter.common.domain.dto.ItemSalesSpuCountDTO;
import com.mockuai.itemcenter.core.dao.ItemSalesSpuCountDAO;
import com.mockuai.itemcenter.core.domain.ItemSalesSpuCountDO;

@Repository
public class ItemSalesSpuCountDAOImpl extends SqlMapClientDaoSupport implements ItemSalesSpuCountDAO { 
	@Override
	public ItemSalesSpuCountDO getItemSalesSpuByItemId(Long itemId) {
		return (ItemSalesSpuCountDO) getSqlMapClientTemplate().queryForObject("itemSalesSpu.getItemSalesSpuByItemId", itemId);
	}

	@Override
	public Long addItemSalesSpuCount(ItemSalesSpuCountDTO itemSalesSpuCountDTO) {
		ItemSalesSpuCountDO itemSalesSpuCountDO = new ItemSalesSpuCountDO();
		BeanUtils.copyProperties(itemSalesSpuCountDTO, itemSalesSpuCountDO);
		
		return (Long) getSqlMapClientTemplate().insert("itemSalesSpu.addItemSalesSpuCount", itemSalesSpuCountDO);
	}

	@Override
	public Long updateItemSalesSpuCount(ItemSalesSpuCountDO itemSalesSpuCountDO) {
		return Long.valueOf(getSqlMapClientTemplate().update("itemSalesSpu.updateItemSalesSpuCount", itemSalesSpuCountDO));
	}
}