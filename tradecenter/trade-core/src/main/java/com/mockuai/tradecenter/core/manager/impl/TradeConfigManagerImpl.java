package com.mockuai.tradecenter.core.manager.impl;

import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.TradeConfigQTO;
import com.mockuai.tradecenter.core.dao.TradeConfigDAO;
import com.mockuai.tradecenter.core.domain.TradeConfigDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.TradeConfigManager;
import com.mockuai.tradecenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zengzhangqiang on 6/15/16.
 */
public class TradeConfigManagerImpl implements TradeConfigManager{
    private static final Logger log = LoggerFactory.getLogger(TradeConfigManagerImpl.class);

    @Resource
    private TradeConfigDAO tradeConfigDAO;

    @Override
    public Long addTradeConfig(TradeConfigDO record) throws TradeException {
        try {
            return tradeConfigDAO.addTradeConfig(record);
        } catch (Exception e) {
            log.error("error to addTradeConfig, tradeConf:{}", JsonUtil.toJson(record), e);
            throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
        }
    }

    @Override
    public int deleteTradeConfig(TradeConfigQTO record) throws TradeException {
        try {
            return tradeConfigDAO.deleteTradeConfig(record);
        } catch (Exception e) {
            log.error("error to deleteTradeConfig, tradeConf:{}", JsonUtil.toJson(record), e);
            throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
        }

    }

    @Override
    public List<TradeConfigDO> queryTradeConfig(TradeConfigQTO record) throws TradeException {
        try {
            return tradeConfigDAO.queryTradeConfig(record);
        } catch (Exception e) {
            log.error("error to queryTradeConfig, tradeConf:{}", JsonUtil.toJson(record), e);
            throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
        }

    }

    @Override
    public int updateTradeConfig(TradeConfigDO record) throws TradeException {
        try {
            return tradeConfigDAO.updateTradeConfig(record);
        } catch (Exception e) {
            log.error("error to updateTradeConfig, tradeConf:{}", JsonUtil.toJson(record), e);
            throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
        }

    }

    @Override
    public TradeConfigDO getTradeConfig(String bizCode, String attrKey) throws TradeException {
        try {
            return tradeConfigDAO.getTradeConfig(bizCode, attrKey);
        } catch (Exception e) {
            log.error("error to getTradeConfig, bizCode:{}, attrKey:{}", bizCode, attrKey, e);
            throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
        }

    }
}
