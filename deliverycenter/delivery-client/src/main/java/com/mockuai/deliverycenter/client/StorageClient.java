package com.mockuai.deliverycenter.client;

import java.util.List;

import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.dto.storage.StorageDTO;
import com.mockuai.deliverycenter.common.dto.storage.StorageSendDTO;
import com.mockuai.deliverycenter.common.qto.storage.StorageQTO;
import com.mockuai.deliverycenter.common.qto.storage.StorageSendQTO;

public interface StorageClient {
	/**
	 * 新增仓库
	 * 
	 * @param storageDTO
	 * @return
	 */
	public Response<StorageDTO> addStorage(StorageDTO storageDTO,String appkey);

	/**
	 * 新增发货仓库
	 * 
	 * @param storageSendDTO
	 * @return
	 */
	public Response<StorageSendDTO> addStorageSend(StorageSendDTO storageSendDTO,String appkey);

	/**
	 * 删除仓库
	 * 
	 * @param id
	 * @return
	 */
	public Response<Boolean> deleteStorage(Integer id,String appkey);

	/**
	 * 删除发货仓库
	 * 
	 * @param id
	 * @return
	 */
	public Response<Boolean> deleteStorageSend(Integer id,String appkey);

	/**
	 * 查询仓库
	 * 
	 * @param storageQTO
	 * @return
	 */
	public Response<List<StorageDTO>> queryStorage(StorageQTO storageQTO,String appkey);

	/**
	 * 查询发货仓库
	 * 
	 * @param storageSendQTO
	 * @return
	 */
	public Response<List<StorageSendDTO>> queryStorageSend(
			StorageSendQTO storageSendQTO,String appkey);

	/**
	 * 修改仓库
	 * 
	 * @param storageDTO
	 * @return
	 */
	public Response<Boolean> updateStorage(StorageDTO storageDTO,String appkey);

	/**
	 * 修改发货仓库
	 * 
	 * @param storageSendDTO
	 * @return
	 */
	public Response<Boolean> updateStorageSend(StorageSendDTO storageSendDTO,String appkey);

}
