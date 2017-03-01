package com.mockuai.usercenter.core;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.mockuai.usercenter.common.api.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.dto.UserAuthInfoDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class UserAuthTest {
	@Resource
	private UserDispatchService userDispatchService;

	@Test
	public void test() {

		Request request = new BaseRequest();

		/*
		 * UserAuthInfoDTO userAuthInfoDto = new UserAuthInfoDTO();
		 * userAuthInfoDto.setReceiverId(11L);
		 * userAuthInfoDto.setIdcardNo("362324199109245718");
		 * userAuthInfoDto.setRealName("大神"); userAuthInfoDto
		 * .setIdcardFrontImg(
		 * "http://society.huanqiu.com/photonew/2015-01/2760449.html");
		 * userAuthInfoDto .setIdcardReverseImg(
		 * "http://g.hiphotos.baidu.com/super/whfpf%3D425%2C260%2C50/sign=f450e5c46e224f4a57cc20536fcaa460/ac4bd11373f082021c0402dd48fbfbedaa641bb9.jpg"
		 * );
		 * 
		 * request.setParam("userAuthDTO", userAuthInfoDto);
		 * request.setCommand(ActionEnum.UPDATE_USER_AUTH_INFO.getActionName());
		 */

		/*
		 * request.setParam("userId", 11L); request.setParam("remark", "信息不合法");
		 * request.setCommand(ActionEnum.REJECT_USER_AUTH_INFO
		 * .getActionName());
		 */

		request.setParam("userId", 11L);
		request.setCommand(ActionEnum.GET_USER_AUTH_INFO.getActionName());
		Response response = userDispatchService.execute(request);

	}

	/**
	 * 添加用户的实名认证信息测试
	 * */
	@Test
	public void addTest() {

		Request request = new BaseRequest();
		UserAuthInfoDTO userAuthInfoDto = new UserAuthInfoDTO();
		userAuthInfoDto.setUserId(19L);
		userAuthInfoDto.setIdcardNo("410426199001043057");
		userAuthInfoDto.setRealName("大神");
		userAuthInfoDto
				.setIdcardFrontImg("http://society.huanqiu.com/photonew/2015-01/2760449.html");
		userAuthInfoDto
				.setIdcardReverseImg("http://g.hiphotos.baidu.com/super/whfpf%3D425%2C260%2C50/sign=f450e5c46e224f4a57cc20536fcaa460/ac4bd11373f082021c0402dd48fbfbedaa641bb9.jpg");
		request.setParam("userAuthDTO", userAuthInfoDto);
		request.setCommand(ActionEnum.ADD_USER_AUTH_INFO.getActionName());
		Response response = userDispatchService.execute(request);
		System.out.println(JSON.toJSON(response));
	}

	/**
	 * 修改测试
	 * */
	@Test
	public void updateTest() {
		Request request = new BaseRequest();
		UserAuthInfoDTO userAuthInfoDto = new UserAuthInfoDTO();
		userAuthInfoDto.setUserId(19L);
		userAuthInfoDto.setId(1L);
		userAuthInfoDto.setIdcardNo("410426199001043057");
		userAuthInfoDto.setRealName("大逗");
		userAuthInfoDto
				.setIdcardFrontImg("http://society.huanqiu.com/photonew/2015-01/2760449.html");
		userAuthInfoDto
				.setIdcardReverseImg("http://g.hiphotos.baidu.com/super/whfpf%3D425%2C260%2C50/sign=f450e5c46e224f4a57cc20536fcaa460/ac4bd11373f082021c0402dd48fbfbedaa641bb9.jpg");

		request.setParam("userAuthDTO", userAuthInfoDto);
		request.setCommand(ActionEnum.ADD_USER_AUTH_INFO.getActionName());
		Response response = userDispatchService.execute(request);
		System.out.println(JSON.toJSON(response));
	}

	@Test
	public void identifiedTest() {
		Request request = new BaseRequest();
		request.setParam("userId", 30L);
		request.setParam("remark", "这个人很帅");
		request.setCommand(ActionEnum.PASS_USER_AUTH_INFO.getActionName());
		Response response = userDispatchService.execute(request);

	}

	@Test
	public void failIdentifiedTest() {
		Request request = new BaseRequest();
		request.setParam("userId", 12L);
		request.setParam("remark", "sb一律不通过");
		request.setCommand(ActionEnum.REJECT_USER_AUTH_INFO
				.getActionName());
		Response response = userDispatchService.execute(request);
	}

}
