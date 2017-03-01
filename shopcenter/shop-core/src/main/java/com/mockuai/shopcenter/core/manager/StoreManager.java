package com.mockuai.shopcenter.core.manager;

import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.domain.dto.StoreDTO;
import com.mockuai.shopcenter.domain.qto.StoreQTO;

import java.util.List;

/**
 * Created by yindingyu on 15/11/3.
 */
public interface StoreManager {

    Long addStore(StoreDTO storeDTO) throws ShopException;

    Long updateStore(StoreDTO storeDTO)throws ShopException;

    StoreDTO getStore(Long id, Long seller_id, String bizCode)throws ShopException;

    Long deleteStore(Long id, Long seller_id, String bizCode)throws ShopException;

    List<StoreDTO> queryStore(StoreQTO storeQTO)throws ShopException;

    /**
     * 查询所有配送范围包括该坐标的门店
     * @param sellerId
     * @param longitude
     * @param latitude
     * @param bizCode
     * @return
     * @throws ShopException
     */
    StoreDTO getStoreByCoordinates(Long sellerId, double longitude, double latitude, String bizCode)throws ShopException;

    StoreDTO getStoreByOwnerId(Long ownerId, String bizCode)throws ShopException;

    StoreDTO getRecoveryStoreByCoordinates(Long sellerId, double longitude, double latitude, String bizCode) throws ShopException;

    StoreDTO getRepairStoreByCoordinates(Long sellerId, double longitude, double latitude, String bizCode) throws ShopException;

    List<StoreDTO> queryRecoveryStoreByCoordinates(Long sellerId, double longitude, double latitude, String bizCode) throws ShopException;

    List<StoreDTO> queryRepairStoreByCoordinates(Long sellerId, double longitude, double latitude, String bizCode) throws ShopException;

    List<StoreDTO> queryStoreByCoordinates(Long sellerId, double longitude, double latitude, String bizCode) throws ShopException;
}
