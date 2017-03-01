//package com.mockuai.deliverycenter.core.manager.storage.impl;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.deliverycenter.common.constant.RetCodeEnum;
//import com.mockuai.deliverycenter.common.dto.storage.StorageSendDTO;
//import com.mockuai.deliverycenter.common.qto.storage.StorageSendQTO;
//import com.mockuai.deliverycenter.core.domain.BaseDo;
//import com.mockuai.deliverycenter.core.domain.storage.StorageSend;
//import com.mockuai.deliverycenter.core.exception.DeliveryException;
//import com.mockuai.deliverycenter.core.manager.BaseManager;
//import com.mockuai.deliverycenter.core.manager.storage.StorageSendManager;
//import com.mockuai.deliverycenter.core.util.TransUtil;
//
//@Service
//public class StorageSendManagerImpl extends BaseManager implements
//		StorageSendManager {
//
//	@Override
//	public StorageSendDTO addStorageSend(StorageSendDTO storageSendDTO)
//			throws DeliveryException {
//		if (storageSendDTO == null) {
//			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
//					"storageSendDTO is null");
//		}
//		checkStatus(storageSendDTO.getStatus());
//		// 创建一个DO
//		StorageSend storageSend = new StorageSend();
//		// DTO转换成DO
//		storageSend = (StorageSend) TransUtil.trans2Do(storageSendDTO,
//				storageSend);
//		// 执行新增操作
//		storageSend = (StorageSend) getBaseDao().insert(storageSend);
//		// DO转换成DTO
//		storageSendDTO = (StorageSendDTO) TransUtil.trans2Dto(storageSendDTO,
//				storageSend);
//		return storageSendDTO;
//	}
//
//	@Override
//	public int updateStorageSend(StorageSendDTO storageSendDTO)
//			throws DeliveryException {
//		if (storageSendDTO == null) {
//			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
//					"storageSendDTO is null");
//		}
//		checkStatus(storageSendDTO.getStatus());
//		// 创建一个DO
//		BaseDo storageSend = new StorageSend();
//		// DTO转换成DO
//		storageSend = TransUtil.trans2Do(storageSendDTO, storageSend);
//		// 执行更新操作
//		int row = getBaseDao().update(storageSend);
//		if (row != 1) {
//			throw new DeliveryException(RetCodeEnum.UPDATE_ERROR);
//		}
//		return row;
//	}
//
//	@Override
//	public int deleteStorageSend(Integer id) throws DeliveryException {
//		if (id == null) {
//			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
//					"id is null");
//		}
//		// 创建一个DO
//		StorageSend storageSend = new StorageSend();
//		// DO赋值逻辑删除
//		storageSend.setId(id);
//		storageSend.setDeleted(1);
//		// 执行更新操作
//		int row = getBaseDao().update(storageSend);
//		if (row != 1) {
//			throw new DeliveryException(RetCodeEnum.DELETE_ERROR);
//		}
//		return row;
//	}
//
//	@Override
//	public List queryStorageSend(StorageSendQTO storageSendQTO)
//			throws DeliveryException {
//		if (storageSendQTO == null) {
//			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
//					"storageSendQTO is null");
//		}
//		checkStatus(storageSendQTO.getStatus());
//		List<BaseDo> storageSendList = query(storageSendQTO);
//		List<StorageSendDTO> storageSendDTOList = new ArrayList<StorageSendDTO>();
//		for (BaseDo storageSend : storageSendList) {
//			// 创建一个DtO
//			StorageSendDTO storageSendDTO = new StorageSendDTO();
//			// DO转换成DTO
//			storageSendDTO = (StorageSendDTO) TransUtil.trans2Dto(
//					storageSendDTO, storageSend);
//			storageSendDTOList.add(storageSendDTO);
//		}
//		return storageSendDTOList;
//	}
//
//	@Override
//	public StorageSend getStorageSend(Integer storageSendId)
//			throws DeliveryException {
//		if (storageSendId == null) {
//			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
//					"storageSendId is null");
//		}
//		StorageSend storageSend = new StorageSend();
//		storageSend.setId(storageSendId);
//		return (StorageSend) getBaseDao().get(storageSend);
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
//}
