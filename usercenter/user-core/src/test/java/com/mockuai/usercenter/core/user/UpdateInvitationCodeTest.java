package com.mockuai.usercenter.core.user;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.api.UserDispatchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class UpdateInvitationCodeTest {

	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 1、普通更新邀请码
	 * 2、更新邀请码重复
	 * 3、更新随机邀请码
	 * */
	@Test
	public void updateInvitationCodeTest()
	{
		Request request = new BaseRequest();

		request.setParam("userId", 82L);
		request.setParam("invitationCode", "18668017860");
		request.setCommand(ActionEnum.UPDATE_INVITATION_CODE.getActionName());
		Response response = userDispatchService.execute(request);

		System.out.println("123123213" + response.getMessage());
	}
}
