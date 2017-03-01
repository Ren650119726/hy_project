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
public class SetUserRole {

	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 正常修改用户的角色 测试结果：修改正常
	 */
	@Test
	public void test1() {
		Request request = new BaseRequest();
		request.setParam("userId", 30L);
		request.setParam("role", (byte) 1);
		request.setCommand(ActionEnum.SET_ROLE.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * 用户角色错误 测试结果： role value is error
	 * */
	@Test
	public void test2() {
		Request request = new BaseRequest();
		request.setParam("userId", 30L);
		request.setParam("role", (byte) 10);
		request.setCommand(ActionEnum.SET_ROLE.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * 用户不存在 测试结果：user is not exist
	 * */
	@Test
	public void test3() {
		Request request = new BaseRequest();
		request.setParam("userId", 100L);
		request.setParam("role", (byte) 0);
		request.setCommand(ActionEnum.SET_ROLE.getActionName());
		Response response = userDispatchService.execute(request);
	}
}
