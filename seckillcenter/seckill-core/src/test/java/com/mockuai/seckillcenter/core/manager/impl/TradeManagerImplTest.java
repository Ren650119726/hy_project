package com.mockuai.seckillcenter.core.manager.impl;

import com.mockuai.seckillcenter.core.BaseTest;
import com.mockuai.seckillcenter.core.manager.TradeManager;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 7/23/2016.
 */
public class TradeManagerImplTest extends BaseTest {


	@Autowired
	private TradeManager tradeManager;

	@Test
	public void testAddPreOrder() throws Exception {

	}

	@Test
	public void testGetOrder() throws Exception {

	}

	@Test
	public void testQueryPreOrder() throws Exception {
		OrderQTO orderQTO = new OrderQTO();
		orderQTO.setUserId(22L);
		orderQTO.setItemSkuId(22L);
		tradeManager.queryPreOrder(orderQTO, "27c7bc87733c6d253458fa8908001eef");
	}

	@Test
	public void testQuerySalesRatio() throws Exception {

	}

	@Test
	public void testQueryItemSalesVolume() throws Exception {

	}

	@Test
	public void testGetData() throws Exception {

	}
}