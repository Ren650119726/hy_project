package com.mockuai.virtualwealthcenter.core.service.action.virtualwealth;


import org.junit.Test;

import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.core.BaseActionTest;

public class ManualAuditActionTest extends BaseActionTest {

	public ManualAuditActionTest(String className) {
		 super(ManualAuditActionTest.class.getName());
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getCommand() {
		// TODO Auto-generated method stub
		return ActionEnum.USER_AUTHON_ALL.getActionName();
	}
	@Test
    public void test() {
        request.setParam("offset", 0);
        request.setParam("count", 20);
        doExecute();
    }
}
