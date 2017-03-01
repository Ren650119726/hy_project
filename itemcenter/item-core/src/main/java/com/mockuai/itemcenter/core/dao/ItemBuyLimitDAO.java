package com.mockuai.itemcenter.core.dao;

import com.mockuai.itemcenter.core.domain.ItemBuyLimitDO;
import com.mockuai.itemcenter.core.exception.ItemException;

import java.util.List;

/**
 * Created by luliang on 15/7/17.
 */
public interface ItemBuyLimitDAO {

    Long addItemBuyLimit(ItemBuyLimitDO itemBuyLimitDO) throws ItemException;

    List<ItemBuyLimitDO> queryItemBuyLimit(Long itemId, Long sellerId) throws ItemException;

    List<ItemBuyLimitDO> queryItemBuyLimit(List<Long> itemIdList, Long sellerId) throws ItemException;

    int deleteItemBuyLimit(ItemBuyLimitDO itemBuyLimitDO) throws ItemException;

    Long trashItemBuyLimit(ItemBuyLimitDO query) throws ItemException;

    Long recoveryItemBuyLimit(ItemBuyLimitDO query) throws ItemException;

    Long emptyRecycleBin(ItemBuyLimitDO query) throws ItemException;
}
