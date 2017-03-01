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
public class AddUserBabyTest {
	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 正确的添加用的baby信息 测试结果：添加正确
	 * */
	@Test
	public void test1() {
		Request request = new BaseRequest();
		UserBabyInfoDTO userBabyInfoDto = new UserBabyInfoDTO();

		userBabyInfoDto.setUserId(31L);
		userBabyInfoDto.setBabyName("小飞机飞不停");
		userBabyInfoDto.setBabySex((byte) 0);
		userBabyInfoDto.setBabyStatus((byte) 1);
		userBabyInfoDto.setExpectedChildbirthDate(20150505);
		userBabyInfoDto.setBabyBirthday(20150505);

		request.setParam("userBabyDTO", userBabyInfoDto);
		request.setCommand(ActionEnum.ADD_USER_BABY_INFO.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * userBabyDTO 为空添加baby信息 测试结果:babyInfoDto is null
	 * */
	@Test
	public void test2() {
		Request request = new BaseRequest();
		UserBabyInfoDTO userBabyInfoDto = null;

		request.setParam("userBabyDTO", userBabyInfoDto);
		request.setCommand(ActionEnum.ADD_USER_BABY_INFO.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * 宝宝出生状态填写错误 测试结果：babyStatus is error
	 * */
	@Test
	public void test3() {
		Request request = new BaseRequest();
		UserBabyInfoDTO userBabyInfoDto = new UserBabyInfoDTO();

		userBabyInfoDto.setUserId(30L);
		userBabyInfoDto.setBabyName("小飞机飞不停");
		userBabyInfoDto.setBabyStatus((byte) 10);

		request.setParam("userBabyDTO", userBabyInfoDto);
		request.setCommand(ActionEnum.ADD_USER_BABY_INFO.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * 宝宝性别填写错误 测试结果： babySex is error
	 * */
	@Test
	public void test4() {
		Request request = new BaseRequest();
		UserBabyInfoDTO userBabyInfoDto = new UserBabyInfoDTO();

		userBabyInfoDto.setUserId(30L);
		userBabyInfoDto.setBabyName("小飞机飞不停");
		userBabyInfoDto.setBabyStatus((byte) 1);
		userBabyInfoDto.setBabySex((byte) 10);

		request.setParam("userBabyDTO", userBabyInfoDto);
		request.setCommand(ActionEnum.ADD_USER_BABY_INFO.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * 指定用户拥有的宝宝数大于最大值 测试结果： baby count greater than 10
	 * */
	@Test
	public void test6() {
		Request request = new BaseRequest();
		UserBabyInfoDTO userBabyInfoDto = new UserBabyInfoDTO();

		userBabyInfoDto.setUserId(30L);
		userBabyInfoDto.setBabyName("小飞机飞不停");

		request.setParam("userBabyDTO", userBabyInfoDto);
		request.setCommand(ActionEnum.ADD_USER_BABY_INFO.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * 添加的宝宝为指定用户的默认宝宝 测试结果：添加正确
	 * */
	@Test
	public void test7() {
		Request request = new BaseRequest();
		UserBabyInfoDTO userBabyInfoDto = new UserBabyInfoDTO();

		userBabyInfoDto.setUserId(31L);
		userBabyInfoDto.setBabyName("小飞机飞不停");
		userBabyInfoDto.setIsDefault(true);

		request.setParam("userBabyDTO", userBabyInfoDto);
		request.setCommand(ActionEnum.ADD_USER_BABY_INFO.getActionName());
		Response response = userDispatchService.execute(request);
	}
}
