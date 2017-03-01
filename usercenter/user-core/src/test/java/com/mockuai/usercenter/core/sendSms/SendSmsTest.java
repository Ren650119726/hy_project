package com.mockuai.usercenter.core.sendSms;

import com.alibaba.fastjson.JSON;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.api.UserDispatchService;
import com.mockuai.usercenter.common.dto.SendSmsDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class SendSmsTest {
	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 正常给用户发送消息 测试结果：OK
	 */
	@Test
	public void test1() {
		Request request = new BaseRequest();
		SendSmsDTO sendSmsDto = new SendSmsDTO();

		sendSmsDto.setMobile("18668194950");

		request.setParam("appKey", "eb1b83c003bb6f2a938a5815e47e77f7");
		request.setParam("sendSmsDTO", sendSmsDto);
		request.setCommand(ActionEnum.SEND_SMS.getActionName());
		Response response = userDispatchService.execute(request);
		System.out.println(JSON.toJSON(response));
	}

	/**
	 * 默认用户名密码发消息 测试结果：OK
	 */
	//@Test
	public void test2() {
		Request request = new BaseRequest();
		SendSmsDTO sendSmsDto = new SendSmsDTO();

		sendSmsDto.setMobile("18668194950");
		sendSmsDto.setContent("这是您的注册验证码：123456，感谢您的注册！【魔筷中国】");

		request.setParam("sendSmsDTO", sendSmsDto);
		request.setCommand(ActionEnum.SEND_SMS.getActionName());
		Response response = userDispatchService.execute(request);
		System.out.println(JSON.toJSON(response));
	}

	/**
	 * sendSmsDTO 为空添加信息 测试结果:sendSmsDTO is null
	 */
	//@Test
	public void test3() {
		Request request = new BaseRequest();
		SendSmsDTO sendSmsDto = null;

		request.setParam("sendSmsDTO", sendSmsDto);
		request.setCommand(ActionEnum.ADD_USER_MESSAGE.getActionName());
		Response response = userDispatchService.execute(request);
		System.out.println(JSON.toJSON(response));
	}

	/**
	 * 手机号为空 测试结果：Mobile is null
	 */
	//@Test
	public void test4() {
		Request request = new BaseRequest();
		SendSmsDTO sendSmsDto = new SendSmsDTO();

		//sendSmsDto.setMobile("18668194950");
		sendSmsDto.setContent("这是您的注册验证码：123456，感谢您的注册！【魔筷中国】");

		request.setParam("sendSmsDTO", sendSmsDto);
		request.setCommand(ActionEnum.SEND_SMS.getActionName());
		Response response = userDispatchService.execute(request);
		System.out.println(JSON.toJSON(response));
	}

	/**
	 * 内容为空 测试结果：content is null
	 */
	//@Test
	public void test5() {
		Request request = new BaseRequest();
		SendSmsDTO sendSmsDto = new SendSmsDTO();

		sendSmsDto.setMobile("18668194950");
		//sendSmsDto.setContent("这是您的注册验证码：123456，感谢您的注册！【魔筷中国】");

		request.setParam("sendSmsDTO", sendSmsDto);
		request.setCommand(ActionEnum.SEND_SMS.getActionName());
		Response response = userDispatchService.execute(request);
		System.out.println(JSON.toJSON(response));
	}

}
