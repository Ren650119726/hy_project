package com.mockuai.deliverycenter.client.impl;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.deliverycenter.client.StorageClient;
import com.mockuai.deliverycenter.common.api.BaseRequest;
import com.mockuai.deliverycenter.common.api.DeliveryService;
import com.mockuai.deliverycenter.common.api.Request;
import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.constant.ActionEnum;
import com.mockuai.deliverycenter.common.dto.storage.StorageDTO;
import com.mockuai.deliverycenter.common.dto.storage.StorageSendDTO;
import com.mockuai.deliverycenter.common.qto.storage.StorageQTO;
import com.mockuai.deliverycenter.common.qto.storage.StorageSendQTO;

public class StorageClientImpl implements StorageClient {
	@Resource
	private DeliveryService deliveryService;

	public Response<StorageDTO> addStorage(StorageDTO storageDTO,String appkey) {
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_STORAGE.getActionName());
		request.setParam("storageDTO", storageDTO);
		request.setParam("appKey", appkey);
		// 执行分发执行对应Action
		return deliveryService.execute(request);
	}

	public Response<StorageSendDTO> addStorageSend(StorageSendDTO storageSendDTO,String appkey) {
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_STORAGE_SEND.getActionName());
		request.setParam("storageSendDTO", storageSendDTO);
		request.setParam("appKey", appkey);
		// 执行分发执行对应Action
		return deliveryService.execute(request);
	}

	public Response<Boolean> deleteStorage(Integer id,String appkey) {
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.DELETE_STORAGE.getActionName());
		request.setParam("id", id);
		request.setParam("appKey", appkey);
		// 执行分发执行对应Action
		return deliveryService.execute(request);
	}

	public Response<Boolean> deleteStorageSend(Integer id,String appkey) {
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.DELETE_STORAGE_SEND.getActionName());
		request.setParam("id", id);
		request.setParam("appKey", appkey);
		// 执行分发执行对应Action
		return deliveryService.execute(request);
	}

	public Response<List<StorageDTO>> queryStorage(StorageQTO storageQTO,String appkey) {
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_STORAGE.getActionName());
		request.setParam("storageQTO", storageQTO);
		request.setParam("appKey", appkey);
		// 执行分发执行对应Action
		return deliveryService.execute(request);
	}

	public Response<List<StorageSendDTO>> queryStorageSend(
			StorageSendQTO storageSendQTO,String appkey) {
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_STORAGE_SEND.getActionName());
		request.setParam("storageSendQTO", storageSendQTO);
		request.setParam("appKey", appkey);
		// 执行分发执行对应Action
		return deliveryService.execute(request);
	}

	public Response<Boolean> updateStorage(StorageDTO storageDTO,String appkey) {
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_STORAGE.getActionName());
		request.setParam("storageDTO", storageDTO);
		request.setParam("appKey", appkey);
		// 执行分发执行对应Action
		return deliveryService.execute(request);
	}

	public Response<Boolean> updateStorageSend(StorageSendDTO storageSendDTO,String appkey) {
		// 创建request对象并赋值
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_STORAGE_SEND.getActionName());
		request.setParam("storageSendDTO", storageSendDTO);
		request.setParam("appKey", appkey);
		// 执行分发执行对应Action
		return deliveryService.execute(request);
	}

}
