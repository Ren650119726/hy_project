package com.mockuai.shopcenter.core.dao.impl;

import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.core.dao.ShopDAO;
import com.mockuai.shopcenter.core.domain.ShopDO;
import com.mockuai.shopcenter.domain.dto.LastDaysCountDTO;
import com.mockuai.shopcenter.domain.qto.ShopQTO;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ziqi.
 */
@Service
public class ShopDAOImpl extends SqlMapClientDaoSupport implements ShopDAO {

    private static final Logger log = LoggerFactory.getLogger(ShopDAOImpl.class);

    @Override
    public Long addShop(ShopDO shopDO) throws ShopException {
        Long id = null;
        try {
            id = (Long) getSqlMapClientTemplate().insert("Shop.addShop", shopDO);
        } catch (Throwable e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_INSERT, "create shop exception.");
        }
        return id;
    }

    @Override
    public ShopDO getShop(ShopDO shopDO) throws ShopException {

        try {
            shopDO = (ShopDO) getSqlMapClientTemplate().queryForObject("Shop.getShop", shopDO);
        } catch (Throwable e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_QUERY, "get shop exception.");
        }
        if(shopDO == null) {
            throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "record not exists.");
        }
        return shopDO;
    }

    @Override
    public Boolean isExistShop(ShopDO shopDO) throws ShopException {
        Integer count = null;
        try {
            count = (Integer) getSqlMapClientTemplate().queryForObject("Shop.isShopExist", shopDO);
            if(count.intValue() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Throwable e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_QUERY, "query error.");
        }
    }

    @Override
    public Integer deleteShop(Long id, Long sellerId) throws ShopException {
        Integer count = null;
        ShopDO shopDO = new ShopDO();
        shopDO.setId(id);
        shopDO.setSellerId(sellerId);
        try {
            count = getSqlMapClientTemplate().update("Shop.deleteShop", shopDO);
        } catch (Throwable e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_DELETE, "delete shop exception.");
        }
        return count;
    }

    @Override
    public Integer updateShopStatus(Long sellerId, Integer status) throws ShopException {
        Integer count = null;
        ShopDO query = new ShopDO();
        query.setSellerId(sellerId);
        query.setShopStatus(status);
        try {
            count = getSqlMapClientTemplate().update("Shop.updateShopStatus", query);
        } catch (Throwable e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_UPDATE, "update shop status exception.");
        }
        return count;
    }

    @Override
    public Integer updateShop(ShopDO shopDO) throws ShopException {
        Integer count = null;
        try {
            count = getSqlMapClientTemplate().update("Shop.updateShop", shopDO);
        } catch (Throwable e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_UPDATE, "update shop exception.");
        }
        return count;
    }

    @Override
    public List<ShopDO> queryShop(ShopQTO shopQTO) throws ShopException {

        shopQTO.setDeleteMark(0);
        Integer totalCount = (Integer) getSqlMapClientTemplate().queryForObject("Shop.countShop", shopQTO);// 总记录数
        shopQTO.setTotalCount(totalCount);
        if (totalCount == 0) {
            return new ArrayList<ShopDO>();
        } else {
            shopQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
        }
        List<ShopDO> shopDOs = new ArrayList<ShopDO>();
        try {
            shopDOs = getSqlMapClientTemplate().queryForList("Shop.queryShopList", shopQTO);
        } catch (Throwable e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_QUERY, "query shop exception.");
        }
        return shopDOs;
    }

    @Override
    public Integer countShop(ShopQTO shopQTO) throws ShopException {
        Integer count = null;
        try {
            count = (Integer)getSqlMapClientTemplate().queryForObject("Shop.countShop", shopQTO);
        } catch (Throwable e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_QUERY, "count shop exception.");
        }
        return count;
    }

    @Override
    public List<LastDaysCountDTO> countLastEveryDayShop(ShopQTO shopQTO) throws ShopException {
        List<LastDaysCountDTO> lastDaysCountDTOs = null;
        try {
            lastDaysCountDTOs = getSqlMapClientTemplate().queryForList("Shop.countLastEveryDaysShop", shopQTO);
        } catch (Throwable e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_QUERY, "count shop exception.");
        }
        return lastDaysCountDTOs;
    }
}
