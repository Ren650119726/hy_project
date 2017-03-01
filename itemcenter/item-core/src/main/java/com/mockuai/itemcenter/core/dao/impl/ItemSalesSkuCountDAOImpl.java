package com.mockuai.itemcenter.core.dao.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.mockuai.itemcenter.common.domain.dto.ItemSalesSkuCountDTO;
import com.mockuai.itemcenter.core.dao.ItemSalesSkuCountDAO;
import com.mockuai.itemcenter.core.domain.ItemSalesSkuCountDO;

@Repository
public class ItemSalesSkuCountDAOImpl extends SqlMapClientDaoSupport implements ItemSalesSkuCountDAO {
	@Override
	public ItemSalesSkuCountDO getItemSalesSkuBySkuId(Long skuId) {
		return (ItemSalesSkuCountDO) getSqlMapClientTemplate().queryForObject("itemSalesSku.getItemSalesSkuByItemId", skuId);
	}

	@Override
	public Long addItemSalesSkuCount(ItemSalesSkuCountDTO itemSalesSkuCountDTO) {
		ItemSalesSkuCountDO itemSalesSkuCountDO = new ItemSalesSkuCountDO();
		BeanUtils.copyProperties(itemSalesSkuCountDTO, itemSalesSkuCountDO);
		
		return (Long) getSqlMapClientTemplate().insert("itemSalesSku.addItemSalesSkuCount", itemSalesSkuCountDO);
	}

	@Override
	public Long updateItemSalesSkuCount(ItemSalesSkuCountDO itemSalesSkuCountDO) {
		return Long.valueOf(getSqlMapClientTemplate().update("itemSalesSku.updateItemSalesSkuCount", itemSalesSkuCountDO));
	}
}