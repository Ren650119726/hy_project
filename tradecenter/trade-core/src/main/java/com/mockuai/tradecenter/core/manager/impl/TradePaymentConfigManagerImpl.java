package com.mockuai.tradecenter.core.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.TradePaymentConfigDTO;
import com.mockuai.tradecenter.common.domain.TradePaymentConfigQTO;
import com.mockuai.tradecenter.core.dao.TradePaymentConfigDAO;
import com.mockuai.tradecenter.core.domain.TradePaymentConfigDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.TradePaymentConfigManager;
import com.mockuai.tradecenter.core.util.JsonUtil;
import com.mockuai.tradecenter.core.util.ModelUtil;


public class TradePaymentConfigManagerImpl implements TradePaymentConfigManager{
    private static final Logger log = LoggerFactory.getLogger(TradePaymentConfigManagerImpl.class);

    @Resource
    private TradePaymentConfigDAO tradePaymentConfigDAO;

    @Override
    public List<TradePaymentConfigDTO> queryTradePaymentConfig(TradePaymentConfigQTO record) throws TradeException {

    	List<TradePaymentConfigDTO> tradePaymentConfigDTOList = new ArrayList<TradePaymentConfigDTO>();
    	List<TradePaymentConfigDO> tradePaymentConfigDOList = null;
        try {        	
        	tradePaymentConfigDOList = tradePaymentConfigDAO.queryTradePaymentConfig(record);
        } catch (Exception e) {
            log.error("error to queryTradePaymentConfig, tradeConf:{}", JsonUtil.toJson(record), e);
            throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
        }
    	if(CollectionUtils.isNotEmpty(tradePaymentConfigDOList)){
    		for(TradePaymentConfigDO tradePaymentConfigDO:tradePaymentConfigDOList){
        		TradePaymentConfigDTO tradePaymentConfigDTO = ModelUtil.convert2TradePaymentConfigDTO(tradePaymentConfigDO);
        		tradePaymentConfigDTOList.add(tradePaymentConfigDTO);
        	}
    	}
    	else{
//    		throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "无可用的支付方式");
    		throw new TradeException(ResponseCode.NO_PAYMENT_METHOD);
    	}
    	
        return tradePaymentConfigDTOList;
        

    }

}
