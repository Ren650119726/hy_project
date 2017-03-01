package com.mockuai.itemcenter.core.manager;

import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.shopcenter.api.Response;
import com.mockuai.shopcenter.domain.dto.ShopDTO;
import com.mockuai.shopcenter.domain.dto.ShopItemGroupDTO;

/**
 * Created by yindingyu on 16/1/18.
 */
public interface ShopManager {

    ShopDTO getShop(Long sellerId, String appKey) throws ItemException;

    ShopItemGroupDTO getShopItemGroup(Long sellerId, Long groupId, String appKey) throws ItemException;
}
