package com.mockuai.itemcenter.core.dao;

import com.mockuai.itemcenter.common.domain.dto.ItemSalesSpuCountDTO;
import com.mockuai.itemcenter.core.domain.ItemSalesSpuCountDO;


public interface ItemSalesSpuCountDAO {
	ItemSalesSpuCountDO getItemSalesSpuByItemId(Long itemId);

	Long addItemSalesSpuCount(ItemSalesSpuCountDTO itemSalesSpuCountDTO);

	Long updateItemSalesSpuCount(ItemSalesSpuCountDO itemSalesSpuCountDO);
}