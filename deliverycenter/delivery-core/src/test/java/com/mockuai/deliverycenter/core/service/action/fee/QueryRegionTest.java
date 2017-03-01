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
import com.mockuai.deliverycenter.common.qto.fee.RegionQTO;
import com.mockuai.deliverycenter.core.service.BaseTest;

public class QueryRegionTest extends BaseTest {

	@Resource
	private DeliveryService deliveryService;

	/**
	 * 测试查询有数据分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test1() throws Exception {
		// 创建一个QTO并赋值
		RegionQTO regionQTO = new RegionQTO();
		regionQTO.setPageNo(1);
		regionQTO.setPageSize(50);
//		regionQTO.setParentCode("0");
		regionQTO.setName("浙江");
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setParam("appKey", "5b036edd2fe8730db1983368a122fb45");
		request.setCommand(ActionEnum.QUERY_REGION.getActionName());
		request.setParam("regionQTO", regionQTO);
		// 执行分发执行对应Action
		Response<List<RegionDTO>> response = deliveryService.execute(request);
		Assert.assertEquals(true, response.isSuccess());
		Assert.assertTrue(response.getModule().size() > 0);
	}

	/**
	 * 测试查询parentId不存在没有数据分支
	 * 
	 * @throws Exception
	 */
//	@Test
	public void test2() throws Exception {
		// 创建一个QTO并赋值
		RegionQTO regionQTO = new RegionQTO();
		regionQTO.setPageNo(1);
		regionQTO.setPageSize(50);
		regionQTO.setParentCode("9");
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_REGION.getActionName());
		request.setParam("regionQTO", regionQTO);
		// 执行分发执行对应Action
		Response<List<RegionDTO>> response = deliveryService.execute(request);
		Assert.assertEquals(true, response.isSuccess());
		Assert.assertTrue(response.getModule().size() == 0);
	}

	/**
	 * 测试参数为空分支
	 * 
	 * @throws Exception
	 */
//	@Test
	public void test3() throws Exception {
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_REGION.getActionName());
		request.setParam("regionQTO", null);
		// 执行分发执行对应Action
		Response<List<RegionDTO>> response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}
}
