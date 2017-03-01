package com.mockuai.tradecenter.core.service.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.TradeConfigDTO;
import com.mockuai.tradecenter.core.dao.TradeConfigDAO;
import com.mockuai.tradecenter.core.domain.TradeConfigDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.util.DozerBeanService;

public class UpdateTradeConfig implements Action {
	private static final Logger log = LoggerFactory.getLogger(UpdateTradeConfig.class);

	@Resource
	private TradeConfigDAO tradeConfigDAO;

	@Resource
	private TransactionTemplate transactionTemplate;
	
	@Resource
	private DozerBeanService dozerBeanService;

	@SuppressWarnings("unchecked")
	public TradeResponse<Boolean> execute(RequestContext context) throws TradeException {
		Request request = context.getRequest();
		TradeResponse<Boolean> response = null;
		if (request.getParam("tradeConfigAttrs") == null) {
			log.error("tradeConfigAttrs is null ");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "tradeConfigAttrs is null");
		}
		String bizCode = (String) context.get("bizCode");

		final List<TradeConfigDTO> configAttrs = (List<TradeConfigDTO>) request.getParam("tradeConfigAttrs");
		if (configAttrs.isEmpty()) {
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "tradeConfigAttrs is empty");
		}
		for (TradeConfigDTO configAttrDTO : configAttrs) {
			if (StringUtils.isBlank(configAttrDTO.getAttrKey()))
				return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "attrKey is not null");
			if (StringUtils.isBlank(configAttrDTO.getAttrValue()))
				return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "attrValue is not null");

			configAttrDTO.setBizCode(bizCode);
		}

		response = transactionTemplate.execute(new TransactionCallback<TradeResponse>() {
			TradeResponse<Boolean> response = new TradeResponse(ResponseCode.RESPONSE_SUCCESS);
			@Override
			public TradeResponse doInTransaction(TransactionStatus status) {
				try{
					for (TradeConfigDTO configAttrDTO : configAttrs) {
						TradeConfigDO tradeConfigDO =	tradeConfigDAO.getTradeConfig(configAttrDTO.getBizCode(), configAttrDTO.getAttrKey());
						if(null==tradeConfigDO){
							tradeConfigDAO.addTradeConfig(dozerBeanService.cover(configAttrDTO, TradeConfigDO.class));
						}else{
							tradeConfigDO.setAttrValue(configAttrDTO.getAttrValue());
							tradeConfigDAO.updateTradeConfig(tradeConfigDO);
						}
					}
				}catch(Exception e){
					log.error("updateTradeConfig error",e);
					response.setCode(ResponseCode.SYS_E_SERVICE_EXCEPTION.getCode());
					response.setMessage(ResponseCode.SYS_E_SERVICE_EXCEPTION.getComment());
				}
				return response;
			}

		});

		return response;
	}

	@Override
	public String getName() {
		return ActionEnum.UPDATE_TRADE_CONFIG.getActionName();
	}
}
