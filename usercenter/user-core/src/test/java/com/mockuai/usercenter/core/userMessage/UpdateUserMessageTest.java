package com.mockuai.usercenter.core.userMessage;

import com.alibaba.fastjson.JSON;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.api.UserDispatchService;
import com.mockuai.usercenter.common.dto.UserMessageDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class UpdateUserMessageTest {
	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 修改信息状态 测试结果：request success
	 */
	//@Test
	public void test1() {

		Request request = new BaseRequest();
		UserMessageDTO userMessageDto = new UserMessageDTO();
		request.setParam("id", 7L);
		request.setParam("userId", 7L);
		request.setParam("UserMessageDTO", userMessageDto);
		request.setCommand(ActionEnum.UPDATE_USER_MESSAGE_STATUS.getActionName());
		Response response = userDispatchService.execute(request);
		System.out.println(JSON.toJSON(response));
	}

	/**
	 * id为空测试 测试结果：id is null
	 * */
	@Test
	public void test2() {
		Request request = new BaseRequest();

		//request.setParam("id", 1L);
		request.setParam("userId", 1L);

		request.setCommand(ActionEnum.UPDATE_USER_MESSAGE_STATUS.getActionName());
		Response response = userDispatchService.execute(request);
		System.out.println(JSON.toJSON(response));
	}

	/**
	 * userId为空测试 测试结果：userId is null
	 * */
	@Test
	public void test3() {
		Request request = new BaseRequest();
		request.setParam("id", 1L);
		//request.setParam("userId", 1L);
		request.setCommand(ActionEnum.UPDATE_USER_MESSAGE_STATUS.getActionName());
		Response response = userDispatchService.execute(request);
		System.out.println(JSON.toJSON(response));
	}
}
