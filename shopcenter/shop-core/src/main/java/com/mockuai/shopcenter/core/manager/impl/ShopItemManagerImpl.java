package com.mockuai.shopcenter.core.manager.impl;

import com.google.common.collect.Lists;
import com.mockuai.shopcenter.core.dao.ShopItemDAO;
import com.mockuai.shopcenter.core.domain.ShopItemDO;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.ShopItemManager;
import com.mockuai.shopcenter.domain.dto.ShopItemDTO;
import com.mockuai.shopcenter.domain.qto.ShopItemQTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 16/1/12.
 */
@Service
public class ShopItemManagerImpl implements ShopItemManager {

    @Resource
    private ShopItemDAO shopItemDAO;

    @Override
    public List<ShopItemDTO> queryShopItem(ShopItemQTO shopCouponQTO) throws ShopException{

        List<ShopItemDO> shopItemDOList = shopItemDAO.queryShopItem(shopCouponQTO);

        List<ShopItemDTO> shopItemDTOList = Lists.newArrayListWithCapacity(shopItemDOList.size());

        for(ShopItemDO shopItemDO : shopItemDOList){
            ShopItemDTO shopItemDTO = new ShopItemDTO();
            BeanUtils.copyProperties(shopItemDO,shopItemDTO);
            shopItemDTOList.add(shopItemDTO);
        }

        return shopItemDTOList;
    }

    @Override
    public Long addShopItem(ShopItemDTO shopItemDTO)  throws ShopException{

        ShopItemDO shopItemDO = new ShopItemDO();

        BeanUtils.copyProperties(shopItemDTO,shopItemDO);

        Long result = shopItemDAO.addShopItem(shopItemDO);

        return result;
    }

    @Override
    public Long batchDeleteShopItem(List<Long> shopItemIdList, Long shopId, String bizCode) throws ShopException {

        ShopItemQTO shopItemQTO = new ShopItemQTO();
        shopItemQTO.setIdList(shopItemIdList);
        shopItemQTO.setShopId(shopId);
        shopItemQTO.setBizCode(bizCode);

        Long result = shopItemDAO.batchDeleteShopItem(shopItemQTO);

        return result;
    }
}
