package com.mockuai.seckillcenter.core.service.action.orderhistory;

import com.mockuai.seckillcenter.common.constant.ActionEnum;
import com.mockuai.seckillcenter.common.domain.qto.OrderHistoryQTO;
import com.mockuai.seckillcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 7/21/2016.
 */
public class QueryOrderHistoryActionTest extends BaseActionTest {

	public QueryOrderHistoryActionTest() {
		super(QueryOrderHistoryActionTest.class.getName());
	}

	@Override
	protected String getCommand() {
		return ActionEnum.QUERY_ORDER_HISTORY.getActionName();
	}

	@Test
	public void test() {

		OrderHistoryQTO orderHistoryQTO = new OrderHistoryQTO();
		orderHistoryQTO.setSeckillId(10L);
		orderHistoryQTO.setCount(10);
		orderHistoryQTO.setOffset(10);
		request.setParam("orderHistoryQTO", orderHistoryQTO);
		doExecute();
	}
}