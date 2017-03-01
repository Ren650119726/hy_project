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
public class GetSupplierExtraTest {

	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 正确获取供应商扩展信息 测试结果：获取获取
	 * */
	@Test
	public void test1() {
		Request request = new BaseRequest();
		request.setParam("userId", 19L);
		request.setCommand(ActionEnum.GET_SUPPLIER_EXTRA_INFO.getActionName());

		Response response = userDispatchService.execute(request);
	}

	/**
	 * 指定的供应商不存在 测试结果：user is not exist
	 * */
	@Test
	public void test2() {
		Request request = new BaseRequest();
		request.setParam("userId", 20L);
		request.setCommand(ActionEnum.GET_SUPPLIER_EXTRA_INFO.getActionName());

		Response response = userDispatchService.execute(request);
	}
}
