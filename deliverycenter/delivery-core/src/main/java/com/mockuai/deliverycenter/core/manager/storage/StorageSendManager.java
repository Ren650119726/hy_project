//package com.mockuai.deliverycenter.core.manager.storage;
//
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.deliverycenter.common.dto.storage.StorageSendDTO;
//import com.mockuai.deliverycenter.common.qto.storage.StorageSendQTO;
//import com.mockuai.deliverycenter.core.domain.storage.StorageSend;
//import com.mockuai.deliverycenter.core.exception.DeliveryException;
//
//@Service
//public interface StorageSendManager {
//
//	public StorageSendDTO addStorageSend(StorageSendDTO StorageSendDTO)
//			throws DeliveryException;
//
//	public int updateStorageSend(StorageSendDTO StorageSendDTO)
//			throws DeliveryException;
//
//	public int deleteStorageSend(Integer id) throws DeliveryException;
//
//	public List<StorageSendDTO> queryStorageSend(StorageSendQTO storageSendQTO)
//			throws DeliveryException;
//
//	public StorageSend getStorageSend(Integer storageSendId)
//			throws DeliveryException;
//
//}
