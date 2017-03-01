package com.mockuai.usercenter.core.user;

import java.util.ArrayList;
import java.util.List;

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
public class UpdateUserInfoTest {

	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 参数正确的情况下修改密码 测试结果：修改成功
	 * */	
	@Test
	public void UpdateHeadImg() {
			Request request = new BaseRequest();
			request.setParam("user_id", 1841258L);
			request.setParam("headImg", "/user/img/headImg.png");
			request.setParam("appKey", "e39f32712b4139ee5785f0c40a38031c");
			request.setCommand(ActionEnum.UPDATE_HEADIMG.getActionName());
			Response response = userDispatchService.execute(request);
			Assert.assertTrue(response.isSuccess());
			System.out.println(JsonUtil.toJson(response));
	}


	@Test
	public void updateSexAndBirthday() {
		Request request = new BaseRequest();
		request.setParam("user_id", 1841258L);
		request.setParam("birthday", "1992-11-11");
		request.setParam("sex", "0");
		request.setParam("appKey", "e39f32712b4139ee5785f0c40a38031c");
		request.setCommand(ActionEnum.UPDATE_SEXANDBIRTHDAY.getActionName());
		Response response = userDispatchService.execute(request);
		System.out.println(JsonUtil.toJson(response));
	}

	
	@Test
	public void updateNickNname() {
		Request request = new BaseRequest();
		request.setParam("user_id", 1841258L);
		request.setParam("nick_name", "np53 56822");
		request.setParam("appKey", "e39f32712b4139ee5785f0c40a38031c");
		request.setCommand(ActionEnum.UPDATE_NICKNAME.getActionName());
		Response response = userDispatchService.execute(request);
		System.out.println(JsonUtil.toJson(response));
	}

	@Test
	public void queryUserMessage() {
		Request request = new BaseRequest();
		request.setParam("user_id", 1841256L);
		request.setParam("appKey", "e39f32712b4139ee5785f0c40a38031c");
		request.setCommand(ActionEnum.QUERY_USERONESELFINFO.getActionName());
		Response response = userDispatchService.execute(request);
		System.out.println(JsonUtil.toJson(response));
	}
	
	@Test
	public void updateUserPayPwd() {
		Request request = new BaseRequest();
		request.setParam("user_id", 1841258L);
		request.setParam("pay_pwd", "123456");
		request.setParam("new_pay_pwd", "123454");
		request.setParam("once_pay_pwd", "123454");
		request.setParam("appKey", "e39f32712b4139ee5785f0c40a38031c");
		request.setCommand(ActionEnum.UPDATEUSERPAYPWD.getActionName());
		Response response = userDispatchService.execute(request);
		System.out.println(JsonUtil.toJson(response));
	}
	
	@Test
	public void resetUserPayPwd() {
		Request request = new BaseRequest();
		request.setParam("user_id", 1841258L);
		request.setParam("pay_pwd", "123456");
		request.setParam("once_pay_pwd", "123456");
		request.setParam("id_card", "410526199004294439");
		request.setParam("appKey", "e39f32712b4139ee5785f0c40a38031c");
		request.setCommand(ActionEnum.RESETUSERPAYPWD.getActionName());
		Response response = userDispatchService.execute(request);
		System.out.println(JsonUtil.toJson(response));
	}
	
	@Test
	public void getUserAuthInfo() {
		Request request = new BaseRequest();
		request.setParam("user_id", 1841255323L);
		request.setParam("appKey", "e39f32712b4139ee5785f0c40a38031c");
		request.setCommand(ActionEnum.GET_USER_AUTH_INFO.getActionName());
		Response response = userDispatchService.execute(request);
		System.out.println(JsonUtil.toJson(response));
	}
	
	@Test
	public void checkUserMobile() {
		Request request = new BaseRequest();
		request.setParam("user_id", 1841258L);
		request.setParam("mobile", "18410091381");
		request.setParam("verify_code", "222222");
		request.setParam("bank_personal_id", "");
		request.setParam("mFlag", "1");
		request.setParam("appKey", "e39f32712b4139ee5785f0c40a38031c");
		request.setCommand(ActionEnum.CHECK_USERMOBILE.getActionName());
		Response response = userDispatchService.execute(request);
		System.out.println(JsonUtil.toJson(response));
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void queryUserSafeInfo() {
		Request request = new BaseRequest();
		request.setParam("user_id", 1841258L);			
		request.setParam("appKey", "e39f32712b4139ee5785f0c40a38031c");
		request.setCommand(ActionEnum.QUERY_USERSAFEINFO.getActionName());
		Response response = userDispatchService.execute(request);
		System.out.println(JsonUtil.toJson(response));
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void queryUserDirectory() {
		Request request = new BaseRequest();
		List<String> jsonList = new ArrayList<String>();
		jsonList.add("{name=\"csy\",mobile=\"18410091380\"}");
		
		request.setParam("userMobileDirJsonList", jsonList);			
		request.setParam("appKey", "e39f32712b4139ee5785f0c40a38031c");
		request.setCommand(ActionEnum.QUERY_USERMOBILEDIRECTORY.getActionName());
		Response response = userDispatchService.execute(request);
		System.out.println(JsonUtil.toJson(response));
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void queryInviterListByUserId() {
		Request request = new BaseRequest();
		UserQTO userQto = new UserQTO();
		userQto.setCount(6);
		userQto.setOffset(2L);
		userQto.setId(1L);
		
		request.setParam("userQTO", userQto);			
		request.setParam("appKey", "e39f32712b4139ee5785f0c40a38031c");
		request.setCommand(ActionEnum.QUERY_INVITERLISTBYUSERID.getActionName());
		Response response = userDispatchService.execute(request);
		System.out.println(JsonUtil.toJson(response));
	}
}
