//package com.mockuai.deliverycenter.core.manager.storage;
//
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.deliverycenter.common.dto.storage.StorageDTO;
//import com.mockuai.deliverycenter.common.qto.storage.StorageQTO;
//import com.mockuai.deliverycenter.core.domain.storage.Storage;
//import com.mockuai.deliverycenter.core.exception.DeliveryException;
//
//@Service
//public interface StorageManager {
//
//	public StorageDTO addStorage(StorageDTO StorageDTO)
//			throws DeliveryException;
//
//	public int updateStorage(StorageDTO StorageDTO) throws DeliveryException;
//
//	public int deleteStorage(Integer id) throws DeliveryException;
//
//	public List<StorageDTO> queryStorage(StorageQTO storageQTO)
//			throws DeliveryException;
//
//	public Storage getStorage(Integer regonId) throws DeliveryException;
//
//}
