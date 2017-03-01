package com.mockuai.usercenter.core.group;

import javax.annotation.Resource;

import com.mockuai.usercenter.common.api.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.dto.UserGroupDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AddUserGroupTest {
	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 正确的添加用户组 测试结果：添加成功
	 * */
	@Test
	public void test1() {

		Request request = new BaseRequest();

		UserGroupDTO userGroupDTO = new UserGroupDTO();
		userGroupDTO.setName("不屈白银");
		userGroupDTO.setDiscount(80);
		userGroupDTO.setGroupNo(2);
		userGroupDTO.setMaxIntegral(1000L);

		request.setParam("userGroupDTO", userGroupDTO);
		request.setCommand(ActionEnum.ADD_USER_GROUP.getActionName());

		Response response = userDispatchService.execute(request);

	}

	/**
	 * userGroupDTO为空 测试结果：userGroupDto is null
	 * */
	@Test
	public void test2() {

		Request request = new BaseRequest();

		UserGroupDTO userGroupDTO = null;

		request.setParam("userGroupDTO", userGroupDTO);
		request.setCommand(ActionEnum.ADD_USER_GROUP.getActionName());

		Response response = userDispatchService.execute(request);

	}

	/**
	 * 等级名称为空 测试结果： name is null
	 * */
	@Test
	public void test3() {

		Request request = new BaseRequest();

		UserGroupDTO userGroupDTO = new UserGroupDTO();
		userGroupDTO.setDiscount(90);
		userGroupDTO.setGroupNo(1);
		userGroupDTO.setMaxIntegral(100L);

		request.setParam("userGroupDTO", userGroupDTO);
		request.setCommand(ActionEnum.ADD_USER_GROUP.getActionName());

		Response response = userDispatchService.execute(request);

	}

	/**
	 * 等级号为空 测试结果：groupNo is null
	 */
	@Test
	public void test4() {

		Request request = new BaseRequest();

		UserGroupDTO userGroupDTO = new UserGroupDTO();
		userGroupDTO.setName("英勇黄铜");
		userGroupDTO.setDiscount(90);
		userGroupDTO.setMaxIntegral(100L);

		request.setParam("userGroupDTO", userGroupDTO);
		request.setCommand(ActionEnum.ADD_USER_GROUP.getActionName());

		Response response = userDispatchService.execute(request);

	}

	/**
	 * 最大积分值为空 测试结果： maxIntegral is null
	 * */
	@Test
	public void test5() {

		Request request = new BaseRequest();

		UserGroupDTO userGroupDTO = new UserGroupDTO();
		userGroupDTO.setName("英勇黄铜");
		userGroupDTO.setDiscount(90);
		userGroupDTO.setGroupNo(1);
		Long max = null;
		userGroupDTO.setMaxIntegral(null);

		request.setParam("userGroupDTO", userGroupDTO);
		request.setCommand(ActionEnum.ADD_USER_GROUP.getActionName());

		Response response = userDispatchService.execute(request);

	}

	/**
	 * 添加了相同的等级组号 测试结果：groupNo is exist
	 * */
	@Test
	public void test6() {

		Request request = new BaseRequest();

		UserGroupDTO userGroupDTO = new UserGroupDTO();
		userGroupDTO.setName("英勇黄铜");
		userGroupDTO.setDiscount(90);
		userGroupDTO.setGroupNo(1);
		userGroupDTO.setMaxIntegral(1000L);

		request.setParam("userGroupDTO", userGroupDTO);
		request.setCommand(ActionEnum.ADD_USER_GROUP.getActionName());

		Response response = userDispatchService.execute(request);

	}

	/**
	 * 新添加的用户组的最高积分，小于当前最大等级号的最高积分 测试结果：the maximum current greater than the
	 * maximum incoming
	 * */
	@Test
	public void test7() {

		Request request = new BaseRequest();

		UserGroupDTO userGroupDTO = new UserGroupDTO();
		userGroupDTO.setName("英勇黄铜");
		userGroupDTO.setDiscount(90);
		userGroupDTO.setGroupNo(2);
		userGroupDTO.setMaxIntegral(10L);

		request.setParam("userGroupDTO", userGroupDTO);
		request.setCommand(ActionEnum.ADD_USER_GROUP.getActionName());

		Response response = userDispatchService.execute(request);

	}

	/**
	 * 折扣大于最大折扣值 测试结果： discount can't greater than 100
	 * */
	@Test
	public void test8() {

		Request request = new BaseRequest();

		UserGroupDTO userGroupDTO = new UserGroupDTO();
		userGroupDTO.setName("英勇黄铜");
		userGroupDTO.setDiscount(110);
		userGroupDTO.setGroupNo(2);
		userGroupDTO.setMaxIntegral(1000L);

		request.setParam("userGroupDTO", userGroupDTO);
		request.setCommand(ActionEnum.ADD_USER_GROUP.getActionName());

		Response response = userDispatchService.execute(request);

	}

	/**
	 * 折扣为空 测试结果： discount is null
	 * */
	@Test
	public void test9() {

		Request request = new BaseRequest();

		UserGroupDTO userGroupDTO = new UserGroupDTO();
		userGroupDTO.setName("英勇黄铜");
		userGroupDTO.setGroupNo(1);
		userGroupDTO.setMaxIntegral(100L);

		request.setParam("userGroupDTO", userGroupDTO);
		request.setCommand(ActionEnum.ADD_USER_GROUP.getActionName());

		Response response = userDispatchService.execute(request);

	}

	/**
	 * 折扣小于最小折扣值 测试结果： discount can't less than 0
	 * */
	@Test
	public void test10() {

		Request request = new BaseRequest();

		UserGroupDTO userGroupDTO = new UserGroupDTO();
		userGroupDTO.setName("英勇黄铜");
		userGroupDTO.setDiscount(-1);
		userGroupDTO.setGroupNo(1);
		userGroupDTO.setMaxIntegral(100L);

		request.setParam("userGroupDTO", userGroupDTO);
		request.setCommand(ActionEnum.ADD_USER_GROUP.getActionName());

		Response response = userDispatchService.execute(request);

	}

}
