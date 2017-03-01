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
public class DeleteUserGroupTest {
	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 正确的删除 测试结果：成功删除
	 * */
	@Test
	public void test1() {

		Request request = new BaseRequest();

		request.setParam("groupId", 15);
		request.setCommand(ActionEnum.DEL_USER_GROUP.getActionName());

		Response response = userDispatchService.execute(request);

	}

	/**
	 * groupId 为空 测试结果：groupId is null
	 * */
	@Test
	public void test2() {

		Request request = new BaseRequest();

		Long groupId = null;
		request.setParam("groupId", groupId);
		request.setCommand(ActionEnum.DEL_USER_GROUP.getActionName());

		Response response = userDispatchService.execute(request);

	}

	/**
	 * 删除非组等级最高的组 测试结果：delete error,not the largest group_no
	 */
	@Test
	public void test3() {

		Request request = new BaseRequest();

		request.setParam("groupId", 16);
		request.setCommand(ActionEnum.DEL_USER_GROUP.getActionName());

		Response response = userDispatchService.execute(request);

	}

}
