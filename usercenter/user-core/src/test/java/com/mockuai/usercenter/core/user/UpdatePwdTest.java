package com.mockuai.usercenter.core.user;

import javax.annotation.Resource;

import com.mockuai.usercenter.common.api.*;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.qto.UserQTO;
import com.mockuai.usercenter.core.util.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class UpdatePwdTest {

	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 参数正确的情况下修改密码 测试结果：修改成功
	 * */	
	@SuppressWarnings("rawtypes")
	@Test
	public void updatePwdTest() {
		Request request = new BaseRequest();
		request.setParam("user_id", 1841255L);
		request.setParam("mobile", "11111111111");
		request.setParam("verify_code", "456123");
		request.setParam("newPwd", "123456789");
		request.setParam("appKey","3bc25302234640259fadea047cb7c7d3");
		request.setCommand(ActionEnum.UPDATE_PWD.getActionName());
		Response response = userDispatchService.execute(request);
		System.out.println(JsonUtil.toJson(response));
		Assert.assertTrue(response.isSuccess());
	}

	/**
	 * 用户名和密码不匹配的情况下修改密码 测试结果：old password is error
	 * */
	@Test
	public void updatePwdTest1() {
		Request request = new BaseRequest();
		request.setParam("userId", 14L);
		request.setParam("oldPwd", "zl123456");
		request.setParam("newPwd", "np5356822");
		request.setCommand(ActionEnum.UPDATE_PWD.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * 新老密码相同的情况下修改密码 测试结果：new password and old password is same
	 * */
	@Test
	public void updatePwdTest2() {
		Request request = new BaseRequest();
		request.setParam("userId", 14L);
		request.setParam("oldPwd", "np5356822");
		request.setParam("newPwd", "np5356822");
		request.setCommand(ActionEnum.UPDATE_PWD.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * 错误的密码格式下修改密码 测试结果：password format error
	 * */
	@Test
	public void updatePwdTest3() {
		Request request = new BaseRequest();
		request.setParam("userId", 14L);
		request.setParam("oldPwd", "np5356822");
		request.setParam("newPwd", "np5");
		request.setCommand(ActionEnum.UPDATE_PWD.getActionName());
		Response response = userDispatchService.execute(request);
	}
	
	@Test
	public void updatePwdTest4() {
		Request request = new BaseRequest();
		request.setParam("user_id", 626L);
		request.setParam("appKey", "e39f32712b4139ee5785f0c40a38031c");
		request.setCommand(ActionEnum.QUERY_HIKECONDITION.getActionName());
		Response response = userDispatchService.execute(request);
	}
	
	@Test
	public void updatePwdTest5() {
		Request request = new BaseRequest();
		request.setParam("user_id", 626L);
		request.setParam("wechat", "5555555");
		request.setParam("qq_code", "4554545454");
		request.setParam("appKey", "e39f32712b4139ee5785f0c40a38031c");
		request.setCommand(ActionEnum.UPDATE_WECHATANDQQCODE.getActionName());
		Response response = userDispatchService.execute(request);
	}
	
	@Test
	public void updatePwdTest6() {
		Request request = new BaseRequest();
		UserQTO userQTO = new UserQTO();
		userQTO.setId(1L);
		request.setParam("userQTO", userQTO);
		request.setParam("appKey", "e39f32712b4139ee5785f0c40a38031c");
		request.setCommand(ActionEnum.QUERY_INVITERLISTBYUSERID.getActionName());
		Response response = userDispatchService.execute(request);
	}
}
