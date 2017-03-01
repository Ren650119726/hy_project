package com.mockuai.usercenter.core.baby;

import javax.annotation.Resource;

import com.mockuai.usercenter.common.api.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockuai.usercenter.common.action.ActionEnum;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class DeleteUserBabyTest {
	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 正确的删除baby信息 测试结果：添加正确
	 * */
	@Test
	public void test1() {
		Request request = new BaseRequest();
		request.setParam("userId", 30L);
		request.setParam("babyId", 8L);
		request.setCommand(ActionEnum.DELETE_USER_BABY_INFO.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * babyId小于0 测试结果：id must greater than zero
	 * */
	@Test
	public void test2() {
		Request request = new BaseRequest();
		request.setParam("userId", 30L);
		request.setParam("babyId", -3L);
		request.setCommand(ActionEnum.DELETE_USER_BABY_INFO.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * babyId为空 测试结果：babyId is null
	 * */
	@Test
	public void test3() {
		Request request = new BaseRequest();
		Long babyId = null;
		request.setParam("userId", 30L);
		request.setParam("babyId", babyId);
		request.setCommand(ActionEnum.DELETE_USER_BABY_INFO.getActionName());
		Response response = userDispatchService.execute(request);
	}

}
