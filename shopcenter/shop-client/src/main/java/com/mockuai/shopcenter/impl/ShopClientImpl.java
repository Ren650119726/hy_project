package com.mockuai.shopcenter.impl;

import com.mockuai.shopcenter.ShopClient;
import com.mockuai.shopcenter.api.BaseRequest;
import com.mockuai.shopcenter.api.Request;
import com.mockuai.shopcenter.api.Response;
import com.mockuai.shopcenter.api.ShopService;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.domain.dto.LastDaysCountDTO;
import com.mockuai.shopcenter.domain.dto.ShopCollectionDTO;
import com.mockuai.shopcenter.domain.dto.ShopDTO;
import com.mockuai.shopcenter.domain.dto.ShopItemGroupDTO;
import com.mockuai.shopcenter.domain.qto.ShopCollectionQTO;
import com.mockuai.shopcenter.domain.qto.ShopItemGroupQTO;
import com.mockuai.shopcenter.domain.qto.ShopQTO;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ziqi.
 */
public class ShopClientImpl implements ShopClient {

    @Resource
    private ShopService shopService;

    public Response<ShopDTO> addShop(ShopDTO shopDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("shopDTO", shopDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.ADD_SHOP.getActionName());
        return shopService.execute(request);
    }

    public Response<List<ShopDTO>> queryShop(ShopQTO shopQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("shopQTO", shopQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_SHOP.getActionName());
        return shopService.execute(request);
    }

    public Response<Integer> countShop(ShopQTO shopQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("shopQTO", shopQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.COUNT_ALL_SHOP.getActionName());
        return shopService.execute(request);
    }

    public Response<ShopDTO> getShop(Long sellerId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("sellerId", sellerId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_SHOP.getActionName());
        return shopService.execute(request);
    }

    public Response<Boolean> updateShop(ShopDTO shopDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("shopDTO", shopDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.UPDATE_SHOP.getActionName());
        return shopService.execute(request);
    }

    public Response<ShopItemGroupDTO> addShopItemGroup(ShopItemGroupDTO shopItemGroupDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("shopItemGroupDTO", shopItemGroupDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.ADD_SHOP_ITEM_GROUP.getActionName());
        return shopService.execute(request);
    }

    public Response<Boolean> updateShopItemGroup(ShopItemGroupDTO shopItemGroupDTO,String updateItems, String appKey) {
        Request request = new BaseRequest();
        request.setParam("shopItemGroupDTO", shopItemGroupDTO);
        request.setParam("updateItems",updateItems);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.UPDATE_SHOP_ITEM_GROUP.getActionName());
        return shopService.execute(request);
    }

    public Response<ShopItemGroupDTO> getShopItemGroup(Long sellerId, Long groupId,String needItems, String appKey) {
        Request request = new BaseRequest();
        request.setParam("sellerId", sellerId);
        request.setParam("groupId", groupId);
        request.setParam("needItems",needItems);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_SHOP_ITEM_GROUP.getActionName());
        return shopService.execute(request);
    }

    public Response<Boolean> deleteShopItemGroup(Long id, Long sellerId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("id", id);
        request.setParam("sellerId", sellerId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.DELETE_SHOP_ITEM_GROUP.getActionName());
        return shopService.execute(request);
    }

    public Response<List<ShopItemGroupDTO>> queryShopItemGroup(ShopItemGroupQTO shopItemGroupQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("shopItemGroupQTO", shopItemGroupQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_SHOP_ITEM_GROUP.getActionName());
        return shopService.execute(request);
    }

    public Response<Integer> getShopStatus(Long sellerId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("sellerId", sellerId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_SHOP_STATUS.getActionName());
        return shopService.execute(request);
    }

    public Response<Boolean> freezeShop(Long sellerId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("sellerId", sellerId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.FREEZE_SHOP.getActionName());
        return shopService.execute(request);
    }

    public Response<Boolean> thawShop(Long sellerId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("sellerId", sellerId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.THAW_SHOP.getActionName());
        return shopService.execute(request);
    }

    public Response<ShopCollectionDTO> addShopCollection(ShopCollectionDTO shopCollectionDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("shopCollectionDTO", shopCollectionDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.ADD_SHOP_COLLECTION.getActionName());
        return shopService.execute(request);
    }

    public Response<Boolean> cancelShopCollection(Long sellerId, Long userId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("sellerId", sellerId);
        request.setParam("userId", userId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.CANCEL_SHOP_COLLECTION.getActionName());
        return shopService.execute(request);
    }

    public Response<Boolean> checkShopCollection(Long sellerId, Long userId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("sellerId", sellerId);
        request.setParam("userId", userId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.CHECK_SHOP_COLLECTION.getActionName());
        return shopService.execute(request);
    }

    public Response<List<ShopCollectionDTO>> queryShopCollectionUser(ShopCollectionQTO shopCollectionQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("shopCollectionQTO", shopCollectionQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_SHOP_COLLECTION_USER.getActionName());
        return shopService.execute(request);
    }

    public Response<List<ShopDTO>> queryUserCollectionShop(ShopCollectionQTO shopCollectionQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("shopCollectionQTO", shopCollectionQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_USER_COLLECTION_SHOP.getActionName());
        return shopService.execute(request);
    }

    public Response<List<LastDaysCountDTO>> countLastDaysShop(ShopQTO shopQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("shopQTO", shopQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.COUNT_LAST_DAYS_SHOP.getActionName());
        return shopService.execute(request);
    }

    public Response<ShopDTO> getShopDetails(Long shopId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("shopId", shopId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_SHOP_DETAILS.getActionName());
        return shopService.execute(request);
    }

    public Response<Boolean> setShopDetails(ShopDTO shopDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("shopDTO", shopDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.SET_SHOP_DETAILS.getActionName());
        return shopService.execute(request);
    }
}
