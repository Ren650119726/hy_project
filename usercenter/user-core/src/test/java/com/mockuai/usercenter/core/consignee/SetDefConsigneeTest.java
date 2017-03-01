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
public class SetDefConsigneeTest {

	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 正确的将指定的收货地址设为默认 测试结果：正确
	 * */
	@Test
	public void test1() {
		Request request = new BaseRequest();
		request.setParam("userId", 12L);
		request.setParam("consigneeId", 12L);
		request.setCommand(ActionEnum.SET_DEFAULT_CONSIGNEE.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * 将被删除的地址设为默认 测试结果：consignee is not exist
	 * */
	@Test
	public void test2() {
		Request request = new BaseRequest();
		request.setParam("userId", 12L);
		request.setParam("consigneeId", 8L);
		request.setCommand(ActionEnum.SET_DEFAULT_CONSIGNEE.getActionName());
		Response response = userDispatchService.execute(request);
	}
}
