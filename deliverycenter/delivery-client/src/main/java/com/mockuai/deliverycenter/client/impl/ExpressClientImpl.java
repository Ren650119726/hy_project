package com.mockuai.deliverycenter.client.impl;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.deliverycenter.client.ExpressClient;
import com.mockuai.deliverycenter.common.api.BaseRequest;
import com.mockuai.deliverycenter.common.api.DeliveryService;
import com.mockuai.deliverycenter.common.api.Request;
import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.constant.ActionEnum;
import com.mockuai.deliverycenter.common.dto.express.ExpressDTO;
import com.mockuai.deliverycenter.common.dto.express.ExpressPropertyDTO;
import com.mockuai.deliverycenter.common.dto.express.ExpressRegionDTO;
import com.mockuai.deliverycenter.common.qto.express.ExpressQTO;

public class ExpressClientImpl implements ExpressClient {
	@Resource
	private DeliveryService deliveryService;

	public Response<ExpressDTO> addExpress(ExpressDTO expressDTO,String appkey) {
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_EXPRESS.getActionName());
		request.setParam("expressDTO", expressDTO);
		request.setParam("appKey", appkey);
		// 执行分发执行对应Action
		return deliveryService.execute(request);
	}

	public Response<List<ExpressPropertyDTO>> addExpressProperty(
			List<ExpressPropertyDTO> expressPropertyDTOList,String appkey) {
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_EXPRESS_PROPERTY.getActionName());
		request.setParam("expressPropertyDTOList", expressPropertyDTOList);
		request.setParam("appKey", appkey);
		// 执行分发执行对应Action
		return deliveryService.execute(request);
	}

	public Response<Boolean> checkRegionSupported(
			ExpressRegionDTO expressRegionDTO,String appkey) {
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.CHECK_REGION_SUPPORTED.getActionName());
		request.setParam("expressRegionDTO", expressRegionDTO);
		request.setParam("appKey", appkey);
		// 执行分发执行对应Action
		return deliveryService.execute(request);
	}

	public Response<Boolean> deleteExpress(Integer id,String appkey) {
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.DELETE_EXPRESS.getActionName());
		request.setParam("id", id);
		request.setParam("appKey", appkey);
		// 执行分发执行对应Action
		return deliveryService.execute(request);
	}

	public Response<Boolean> deleteExpressProperty(
			List<Integer> expressPropertyIdList,String appkey) {
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.DELETE_EXPRESS_PROPERTY.getActionName());
		request.setParam("expressPropertyIdList", expressPropertyIdList);
		request.setParam("appKey", appkey);
		// 执行分发执行对应Action
		return deliveryService.execute(request);
	}

	public Response<List<ExpressDTO>> queryExpress(ExpressQTO expressQTO,String appkey) {
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_EXPRESS.getActionName());
		request.setParam("expressQTO", expressQTO);
		request.setParam("appKey", appkey);
		// 执行分发执行对应Action
		return deliveryService.execute(request);
	}

	public Response<Boolean> updateExpress(ExpressDTO expressDTO,String appkey) {
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_EXPRESS.getActionName());
		request.setParam("expressDTO", expressDTO);
		request.setParam("appKey", appkey);
		// 执行分发执行对应Action
		return deliveryService.execute(request);
	}

}
