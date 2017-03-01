package com.mockuai.shopcenter.core.dao;

import com.mockuai.shopcenter.core.domain.ShopImageDO;
import com.mockuai.shopcenter.core.exception.ShopException;

/**
 * Created by luliang on 15/7/26.
 */
public interface ShopImageDAO {

    /**
     * 添加图片
     * @param shopImageDO
     * @return
     */
    Long addShopImage(ShopImageDO shopImageDO) throws ShopException;

    ShopImageDO getShopImage(Long id, Long sellerId) throws ShopException;

    Integer updateShopImage(ShopImageDO shopImageDO) throws ShopException;

    Integer deleteShopImage(Long id, Long sellerId) throws ShopException;
}
