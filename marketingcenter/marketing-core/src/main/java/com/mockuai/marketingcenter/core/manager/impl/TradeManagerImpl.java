package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.TradeManager;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import com.mockuai.tradecenter.client.OrderClient;
import com.mockuai.tradecenter.client.TradeDataReportClient;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.domain.DataQTO;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.SalesTotalDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by edgar.zr on 12/16/15.
 */
public class TradeManagerImpl implements TradeManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(TradeManagerImpl.class);

	@Autowired
	private TradeDataReportClient tradeDataReportClient;

	@Autowired
	private OrderClient orderClient;

	public Long querySalesRatio(DataQTO dataQTO, String appKey) throws MarketingException {
		try {
			Response<SalesTotalDTO> response = tradeDataReportClient.querySalesRatio(dataQTO, appKey);
			if (response.isSuccess()) {
				return Long.valueOf(response.getModule().getSalesVolumes());
			}
			LOGGER.error("error to querySalesRatio, dataQTO : {}, errCode : {}, errMsg : {}, appKey :{}",
					JsonUtil.toJson(dataQTO), response.getCode(), response.getMessage(), appKey);
			throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, response.getMessage());
		} catch (MarketingException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("error to querySalesRatio, dataQTO : {}, appKey :{}",
					JsonUtil.toJson(dataQTO), appKey, e);
			throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
		}
	}

	@Override
	public OrderDTO getOrder(Long orderId, Long userId, String appKey) throws MarketingException {
		try {
			Response<OrderDTO> response = orderClient.getOrder(orderId, userId, appKey);
			if (response.isSuccess()) {
				return response.getModule();
			}
			LOGGER.error("error to getOrder, orderId : {}, userId : {}, appKey :{}, errCode : {}, errMsg : {}",
					orderId, userId, appKey, response.getCode(), response.getMessage());
			throw new MarketingException(response.getCode(), response.getMessage());
		} catch (MarketingException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("error to getOrder, orderId : {}, userId : {},, appKey :{}",
					orderId, userId, appKey, e);
			throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
		}
	}
}