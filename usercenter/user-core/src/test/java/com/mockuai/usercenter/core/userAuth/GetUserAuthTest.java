package com.mockuai.usercenter.core.userAuth;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.mockuai.usercenter.common.api.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockuai.usercenter.common.action.ActionEnum;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class GetUserAuthTest {

	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 正确的获取用户认证信息 测试结果:获取正确
	 * */
	@Test
	public void test1() {
		Request request = new BaseRequest();
		request.setParam("userId", 30L);
		request.setCommand(ActionEnum.GET_USER_AUTH_INFO.getActionName());
		Response response = userDispatchService.execute(request);
		System.out.println(JSON.toJSON(response));
	}

}
