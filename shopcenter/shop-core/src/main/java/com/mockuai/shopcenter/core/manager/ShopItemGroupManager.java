package com.mockuai.shopcenter.core.manager;

import com.mockuai.shopcenter.domain.dto.ShopItemGroupDTO;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.domain.qto.ShopItemGroupQTO;

import java.util.List;

/**
 * Created by luliang on 15/7/27.
 */
public interface ShopItemGroupManager {

    public Long addShopItemGroup(ShopItemGroupDTO shopItemGroupDTO) throws ShopException;

    public Boolean updateShopItemGroup(ShopItemGroupDTO shopItemGroupDTO) throws ShopException;

    public Boolean deleteShopItemGroup(Long id, Long sellerId) throws ShopException;

    public List<ShopItemGroupDTO> queryShopItemGroup(ShopItemGroupQTO shopItemGroupQTO) throws ShopException;

    public ShopItemGroupDTO getShopItemGroup(Long id, Long sellerId) throws ShopException;
}
