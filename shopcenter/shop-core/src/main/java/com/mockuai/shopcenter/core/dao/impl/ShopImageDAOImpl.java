package com.mockuai.shopcenter.core.dao.impl;

import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.core.dao.ShopImageDAO;
import com.mockuai.shopcenter.core.domain.ShopImageDO;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.util.ExceptionUtil;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

/**
 * Created by ziqi.
 */
@Service
public class ShopImageDAOImpl extends SqlMapClientDaoSupport implements ShopImageDAO {
    @Override
    public Long addShopImage(ShopImageDO shopImageDO) throws ShopException {
        Long id = null;
        try {
            id = (Long) getSqlMapClientTemplate().insert("ShopImage.addShopImage", shopImageDO);
        } catch (Throwable e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_INSERT, "create shop image exception.");
        }
        return id;
    }

    @Override
    public ShopImageDO getShopImage(Long id, Long sellerId) throws ShopException {
        ShopImageDO shopImageDO = null;
        ShopImageDO query = new ShopImageDO();
        query.setId(id);
        query.setSellerId(sellerId);
        query.setDeleteMark(0);
        try {
            shopImageDO = (ShopImageDO) getSqlMapClientTemplate().queryForObject("ShopImage.getShopImage", query);
        } catch (Throwable e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_QUERY, "get Shop Image exception.");
        }
        return shopImageDO;
    }

    @Override
    public Integer updateShopImage(ShopImageDO shopImageDO) throws ShopException {
        Integer count = null;
        try {
            count = getSqlMapClientTemplate().update("ShopImage.updateShopImage", shopImageDO);
        } catch (Throwable e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_UPDATE, "update Shop Image exception.");
        }
        return count;
    }

    @Override
    public Integer deleteShopImage(Long id, Long sellerId) throws ShopException {

        Integer count = null;
        ShopImageDO shopImageDO = new ShopImageDO();
        shopImageDO.setId(id);
        shopImageDO.setSellerId(sellerId);
        try {
            count = getSqlMapClientTemplate().update("ShopImage.deleteShopImage", shopImageDO);
        } catch (Throwable e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_UPDATE, "update Shop Image exception.");
        }
        return count;
    }
}
