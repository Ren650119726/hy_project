package com.mockuai.itemcenter.core.dao;

import com.mockuai.itemcenter.common.domain.dto.ItemSalesSkuCountDTO;
import com.mockuai.itemcenter.core.domain.ItemSalesSkuCountDO;

public interface ItemSalesSkuCountDAO {
	ItemSalesSkuCountDO getItemSalesSkuBySkuId(Long skuId);

	Long addItemSalesSkuCount(ItemSalesSkuCountDTO itemSalesSkuCountDTO);

	Long updateItemSalesSkuCount(ItemSalesSkuCountDO itemSalesSkuCountDO);
}