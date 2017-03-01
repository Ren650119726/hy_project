package com.mockuai.marketingcenter.core.manager;

import com.mockuai.marketingcenter.common.domain.qto.MarketToolQTO;
import com.mockuai.marketingcenter.core.domain.MarketToolDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;

import java.util.List;

public abstract interface MarketToolManager {
    public abstract Long addTool(MarketToolDO paramMarketToolDO)
            throws MarketingException;

    public abstract int updateTool(MarketToolDO paramMarketToolDO)
            throws MarketingException;

    public abstract List<MarketToolDO> queryTool(MarketToolQTO paramMarketToolQTO)
            throws MarketingException;

    public abstract MarketToolDO getTool(long paramLong, String paramString)
            throws MarketingException;

    public abstract MarketToolDO getTool(String toolCode)
            throws MarketingException;

    public abstract int queryToolCount(MarketToolQTO paramMarketToolQTO)
            throws MarketingException;
}