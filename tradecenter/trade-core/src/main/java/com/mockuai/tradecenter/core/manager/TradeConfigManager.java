package com.mockuai.tradecenter.core.manager;

import com.mockuai.tradecenter.common.domain.TradeConfigQTO;
import com.mockuai.tradecenter.core.domain.TradeConfigDO;
import com.mockuai.tradecenter.core.exception.TradeException;

import java.util.List;

/**
 * Created by zengzhangqiang on 6/15/16.
 */
public interface TradeConfigManager {
    public Long addTradeConfig(TradeConfigDO record) throws TradeException;

    public int deleteTradeConfig(TradeConfigQTO record) throws TradeException;

    public List<TradeConfigDO> queryTradeConfig(TradeConfigQTO record) throws TradeException;

    public int updateTradeConfig(TradeConfigDO record) throws TradeException;

    public TradeConfigDO getTradeConfig(String bizCode,String attrKey) throws TradeException;
}
