package com.mockuai.usercenter.core.sendSms;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.mockuai.messagecenter.common.action.ActionEnum;
import com.mockuai.messagecenter.common.api.BaseRequest;
import com.mockuai.messagecenter.common.api.MessageDispatchService;
import com.mockuai.messagecenter.common.api.Request;
import com.mockuai.messagecenter.common.api.Response;
import com.mockuai.messagecenter.common.constant.HandleTypeEnum;
import com.mockuai.messagecenter.common.constant.SmsTempSnEnum;
import com.mockuai.messagecenter.common.dto.SendSmsDTO;
import com.mockuai.messagecenter.common.qto.VerifySmsQTO;
import com.mockuai.messagecenter.core.manager.CacheManager;
import com.mockuai.messagecenter.core.manager.impl.CacheManagerImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class SendSmsTest {
	@Resource
	private MessageDispatchService messageDispatchService;
	
	private final String mobile = "15868145571";

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
		Response response = messageDispatchService.execute(request);
		System.out.println(JSON.toJSON(response));
	}
	

	@Test
	public void smsSertest1() {
		Request request = new BaseRequest();
		
		VerifySmsQTO verifySmsDTO = new VerifySmsQTO();
		
		verifySmsDTO.setMobile(mobile);
		
		verifySmsDTO.setHandleType(HandleTypeEnum.REGISTER.getCode());
		
		request.setParam("appKey", "6562b5ddf0aed2aad8fe471ce2a2c8a0");
		request.setParam("verifySmsQTO", verifySmsDTO);
		request.setCommand(ActionEnum.SMS_SERVICE.getActionName());
		Response response = messageDispatchService.execute(request);
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
		Response response = messageDispatchService.execute(request);
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
		Response response = messageDispatchService.execute(request);
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
		Response response = messageDispatchService.execute(request);
		System.out.println(JSON.toJSON(response));
	}

}
