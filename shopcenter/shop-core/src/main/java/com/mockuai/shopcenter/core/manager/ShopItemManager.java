package com.mockuai.shopcenter.core.manager;

import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.domain.dto.ShopItemDTO;
import com.mockuai.shopcenter.domain.qto.ShopCouponQTO;
import com.mockuai.shopcenter.domain.qto.ShopItemQTO;

import java.util.List;

/**
 * Created by yindingyu on 16/1/12.
 */
public interface ShopItemManager {

    List<ShopItemDTO> queryShopItem(ShopItemQTO shopCouponQTO) throws ShopException;

    Long addShopItem(ShopItemDTO shopItemDTO) throws ShopException;

    Long batchDeleteShopItem(List<Long> shopItemIdList, Long id, String bizCode) throws ShopException;
}
