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
public class GetUserByMobileTest {

	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 获取用户 测试结果：获取成功
	 * */
	@Test
	public void test1() {
		Request request = new BaseRequest();
		request.setParam("mobile", "18668017860");
		request.setCommand(ActionEnum.GET_USER_BY_MOBILE.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * 不存在的手机号获取用户 测试结果:user is not exist
	 */
	@Test
	public void test3() {
		Request request = new BaseRequest();
		request.setParam("mobile", "18666666666");
		request.setCommand(ActionEnum.GET_USER_BY_MOBILE.getActionName());
		Response response = userDispatchService.execute(request);
	}

}
