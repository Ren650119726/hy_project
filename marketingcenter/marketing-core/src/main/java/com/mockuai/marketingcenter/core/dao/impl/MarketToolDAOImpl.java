package com.mockuai.marketingcenter.core.dao.impl;

import com.mockuai.marketingcenter.common.domain.qto.MarketToolQTO;
import com.mockuai.marketingcenter.core.dao.MarketToolDAO;
import com.mockuai.marketingcenter.core.domain.MarketToolDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

public class MarketToolDAOImpl extends SqlMapClientDaoSupport
        implements MarketToolDAO {

    public Long addTool(MarketToolDO marketToolDO) {

        return (Long) getSqlMapClientTemplate().insert("market_tool.addTool", marketToolDO);
    }

    public int updateTool(MarketToolDO marketToolDO) {

        return getSqlMapClientTemplate().update("market_tool.updateTool", marketToolDO);
    }

    public MarketToolDO getTool(long id, String bizCode) {

        MarketToolDO marketToolDO = new MarketToolDO();
        marketToolDO.setId(Long.valueOf(id));
        marketToolDO.setBizCode(bizCode);
        return (MarketToolDO) getSqlMapClientTemplate().queryForObject("market_tool.getTool", marketToolDO);
    }

    @Override
    public MarketToolDO getTool(String toolCode) {

        MarketToolDO marketToolDO = new MarketToolDO();
        marketToolDO.setToolCode(toolCode);

        return (MarketToolDO) getSqlMapClientTemplate().queryForObject("market_tool.getToolByCode", marketToolDO);
    }

    public List<MarketToolDO> queryTool(MarketToolQTO marketToolQTO) {

        return getSqlMapClientTemplate().queryForList("market_tool.queryTool", marketToolQTO);
    }


    public int queryToolCount(MarketToolQTO marketToolQTO) {

        return ((Integer) getSqlMapClientTemplate().queryForObject("market_tool.queryToolCount", marketToolQTO)).intValue();
    }
}