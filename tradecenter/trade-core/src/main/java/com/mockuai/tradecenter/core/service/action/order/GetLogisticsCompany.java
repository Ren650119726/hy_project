package com.mockuai.tradecenter.core.service.action.order;

import java.util.List;

import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.domain.LogisticsCompanyDTO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.util.LogisticsCompanyUtil;

public class GetLogisticsCompany   implements Action{

	@Override
	public TradeResponse< List<LogisticsCompanyDTO>> execute(RequestContext context) throws TradeException {
		List<LogisticsCompanyDTO> result = LogisticsCompanyUtil.getLogisticsCompany();
		return ResponseUtils.getSuccessResponse(result);
	}

	@Override
	public String getName() {
		return ActionEnum.GET_LOGISTICS_COMPANY.getActionName();
	}
	
}
