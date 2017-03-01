package com.mockuai.virtualwealthcenter.core.service.action.virtualwealth;


import org.testng.annotations.Test;

import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.core.BaseActionTest;
import com.mockuai.virtualwealthcenter.core.domain.UserAuthonAppDO;

public class NewUserAuthon extends BaseActionTest{

	public NewUserAuthon(String className) {

		super(NewUserAuthon.class.getName());
	}

	@Override
	protected String getCommand() {
		// TODO Auto-generated method stub
		return ActionEnum.USER_AUTHON_ADD.getActionName();
	}
	@Test
	public void test() {
		
		request.setParam("bank_realname", "程光民");
		request.setParam("bank_personal_id", "15825825823");

		doExecute();
	}
}
