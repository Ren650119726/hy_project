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
public class UserLoginTest {

	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 
	 * */
	@Test
	public void UpdateHeadImg() {
		Request request = new BaseRequest();
		request.setParam("loginName", "18410091380");
		request.setParam("loginPwd", "123456789");
		request.setParam("loginVerifyCode", "222222");
		request.setParam("loginFlag", "0");
		request.setParam("loginSource", "1");
		request.setParam("appKey", "6562b5ddf0aed2aad8fe471ce2a2c8a0");
		request.setCommand(ActionEnum.USER_LOGIN.getActionName());
		Response response = userDispatchService.execute(request);
		System.out.println(JsonUtil.toJson(response));
		Assert.assertNotNull(response.getModule());
	}

	/**
	 * 设置用户登录基础信息
	 * */
	@Test
	public void test2() {
		Request request = new BaseRequest();
		request.setParam("user_id", 1841258L);
		request.setParam("mobile", "18410091381");
		request.setParam("password", "123456789");
		request.setParam("verifyCode", "5EMYZZUKC");
		request.setParam("appKey", "e39f32712b4139ee5785f0c40a38031c");
		request.setCommand(ActionEnum.UPDATEUSERLOGININFOMISS.getActionName());		
		Response response = userDispatchService.execute(request);
		System.out.println(JsonUtil.toJson(response));
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
