package com.mockuai.marketingcenter.core.dao.impl;

import com.mockuai.marketingcenter.common.domain.qto.MarketActivityQTO;
import com.mockuai.marketingcenter.core.dao.MarketActivityDAO;
import com.mockuai.marketingcenter.core.domain.MarketActivityDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MarketActivityDAOImpl extends SqlMapClientDaoSupport implements MarketActivityDAO {

    @Override
    public List<MarketActivityDO> overlappingByTimeActivity(MarketActivityDO marketActivityDO) {
        return getSqlMapClientTemplate().queryForList("market_activity.overlappingByTimeActivity", marketActivityDO);
    }

    public long addActivity(MarketActivityDO marketActivityDO) {
        return ((Long) getSqlMapClientTemplate().insert("market_activity.addActivity", marketActivityDO)).longValue();
    }

    public int updateActivity(MarketActivityDO marketActivityDO) {
        return getSqlMapClientTemplate().update("market_activity.updateActivity", marketActivityDO);
    }

    @Override
    public int deleteActivity(MarketActivityQTO marketActivityQTO) {
        return getSqlMapClientTemplate().update("market_activity.deleteActivity", marketActivityQTO);
    }

    public List<MarketActivityDO> queryActivity(MarketActivityQTO marketActivityQTO) {
        return getSqlMapClientTemplate().queryForList("market_activity.queryActivity", marketActivityQTO);
    }

    @Override
    public List<MarketActivityDO> queryActivityForSettlement(MarketActivityQTO marketActivityQTO) {
        return getSqlMapClientTemplate().queryForList("market_activity.queryActivityForSettlement", marketActivityQTO);
    }

    @Override
    public int countOfQueryActivityForSettlement(MarketActivityQTO marketActivityQTO) {
        return (int) getSqlMapClientTemplate()
                .queryForObject("market_activity.countOfQueryActivityForSettlement", marketActivityQTO);
    }

    public int queryActivityCount(MarketActivityQTO marketActivityQTO) {
        return ((Integer) getSqlMapClientTemplate().queryForObject("market_activity.queryActivityCount", marketActivityQTO)).intValue();
    }

    public MarketActivityDO getActivity(MarketActivityDO marketActivityDO) {
        return (MarketActivityDO) getSqlMapClientTemplate().queryForObject("market_activity.getActivity", marketActivityDO);
    }
}