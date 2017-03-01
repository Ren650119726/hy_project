package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.marketingcenter.common.domain.qto.OrderRecordQTO;
import com.mockuai.marketingcenter.core.BaseTest;
import com.mockuai.marketingcenter.core.domain.OrderRecordDO;
import com.mockuai.marketingcenter.core.manager.OrderRecordManager;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by edgar.zr on 7/26/2016.
 */
public class OrderRecordMangerImplTest extends BaseTest {

	@Autowired
	private OrderRecordManager orderRecordManager;

	@Test(invocationCount = 20)
	public void testAddOrderRecord() throws Exception {
		OrderRecordDO orderRecordDO = genOrderRecordDO();
		orderRecordManager.addOrderRecord(orderRecordDO);
	}

	private OrderRecordDO genOrderRecordDO() {
		OrderRecordDO orderRecordDO = new OrderRecordDO();
		orderRecordDO.setOrderId(1L);
		orderRecordDO.setUserId(593L);
		orderRecordDO.setActivityId(2352L);
		return orderRecordDO;
	}

	@Test
	public void testQueryOrderRecord() throws Exception {
		OrderRecordQTO orderRecordQTO = new OrderRecordQTO();
		orderRecordQTO.setActivityId(2352L);
		orderRecordQTO.setCount(10);
		orderRecordQTO.setOffset(0);
		List<OrderRecordDO> orderRecordDOs = orderRecordManager.queryOrderRecord(orderRecordQTO);
		System.err.println(JsonUtil.toJson(orderRecordDOs));
	}
}