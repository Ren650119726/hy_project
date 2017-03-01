package com.mockuai.shopcenter;

import com.mockuai.shopcenter.api.Response;
import com.mockuai.shopcenter.domain.dto.StoreDTO;
import com.mockuai.shopcenter.domain.qto.StoreQTO;

import java.util.List;

/**
 * Created by yindingyu on 15/11/4.
 */
public interface StoreClient {

    public Response<StoreDTO> getStore(Long id,Long sellerId,String appKey);

    public Response<StoreDTO> getStoreByOwnerId(Long ownerId,String appKey);

    public Response<List<StoreDTO>> queryStore(StoreQTO storeQTO,String appKey);

    public Response<Long> addStore(StoreDTO storeDTO,String appKey);

    public Response<Long> updateStore(StoreDTO storeDTO,String appKey);

    public Response<Long> deleteStore(Long id,Long sellerId,String appKey);

    public Response<StoreDTO> getStoreByAddress(Long sellerId,Long userId,Long addressId,String condition,String appKey);


}
