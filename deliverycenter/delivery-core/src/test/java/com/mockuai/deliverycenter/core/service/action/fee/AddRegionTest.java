package com.mockuai.deliverycenter.core.service.action.fee;

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

public class AddRegionTest extends BaseTest {

	@Resource
	private DeliveryService deliveryService;

	/**
	 * 测试新增顶级区域分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test1() throws Exception {
		// 创建一个DTO并赋值
		RegionDTO regionDTO = new RegionDTO();
		regionDTO.setCode("zhongguo");
		regionDTO.setName("中国");
		regionDTO.setParentCode("0");
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_REGION.getActionName());
		request.setParam("regionDTO", regionDTO);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(true, response.isSuccess());
	}

	/**
	 * 测试新增下级区域分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test2() throws Exception {
		// 创建一个DTO并赋值
		RegionDTO regionDTO = new RegionDTO();
		regionDTO.setCode("guangxi");
		regionDTO.setName("广西");
		regionDTO.setParentCode("");
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_REGION.getActionName());
		request.setParam("regionDTO", regionDTO);
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
	public void test3() throws Exception {
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_REGION.getActionName());
		request.setParam("regionDTO", null);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}

	/**
	 * 测试上级ID不存在分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test4() throws Exception {
		// 创建一个DTO并赋值
		RegionDTO regionDTO = new RegionDTO();
		regionDTO.setCode("guangdong");
		regionDTO.setName("广东");
		regionDTO.setParentCode("8");
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_REGION.getActionName());
		request.setParam("regionDTO", regionDTO);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}

}
