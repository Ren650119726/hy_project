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
public class UpdateMobileTest {

	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 参数正确的情况下修改手机号 测试结果：修改成功
	 * */
	@Test
	public void updateMobileTest() {

		Request request = new BaseRequest();

		request.setParam("userId", 30L);
		request.setParam("mobile", "18668017860");
		request.setCommand(ActionEnum.UPDATE_MOBILE.getActionName());
		Response response = userDispatchService.execute(request);
	}

}
