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
import com.mockuai.deliverycenter.common.dto.storage.StorageSendDTO;
import com.mockuai.deliverycenter.common.qto.storage.StorageSendQTO;
import com.mockuai.deliverycenter.core.service.BaseTest;

public class QueryStorageSendTest extends BaseTest {

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
		StorageSendQTO storageSendQTO = new StorageSendQTO();
		storageSendQTO.setPageNo(1);
		storageSendQTO.setPageSize(50);
		storageSendQTO.setStatus(1);
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_STORAGE_SEND.getActionName());
		request.setParam("storageSendQTO", storageSendQTO);
		// 执行分发执行对应Action
		Response<List<StorageSendDTO>> response = deliveryService
				.execute(request);
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
		StorageSendQTO storageSendQTO = new StorageSendQTO();
		storageSendQTO.setPageNo(1);
		storageSendQTO.setPageSize(50);
		storageSendQTO.setStatus(0);
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_STORAGE_SEND.getActionName());
		request.setParam("storageSendQTO", storageSendQTO);
		// 执行分发执行对应Action
		Response<List<StorageSendDTO>> response = deliveryService
				.execute(request);
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
		request.setCommand(ActionEnum.QUERY_STORAGE_SEND.getActionName());
		request.setParam("storageSendQTO", null);
		// 执行分发执行对应Action
		Response<List<StorageSendDTO>> response = deliveryService
				.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}
}
