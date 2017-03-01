//package com.mockuai.deliverycenter.core.manager.storage.impl;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.deliverycenter.common.constant.RetCodeEnum;
//import com.mockuai.deliverycenter.common.dto.storage.StorageDTO;
//import com.mockuai.deliverycenter.common.qto.storage.StorageQTO;
//import com.mockuai.deliverycenter.core.domain.BaseDo;
//import com.mockuai.deliverycenter.core.domain.storage.Storage;
//import com.mockuai.deliverycenter.core.exception.DeliveryException;
//import com.mockuai.deliverycenter.core.manager.BaseManager;
//import com.mockuai.deliverycenter.core.manager.storage.StorageManager;
//import com.mockuai.deliverycenter.core.util.TransUtil;
//
//@Service
//public class StorageManagerImpl extends BaseManager implements StorageManager {
//
//	@Override
//	public StorageDTO addStorage(StorageDTO storageDTO)
//			throws DeliveryException {
//		if (storageDTO == null) {
//			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
//					"storageDTO is null");
//		}
//		checkStatus(storageDTO.getStatus());
//		// 创建一个DO
//		Storage storage = new Storage();
//		// DTO转换成DO
//		storage = (Storage) TransUtil.trans2Do(storageDTO, storage);
//		// 执行新增操作
//		storage = (Storage) getBaseDao().insert(storage);
//		// DO转换成DTO
//		storageDTO = (StorageDTO) TransUtil.trans2Dto(storageDTO, storage);
//		return storageDTO;
//	}
//
//	@Override
//	public int updateStorage(StorageDTO storageDTO) throws DeliveryException {
//		if (storageDTO == null) {
//			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
//					"storageDTO is null");
//		}
//		checkStatus(storageDTO.getStatus());
//		// 创建一个DO
//		BaseDo storage = new Storage();
//		// DTO转换成DO
//		storage = TransUtil.trans2Do(storageDTO, storage);
//		// 执行更新操作
//		int row = getBaseDao().update(storage);
//		if (row != 1) {
//			throw new DeliveryException(RetCodeEnum.UPDATE_ERROR);
//		}
//		return row;
//	}
//
//	@Override
//	public int deleteStorage(Integer id) throws DeliveryException {
//		if (id == null) {
//			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
//					"id is null");
//		}
//		// 创建一个DO
//		Storage storage = new Storage();
//		// DO赋值逻辑删除
//		storage.setId(id);
//		storage.setDeleted(1);
//		// 执行更新操作
//		int row = getBaseDao().update(storage);
//		if (row != 1) {
//			throw new DeliveryException(RetCodeEnum.DELETE_ERROR);
//		}
//		return row;
//	}
//
//	@Override
//	public List queryStorage(StorageQTO storageQTO) throws DeliveryException {
//		if (storageQTO == null) {
//			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
//					"storageQTO is null");
//		}
//		checkStatus(storageQTO.getStatus());
//		List<BaseDo> storageList = query(storageQTO);
//		List<StorageDTO> storageDTOList = new ArrayList<StorageDTO>();
//		for (BaseDo storage : storageList) {
//			// 创建一个DtO
//			StorageDTO storageDTO = new StorageDTO();
//			// DO转换成DTO
//			storageDTO = (StorageDTO) TransUtil.trans2Dto(storageDTO, storage);
//			storageDTOList.add(storageDTO);
//		}
//		return storageDTOList;
//	}
//
//	@Override
//	public Storage getStorage(Integer storageId) throws DeliveryException {
//		if (storageId == null) {
//			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
//					"storageId is null");
//		}
//		Storage storage = new Storage();
//		storage.setId(storageId);
//		return (Storage) getBaseDao().get(storage);
//	}
//
//	private void checkStatus(Integer status) throws DeliveryException {
//		if (status == null) {
//			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
//					"status is null");
//		}
//		if (status != 0 && status != 1) {
//			throw new DeliveryException(RetCodeEnum.PARAMETER_ERROR.getCode(),
//					"status must in (0,1)");
//		}
//	}
//
//}
