package com.mockuai.deliverycenter.core.service.action.storage;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;

import com.mockuai.deliverycenter.common.api.BaseRequest;
import com.mockuai.deliverycenter.common.api.DeliveryService;
import com.mockuai.deliverycenter.common.api.Request;
import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.constant.ActionEnum;
import com.mockuai.deliverycenter.common.qto.storage.StorageQTO;
import com.mockuai.deliverycenter.core.domain.storage.Storage;
import com.mockuai.deliverycenter.core.service.BaseTest;

public class QueryStorageTest extends BaseTest {

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
		StorageQTO storageQTO = new StorageQTO();
		storageQTO.setPageNo(1);
		storageQTO.setPageSize(50);
		storageQTO.setStatus(1);
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_STORAGE.getActionName());
		request.setParam("storageQTO", storageQTO);
		// 执行分发执行对应Action
		Response<List<Storage>> response = deliveryService.execute(request);
		Assert.assertEquals(true, response.isSuccess());
		Assert.assertTrue(response.getModule().size() > 0);
	}

	/**
	 * 测试查询没有数据分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test2() throws Exception {
		// 创建一个QTO并赋值
		StorageQTO storageQTO = new StorageQTO();
		storageQTO.setPageNo(1);
		storageQTO.setPageSize(50);
		storageQTO.setStatus(0);
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_STORAGE.getActionName());
		request.setParam("storageQTO", storageQTO);
		// 执行分发执行对应Action
		Response<List<Storage>> response = deliveryService.execute(request);
		Assert.assertEquals(true, response.isSuccess());
		Assert.assertTrue(response.getModule().size() == 0);
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
		request.setCommand(ActionEnum.QUERY_STORAGE.getActionName());
		request.setParam("storageQTO", null);
		// 执行分发执行对应Action
		Response<List<Storage>> response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}
}
