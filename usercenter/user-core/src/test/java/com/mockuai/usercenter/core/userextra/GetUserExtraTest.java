package com.mockuai.usercenter.core.userextra;

import javax.annotation.Resource;

import com.mockuai.usercenter.common.api.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockuai.usercenter.common.action.ActionEnum;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class GetUserExtraTest {

	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 正确获取买家的扩展信息 测试结果：获取获取
	 * */
	@Test
	public void test1() {
		Request request = new BaseRequest();
		request.setParam("userId", 30L);
		request.setCommand(ActionEnum.GET_USER_EXTRA_INFO.getActionName());

		Response response = userDispatchService.execute(request);
	}

	/**
	 * 指定的用户不存在 测试结果：user is not exist
	 * */
	@Test
	public void test2() {
		Request request = new BaseRequest();
		request.setParam("userId", 100L);
		request.setCommand(ActionEnum.GET_SUPPLIER_EXTRA_INFO.getActionName());

		Response response = userDispatchService.execute(request);
	}
}
