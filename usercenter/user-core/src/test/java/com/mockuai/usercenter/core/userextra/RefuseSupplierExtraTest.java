package com.mockuai.usercenter.core.userextra;

import javax.annotation.Resource;

import com.mockuai.usercenter.common.api.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockuai.usercenter.common.action.ActionEnum;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class RefuseSupplierExtraTest {

	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 忽略供应商的审核 测试结果：成功
	 * */
	@Test
	public void test1() {
		Request request = new BaseRequest();
		request.setParam("userId", 19L);
		request.setCommand(ActionEnum.SUPPLIER_FAIL_IDENTIFIED_ACTION
				.getActionName());
		Response response = userDispatchService.execute(request);
	}
}
