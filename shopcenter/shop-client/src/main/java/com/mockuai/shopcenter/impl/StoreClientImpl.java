package com.mockuai.shopcenter.impl;

import com.mockuai.shopcenter.StoreClient;
import com.mockuai.shopcenter.api.BaseRequest;
import com.mockuai.shopcenter.api.Request;
import com.mockuai.shopcenter.api.Response;
import com.mockuai.shopcenter.api.ShopService;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.domain.dto.StoreDTO;
import com.mockuai.shopcenter.domain.qto.StoreQTO;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 15/11/4.
 */
public class StoreClientImpl implements StoreClient{

    @Resource
    private ShopService shopService;

    public Response<StoreDTO> getStore(Long id, Long sellerId, String appKey) {

        Request request = new BaseRequest();
        request.setParam("sellerId",sellerId);
        request.setParam("id",id);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_STORE.getActionName());

        return shopService.execute(request);
    }

    public Response<StoreDTO> getStoreByOwnerId(Long ownerId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("ownerId",ownerId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_STORE_BY_OWNER_ID.getActionName());

        return shopService.execute(request);
    }

    public Response<List<StoreDTO>> queryStore(StoreQTO storeQTO, String appKey) {

        Request request = new BaseRequest();
        request.setParam("storeQTO",storeQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_STORE.getActionName());

        return shopService.execute(request);
    }

    public Response<Long> addStore(StoreDTO storeDTO, String appKey) {

        Request request = new BaseRequest();
        request.setParam("storeDTO",storeDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.ADD_STORE.getActionName());

        return shopService.execute(request);
    }

    public Response<Long> updateStore(StoreDTO storeDTO, String appKey) {

        Request request = new BaseRequest();
        request.setParam("storeDTO",storeDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.UPDATE_STORE.getActionName());

        return shopService.execute(request);
    }

    public Response<Long> deleteStore(Long id, Long sellerId, String appKey) {

        Request request = new BaseRequest();
        request.setParam("sellerId",sellerId);
        request.setParam("id",id);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.DELETE_STORE.getActionName());

        return shopService.execute(request);
    }

    public Response<StoreDTO> getStoreByAddress(Long sellerId, Long userId,Long addressId, String condition, String appKey) {
        Request request = new BaseRequest();
        request.setParam("sellerId",sellerId);
        request.setParam("userId",userId);
        request.setParam("addressId",addressId);
        request.setParam("condition", condition);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_STORE_BY_ADDRESS.getActionName());

        return shopService.execute(request);
    }
}
