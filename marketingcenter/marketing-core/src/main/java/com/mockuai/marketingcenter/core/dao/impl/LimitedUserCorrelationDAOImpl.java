package com.mockuai.marketingcenter.core.dao.impl;

import com.mockuai.marketingcenter.core.dao.LimitedUserCorrelationDAO;
import com.mockuai.marketingcenter.core.domain.LimitedUserCorrelationDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 * Created by huangsiqian on 2016/10/19.
 */
public class LimitedUserCorrelationDAOImpl extends SqlMapClientDaoSupport implements LimitedUserCorrelationDAO {
    @Override
    public Integer updatePurchaseQuantity(LimitedUserCorrelationDO limitedUserCorrelationDO) {
        return (Integer) getSqlMapClientTemplate().update("limited_user_correlation.updatePurchaseQuantity",limitedUserCorrelationDO);
    }
    @Override
    public Integer orderCancelledgoods(LimitedUserCorrelationDO limitedUserCorrelationDO) {
        return (Integer) getSqlMapClientTemplate().update("limited_user_correlation.orderCancelledgoods",limitedUserCorrelationDO);
    }

    @Override
    public LimitedUserCorrelationDO selectUserMsg(LimitedUserCorrelationDO limitedUserCorrelationDO) {
        return (LimitedUserCorrelationDO) getSqlMapClientTemplate().queryForObject("limited_user_correlation.selectUserMsg",limitedUserCorrelationDO);
    }

    @Override
    public Long addUserMsg(LimitedUserCorrelationDO limitedUserCorrelationDO) {
        return (Long) getSqlMapClientTemplate().insert("limited_user_correlation.addUserMsg",limitedUserCorrelationDO);
    }
}
