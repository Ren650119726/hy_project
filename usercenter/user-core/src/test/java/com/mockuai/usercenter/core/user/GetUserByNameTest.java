package com.mockuai.usercenter.core.user;

import javax.annotation.Resource;

import com.mockuai.usercenter.common.api.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockuai.usercenter.common.action.ActionEnum;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class GetUserByNameTest {

	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 获取用户 测试结果：获取成功
	 * */
	@Test
	public void test1() {
		Request request = new BaseRequest();
		request.setParam("name", "yezhenglei2");
		request.setCommand(ActionEnum.GET_USER_BY_NAME.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * 根据不存在的用户名获取用户 测试结果：user is not exist
	 * */
	@Test
	public void test2() {
		Request request = new BaseRequest();
		request.setParam("name", "dasbdds");
		request.setCommand(ActionEnum.GET_USER_BY_NAME.getActionName());
		Response response = userDispatchService.execute(request);
	}
}
