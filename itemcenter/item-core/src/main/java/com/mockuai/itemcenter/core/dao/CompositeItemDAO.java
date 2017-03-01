package com.mockuai.itemcenter.core.dao;

import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.itemcenter.core.domain.CompositeItemDO;

import java.util.List;

public interface CompositeItemDAO {
	
	 Long addCompositeItem(CompositeItemDO compositeItemDO);

    void batchAddCompositeItem(List<CompositeItemDO> compositeItemDO);

    int deleteCompositeItemByItemId(Long id);
	
	 List<CompositeItemDO> getCompositeItemByItemId(Long id);
	 List<CompositeItemDO> queryCompositeItemByItemIdList(List<Long> itemIdList);


    List<CompositeItemDO> queryCompositeItemByItemSkuQTO(ItemSkuQTO itemSkuQTO);
}
