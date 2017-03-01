package com.mockuai.mainweb.core.manager;

import com.mockuai.shopcenter.domain.dto.ShopItemGroupDTO;

/**
 * Created by edgar.zr on 5/25/2016.
 */
public interface ShopManager {

    /**
     * 根据 groupId 获取组下商品 列表
     *
     * @param sellerId
     * @param groupId
     * @param needItems
     * @param appKey
     * @return
     */
    ShopItemGroupDTO getShopItemGroup(Long sellerId, Long groupId, String needItems, String appKey);
}