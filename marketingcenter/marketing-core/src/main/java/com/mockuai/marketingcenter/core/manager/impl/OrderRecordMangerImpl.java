package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.qto.OrderRecordQTO;
import com.mockuai.marketingcenter.core.dao.OrderRecordDAO;
import com.mockuai.marketingcenter.core.domain.OrderRecordDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.OrderRecordManager;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by edgar.zr on 7/25/2016.
 */
public class OrderRecordMangerImpl implements OrderRecordManager {

	public static final Logger LOGGER = LoggerFactory.getLogger(OrderRecordMangerImpl.class);

	@Autowired
	private OrderRecordDAO orderRecordDAO;

	@Override
	public Long addOrderRecord(OrderRecordDO orderRecordDO) throws MarketingException {
		try {
			Long id = orderRecordDAO.addOrderRecord(orderRecordDO);
			return id;
		} catch (Exception e) {
			LOGGER.error("failed when addOrderRecord, orderRecordDO : {}",
					JsonUtil.toJson(orderRecordDO), e);
			throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
		}
	}

	@Override
	public List<OrderRecordDO> queryOrderRecord(OrderRecordQTO orderRecordQTO) throws MarketingException {
		try {
			List<OrderRecordDO> orderRecordDOs = orderRecordDAO.queryOrderRecord(orderRecordQTO);
			return orderRecordDOs;
		} catch (Exception e) {
			LOGGER.error("failed when queryOrderRecord, orderRecordQTO : {}",
					JsonUtil.toJson(orderRecordQTO), e);
			throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
		}
	}
}