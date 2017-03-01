package com.mockuai.itemcenter.core.dao;

import com.mockuai.itemcenter.common.domain.qto.ItemSuitQTO;
import com.mockuai.itemcenter.core.domain.ItemSuitDO;

import java.util.List;

public interface ItemSuitDAO {

    public ItemSuitDO getItemSuit(Long id,Long sellerId,String bizCode);

    public ItemSuitDO getItemSuitByItemId(Long itemId,Long sellerId,String bizCode);

    public List<ItemSuitDO> queryItemSuit(ItemSuitQTO itemSuitQTO);

    public Integer addItemSuit(ItemSuitDO itemSuitDO);

    Long disableItemSuit(Long itemId, Long sellerId, String bizCode);

    void increaseSuitSalesVolume(ItemSuitDO itemSuitDO);

    List<ItemSuitDO> queryItemSuitByLifecycle(ItemSuitQTO itemSuitQTO);
}