package com.mockuai.deliverycenter.core.service.action.storage;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;

import com.mockuai.deliverycenter.common.api.BaseRequest;
import com.mockuai.deliverycenter.common.api.DeliveryService;
import com.mockuai.deliverycenter.common.api.Request;
import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.constant.ActionEnum;
import com.mockuai.deliverycenter.common.dto.storage.StorageSendDTO;
import com.mockuai.deliverycenter.core.service.BaseTest;

public class UpdateStorageSendTest extends BaseTest {

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
		StorageSendDTO storageSendDTO = new StorageSendDTO();
		storageSendDTO.setId(2);
		storageSendDTO.setName("3");
		storageSendDTO.setSort(3);
		storageSendDTO.setStatus(0);

		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_STORAGE_SEND.getActionName());
		request.setParam("storageSendDTO", storageSendDTO);
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
		request.setCommand(ActionEnum.UPDATE_STORAGE_SEND.getActionName());
		request.setParam("storageSendDTO", null);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}

	/**
	 * 测试ID不存在分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test3() throws Exception {
		// 创建一个DTO并赋值
		StorageSendDTO storageSendDTO = new StorageSendDTO();
		storageSendDTO.setId(9);
		storageSendDTO.setName("3");
		storageSendDTO.setSort(3);
		storageSendDTO.setStatus(0);

		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_STORAGE_SEND.getActionName());
		request.setParam("storageSendDTO", storageSendDTO);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}

}
