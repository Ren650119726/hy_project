package com.mockuai.deliverycenter.core.service.action.fee;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;

import com.mockuai.deliverycenter.common.api.BaseRequest;
import com.mockuai.deliverycenter.common.api.DeliveryService;
import com.mockuai.deliverycenter.common.api.Request;
import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.constant.ActionEnum;
import com.mockuai.deliverycenter.common.dto.fee.RegionDTO;
import com.mockuai.deliverycenter.core.service.BaseTest;

public class GetRegionTest extends BaseTest {

	@Resource
	private DeliveryService deliveryService;

	/**
	 * 测试查询有数据分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test1() throws Exception {
		int id = 2;
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.GET_REGION.getActionName());
		request.setParam("id", id);
		// 执行分发执行对应Action
		Response<List<RegionDTO>> response = deliveryService.execute(request);
		Assert.assertEquals(true, response.isSuccess());
		Assert.assertNotNull(response.getModule());
	}

	/**
	 * 测试查询id不存在没有数据分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test2() throws Exception {
		int id = 9;
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.GET_REGION.getActionName());
		request.setParam("id", id);
		// 执行分发执行对应Action
		Response<List<RegionDTO>> response = deliveryService.execute(request);
		Assert.assertEquals(true, response.isSuccess());
		Assert.assertNull(response.getModule());
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
		request.setCommand(ActionEnum.GET_REGION.getActionName());
		request.setParam("id", null);
		// 执行分发执行对应Action
		Response<List<RegionDTO>> response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}
}
