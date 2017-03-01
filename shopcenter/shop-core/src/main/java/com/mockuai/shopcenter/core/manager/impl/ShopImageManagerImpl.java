package com.mockuai.shopcenter.core.manager.impl;

import com.mockuai.shopcenter.domain.dto.ShopImageDTO;
import com.mockuai.shopcenter.core.dao.ShopImageDAO;
import com.mockuai.shopcenter.core.domain.ShopImageDO;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.ShopImageManager;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by luliang on 15/7/27.
 */
@Service
public class ShopImageManagerImpl implements ShopImageManager {

    @Resource
    private ShopImageDAO shopImageDAO;

    @Override
    public Long addShopImage(ShopImageDTO shopImageDTO) throws ShopException {
        ShopImageDO shopImageDO = new ShopImageDO();
        shopImageDTO.setDeleteMark(0);
        BeanUtils.copyProperties(shopImageDTO, shopImageDO);
        return shopImageDAO.addShopImage(shopImageDO);
    }

    @Override
    public Integer updateShopImage(ShopImageDTO shopImageDTO) throws ShopException {
        shopImageDTO.setDeleteMark(0);
        ShopImageDO shopImageDO = new ShopImageDO();
        BeanUtils.copyProperties(shopImageDTO, shopImageDO);
        return shopImageDAO.updateShopImage(shopImageDO);
    }

    @Override
    public ShopImageDTO getShopImage(Long id, Long sellerId) throws ShopException {
        ShopImageDO shopImageDO = shopImageDAO.getShopImage(id, sellerId);
        ShopImageDTO shopImageDTO = new ShopImageDTO();
        if(shopImageDO==null){
            return null;
        }
        BeanUtils.copyProperties(shopImageDO, shopImageDTO);
        return shopImageDTO;
    }
}
