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
public class IgnoreSupplierExtraTest {

	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 忽略供应商的审核 测试结果：成功
	 * */
	@Test
	public void test1() {
		Request request = new BaseRequest();
		request.setParam("userId", 19L);
		request.setCommand(ActionEnum.SUPPLIER_IGNORE_IDENTIFIED_ACTION
				.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * 指定的用户id不存在 测试结果：user is not exist
	 * */
	@Test
	public void test2() {
		Request request = new BaseRequest();
		request.setParam("userId", 100L);
		request.setCommand(ActionEnum.SUPPLIER_IGNORE_IDENTIFIED_ACTION
				.getActionName());
		Response response = userDispatchService.execute(request);
	}
}
