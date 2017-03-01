package com.mockuai.shopcenter.core.dao.impl;

import com.mockuai.shopcenter.core.dao.ShopItemDAO;
import com.mockuai.shopcenter.core.domain.ShopItemDO;
import com.mockuai.shopcenter.core.domain.ShopItemDOExample;

import java.util.Collections;
import java.util.List;

import com.mockuai.shopcenter.domain.qto.ShopItemQTO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class ShopItemDAOImpl extends SqlMapClientDaoSupport implements ShopItemDAO {


    @Override
    public List<ShopItemDO> queryShopItem(ShopItemQTO shopCouponQTO) {

        if(shopCouponQTO.getNeedPaging()!=null&&shopCouponQTO.getNeedPaging()==true){
            Integer count = (Integer) getSqlMapClientTemplate().queryForObject("ShopItem.countShopItem",shopCouponQTO);

            if(count == 0){
                return Collections.EMPTY_LIST;
            }else {
                shopCouponQTO.setOffsetAndTotalPage();
            }
        }

        List<ShopItemDO> shopItemDOList = getSqlMapClientTemplate().queryForList("ShopItem.queryShopItem", shopCouponQTO);

        return shopItemDOList;
    }

    @Override
    public Long addShopItem(ShopItemDO shopItemDO) {
        return (Long) getSqlMapClientTemplate().insert("ShopItem.addShopItem",shopItemDO);
    }

    @Override
    public Long batchDeleteShopItem(ShopItemQTO shopItemQTO) {
        return Long.valueOf(getSqlMapClientTemplate().update("ShopItem.batchDeleteShopItem",shopItemQTO));
    }
}