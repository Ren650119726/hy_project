package com.mockuai.shopcenter.core.dao;

import com.mockuai.shopcenter.core.domain.ShopItemDO;
import com.mockuai.shopcenter.core.domain.ShopItemDOExample;
import com.mockuai.shopcenter.domain.qto.ShopItemQTO;

import java.util.List;

public interface ShopItemDAO {


    List<ShopItemDO> queryShopItem(ShopItemQTO shopCouponQTO);

    Long addShopItem(ShopItemDO shopItemDO);

    Long batchDeleteShopItem(ShopItemQTO shopItemQTO);
}