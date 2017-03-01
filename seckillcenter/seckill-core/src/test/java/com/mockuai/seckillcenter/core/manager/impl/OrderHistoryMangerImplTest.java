package com.mockuai.seckillcenter.core.manager.impl;

import com.mockuai.seckillcenter.common.domain.qto.OrderHistoryQTO;
import com.mockuai.seckillcenter.core.BaseTest;
import com.mockuai.seckillcenter.core.domain.OrderHistoryDO;
import com.mockuai.seckillcenter.core.manager.OrderHistoryManager;
import com.mockuai.seckillcenter.core.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by edgar.zr on 7/21/2016.
 */
public class OrderHistoryMangerImplTest extends BaseTest {

	@Autowired
	private OrderHistoryManager orderHistoryManager;

	@Test(invocationCount = 100)
	public void testAddOrderHistory() throws Exception {
		orderHistoryManager.addOrderHistory(genOrderHistory());
	}

	private OrderHistoryDO genOrderHistory() {
		OrderHistoryDO orderHistoryDO = new OrderHistoryDO();

		orderHistoryDO.setOrderId(9909L);
		orderHistoryDO.setSeckillId(10L);

		return orderHistoryDO;
	}

	@Test
	public void testQueryOrderHistory() throws Exception {
		OrderHistoryQTO orderHistoryQTO = new OrderHistoryQTO();
		orderHistoryQTO.setSeckillId(10L);
		orderHistoryQTO.setCount(10);
		orderHistoryQTO.setOffset(0);
		List<OrderHistoryDO> orderHistoryDOs = orderHistoryManager.queryOrderHistory(orderHistoryQTO);
		System.err.println(JsonUtil.toJson(orderHistoryDOs));
	}

	@Test
	public void testAddOrderHistory1() throws Exception {

	}

	@Test
	public void testQueryOrderHistory1() throws Exception {

	}
}