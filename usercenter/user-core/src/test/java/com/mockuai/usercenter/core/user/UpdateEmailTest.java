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
public class UpdateEmailTest {

	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 参数正确的情况下修改邮箱 测试结果：修改成功
	 * */
	@Test
	public void updateEmailTest() {

		Request request = new BaseRequest();
		request.setParam("userId", 30L);
		request.setParam("email", "742001948@qq.com");
		request.setCommand(ActionEnum.UPDATE_EMAIL.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * 邮箱格式错误的情况下修改邮箱 测试结果：email format error
	 */
	@Test
	public void updateEmailTest2() {

		Request request = new BaseRequest();
		request.setParam("userId", 30L);
		request.setParam("email", "742001938qq.com");
		request.setCommand(ActionEnum.UPDATE_EMAIL.getActionName());
		Response response = userDispatchService.execute(request);
	}
}
