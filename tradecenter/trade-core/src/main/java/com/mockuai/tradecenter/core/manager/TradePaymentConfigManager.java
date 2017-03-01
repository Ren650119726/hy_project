package com.mockuai.tradecenter.core.manager;

import java.util.List;

import com.mockuai.tradecenter.common.domain.TradePaymentConfigDTO;
import com.mockuai.tradecenter.common.domain.TradePaymentConfigQTO;
import com.mockuai.tradecenter.core.exception.TradeException;

/**
 * 
 */
public interface TradePaymentConfigManager {

    public List<TradePaymentConfigDTO> queryTradePaymentConfig(TradePaymentConfigQTO record) throws TradeException;
    
}
