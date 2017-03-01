package com.mockuai.shopcenter.core.dao;

import com.mockuai.shopcenter.core.domain.ShopCouponDO;
import com.mockuai.shopcenter.domain.qto.ShopCouponQTO;

import java.util.List;

public interface ShopCouponDAO {

    List<ShopCouponDO> queryShopCoupon(ShopCouponQTO shopCouponQTO);

    Long batchDeleteShopCoupon(ShopCouponQTO query);

    Long addShopCoupon(ShopCouponDO shopCouponDO);
}