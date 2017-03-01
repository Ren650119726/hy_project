package com.mockuai.usercenter.core.user;

import javax.annotation.Resource;

import com.mockuai.usercenter.common.api.*;
import com.mockuai.usercenter.core.util.JsonUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.qto.UserQTO;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class QueryUserTest {

	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 获取用户 测试结果：获取成功
	 * */
	@Test
	public void test1() {
		Request request = new BaseRequest();
		UserQTO userQto = new UserQTO();
		userQto.setName("我爱睡觉");
		request.setParam("userQTO", userQto);
		request.setParam("appKey", "4f5508d72d9d78c9242bf1c867ac1063");
		request.setCommand(ActionEnum.QUERY_USER.getActionName());
		Response response = userDispatchService.execute(request);
	}




	/**
	 * userQto 为空获取用户 测试结果：userQto is null
	 * */
	@Test
	public void test2() {
		Request request = new BaseRequest();
		UserQTO userQto = null;
		request.setParam("userQTO", userQto);
		request.setCommand(ActionEnum.QUERY_USER.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * 查询的页数大于最大一页，这默认进入最后一页
	 * */
	@Test
	public void test3() {
		Request request = new BaseRequest();
		UserQTO userQto = new UserQTO();
		userQto.setName("我爱睡觉");
		userQto.setEmail("1dddd@qq.com");
		userQto.setCount(100);
		request.setParam("userQTO", userQto);
		request.setCommand(ActionEnum.QUERY_USER.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * 手机格式错误查询
	 */
	@Test
	public void test4() {
		Request request = new BaseRequest();
		UserQTO userQto = new UserQTO();
		userQto.setName("我爱睡觉");

		userQto.setEmail("1dddd@qq.com");
		request.setParam("userQTO", userQto);
		request.setCommand(ActionEnum.QUERY_USER.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * 通过邀请码获取用户 测试结果：获取成功
	 * */
	@Test
	public void test5() {
		Request request = new BaseRequest();
		UserQTO userQto = new UserQTO();
		userQto.setInvitationCode("5E6TK4FGN");
		request.setParam("userQTO", userQto);
		request.setParam("appKey", "0dcbdaa661f317efd479273b2e46e6aa");
		request.setCommand(ActionEnum.QUERY_USER.getActionName());
		Response response = userDispatchService.execute(request);
		Assert.assertTrue(response.isSuccess());
		System.out.println(JsonUtil.toJson(response.getModule()));
	}

	@Test
	public void test() {
		Request request = new BaseRequest();
		UserQTO userQTO = new UserQTO();
		request.setParam("userQTO", userQTO);
		request.setParam("appKey", "4f5508d72d9d78c9242bf1c867ac1063");
		request.setCommand(ActionEnum.QUERY_USER.getActionName());
		Response response = userDispatchService.execute(request);
		System.out.println("total count = " + response.getTotalCount());
	}

	@Test
	public void test6(){
		Request request = new BaseRequest();
		UserQTO userQTO = new UserQTO();
		List<Long> idList = new ArrayList<Long>();
		idList.add(39392L);
		idList.add(39391L);
		userQTO.setIdList(idList);
		request.setParam("appKey", "4f5508d72d9d78c9242bf1c867ac1063");
		request.setParam("userQTO",userQTO);
		request.setCommand(ActionEnum.QUERY_USER.getActionName());
		Response response = userDispatchService.execute(request);
		System.out.println("total count = " + response.getTotalCount());
	}

}
