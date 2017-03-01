package com.mockuai.marketingcenter.core.dao;

import com.mockuai.marketingcenter.common.domain.qto.MarketToolQTO;
import com.mockuai.marketingcenter.core.domain.MarketToolDO;

import java.util.List;

public abstract interface MarketToolDAO {
    
    public abstract Long addTool(MarketToolDO paramMarketToolDO);

    public abstract int updateTool(MarketToolDO paramMarketToolDO);

    public abstract MarketToolDO getTool(long paramLong, String paramString);

    public abstract MarketToolDO getTool(String toolCode);

    public abstract List<MarketToolDO> queryTool(MarketToolQTO paramMarketToolQTO);

    public abstract int queryToolCount(MarketToolQTO paramMarketToolQTO);
}