package com.mockuai.seckillcenter.core.service.action.orderhistory;

import com.mockuai.seckillcenter.common.api.SeckillResponse;
import com.mockuai.seckillcenter.common.constant.ActionEnum;
import com.mockuai.seckillcenter.common.domain.dto.OrderHistoryDTO;
import com.mockuai.seckillcenter.common.domain.qto.OrderHistoryQTO;
import com.mockuai.seckillcenter.core.domain.OrderHistoryDO;
import com.mockuai.seckillcenter.core.exception.SeckillException;
import com.mockuai.seckillcenter.core.manager.OrderHistoryManager;
import com.mockuai.seckillcenter.core.service.RequestContext;
import com.mockuai.seckillcenter.core.service.action.TransAction;
import com.mockuai.seckillcenter.core.util.ModelUtil;
import com.mockuai.seckillcenter.core.util.SeckillUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by edgar.zr on 7/21/2016.
 */
@Service
public class QueryOrderHistoryAction extends TransAction {

	@Autowired
	private OrderHistoryManager orderHistoryManager;

	@Override
	protected SeckillResponse doTransaction(RequestContext context) throws SeckillException {
		OrderHistoryQTO orderHistoryQTO = (OrderHistoryQTO) context.getRequest().getParam("orderHistoryQTO");

		List<OrderHistoryDO> orderHistoryDOs = orderHistoryManager.queryOrderHistory(orderHistoryQTO);

		List<OrderHistoryDTO> orderHistoryDTOs = ModelUtil.genOrderHistoryDTOList(orderHistoryDOs);

		return SeckillUtils.getSuccessResponse(orderHistoryDTOs, orderHistoryQTO.getTotalCount());
	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_ORDER_HISTORY.getActionName();
	}
}