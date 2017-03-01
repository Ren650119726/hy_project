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
//import com.mockuai.usercenter.common.dto.UserExtraInfoDTO;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
//public class UserExtraInfoTest {
//	@Resource
//	private UserDispatchService userDispatchService;
//
//	@Test
//	public void test() {
//		Request request = new BaseRequest();
//
//		UserExtraInfoDTO userExtraDTO = new UserExtraInfoDTO();
//
//		userExtraDTO.setUserId(30L);
//		userExtraDTO.setAlipayId("1f2sd1");
//		request.setParam("userExtraDTO", userExtraDTO);
//		request.setCommand(ActionEnum.ADD_USER_EXTRA_INFO.getActionName());
//
//		Response response = userDispatchService.execute(request);
//	}
//}
