package com.mockuai.shopcenter.core.dao.impl;

import com.mockuai.shopcenter.core.dao.ShopCouponDAO;
import com.mockuai.shopcenter.core.domain.ShopCouponDO;

import java.util.Collections;
import java.util.List;

import com.mockuai.shopcenter.domain.qto.ShopCouponQTO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class ShopCouponDAOImpl extends SqlMapClientDaoSupport implements ShopCouponDAO {


    @Override
    public List<ShopCouponDO> queryShopCoupon(ShopCouponQTO shopCouponQTO) {

        if (shopCouponQTO.getNeedPaging() != null && shopCouponQTO.getNeedPaging() == true) {
            Integer count = (Integer) getSqlMapClientTemplate().queryForObject("ShopCoupon.countShopCoupon");

            if (count > 0) {
                shopCouponQTO.setOffsetAndTotalPage();
            } else {
                return Collections.EMPTY_LIST;
            }
        }

        List<ShopCouponDO> shopCouponDOList = getSqlMapClientTemplate().queryForList("ShopCoupon.queryShopCoupon");
        return shopCouponDOList;
    }

    @Override
    public Long batchDeleteShopCoupon(ShopCouponQTO query) {
        Long result = Long.valueOf(getSqlMapClientTemplate().update("ShopCoupon.batchDeleteShopCoupon",query));
        return result;
    }

    @Override
    public Long addShopCoupon(ShopCouponDO shopCouponDO) {
        Long result = (Long) getSqlMapClientTemplate().insert("ShopCoupon.addShopCoupon",shopCouponDO);

        return result;
    }
}