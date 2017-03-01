package com.mockuai.marketingcenter.core.service.action.activity;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.domain.dto.OrderGeneralDTO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.OrderGeneralManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.Action;
import com.mockuai.marketingcenter.core.util.MarketingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by edgar.zr on 7/25/2016.
 */
@Service
public class GetOrderGeneralAction implements Action {

	public static final Logger LOGGER = LoggerFactory.getLogger(GetOrderGeneralAction.class);

	@Autowired
	private OrderGeneralManager orderGeneralManager;

	@Override
	public MarketingResponse execute(RequestContext context) throws MarketingException {
		OrderGeneralDTO orderGeneralDTO = (OrderGeneralDTO) context.getRequest().getParam("orderGeneralDTO");

		OrderGeneralDTO record = orderGeneralManager.getOrderGeneral(orderGeneralDTO);

		// TODO 部署包
		return MarketingUtils.getSuccessResponse(record);
	}

	@Override
	public String getName() {
		return ActionEnum.GET_ORDER_GENERAL.getActionName();
	}
}