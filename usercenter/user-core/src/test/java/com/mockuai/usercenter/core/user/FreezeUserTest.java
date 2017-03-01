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
public class FreezeUserTest {

	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 正确的冻结用户 测试结果:正常冻结
	 * */
	@Test
	public void test1() {
		Request request = new BaseRequest();
		request.setParam("userId", 9L);
		request.setCommand(ActionEnum.FREEZE_USER.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * 对处于冻结状态的用户冻结 测试结果:user is freezed
	 * */
	@Test
	public void test2() {
		Request request = new BaseRequest();
		request.setParam("userId", 7L);
		request.setCommand(ActionEnum.FREEZE_USER.getActionName());
		Response response = userDispatchService.execute(request);
	}
}
