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
public class MoveUserIntoRecycleTest {

	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 正确的将用户移入回收站 测试结果：回收正确
	 * */
	@Test
	public void test1() {
		Request request = new BaseRequest();
		request.setParam("userId", 6L);
		request.setCommand(ActionEnum.MOVE_USER.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * 重复移入回收站 测试结果：
	 */
	@Test
	public void test2() {
		Request request = new BaseRequest();
		request.setParam("userId", 6L);
		request.setCommand(ActionEnum.MOVE_USER.getActionName());
		Response response = userDispatchService.execute(request);
	}
}
