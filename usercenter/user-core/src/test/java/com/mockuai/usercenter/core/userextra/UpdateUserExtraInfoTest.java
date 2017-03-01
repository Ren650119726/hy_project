//package com.mockuai.usercenter.core.userextra;
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
//import com.mockuai.usercenter.common.dto.UserExtraInfoDTO;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
//public class UpdateUserExtraInfoTest {
//	@Resource
//	private UserDispatchService userDispatchService;
//
//	/**
//	 * 正确修改用户的扩展信息 测试结果：成功修改
//	 * */
//	@Test
//	public void test1() {
//		Request request = new BaseRequest();
//
//		UserExtraInfoDTO userExtraDTO = new UserExtraInfoDTO();
//
//		userExtraDTO.setUserId(30L);
//		userExtraDTO.setAlipayId("1f2sd");
//		userExtraDTO.setSex((byte) 0);
//		request.setParam("userExtraDTO", userExtraDTO);
//		request.setCommand(ActionEnum.UPDATE_USER_EXTRA_INFO.getActionName());
//
//		Response response = userDispatchService.execute(request);
//	}
//
//	/**
//	 * 修改不存在用户的扩展信息 测试结果：user is not exist
//	 * */
//	@Test
//	public void test2() {
//		Request request = new BaseRequest();
//
//		UserExtraInfoDTO userExtraDTO = new UserExtraInfoDTO();
//
//		userExtraDTO.setUserId(100L);
//		userExtraDTO.setAlipayId("1f2sd");
//		userExtraDTO.setSex((byte) 0);
//		request.setParam("userExtraDTO", userExtraDTO);
//		request.setCommand(ActionEnum.UPDATE_USER_EXTRA_INFO.getActionName());
//
//		Response response = userDispatchService.execute(request);
//	}
//}
