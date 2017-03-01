package com.mockuai.deliverycenter.core.service.action.fee;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;

import com.mockuai.deliverycenter.common.api.BaseRequest;
import com.mockuai.deliverycenter.common.api.DeliveryService;
import com.mockuai.deliverycenter.common.api.Request;
import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.constant.ActionEnum;
import com.mockuai.deliverycenter.common.dto.fee.StdFeeDTO;
import com.mockuai.deliverycenter.core.service.BaseTest;

public class UpdateStdFeeTest extends BaseTest {

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
		StdFeeDTO stdFeeDTO = new StdFeeDTO();
		stdFeeDTO.setId(5);
		stdFeeDTO.setAllowDefault(2);
		stdFeeDTO.setCode("2");
		stdFeeDTO.setContinueFee(2l);
		stdFeeDTO.setContinueWeight(2l);
		stdFeeDTO.setRemark("2");
		stdFeeDTO.setFeeLimitMark(2);
		stdFeeDTO.setFirstFee(2l);
		stdFeeDTO.setFirstWeight(2l);
		stdFeeDTO.setMaxFee(2l);
		stdFeeDTO.setMinFee(2l);
		stdFeeDTO.setName("2");
		stdFeeDTO.setSort(2);
		stdFeeDTO.setWeightUnit(2);

		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_STDFEE.getActionName());
		request.setParam("stdFeeDTO", stdFeeDTO);
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
		request.setCommand(ActionEnum.UPDATE_STDFEE.getActionName());
		request.setParam("stdFeeDTO", null);
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
		StdFeeDTO stdFeeDTO = new StdFeeDTO();
		stdFeeDTO.setId(20);
		stdFeeDTO.setAllowDefault(2);
		stdFeeDTO.setCode("2");
		stdFeeDTO.setContinueFee(2l);
		stdFeeDTO.setContinueWeight(2l);
		stdFeeDTO.setRemark("2");
		stdFeeDTO.setFeeLimitMark(2);
		stdFeeDTO.setFirstFee(2l);
		stdFeeDTO.setFirstWeight(2l);
		stdFeeDTO.setMaxFee(2l);
		stdFeeDTO.setMinFee(2l);
		stdFeeDTO.setName("2");
		stdFeeDTO.setSort(2);
		stdFeeDTO.setWeightUnit(2);

		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_STDFEE.getActionName());
		request.setParam("stdFeeDTO", stdFeeDTO);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}
}
