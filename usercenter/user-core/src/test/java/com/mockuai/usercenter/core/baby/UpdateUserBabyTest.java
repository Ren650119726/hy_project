package com.mockuai.usercenter.core.baby;

import javax.annotation.Resource;

import com.mockuai.usercenter.common.api.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.dto.UserBabyInfoDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class UpdateUserBabyTest {
	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 正确修改baby信息 测试结果：添加正确
	 * */
	@Test
	public void test1() {
		Request request = new BaseRequest();
		UserBabyInfoDTO userBabyInfoDto = new UserBabyInfoDTO();
		userBabyInfoDto.setId(6L);
		userBabyInfoDto.setBabySex((byte) 0);
		userBabyInfoDto.setBabyStatus((byte) 0);
		userBabyInfoDto.setIsDefault(true);
		userBabyInfoDto.setBabyName("飞机飞啊飞");
		userBabyInfoDto.setExpectedChildbirthDate(20150606);
		userBabyInfoDto.setBabyBirthday(20160606);

		request.setParam("userBabyDTO", userBabyInfoDto);
		request.setCommand(ActionEnum.UPDATE_USER_BABY_INFO.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * userBabyDTO为空测试 测试结果：babyInfoDto is null
	 * */
	@Test
	public void test2() {
		Request request = new BaseRequest();
		UserBabyInfoDTO userBabyInfoDto = null;

		request.setParam("userBabyDTO", userBabyInfoDto);
		request.setCommand(ActionEnum.UPDATE_USER_BABY_INFO.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * baby的出生状态错误修改 测试结果：babyStatus is error
	 * */
	@Test
	public void test3() {
		Request request = new BaseRequest();
		UserBabyInfoDTO userBabyInfoDto = new UserBabyInfoDTO();
		userBabyInfoDto.setId(2L);
		userBabyInfoDto.setBabySex((byte) 0);
		userBabyInfoDto.setBabyStatus((byte) 10);
		userBabyInfoDto.setIsDefault(true);
		userBabyInfoDto.setBabyName("飞机飞啊飞");

		request.setParam("userBabyDTO", userBabyInfoDto);
		request.setCommand(ActionEnum.UPDATE_USER_BABY_INFO.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * baby的性别错误修改 测试结果：babySex is error
	 * */
	@Test
	public void test4() {
		Request request = new BaseRequest();
		UserBabyInfoDTO userBabyInfoDto = new UserBabyInfoDTO();
		userBabyInfoDto.setId(2L);
		userBabyInfoDto.setBabySex((byte) 10);
		userBabyInfoDto.setBabyStatus((byte) 0);
		userBabyInfoDto.setIsDefault(true);
		userBabyInfoDto.setBabyName("飞机飞啊飞");

		request.setParam("userBabyDTO", userBabyInfoDto);
		request.setCommand(ActionEnum.UPDATE_USER_BABY_INFO.getActionName());
		Response response = userDispatchService.execute(request);
	}
}
