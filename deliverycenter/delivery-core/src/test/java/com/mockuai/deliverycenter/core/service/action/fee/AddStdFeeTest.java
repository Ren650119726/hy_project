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
import com.mockuai.deliverycenter.common.dto.fee.RegionFeeDTO;
import com.mockuai.deliverycenter.common.dto.fee.StdFeeDTO;
import com.mockuai.deliverycenter.core.service.BaseTest;

public class AddStdFeeTest extends BaseTest {

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
		StdFeeDTO stdFeeDTO = new StdFeeDTO();
		stdFeeDTO.setAllowDefault(1);
		stdFeeDTO.setCode("peisong2");
		stdFeeDTO.setContinueFee(4l);
		stdFeeDTO.setContinueWeight(120l);
		stdFeeDTO.setRemark("配送标准2");
		stdFeeDTO.setFeeLimitMark(1);
		stdFeeDTO.setFirstFee(10l);
		stdFeeDTO.setFirstWeight(50l);
		stdFeeDTO.setMaxFee(100l);
		stdFeeDTO.setMinFee(10l);
		stdFeeDTO.setName("运费标准1");
		stdFeeDTO.setSort(2);
		stdFeeDTO.setWeightUnit(1);
		// 创建属性
		List propertyList = new ArrayList();
		RegionFeeDTO stdFeePropertyDTO = new RegionFeeDTO();
		stdFeePropertyDTO.setContinueFee(3l);
		stdFeePropertyDTO.setContinueWeight(50l);
		stdFeePropertyDTO.setFirstFee(10l);
		stdFeePropertyDTO.setFirstWeight(30l);
		stdFeePropertyDTO.setRegionId(3);
		RegionFeeDTO stdFeePropertyDTO1 = new RegionFeeDTO();
		stdFeePropertyDTO1.setContinueFee(4l);
		stdFeePropertyDTO1.setContinueWeight(60l);
		stdFeePropertyDTO1.setFirstFee(15l);
		stdFeePropertyDTO1.setFirstWeight(40l);
		stdFeePropertyDTO1.setRegionId(2);
		propertyList.add(stdFeePropertyDTO);
		propertyList.add(stdFeePropertyDTO1);
		stdFeeDTO.setRegionFeeDTOList(propertyList);
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_STDFEE.getActionName());
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
		request.setCommand(ActionEnum.ADD_STDFEE.getActionName());
		request.setParam("stdFeeDTO", null);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}

	/**
	 * 测试开启限价下限比上限大分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test3() throws Exception {
		// 创建一个DTO并赋值
		StdFeeDTO stdFeeDTO = new StdFeeDTO();
		stdFeeDTO.setAllowDefault(1);
		stdFeeDTO.setCode("peisong2");
		stdFeeDTO.setContinueFee(4l);
		stdFeeDTO.setContinueWeight(120l);
		stdFeeDTO.setRemark("配送标准2");
		stdFeeDTO.setFeeLimitMark(1);
		stdFeeDTO.setFirstFee(10l);
		stdFeeDTO.setFirstWeight(50l);
		stdFeeDTO.setMaxFee(100l);
		stdFeeDTO.setMinFee(1000l);
		stdFeeDTO.setName("运费标准1");
		stdFeeDTO.setSort(2);
		stdFeeDTO.setWeightUnit(1);
		// 创建属性
		List propertyList = new ArrayList();
		RegionFeeDTO stdFeePropertyDTO = new RegionFeeDTO();
		stdFeePropertyDTO.setContinueFee(3l);
		stdFeePropertyDTO.setContinueWeight(50l);
		stdFeePropertyDTO.setFirstFee(10l);
		stdFeePropertyDTO.setFirstWeight(30l);
		stdFeePropertyDTO.setRegionId(3);
		RegionFeeDTO stdFeePropertyDTO1 = new RegionFeeDTO();
		stdFeePropertyDTO1.setContinueFee(4l);
		stdFeePropertyDTO1.setContinueWeight(60l);
		stdFeePropertyDTO1.setFirstFee(15l);
		stdFeePropertyDTO1.setFirstWeight(40l);
		stdFeePropertyDTO1.setRegionId(2);
		propertyList.add(stdFeePropertyDTO);
		propertyList.add(stdFeePropertyDTO1);
		stdFeeDTO.setRegionFeeDTOList(propertyList);
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_STDFEE.getActionName());
		request.setParam("stdFeeDTO", stdFeeDTO);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}

}
