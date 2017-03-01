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
import com.mockuai.deliverycenter.core.service.BaseTest;

public class AddRegionFeeTest extends BaseTest {

	@Resource
	private DeliveryService deliveryService;

	/**
	 * 测试新增成功分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test1() throws Exception {
		// 创建属性
		List<RegionFeeDTO> regionFeeDTOList = new ArrayList<RegionFeeDTO>();
		RegionFeeDTO stdFeePropertyDTO = new RegionFeeDTO();
		stdFeePropertyDTO.setContinueFee(3l);
		stdFeePropertyDTO.setContinueWeight(50l);
		stdFeePropertyDTO.setFirstFee(10l);
		stdFeePropertyDTO.setFirstWeight(30l);
		stdFeePropertyDTO.setRegionId(3);
		stdFeePropertyDTO.setFeeId(4);
		RegionFeeDTO stdFeePropertyDTO1 = new RegionFeeDTO();
		stdFeePropertyDTO1.setContinueFee(4l);
		stdFeePropertyDTO1.setContinueWeight(60l);
		stdFeePropertyDTO1.setFirstFee(15l);
		stdFeePropertyDTO1.setFirstWeight(40l);
		stdFeePropertyDTO1.setRegionId(2);
		stdFeePropertyDTO1.setFeeId(5);
		regionFeeDTOList.add(stdFeePropertyDTO);
		regionFeeDTOList.add(stdFeePropertyDTO1);
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_REGION_FEE.getActionName());
		request.setParam("regionFeeDTOList", regionFeeDTOList);
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
		request.setCommand(ActionEnum.ADD_REGION_FEE.getActionName());
		request.setParam("regionFeeDTOList", null);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}

	/**
	 * 测试区域ID不存在（一条记录失败事物回滚）分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test3() throws Exception {
		// 创建属性
		List<RegionFeeDTO> regionFeeDTOList = new ArrayList<RegionFeeDTO>();
		RegionFeeDTO stdFeePropertyDTO = new RegionFeeDTO();
		stdFeePropertyDTO.setContinueFee(3l);
		stdFeePropertyDTO.setContinueWeight(50l);
		stdFeePropertyDTO.setFirstFee(10l);
		stdFeePropertyDTO.setFirstWeight(30l);
		stdFeePropertyDTO.setRegionId(20);
		stdFeePropertyDTO.setFeeId(4);
		RegionFeeDTO stdFeePropertyDTO1 = new RegionFeeDTO();
		stdFeePropertyDTO1.setContinueFee(4l);
		stdFeePropertyDTO1.setContinueWeight(60l);
		stdFeePropertyDTO1.setFirstFee(15l);
		stdFeePropertyDTO1.setFirstWeight(40l);
		stdFeePropertyDTO1.setRegionId(2);
		stdFeePropertyDTO1.setFeeId(5);
		regionFeeDTOList.add(stdFeePropertyDTO);
		regionFeeDTOList.add(stdFeePropertyDTO1);
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_REGION_FEE.getActionName());
		request.setParam("regionFeeDTOList", regionFeeDTOList);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}

	/**
	 * 测试运费标准不存在（一条记录失败事物回滚）分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test4() throws Exception {
		// 创建属性
		List<RegionFeeDTO> regionFeeDTOList = new ArrayList<RegionFeeDTO>();
		RegionFeeDTO stdFeePropertyDTO = new RegionFeeDTO();
		stdFeePropertyDTO.setContinueFee(3l);
		stdFeePropertyDTO.setContinueWeight(50l);
		stdFeePropertyDTO.setFirstFee(10l);
		stdFeePropertyDTO.setFirstWeight(30l);
		stdFeePropertyDTO.setRegionId(3);
		stdFeePropertyDTO.setFeeId(20);
		RegionFeeDTO stdFeePropertyDTO1 = new RegionFeeDTO();
		stdFeePropertyDTO1.setContinueFee(4l);
		stdFeePropertyDTO1.setContinueWeight(60l);
		stdFeePropertyDTO1.setFirstFee(15l);
		stdFeePropertyDTO1.setFirstWeight(40l);
		stdFeePropertyDTO1.setRegionId(2);
		stdFeePropertyDTO1.setFeeId(5);
		regionFeeDTOList.add(stdFeePropertyDTO);
		regionFeeDTOList.add(stdFeePropertyDTO1);
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_REGION_FEE.getActionName());
		request.setParam("regionFeeDTOList", regionFeeDTOList);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}

	/**
	 * 测试事物回滚分支
	 * 
	 * @throws Exception
	 */
	@Test
	public void test5() throws Exception {
		// 创建属性
		List<RegionFeeDTO> regionFeeDTOList = new ArrayList<RegionFeeDTO>();
		RegionFeeDTO stdFeePropertyDTO = new RegionFeeDTO();
		stdFeePropertyDTO.setContinueFee(3l);
		stdFeePropertyDTO.setContinueWeight(50l);
		stdFeePropertyDTO.setFirstFee(10l);
		stdFeePropertyDTO.setFirstWeight(30l);
		stdFeePropertyDTO.setRegionId(3);
		stdFeePropertyDTO.setFeeId(4);
		RegionFeeDTO stdFeePropertyDTO1 = new RegionFeeDTO();
		stdFeePropertyDTO1.setContinueFee(4l);
		stdFeePropertyDTO1.setContinueWeight(60l);
		stdFeePropertyDTO1.setFirstFee(15l);
		stdFeePropertyDTO1.setFirstWeight(40l);
		stdFeePropertyDTO1.setRegionId(22);
		stdFeePropertyDTO1.setFeeId(5);
		regionFeeDTOList.add(stdFeePropertyDTO);
		regionFeeDTOList.add(stdFeePropertyDTO1);
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_REGION_FEE.getActionName());
		request.setParam("regionFeeDTOList", regionFeeDTOList);
		// 执行分发执行对应Action
		Response response = deliveryService.execute(request);
		Assert.assertEquals(false, response.isSuccess());
	}
}
