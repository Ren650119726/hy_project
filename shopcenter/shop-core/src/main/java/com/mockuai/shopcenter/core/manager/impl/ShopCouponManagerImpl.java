package com.mockuai.shopcenter.core.manager.impl;

import com.google.common.collect.Lists;
import com.mockuai.shopcenter.core.dao.ShopCouponDAO;
import com.mockuai.shopcenter.core.domain.ShopCouponDO;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.ShopCouponManager;
import com.mockuai.shopcenter.domain.dto.ShopCouponDTO;
import com.mockuai.shopcenter.domain.qto.ShopCouponQTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 16/1/12.
 */
@Service
public class ShopCouponManagerImpl implements ShopCouponManager {

    @Resource
    private ShopCouponDAO shopCouponDAO;

    @Override
    public List<ShopCouponDTO> queryShopCoupon(ShopCouponQTO shopCouponQTO)  throws ShopException{

        List<ShopCouponDO> shopCouponDOList = shopCouponDAO.queryShopCoupon(shopCouponQTO);

        List<ShopCouponDTO> shopCouponDTOList = Lists.newArrayListWithCapacity(shopCouponDOList.size());

        for(ShopCouponDO shopCouponDO : shopCouponDOList){

            ShopCouponDTO shopCouponDTO = new ShopCouponDTO();

            BeanUtils.copyProperties(shopCouponDO,shopCouponDTO);

            shopCouponDTOList.add(shopCouponDTO);
        }

        return shopCouponDTOList;
    }

    @Override
    public Long batchDeleteShopCoupon(List<Long> shopCouponIdList, Long shopId, String bizCode)  throws ShopException{

        ShopCouponQTO query = new ShopCouponQTO();
        query.setIdList(shopCouponIdList);
        query.setShopId(shopId);
        query.setBizCode(bizCode);

        Long result = shopCouponDAO.batchDeleteShopCoupon(query);

        return result;

    }

    @Override
    public Long addShopCoupon(ShopCouponDTO shopCouponDTO) throws ShopException{

        ShopCouponDO shopCouponDO = new ShopCouponDO();

        BeanUtils.copyProperties(shopCouponDTO, shopCouponDO);

        Long result = shopCouponDAO.addShopCoupon(shopCouponDO);

        return result;
    }
}
