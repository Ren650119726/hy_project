package com.mockuai.shopcenter.core.dao;

import com.mockuai.shopcenter.core.domain.ShopItemGroupDO;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.domain.qto.ShopItemGroupQTO;

import java.util.List;

/**
 * Created by luliang on 15/7/26.
 */
public interface ShopItemGroupDAO {

    /**
     * 添加店铺商品分组;
     * @param shopItemGroupDO
     * @return
     */
    Long addShopItemGroup(ShopItemGroupDO shopItemGroupDO) throws ShopException;

    ShopItemGroupDO getShopItemGroup(Long id, Long sellerId) throws ShopException;

    List<ShopItemGroupDO> queryShopItemGroup(ShopItemGroupQTO shopItemGroupQTO) throws ShopException;

    Integer updateShopItemGroup(ShopItemGroupDO shopItemGroupDO) throws ShopException;

    Integer deleteShopItemGroup(Long id, Long sellerId) throws ShopException;
}
