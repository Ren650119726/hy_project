package com.mockuai.deliverycenter.core.service.action.storage;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;

import com.mockuai.deliverycenter.common.api.BaseRequest;
import com.mockuai.deliverycenter.common.api.DeliveryService;
import com.mockuai.deliverycenter.common.api.Request;
import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.constant.ActionEnum;
import com.mockuai.deliverycenter.common.dto.storage.StorageDTO;
import com.mockuai.deliverycenter.core.service.BaseTest;

public class UpdateStorageTest extends BaseTest {

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
		StorageDTO storageDTO = new StorageDTO();
		storageDTO.setId(1);
		storageDTO.setAddress("2");
		storageDTO.setAdmin("2");
		storageDTO.setAdminTel("2");
		storageDTO.setIsDefault(false);
		storageDTO.setName("2");
		storageDTO.setShortName("2");
		storageDTO.setStatus(0);

		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_STORAGE.getActionName());
		request.setParam("storageDTO", storageDTO);
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
		request.setCommand(ActionEnum.UPDATE_STORAGE.getActionName());
		request.setParam("storageDTO", null);
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
		StorageDTO storageDTO = new StorageDTO();
		storageDTO.setId(9);
		storageDTO.setAddress("2");
		storageDTO.setAdmin("2");
		storageDTO.setAdminTel("2");
		storageDTO.setIsDefault(false);
		storageDTO.setName("2");
		storageDTO.setShortName("2");
		storageDTO.setStatus(0);

		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_STORAGE.getActionName());
		request.setParam("storageDTO", storageDTO);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}
}
