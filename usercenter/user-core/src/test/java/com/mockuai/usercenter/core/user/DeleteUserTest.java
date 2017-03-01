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
public class DeleteUserTest {

	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 正常情况下删除用户(只有在回收站里的用户才能被删除) 测试结果：删除成功
	 */
	@Test
	public void test1() {
		Request request = new BaseRequest();
		request.setParam("userId", 13L);
		request.setCommand(ActionEnum.DELETE_USER.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * 删除不在回收站里的用户 测试结果：user not in recycle
	 */
	@Test
	public void test2() {
		Request request = new BaseRequest();
		request.setParam("userId", 15L);
		request.setCommand(ActionEnum.DELETE_USER.getActionName());
		Response response = userDispatchService.execute(request);
	}

}
