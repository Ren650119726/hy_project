package com.mockuai.virtualwealthcenter.core.manager;

import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;

public interface ItemManager {

    /**
     * 添加商品
     *
     * @param itemDTO
     * @param appKey
     */
    ItemDTO addItem(ItemDTO itemDTO, String appKey) throws VirtualWealthException;

    /**
     * 删除商品
     */
    Boolean deleteItem(Long itemId, Long sellerId, String appKey) throws VirtualWealthException;

    /**
     * 更新商品
     */
    Boolean updateItemSku(ItemSkuDTO itemSkuDTO, String appKey) throws VirtualWealthException;
}