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
public class RefuseUserIdenTest {

	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 正确的用户实名认证通过 测试结果:认证正确
	 * */
	@Test
	public void test1() {
		Request request = new BaseRequest();
		request.setParam("userId", 30L);
		request.setParam("remark", "sb一律不通过");
		request.setCommand(ActionEnum.REJECT_USER_AUTH_INFO
				.getActionName());
		Response response = userDispatchService.execute(request);
		System.out.println(JSON.toJSON(response));
	}

}
