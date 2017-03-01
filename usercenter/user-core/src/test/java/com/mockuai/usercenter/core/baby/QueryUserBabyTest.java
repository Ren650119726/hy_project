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
public class QueryUserBabyTest {
	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 正确的查询用户baby信息 测试结果：添加正确
	 * */
	@Test
	public void test1() {
		Request request = new BaseRequest();
		request.setParam("userId", 30L);
		request.setCommand(ActionEnum.QUERY_USER_BABYS.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * userId对应的用户不存在 测试结果：user is not exist
	 */
	@Test
	public void test2() {
		Request request = new BaseRequest();
		request.setParam("userId", 300L);
		request.setCommand(ActionEnum.QUERY_USER_BABYS.getActionName());
		Response response = userDispatchService.execute(request);
	}
}
