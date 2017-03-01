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
public class GetUserByEmailTest {

	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 获取用户 测试结果：获取成功
	 * */
	@Test
	public void test1() {
		Request request = new BaseRequest();
		request.setParam("email", "742001918@qq.com");
		request.setCommand(ActionEnum.GET_USER_BY_EMAIL.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * 格式错误的邮箱号获取用户 测试结果:email format error
	 */
	@Test
	public void test2() {
		Request request = new BaseRequest();
		request.setParam("email", "742001918qq.com");
		request.setCommand(ActionEnum.GET_USER_BY_EMAIL.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * 不存在的邮箱号获取用户 测试结果:user is not exist
	 */
	@Test
	public void test3() {
		Request request = new BaseRequest();
		request.setParam("email", "qqqqqqqq@qq.com");
		request.setCommand(ActionEnum.GET_USER_BY_EMAIL.getActionName());
		Response response = userDispatchService.execute(request);
	}

}
