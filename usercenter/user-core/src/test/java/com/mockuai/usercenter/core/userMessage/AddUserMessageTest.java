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
public class AddUserMessageTest {
	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 正常给用户发送消息 测试结果：OK
	 */
	@Test
	public void test1() {
		Request request = new BaseRequest();
		UserMessageDTO userMessageDto = new UserMessageDTO();

		userMessageDto.setReceiverId(8L);
		userMessageDto.setSenderId(3L);
		userMessageDto.setType(1);
		userMessageDto.setStatus(1);

		userMessageDto.setSendType(1);
		userMessageDto.setTitle("titleMessagedianxin");
		userMessageDto.setContent("contentMesagedianxin");
		userMessageDto.setDeleteMark(0);
		//userMessageDto.setGmtCreated(20150505);
		//userMessageDto.setGmtModified(20150506);

		request.setParam("userMessageDTO", userMessageDto);
		request.setCommand(ActionEnum.ADD_USER_MESSAGE.getActionName());
		Response response = userDispatchService.execute(request);
		System.out.println(JSON.toJSON(response));
	}

	/**
	 * userMessageDTO 为空添加信息 测试结果:userMessageDto is null
	 */
	//@Test
	public void test2() {
		Request request = new BaseRequest();
		UserMessageDTO userMessageDto = null;

		request.setParam("userMessageDTO", userMessageDto);
		request.setCommand(ActionEnum.ADD_USER_MESSAGE.getActionName());
		Response response = userDispatchService.execute(request);
		System.out.println(JSON.toJSON(response));
	}

	/**
	 * userid不存在 测试结果：userId must greater than 0
	 */
	//@Test
	public void test3() {
		Request request = new BaseRequest();
		UserMessageDTO userMessageDto = new UserMessageDTO();

		//userMessageDto.setReceiverId(1L);
		userMessageDto.setSenderId(2L);
		userMessageDto.setType(1);
		userMessageDto.setStatus(1);

		userMessageDto.setSendType(1);
		userMessageDto.setTitle("title");
		userMessageDto.setDeleteMark(0);

		request.setParam("userMessageDTO", userMessageDto);
		request.setCommand(ActionEnum.ADD_USER_MESSAGE.getActionName());
		Response response = userDispatchService.execute(request);
		System.out.println(JSON.toJSON(response));
	}

	/**
	 * 消息标题为空 测试结果：title is null
	 */
	//@Test
	public void test4() {
		Request request = new BaseRequest();
		UserMessageDTO userMessageDto = new UserMessageDTO();

		userMessageDto.setReceiverId(1L);
		userMessageDto.setSenderId(2L);
		userMessageDto.setType(1);
		userMessageDto.setStatus(1);

		userMessageDto.setSendType(1);
		//userMessageDto.setTitle("title");
		userMessageDto.setDeleteMark(0);

		request.setParam("userMessageDTO", userMessageDto);
		request.setCommand(ActionEnum.ADD_USER_MESSAGE.getActionName());
		Response response = userDispatchService.execute(request);
		System.out.println(JSON.toJSON(response));
	}

	/**
	 * 消息内容为空 测试结果：content is null
	 */
	//@Test
	public void test5() {
		Request request = new BaseRequest();
		UserMessageDTO userMessageDto = new UserMessageDTO();

		userMessageDto.setReceiverId(1L);
		userMessageDto.setSenderId(2L);
		userMessageDto.setType(1);
		userMessageDto.setStatus(1);

		userMessageDto.setSendType(1);
		userMessageDto.setTitle("title");
		//userMessageDto.setSendCondition("content");
		userMessageDto.setDeleteMark(0);

		request.setParam("userMessageDTO", userMessageDto);
		request.setCommand(ActionEnum.ADD_USER_MESSAGE.getActionName());
		Response response = userDispatchService.execute(request);
		System.out.println(JSON.toJSON(response));
	}

}
