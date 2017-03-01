package com.mockuai.marketingcenter.core.dao.impl;


import com.mockuai.marketingcenter.common.domain.qto.TimePurchaseQTO;
import com.mockuai.marketingcenter.core.dao.LimitedPurchaseDAO;
import com.mockuai.marketingcenter.core.domain.LimitedPurchaseDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangsiqian on 2016/10/7.
 */
public class LimitedPurchaseDAOImpl extends SqlMapClientDaoSupport implements LimitedPurchaseDAO {


    @Override
    public LimitedPurchaseDO selectActivities(LimitedPurchaseDO limitedPurchaseDO) {
        return (LimitedPurchaseDO)getSqlMapClientTemplate().queryForObject("limited_purchase.selectActivities",limitedPurchaseDO);
    }

    @Override
    public Long addActivities(LimitedPurchaseDO limitedPurchaseDO) {
        return (Long) getSqlMapClientTemplate().insert("limited_purchase.addActivity", limitedPurchaseDO);
    }

    @Override
    public List<LimitedPurchaseDO> activityList(TimePurchaseQTO timePurchaseQTO) {
        return (List<LimitedPurchaseDO>)getSqlMapClientTemplate().queryForList("limited_purchase.activityList",timePurchaseQTO);
    }

    @Override
    public Integer updateActivity(LimitedPurchaseDO activityDO) {

        return (Integer) getSqlMapClientTemplate().update("limited_purchase.updateActivity", activityDO);
    }

    @Override
    public Integer modifyActivity(LimitedPurchaseDO activityDO) {

        return (Integer) getSqlMapClientTemplate().update("limited_purchase.modifyActivity", activityDO);
    }

    @Override
    public LimitedPurchaseDO activityById(Long id) {
        Map map = new HashMap();
        map.put("id",id);
        return (LimitedPurchaseDO)getSqlMapClientTemplate().queryForObject("limited_purchase.activityById",map);
    }

    @Override
    public Long activityCount(TimePurchaseQTO timePurchaseQTO) {
        return (Long) getSqlMapClientTemplate().queryForObject("limited_purchase.activityCount", timePurchaseQTO);
    }

    @Override
    public Integer startLimtedPurchase(LimitedPurchaseDO activityDO) {
        Long id = activityDO.getId();
        Map map = new HashMap();
        map.put("id",id);
        return (Integer) getSqlMapClientTemplate().update("limited_purchase.startLimtedPurchase", map);
    }

    @Override
    public Integer deleteLimitPurchase(Long activityId) {
        Map map = new HashMap();
        map.put("id",activityId);
        return (Integer) getSqlMapClientTemplate().update("limited_purchase.deletelLimtedPurchase", map);
    }

    @Override
    public Integer updateLimitedPurchaseStatus(String status, Long activityId) {
        Map map = new HashMap();
        map.put("status",status);
        map.put("id",activityId);

        return (Integer)getSqlMapClientTemplate().update("limited_purchase.updateLimitedPurchaseStatus",map);
    }

}
