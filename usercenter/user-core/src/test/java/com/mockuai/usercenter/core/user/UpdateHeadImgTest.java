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
public class UpdateHeadImgTest {

	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 参数正确的情况下修改头像 测试结果：修改成功
	 * */
	@Test
	public void Test1() {

		Request request = new BaseRequest();
		request.setParam("userId", 30L);
		request.setParam("headImg", "www.google.com");
		request.setCommand(ActionEnum.UPDATE_HEADIMG.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * 头像为空 测试结果：email format error
	 */
	@Test
	public void Test2() {

		Request request = new BaseRequest();
		String headImg = null;
		request.setParam("userId", 30L);
		request.setParam("email", headImg);
		request.setCommand(ActionEnum.UPDATE_HEADIMG.getActionName());
		Response response = userDispatchService.execute(request);
	}
}
