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

public class AddStorageTest extends BaseTest {

	@Resource
	private DeliveryService deliveryService;

	/**
	 * 测试新增成功分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test1() throws Exception {
		// 创建一个DTO并赋值
		StorageDTO storageDTO = new StorageDTO();
		storageDTO.setAddress("浙江省台州市");
		storageDTO.setAdmin("赵四");
		storageDTO.setAdminTel("15858476524");
		storageDTO.setIsDefault(true);
		storageDTO.setName("浙江省台州市仓库");
		storageDTO.setShortName("台州库");
		storageDTO.setStatus(1);
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_STORAGE.getActionName());
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
		request.setCommand(ActionEnum.ADD_STORAGE.getActionName());
		request.setParam("storageDTO", null);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}

	/**
	 * 测试Status为空分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test3() throws Exception {
		// 创建一个DTO并赋值
		StorageDTO storageDTO = new StorageDTO();
		storageDTO.setAddress("浙江省台州市");
		storageDTO.setAdmin("赵四");
		storageDTO.setAdminTel("15858476524");
		storageDTO.setIsDefault(true);
		storageDTO.setName("浙江省台州市仓库");
		storageDTO.setShortName("台州库");
		// storageDTO.setStatus(1);
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_STORAGE.getActionName());
		request.setParam("storageDTO", storageDTO);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}

	/**
	 * 测试Status不合法分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test4() throws Exception {
		// 创建一个DTO并赋值
		StorageDTO storageDTO = new StorageDTO();
		storageDTO.setAddress("浙江省台州市");
		storageDTO.setAdmin("赵四");
		storageDTO.setAdminTel("15858476524");
		storageDTO.setIsDefault(true);
		storageDTO.setName("浙江省台州市仓库");
		storageDTO.setShortName("台州库");
		storageDTO.setStatus(2);
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_STORAGE.getActionName());
		request.setParam("storageDTO", storageDTO);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}
}
