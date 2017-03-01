package com.mockuai.deliverycenter.core.service.action.express;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;

import com.mockuai.deliverycenter.common.api.BaseRequest;
import com.mockuai.deliverycenter.common.api.DeliveryService;
import com.mockuai.deliverycenter.common.api.Request;
import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.constant.ActionEnum;
import com.mockuai.deliverycenter.common.dto.express.ExpressDTO;
import com.mockuai.deliverycenter.core.domain.express.Express;
import com.mockuai.deliverycenter.core.service.BaseTest;

public class UpdateExpressTest extends BaseTest {

	@Resource
	private DeliveryService deliveryService;

	/**
	 * 测试修改成功分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test1() throws Exception {
		// 创建一个DTO并赋值
		ExpressDTO expressDTO = new ExpressDTO();
		Express express = new Express();
		expressDTO.setId(57);
		expressDTO.setClassName("2");
		expressDTO.setCode("2");
		expressDTO.setName("2");
		expressDTO.setPrintTemplate("2");

		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_EXPRESS.getActionName());
		request.setParam("expressDTO", expressDTO);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(true, response.isSuccess());
	}

	/**
	 * 测试ID不存在分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test2() throws Exception {
		// 创建一个DTO并赋值
		ExpressDTO expressDTO = new ExpressDTO();
		Express express = new Express();
		expressDTO.setId(20);
		expressDTO.setClassName("2");
		expressDTO.setCode("2");
		expressDTO.setName("2");
		expressDTO.setPrintTemplate("2");

		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_EXPRESS.getActionName());
		request.setParam("expressDTO", expressDTO);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}

	/**
	 * 测试参数为空分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test3() throws Exception {

		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_EXPRESS.getActionName());
		request.setParam("expressDTO", null);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}
}
