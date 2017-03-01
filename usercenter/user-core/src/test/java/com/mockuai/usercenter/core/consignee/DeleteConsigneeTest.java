package com.mockuai.usercenter.core.consignee;

import javax.annotation.Resource;

import com.mockuai.usercenter.common.api.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockuai.usercenter.common.action.ActionEnum;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class DeleteConsigneeTest {

	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 正确的删除指定的 测试结果:正常删除
	 * */
	@Test
	public void test1() {
		Request request = new BaseRequest();
		request.setParam("userId", 12L);
		request.setParam("consigneeId", 12L);
		request.setCommand(ActionEnum.DELETE_CONSIGNEE.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * 收货地址id为空 测试结果： consigneeId is null
	 * */
	@Test
	public void test3() {
		Long consigneeId = null;
		Request request = new BaseRequest();
		request.setParam("consigneeId", consigneeId);
		request.setCommand(ActionEnum.DELETE_CONSIGNEE.getActionName());
		Response response = userDispatchService.execute(request);
	}
}
