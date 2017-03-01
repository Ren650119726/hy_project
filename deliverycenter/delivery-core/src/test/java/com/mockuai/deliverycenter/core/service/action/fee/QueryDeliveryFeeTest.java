package com.mockuai.deliverycenter.core.service.action.fee;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;

import com.mockuai.deliverycenter.common.api.BaseRequest;
import com.mockuai.deliverycenter.common.api.DeliveryService;
import com.mockuai.deliverycenter.common.api.Request;
import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.constant.ActionEnum;
import com.mockuai.deliverycenter.common.qto.fee.DeliveryFeeQTO;
import com.mockuai.deliverycenter.core.service.BaseTest;

public class QueryDeliveryFeeTest extends BaseTest {

	@Resource
	private DeliveryService deliveryService;

	/**
	 * 测试没有配运费标准返回0分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test1() throws Exception {
		// 创建一个QTO并赋值
		DeliveryFeeQTO deliveryFeeQTO = new DeliveryFeeQTO();
		deliveryFeeQTO.setPageNo(1);
		deliveryFeeQTO.setPageSize(50);
		// deliveryFeeQTO.setWeight(30l);
		// deliveryFeeQTO.setRegionId(1l);
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_DELIVERYFEE.getActionName());
		request.setParam("deliveryFeeQTO", deliveryFeeQTO);
		// 执行分发执行对应Action
		Response<Long> response = deliveryService.execute(request);
		Assert.assertEquals(true, response.isSuccess());
		Assert.assertTrue(response.getModule() == 0);
	}

	/**
	 * 测试参数为空查询标准运费分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test2() throws Exception {
		// 创建一个QTO并赋值
		DeliveryFeeQTO deliveryFeeQTO = new DeliveryFeeQTO();
		deliveryFeeQTO.setPageNo(1);
		deliveryFeeQTO.setPageSize(50);
		// deliveryFeeQTO.setWeight(30l);
		// deliveryFeeQTO.setRegionId(1l);
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_DELIVERYFEE.getActionName());
		request.setParam("deliveryFeeQTO", deliveryFeeQTO);
		// 执行分发执行对应Action
		Response<Long> response = deliveryService.execute(request);
		Assert.assertEquals(true, response.isSuccess());
		Assert.assertTrue(response.getModule() > 0);
	}

	/**
	 * 测试该地区配置了运费分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test3() throws Exception {
		// 创建一个QTO并赋值
		DeliveryFeeQTO deliveryFeeQTO = new DeliveryFeeQTO();
		deliveryFeeQTO.setPageNo(1);
		deliveryFeeQTO.setPageSize(50);
		deliveryFeeQTO.setWeight(30l);
		deliveryFeeQTO.setRegionId(3l);
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_DELIVERYFEE.getActionName());
		request.setParam("deliveryFeeQTO", deliveryFeeQTO);
		// 执行分发执行对应Action
		Response<Long> response = deliveryService.execute(request);
		Assert.assertEquals(true, response.isSuccess());
		Assert.assertTrue(response.getModule() > 0);
	}

	/**
	 * 测试该地区没有配置取默认运费分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test4() throws Exception {
		// 创建一个QTO并赋值
		DeliveryFeeQTO deliveryFeeQTO = new DeliveryFeeQTO();
		deliveryFeeQTO.setPageNo(1);
		deliveryFeeQTO.setPageSize(50);
		deliveryFeeQTO.setWeight(30l);
		deliveryFeeQTO.setRegionId(6l);
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_DELIVERYFEE.getActionName());
		request.setParam("deliveryFeeQTO", deliveryFeeQTO);
		// 执行分发执行对应Action
		Response<Long> response = deliveryService.execute(request);
		Assert.assertEquals(true, response.isSuccess());
		Assert.assertTrue(response.getModule() > 0);
	}

	/**
	 * 测试小于最低限价分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test5() throws Exception {
		// 创建一个QTO并赋值
		DeliveryFeeQTO deliveryFeeQTO = new DeliveryFeeQTO();
		deliveryFeeQTO.setPageNo(1);
		deliveryFeeQTO.setPageSize(50);
		deliveryFeeQTO.setWeight(30l);
		deliveryFeeQTO.setRegionId(6l);
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_DELIVERYFEE.getActionName());
		request.setParam("deliveryFeeQTO", deliveryFeeQTO);
		// 执行分发执行对应Action
		Response<Long> response = deliveryService.execute(request);
		Assert.assertEquals(true, response.isSuccess());
		Assert.assertTrue(response.getModule() > 0);
	}

	/**
	 * 测试大于最高限价分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test6() throws Exception {
		// 创建一个QTO并赋值
		DeliveryFeeQTO deliveryFeeQTO = new DeliveryFeeQTO();
		deliveryFeeQTO.setPageNo(1);
		deliveryFeeQTO.setPageSize(50);
		deliveryFeeQTO.setWeight(30l);
		deliveryFeeQTO.setRegionId(6l);
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_DELIVERYFEE.getActionName());
		request.setParam("deliveryFeeQTO", deliveryFeeQTO);
		// 执行分发执行对应Action
		Response<Long> response = deliveryService.execute(request);
		Assert.assertEquals(true, response.isSuccess());
		Assert.assertTrue(response.getModule() > 0);
	}

	/**
	 * 测试参数为空分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test7() throws Exception {
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_DELIVERYFEE.getActionName());
		request.setParam("deliveryFeeQTO", null);
		// 执行分发执行对应Action
		Response<Long> response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}
}
