package com.mockuai.usercenter.core.group;

import javax.annotation.Resource;

import com.mockuai.usercenter.common.api.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockuai.usercenter.common.action.ActionEnum;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class QueryGroupTest {
	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 正确获取用户组列表 测试结果：成功获取
	 * */
	@Test
	public void test1() {

		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_USER_GROUP.getActionName());

		Response response = userDispatchService.execute(request);

	}

}
