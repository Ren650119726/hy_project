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
public class GetUserByIdTest {

	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 获取用户 测试结果：获取成功
	 * */
	@Test
	public void test1() {
		Request request = new BaseRequest();
		request.setParam("userId", 31009L);
		request.setParam("appKey", "0dcbdaa661f317efd479273b2e46e6aa");
		request.setCommand(ActionEnum.GET_USER_BY_ID.getActionName());
		Response response = userDispatchService.execute(request);
		System.out.println(response.getModule());
	}

	/**
	 * 获取不存在的用户 测试结果：user is not exist
	 * */
	@Test
	public void test2() {
		Request request = new BaseRequest();
		request.setParam("userId", 1000L);
		request.setCommand(ActionEnum.GET_USER_BY_ID.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * 用户id格式错误 测试结果：userId must greater than 0
	 * */
	@Test
	public void test3() {
		Request request = new BaseRequest();
		request.setParam("userId", -1L);
		request.setCommand(ActionEnum.GET_USER_BY_ID.getActionName());
		Response response = userDispatchService.execute(request);
	}
}
