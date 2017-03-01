package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.OrderGeneralDTO;
import com.mockuai.marketingcenter.core.dao.OrderGeneralDAO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.OrderGeneralManager;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by edgar.zr on 7/25/2016.
 */
public class OrderGeneralMangerImpl implements OrderGeneralManager {

	public static final Logger LOGGER = LoggerFactory.getLogger(OrderGeneralMangerImpl.class);

	@Autowired
	private OrderGeneralDAO orderGeneralDAO;

	@Override
	public OrderGeneralDTO getOrderGeneral(OrderGeneralDTO orderGeneralDTO) throws MarketingException {
		try {
			OrderGeneralDTO record = orderGeneralDAO.getOrderGeneral(orderGeneralDTO);
			return record;
		} catch (Exception e) {
			LOGGER.error("failed when getOrderGeneral, orderGeneralDTO : {}",
					JsonUtil.toJson(orderGeneralDTO), e);
			throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
		}
	}
}