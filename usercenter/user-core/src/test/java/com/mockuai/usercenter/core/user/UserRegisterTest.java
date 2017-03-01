package com.mockuai.usercenter.core.user;

import javax.annotation.Resource;

import com.mockuai.usercenter.common.api.*;

import junit.framework.Assert;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.core.util.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class UserRegisterTest {

	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 用户正常注册(网页)
	 * */
	@Test
	public void test1() {
		Request request = new BaseRequest();
		request.setParam("mobile", "18410091381");
		request.setParam("verifyCode", "555555");
		request.setParam("password", "123456789");
		request.setParam("invitationCode", "18705213921");
		request.setParam("registerFlag", "1");
		request.setParam("appKey", "3bc25302234640259fadea047cb7c7d3");
		request.setCommand(ActionEnum.USER_REGISTER.getActionName());
		Response response = userDispatchService.execute(request);
		System.out.println(JsonUtil.toJson(response));
		Assert.assertNotNull(response.getModule());
	}

	/**
	 * 用户正常登陆（邮箱号和密码登陆） 测试结果：登陆成功
	 * */
	//@Test
	public void test2() {
		Request request = new BaseRequest();
		request.setParam("loginName", "742001928@qq.com");
		request.setParam("loginPwd", "fsd123456");
		request.setCommand(ActionEnum.USER_LOGIN.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * 用户正常登陆（手机号和密码登陆） 测试结果：登陆成功
	 * */
	//@Test
	public void test3() {
		Request request = new BaseRequest();
		request.setParam("loginName", "18668017861");
		request.setParam("loginPwd", "fsd123456");
		request.setCommand(ActionEnum.USER_LOGIN.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * 密码和用户不一致 测试结果：登陆失败
	 * */
	//@Test
	public void test4() {
		Request request = new BaseRequest();
		request.setParam("loginName", "yezhenglei1");
		request.setParam("loginPwd", "fsd12345");
		request.setCommand(ActionEnum.USER_LOGIN.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * 登陆名为空 测试结果：loginName is null
	 * */
	//@Test
	public void test5() {
		Request request = new BaseRequest();
		String loginName = null;
		request.setParam("loginName", loginName);
		request.setParam("loginPwd", "fsd12345");
		request.setCommand(ActionEnum.USER_LOGIN.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * 密码为空 测试结果： loginPwd is null
	 * */
	//@Test
	public void test6() {
		Request request = new BaseRequest();
		String loginPwd = null;
		request.setParam("loginName", "yezhenglei1");
		request.setParam("loginPwd", loginPwd);
		request.setCommand(ActionEnum.USER_LOGIN.getActionName());
		Response response = userDispatchService.execute(request);
	}
}
