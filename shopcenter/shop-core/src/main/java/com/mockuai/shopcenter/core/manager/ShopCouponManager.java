package com.mockuai.shopcenter.core.manager;

import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.domain.dto.ShopCouponDTO;
import com.mockuai.shopcenter.domain.qto.ShopCouponQTO;

import java.util.List;

/**
 * Created by yindingyu on 16/1/12.
 */
public interface ShopCouponManager {

    List<ShopCouponDTO> queryShopCoupon(ShopCouponQTO shopCouponQTO) throws ShopException;

    Long batchDeleteShopCoupon(List<Long> shopCouponIdList, Long id, String bizCode) throws ShopException;

    Long addShopCoupon(ShopCouponDTO shopCouponDTO) throws ShopException;
}
