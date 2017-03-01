//package com.mockuai.usercenter.core;
//
//import javax.annotation.Resource;
//
//import com.mockuai.usercenter.common.api.*;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.mockuai.usercenter.common.action.ActionEnum;
//import com.mockuai.usercenter.common.dto.UserDTO;
//import com.mockuai.usercenter.common.dto.UserExtraInfoDTO;
//import com.mockuai.usercenter.common.dto.UserInfoDTO;
//import com.mockuai.usercenter.common.qto.UserQTO;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
//public class ATest {
//
//	@Resource
//	private UserDispatchService userDispatchService;
//
//	@Test
//	public void addTest() {
//		System.out.println(1);
//		Request request = new BaseRequest();
//
//		UserDTO userDto = new UserDTO();
//		userDto.setName("我爱睡觉13");
//		userDto.setPassword("np6575e6d22"); //
//		userDto.setEmail("2dq7d3e3d@qq.com");
//
//		request.setCommand(ActionEnum.ADD_USER.getActionName());
//		request.setParam("userDTO", userDto);
//
//		Response response = userDispatchService.execute(request);
//
//		System.out.println(2);
//	}
//
//	@Test
//	public void updatePwdTest() {
//		Request request = new BaseRequest();
//
//		request.setParam("userId", 14L);
//		request.setParam("oldPwd", "np5356822");
//		request.setParam("newPwd", "zl123456");
//		request.setCommand(ActionEnum.UPDATE_PWD.getActionName());
//		Response response = userDispatchService.execute(request);
//	}
//
//	@Test
//	public void updateEmailTest() {
//		Request request = new BaseRequest();
//
//		request.setParam("userId", 6L);
//		request.setParam("email", "742001928@qq.com");
//		request.setCommand(ActionEnum.UPDATE_EMAIL.getActionName());
//		Response response = userDispatchService.execute(request);
//	}
//
//	@Test
//	public void updatemobileTest() {
//		Request request = new BaseRequest();
//
//		request.setParam("userId", 6L);
//		request.setParam("mobile", "18668017860");
//		request.setCommand(ActionEnum.UPDATE_MOBILE.getActionName());
//		Response response = userDispatchService.execute(request);
//	}
//
//	@Test
//	public void activeUserTest() {
//		Request request = new BaseRequest();
//
//		request.setParam("userId", 6L);
//		request.setCommand(ActionEnum.ACTIVE_USER.getActionName());
//		Response response = userDispatchService.execute(request);
//	}
//
//	@Test
//	public void freezeUserTest() {
//		Request request = new BaseRequest();
//		request.setParam("userId", 6L);
//		request.setCommand(ActionEnum.FREEZE_USER.getActionName());
//		Response response = userDispatchService.execute(request);
//	}
//
//	@Test
//	public void getUserById() {
//		Request request = new BaseRequest();
//		request.setParam("userId", 1L);
//		request.setCommand(ActionEnum.GET_USER_BY_ID.getActionName());
//		Response response = userDispatchService.execute(request);
//	}
//
//	@Test
//	public void getUserByName() {
//		Request request = new BaseRequest();
//		request.setParam("name", "yezhenglei");
//		request.setCommand(ActionEnum.GET_USER_BY_NAME.getActionName());
//		Response response = userDispatchService.execute(request);
//	}
//
//	@Test
//	public void getUserByEmail() {
//		Request request = new BaseRequest();
//		request.setParam("email", "742001918@qq.com");
//		request.setCommand(ActionEnum.GET_USER_BY_EMAIL.getActionName());
//		Response response = userDispatchService.execute(request);
//	}
//
//	@Test
//	public void getUserByMobile() {
//		Request request = new BaseRequest();
//		request.setParam("mobile", "18668017860");
//		request.setCommand(ActionEnum.GET_USER_BY_MOBILE.getActionName());
//		Response response = userDispatchService.execute(request);
//	}
//
//	@Test
//	public void queryUser() {
//		Request request = new BaseRequest();
//		UserQTO userQto = new UserQTO();
//		userQto.setName("我爱睡觉");
//		request.setParam("userQto", userQto);
//		request.setCommand(ActionEnum.QUERY_USER.getActionName());
//		Response response = userDispatchService.execute(request);
//	}
//
//	/**
//	 * 添加用户扩展信息测试
//	 */
//	@Test
//	public void addUserExtraTest() {
//		Request request = new BaseRequest();
//		UserExtraInfoDTO userExtraInfoDto = new UserExtraInfoDTO();
//		userExtraInfoDto.setUserId(19L);
//		userExtraInfoDto.setHeight(100);
//		request.setParam("userExtraDTO", userExtraInfoDto);
//		request.setCommand(ActionEnum.ADD_USER_EXTRA_INFO.getActionName());
//		Response response = userDispatchService.execute(request);
//	}
//
//	/**
//	 * 移入回收站
//	 */
//	@Test
//	public void moveUser() {
//		Request request = new BaseRequest();
//		request.setParam("userId", 12L);
//		request.setCommand(ActionEnum.MOVE_USER.getActionName());
//		Response response = userDispatchService.execute(request);
//	}
//
//	/**
//	 * 从回收站还原
//	 */
//	@Test
//	public void restoreUser() {
//		Request request = new BaseRequest();
//		request.setParam("userId", 12L);
//		request.setCommand(ActionEnum.RESTORE_USER.getActionName());
//		Response response = userDispatchService.execute(request);
//	}
//
//	/**
//	 * 删除用户
//	 */
//	@Test
//	public void delUser() {
//		Request request = new BaseRequest();
//		request.setParam("userId", 12L);
//		request.setCommand(ActionEnum.DELETE_USER.getActionName());
//		Response response = userDispatchService.execute(request);
//	}
//
//	/**
//	 * 用户登陆
//	 * */
//	@Test
//	public void userLogin() {
//		Request request = new BaseRequest();
//		request.setParam("loginName", "yezhenglei");
//		request.setParam("loginPwd", "zl123456");
//		request.setCommand(ActionEnum.USER_LOGIN.getActionName());
//		Response response = userDispatchService.execute(request);
//	}
//
//	/**
//	 * 第三方平台登录
//	 * */
//	@Test
//	public void apiUserLogin() {
//		Request request = new BaseRequest();
//		UserInfoDTO userInfoDto = new UserInfoDTO();
//		userInfoDto.setOpenId("123454867534135");
//		userInfoDto.setAuthType(1);
//		request.setParam("userInfoDto", userInfoDto);
//		request.setCommand(ActionEnum.API_USER_LOGIN.getActionName());
//		Response response = userDispatchService.execute(request);
//	}
//
//}
