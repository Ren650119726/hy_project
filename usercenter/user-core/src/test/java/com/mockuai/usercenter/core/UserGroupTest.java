package com.mockuai.usercenter.core;

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
public class UserGroupTest {
	@Resource
	private UserDispatchService userDispatchService;

	@Test
	public void addtest() {

		Request request = new BaseRequest();

		UserGroupDTO userGroupDTO = new UserGroupDTO();
		userGroupDTO.setName("英勇黄铜三");
		userGroupDTO.setDiscount(70);
		userGroupDTO.setGroupNo(3);
		userGroupDTO.setMaxIntegral(10000L);

		request.setParam("userGroupDTO", userGroupDTO);
		request.setCommand(ActionEnum.ADD_USER_GROUP.getActionName());

		Response response = userDispatchService.execute(request);

	}

	@Test
	public void deltest() {

		Request request = new BaseRequest();

		request.setParam("groupId", 2);
		request.setCommand(ActionEnum.DEL_USER_GROUP.getActionName());

		Response response = userDispatchService.execute(request);

	}

	@Test
	public void updatetest() {

		Request request = new BaseRequest();

		UserGroupDTO userGroupDTO = new UserGroupDTO();
		userGroupDTO.setId(2);
		userGroupDTO.setDiscount(85);
		userGroupDTO.setMaxIntegral(1100L);
		request.setParam("userGroupDTO", userGroupDTO);
		request.setCommand(ActionEnum.UPDATE_USER_GROUP.getActionName());

		Response response = userDispatchService.execute(request);

	}

	@Test
	public void querytest() {

		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_USER_GROUP.getActionName());

		Response response = userDispatchService.execute(request);
	}

	@Test
	public void gettest() {

		Request request = new BaseRequest();
		request.setParam("integral", 100002L);
		request.setCommand(ActionEnum.GET_USER_GROUP.getActionName());

		Response response = userDispatchService.execute(request);
	}
}
