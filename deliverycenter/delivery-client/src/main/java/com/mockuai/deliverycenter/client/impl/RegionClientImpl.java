package com.mockuai.deliverycenter.client.impl;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.deliverycenter.client.RegionClient;
import com.mockuai.deliverycenter.common.api.BaseRequest;
import com.mockuai.deliverycenter.common.api.DeliveryService;
import com.mockuai.deliverycenter.common.api.Request;
import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.constant.ActionEnum;
import com.mockuai.deliverycenter.common.dto.fee.RegionDTO;
import com.mockuai.deliverycenter.common.dto.fee.RegionFeeDTO;
import com.mockuai.deliverycenter.common.dto.fee.StdFeeDTO;
import com.mockuai.deliverycenter.common.qto.fee.DeliveryFeeQTO;
import com.mockuai.deliverycenter.common.qto.fee.RegionQTO;
import com.mockuai.deliverycenter.common.qto.fee.StdFeeQTO;

public class RegionClientImpl implements RegionClient {
	@Resource
	private DeliveryService deliveryService;

	public Response<RegionDTO> addRegion(RegionDTO regionDTO,String appkey) {
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_REGION.getActionName());
		request.setParam("regionDTO", regionDTO);
		request.setParam("appKey", appkey);
		// 执行分发执行对应Action
		return deliveryService.execute(request);
	}

	public Response<List<RegionFeeDTO>> addRegionFee(
			List<RegionFeeDTO> regionFeeDTOList,String appkey) {
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_REGION_FEE.getActionName());
		request.setParam("regionFeeDTOList", regionFeeDTOList);
		request.setParam("appKey", appkey);
		// 执行分发执行对应Action
		return deliveryService.execute(request);
	}

	public Response<StdFeeDTO> addStdFee(StdFeeDTO stdFeeDTO,String appkey) {
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_STDFEE.getActionName());
		request.setParam("stdFeeDTO", stdFeeDTO);
		request.setParam("appKey", appkey);
		// 执行分发执行对应Action
		return deliveryService.execute(request);
	}

	public Response<Boolean> deleteRegion(Integer id,String appkey) {
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.DELETE_REGION.getActionName());
		request.setParam("id", id);
		request.setParam("appKey", appkey);
		// 执行分发执行对应Action
		return deliveryService.execute(request);
	}

	public Response<Boolean> deleteRegionFee(List<Integer> regionFeeIdList,String appkey) {
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.DELETE_REGION_FEE.getActionName());
		request.setParam("regionFeeIdList", regionFeeIdList);
		request.setParam("appKey", appkey);
		// 执行分发执行对应Action
		return deliveryService.execute(request);
	}

	public Response<Boolean> deleteStdFee(Integer id,String appkey) {
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.DELETE_STDFEE.getActionName());
		request.setParam("id", id);
		request.setParam("appKey", appkey);
		// 执行分发执行对应Action
		return deliveryService.execute(request);
	}

	public Response<RegionDTO> getRegion(Integer id,String appkey) {
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.GET_REGION.getActionName());
		request.setParam("id", id);
		request.setParam("appKey", appkey);
		// 执行分发执行对应Action
		return deliveryService.execute(request);
	}

	public Response<Long> queryDeliveryFee(DeliveryFeeQTO deliveryFeeQTO,String appkey) {
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_DELIVERYFEE.getActionName());
		request.setParam("deliveryFeeQTO", deliveryFeeQTO);
		request.setParam("appKey", appkey);
		// 执行分发执行对应Action
		return deliveryService.execute(request);
	}

	public Response<List<RegionDTO>> queryRegion(RegionQTO regionQTO,String appkey) {
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_REGION.getActionName());
		request.setParam("regionQTO", regionQTO);
		request.setParam("appKey", appkey);
		// 执行分发执行对应Action
		return deliveryService.execute(request);
	}

	public Response<List<StdFeeDTO>> queryStdFee(StdFeeQTO stdFeeQTO,String appkey) {
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_STDFEE.getActionName());
		request.setParam("stdFeeQTO", stdFeeQTO);
		request.setParam("appKey", appkey);
		// 执行分发执行对应Action
		return deliveryService.execute(request);
	}

	public Response<Boolean> updateRegion(RegionDTO regionDTO,String appkey) {
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_REGION.getActionName());
		request.setParam("regionDTO", regionDTO);
		request.setParam("appKey", appkey);
		// 执行分发执行对应Action
		return deliveryService.execute(request);
	}

	public Response<Boolean> updateStdFee(StdFeeDTO stdFeeDTO,String appkey) {
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_STDFEE.getActionName());
		request.setParam("stdFeeDTO", stdFeeDTO);
		request.setParam("appKey", appkey);
		// 执行分发执行对应Action
		return deliveryService.execute(request);
	}

}
