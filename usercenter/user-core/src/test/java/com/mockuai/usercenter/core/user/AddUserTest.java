package com.mockuai.usercenter.core.user;

import javax.annotation.Resource;

import com.mockuai.usercenter.common.api.*;
import com.mockuai.usercenter.common.constant.RoleType;
import com.mockuai.usercenter.common.dto.UserOpenInfoDTO;
import com.mockuai.usercenter.core.util.JsonUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.dto.UserDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AddUserTest {

	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 参数正确的情况下添加用户 测试结果:成功添加
	 * */
	@Test
	public void addTest() {
		Request request = new BaseRequest();
		UserDTO userDto = new UserDTO();
		userDto.setPassword("12345678");
		userDto.setMobile("15249274838");
		userDto.setRoleMark(RoleType.BUYER.getValue());

		request.setCommand(ActionEnum.ADD_USER.getActionName());
		request.setParam("userDTO", userDto);
		request.setParam("appKey", "ab91c73a77950b05b65a03cdb02d4bda");

		Response response = userDispatchService.execute(request);
		System.out.println("response:"+ JsonUtil.toJson(response));
		Assert.assertNotNull(response.getModule());
	}
	@Test
	public void addTest11() {
		Request request = new BaseRequest();
		UserDTO userDto = new UserDTO();
		userDto.setPassword("12345678");
		userDto.setMobile("12345678903");
		userDto.setRoleMark(RoleType.BUYER.getValue());
		userDto.setInvitationCode("5ECV5EHXH");

		request.setCommand(ActionEnum.ADD_USER.getActionName());
		request.setParam("userDTO", userDto);
		request.setParam("appKey", "0dcbdaa661f317efd479273b2e46e6aa");

		Response response = userDispatchService.execute(request);
		System.out.println("response:"+ JsonUtil.toJson(response));
		Assert.assertNotNull(response.getModule());
	}

	/**
	 * 测试第三方用户埋点
	 */
	@Test
	public void addTest12() {
		Request request = new BaseRequest();

		request.setCommand(ActionEnum.ADD_OPEN_USER.getActionName());
		UserDTO userDTO = new UserDTO();
		userDTO.setMobile("12345678905");
		userDTO.setType(1);
		userDTO.setRoleMark(RoleType.BUYER.getValue());
		userDTO.setPassword("123");

		String appKey = "0dcbdaa661f317efd479273b2e46e6aa";

		request.setParam("userDTO",userDTO);
		request.setParam("appKey",appKey);

		Response<UserDTO> response = userDispatchService.execute(request);
		Assert.assertNotNull(response.getModule());

//		#bizCode#,
//		#userId#,
//		#openType#,
//		#openId#,
//		#openUid#,
//		#appId#,
//		#name#,

//		Request request = new BaseRequest();
//		UserDTO userDto = new UserDTO();
//		userDto.setPassword("12345678");
//		userDto.setMobile("12345678903");
//		userDto.setRoleMark(RoleType.BUYER.getValue());
//		userDto.setInvitationCode("5ECV5EHXH");
//
//		request.setCommand(ActionEnum.ADD_USER.getActionName());
//		request.setParam("userDTO", userDto);
//		request.setParam("appKey", "0dcbdaa661f317efd479273b2e46e6aa");
//
//		Response<UserDTO> response = userDispatchService.execute(request);
//
//		Request request1 = new BaseRequest();
//		UserOpenInfoDTO userOpenInfoDTO = new UserOpenInfoDTO();
//		userOpenInfoDTO.setUserId(response.getModule().getId());
//		userOpenInfoDTO.setName("test12");
//		userOpenInfoDTO.setBizCode("mockuai_demo");
//		userOpenInfoDTO.setOpenType(1);
//		userOpenInfoDTO.setOpenId("o7-q8vgmQhUyCGh3ZNtEtHsKJjzZ");
//		userOpenInfoDTO.setOpenUid("o5tcIv9Uv7UfcVQj8r_yPPjXQIEE");
//		request1.setParam("appKey", "0dcbdaa661f317efd479273b2e46e6aa");
//
//
//
////		UserDTO userDto = new UserDTO();
////		userDto.setPassword("12345678");
////		userDto.setMobile("12345678904");
////
////		userDto.setRoleMark(RoleType.BUYER.getValue());
////		userDto.setInvitationCode("5ECV5EHa");
//
//		request1.setCommand(ActionEnum.ADD_USER_OPEN_INFO.getActionName());
//
//		request1.setParam("userOpenInfoDTO", userOpenInfoDTO);
////		request.setParam("appKey", "0dcbdaa661f317efd479273b2e46e6aa");
//
//		Response response1 = userDispatchService.execute(request1);
//		System.out.println("response:"+ JsonUtil.toJson(response1));
//		Assert.assertNotNull(response1.getModule());
	}

	/**
	 * 用户名重复添加测试 测试结果： username is already register
	 * */
	//@Test
	public void addTest2() {

		Request request = new BaseRequest();

		UserDTO userDto = new UserDTO();
		userDto.setName("我爱睡觉14");
		userDto.setPassword("npl26575e6d22"); //
		userDto.setEmail("2dq7d33d@qq.com");
		userDto.setMobile("13162575117");
		userDto.setInviterId(27L);

		request.setCommand(ActionEnum.ADD_USER.getActionName());
		request.setParam("userDTO", userDto);

		Response response = userDispatchService.execute(request);

	}

	/***
	 * 邮箱重复添加测试 测试结果：email is already register
	 */
	//@Test
	public void addTest3() {

		Request request = new BaseRequest();

		UserDTO userDto = new UserDTO();
		userDto.setName("我爱睡觉15");
		userDto.setPassword("np26575e6d22"); //
		userDto.setEmail("2dq7d323d@qq.com");
		userDto.setMobile("13962575116");
		userDto.setInviterId(27L);

		request.setCommand(ActionEnum.ADD_USER.getActionName());
		request.setParam("userDTO", userDto);

		Response response = userDispatchService.execute(request);

	}

	/**
	 * 邮箱不符合格式测试 测试结果：email format error
	 *
	 * */
	//@Test
	public void addTest4() {

		Request request = new BaseRequest();

		UserDTO userDto = new UserDTO();
		userDto.setName("我爱睡觉15");
		userDto.setPassword("np26575e6d22"); //
		userDto.setEmail("2dq7d323d");
		userDto.setInviterId(27L);

		request.setCommand(ActionEnum.ADD_USER.getActionName());
		request.setParam("userDTO", userDto);

		Response response = userDispatchService.execute(request);

	}

	/**
	 * 手机号码重复添加测试 测试结果：mobile is already register
	 */
	//@Test
	public void addTest5() {

		Request request = new BaseRequest();

		UserDTO userDto = new UserDTO();
		userDto.setName("我爱睡觉15");
		userDto.setPassword("np26575e6d22"); //
		userDto.setEmail("2dq7d3233d@qq.com");
		userDto.setMobile("13162575116");
		userDto.setInviterId(27L);

		request.setCommand(ActionEnum.ADD_USER.getActionName());
		request.setParam("userDTO", userDto);

		Response response = userDispatchService.execute(request);

	}

	/**
	 * 手机号码格式错误添加测试 测试结果：mobile format error
	 */
	//@Test
	public void addTest6() {

		Request request = new BaseRequest();

		UserDTO userDto = new UserDTO();
		userDto.setName("我爱睡觉15");
		userDto.setPassword("np26575e6d22"); //
		userDto.setEmail("2dq7d3233d@qq.com");
		userDto.setMobile("131625751");
		userDto.setInviterId(27L);

		request.setCommand(ActionEnum.ADD_USER.getActionName());
		request.setParam("userDTO", userDto);

		Response response = userDispatchService.execute(request);

	}

	/**
	 * 验证错误的推荐人id 测试结果：user is not exist
	 */
	//@Test
	public void addTest7() {

		Request request = new BaseRequest();

		UserDTO userDto = new UserDTO();
		userDto.setName("我爱睡觉15");
		userDto.setPassword("np26575e6d22"); //
		userDto.setEmail("2dq7d3233d@qq.com");
		userDto.setMobile("13162575166");
		userDto.setInviterId(27L);

		request.setCommand(ActionEnum.ADD_USER.getActionName());
		request.setParam("userDTO", userDto);

		Response response = userDispatchService.execute(request);

	}

	/**
	 * 验证错误的邀请人id 测试结果：user is not exist
	 * */
	//@Test
	public void addTest8() {

		Request request = new BaseRequest();

		UserDTO userDto = new UserDTO();
		userDto.setName("我爱睡觉15");
		userDto.setPassword("np26575e6d22"); //
		userDto.setEmail("2dq7d3233d@qq.com");
		userDto.setMobile("13162575166");
		userDto.setInviterId(100L);

		request.setCommand(ActionEnum.ADD_USER.getActionName());
		request.setParam("userDTO", userDto);

		Response response = userDispatchService.execute(request);

	}

	/**
	 * 验证Dto为空 测试结果：user is not exist
	 * */
	//@Test
	public void addTest9() {

		Request request = new BaseRequest();

		UserDTO userDto = null;

		request.setCommand(ActionEnum.ADD_USER.getActionName());
		request.setParam("userDTO", userDto);

		Response response = userDispatchService.execute(request);

	}

	/**
	 * 用户角色错误 测试结果:user role is error
	 * */
	//@Test
	public void addTest10() {

		Request request = new BaseRequest();

		request = new BaseRequest();
		UserDTO userDto = new UserDTO();
		userDto.setName("我爱睡觉16");
		userDto.setPassword("np265ed6d22"); //
		userDto.setEmail("2dq7dd23d@qq.com");
		userDto.setMobile("13262875116");
		userDto.setRoleMark(10L);
		userDto.setInviterId(27L);

		request.setCommand(ActionEnum.ADD_USER.getActionName());
		request.setParam("userDTO", userDto);

		Response response = userDispatchService.execute(request);
		Assert.assertNotNull(response.getModule());

	}

}
