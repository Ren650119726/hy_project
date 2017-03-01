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
public class RestoreUserTest {

	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 将回收站的里用户正确还原 测试结果：
	 */
	@Test
	public void test1() {
		Request request = new BaseRequest();
		request.setParam("userId", 6L);
		request.setCommand(ActionEnum.RESTORE_USER.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * 将不在回收站里的用户还原 测试结果：user not in recycle
	 */
	@Test
	public void test2() {
		Request request = new BaseRequest();
		request.setParam("userId", 12L);
		request.setCommand(ActionEnum.RESTORE_USER.getActionName());
		Response response = userDispatchService.execute(request);
	}

}
