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
public class QueryConsigneeTest {

	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 正确的查询指定用户的收货地址 测试结果：正确
	 * */
	@Test
	public void test1() {
		Request request = new BaseRequest();
		request.setParam("userId", 12L);
		request.setCommand(ActionEnum.QUERY_CONSIGNEE.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * 查询不存在的用户的收货地址 测试结果：user is not exist
	 */
	@Test
	public void test2() {
		Request request = new BaseRequest();
		request.setParam("userId", 100L);
		request.setCommand(ActionEnum.QUERY_CONSIGNEE.getActionName());
		Response response = userDispatchService.execute(request);
	}
}
