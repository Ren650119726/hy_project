package com.mockuai.deliverycenter.core.service.action.fee;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;

import com.mockuai.deliverycenter.common.api.BaseRequest;
import com.mockuai.deliverycenter.common.api.DeliveryService;
import com.mockuai.deliverycenter.common.api.Request;
import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.constant.ActionEnum;
import com.mockuai.deliverycenter.core.service.BaseTest;

public class DeleteRegionFeeTest extends BaseTest {

	@Resource
	private DeliveryService deliveryService;

	/**
	 * 测试删除成功分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test1() throws Exception {
		// 创建属性
		List regionFeeIdList = new ArrayList();
		regionFeeIdList.add(15);
		regionFeeIdList.add(16);
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.DELETE_REGION_FEE.getActionName());
		request.setParam("regionFeeIdList", regionFeeIdList);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(true, response.isSuccess());
	}

	/**
	 * 测试参数为空分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test2() throws Exception {
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.DELETE_REGION_FEE.getActionName());
		request.setParam("regionFeeIdList", null);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}

	/**
	 * 测试ID不存在（事物回滚）分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test3() throws Exception {
		// 创建属性
		List regionFeeIdList = new ArrayList();
		regionFeeIdList.add(15);
		regionFeeIdList.add(17);
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.DELETE_REGION_FEE.getActionName());
		request.setParam("regionFeeIdList", regionFeeIdList);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}

}
