package com.mockuai.shopcenter.core.dao.impl;

import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.core.dao.ShopItemGroupDAO;
import com.mockuai.shopcenter.core.domain.ShopItemGroupDO;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.util.ExceptionUtil;
import com.mockuai.shopcenter.domain.qto.ShopItemGroupQTO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ziqi.
 */
@Service
public class ShopItemGroupDAOImpl extends SqlMapClientDaoSupport implements ShopItemGroupDAO {

    @Override
    public Long addShopItemGroup(ShopItemGroupDO shopItemGroupDO) throws ShopException {
        Long id = null;
        try {
            id = (Long) getSqlMapClientTemplate().insert("ShopItemGroup.addShopItemGroup", shopItemGroupDO);
        } catch (Throwable e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_INSERT, "create shop item group exception.");
        }
        return id;
    }

    @Override
    public ShopItemGroupDO getShopItemGroup(Long id, Long sellerId) throws ShopException {
        ShopItemGroupDO result = null;
        ShopItemGroupDO query = new ShopItemGroupDO();
        query.setId(id);
        query.setSellerId(sellerId);
        try {
            result = (ShopItemGroupDO) getSqlMapClientTemplate().
                    queryForObject("ShopItemGroup.getShopItemGroup", query);
        } catch (Throwable e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_QUERY, "get shop item group exception.");
        }
        return result;
    }

    @Override
    public List<ShopItemGroupDO> queryShopItemGroup(ShopItemGroupQTO shopItemGroupQTO) throws ShopException {

        if (null != shopItemGroupQTO.getNeedPaging() && shopItemGroupQTO.getNeedPaging().booleanValue()) {
            Integer totalCount = (Integer) getSqlMapClientTemplate().queryForObject("ShopItemGroup.countShopItemGroupList", shopItemGroupQTO);// 总记录数
            shopItemGroupQTO.setTotalCount(totalCount);
            if (totalCount == 0) {
                return new ArrayList<ShopItemGroupDO>();
            } else {
                shopItemGroupQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
            }
        }
        List<ShopItemGroupDO> shopItemGroupDOs = new ArrayList<ShopItemGroupDO>();
        try {
            shopItemGroupDOs = (List<ShopItemGroupDO>) getSqlMapClientTemplate().
                    queryForList("ShopItemGroup.queryShopItemGroupList", shopItemGroupQTO);
        } catch (Throwable e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_QUERY, "query shop item group exception.");
        }
        return shopItemGroupDOs;
    }

    @Override
    public Integer updateShopItemGroup(ShopItemGroupDO shopItemGroupDO) throws ShopException {
        Integer count = null;
        try {
            count = (Integer) getSqlMapClientTemplate().update("ShopItemGroup.updateShopItemGroup", shopItemGroupDO);
        } catch (Throwable e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_UPDATE, "update shop item group exception.");
        }
        return count;
    }

    @Override
    public Integer deleteShopItemGroup(Long id, Long sellerId) throws ShopException {
        Integer count = null;
        ShopItemGroupDO shopItemGroupDO = new ShopItemGroupDO();
        shopItemGroupDO.setId(id);
        shopItemGroupDO.setSellerId(sellerId);
        shopItemGroupDO.setDeleteVersion(System.currentTimeMillis());
        try {
            count = (Integer) getSqlMapClientTemplate().update("ShopItemGroup.deleteShopItemGroup", shopItemGroupDO);
        } catch (Throwable e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_UPDATE, "delete shop item group exception.");
        }
        return count;
    }
}
