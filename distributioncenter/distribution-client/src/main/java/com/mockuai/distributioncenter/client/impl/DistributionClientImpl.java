package com.mockuai.distributioncenter.client.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.mockuai.distributioncenter.client.DistributionClient;
import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.DistributionService;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.domain.dto.*;
import com.mockuai.distributioncenter.common.domain.qto.*;

/**
 * Created by duke on 15/10/28.
 */
public class DistributionClientImpl implements DistributionClient {
    @Resource
    private DistributionService distributionService;

    public Response<Boolean> updateShopQrcodeUri(DistShopQTO qto, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.UPDATE_SHOP_QRCODE_URI.getActionName());
        baseRequest.setParam("distShopDTO", qto);
        baseRequest.setParam("appKey", appKey);
        return distributionService.execute(baseRequest);

    }
        public Response<ItemDistPlanDTO> addItemDistPlan(ItemDistPlanDTO itemDistPlanDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemDistPlanDTO", itemDistPlanDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.ADD_ITEM_DIST_PLAN.getActionName());
        return distributionService.execute(request);
    }

    public Response<List<ItemDistPlanDTO>> getItemDistPlanByItemId(Long itemId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemId", itemId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_ITEM_DIST_PLAN_BY_ITEM.getActionName());
        return distributionService.execute(request);
    }

    public Response<Boolean> updateItemDistPlan(ItemDistPlanDTO itemDistPlanDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemDistPlanDTO", itemDistPlanDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.UPDATE_ITEM_DIST_PLAN.getActionName());
        return distributionService.execute(request);
    }

    public Response<ItemSkuDistPlanDTO> addItemSkuDistPlan(ItemSkuDistPlanDTO itemSkuDistPlanDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemSkuDistPlanDTO", itemSkuDistPlanDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.ADD_ITEM_SKU_DIST_PLAN.getActionName());
        return distributionService.execute(request);
    }

    public Response<ItemSkuDistPlanDTO> getItemSkuDistPlanBySkuId(Long skuId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemSkuId", skuId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_ITEM_SKU_DIST_PLAN_BY_ITEM_SKU.getActionName());
        return distributionService.execute(request);
    }

    public Response<Boolean> updateItemSkuDistPlan(ItemSkuDistPlanDTO itemSkuDistPlanDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemSkuDistPlanDTO", itemSkuDistPlanDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.UPDATE_ITEM_SKU_DIST_PLAN.getActionName());
        return distributionService.execute(request);
    }

    public Response<SellerDTO> getSellerByUserId(Long userId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userId", userId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_SELLER_BY_USER_ID.getActionName());
        return distributionService.execute(request);
    }

    public Response<List<ItemSkuDistPlanDTO>> getItemSkuDistPlanByItemId(Long itemId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemId", itemId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_ITEM_SKU_DIST_PLAN_BY_ITEM.getActionName());
        return distributionService.execute(request);
    }

    public Response<List<SellerDTO>> queryChildSeller(Long userId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userId", userId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_POSTERITY_BY_USER_ID.getActionName());
        return distributionService.execute(request);
    }

    public Response<SellerDTO> getParentSeller(Long userId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userId", userId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_PARENT_SELLER_BY_USER_ID.getActionName());
        return distributionService.execute(request);
    }

    public Response<TeamSummaryDTO> getTeamSummary(Long userId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userId", userId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_TEAM_SUMMARY.getActionName());
        return distributionService.execute(request);
    }

    public Response<List<SellerDTO>> querySeller(SellerQTO sellerQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("sellerQTO", sellerQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_SELLER.getActionName());
        return distributionService.execute(request);
    }

    public Response<List<DistShopDTO>> queryShop(DistShopQTO distShopQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("distShopQTO", distShopQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_SHOP.getActionName());
        return distributionService.execute(request);
    }

    public Response<SellerDTO> createSeller(Long userId, String realName, String mobile, String password, String wechatId, Long inviterSellerId, Integer status, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userId", userId);
        request.setParam("realName", realName);
        request.setParam("mobile", mobile);
        request.setParam("password", password);
        request.setParam("inviterSellerId", inviterSellerId);
        request.setParam("wechatId", wechatId);
        request.setParam("inviterSellerId", inviterSellerId);
        request.setParam("status", status);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.CREATE_SELLER.getActionName());
        return distributionService.execute(request);
    }

    public Response<DistShopDTO> getShopBySellerId(Long sellerId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("sellerId", sellerId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_SHOP_BY_SELLER_ID.getActionName());
        return distributionService.execute(request);
    }

    public Response<DistShopDTO> getShopByUserId(Long userId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userId", userId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_SHOP_BY_USER_ID.getActionName());
        return distributionService.execute(request);
    }

    public Response<DistShopForMopDTO> getShopForMopBySellerId(Long sellerId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("sellerId", sellerId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_SHOP_BY_SELLER_ID.getActionName());
        Response<DistShopDTO> response =  distributionService.execute(request);
        if (response.isSuccess()) {
            DistShopDTO distShopDTO = response.getModule();
            DistShopForMopDTO distShopForMopDTO = new DistShopForMopDTO();
            distShopForMopDTO.setDistributorId(distShopDTO.getSellerId());
            distShopForMopDTO.setShopName(distShopDTO.getShopName());
            distShopForMopDTO.setDistributorSign(distShopDTO.getShopDesc());
            SellerDTO sellerDTO = distShopDTO.getSellerDTO();
            if (sellerDTO != null) {
                distShopForMopDTO.setHeadImgUrl(sellerDTO.getHeadImgUrl());
            }
            return new DistributionResponse<DistShopForMopDTO>(distShopForMopDTO);
        } else {
            return new DistributionResponse<DistShopForMopDTO>(response.getCode(), response.getMessage());
        }
    }

    public Response<Boolean> updateSeller(SellerDTO sellerDTO, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("sellerDTO", sellerDTO);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.UPDATE_SELLER.getActionName());
        return distributionService.execute(baseRequest);
    }

    public Response<SellerDTO> getSellerByInviterCode(String inviterCode, String appKey) {
        Request request = new BaseRequest();
        request.setParam("inviterCode", inviterCode);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_SELLER_BY_INVITER_CODE.getActionName());
        return distributionService.execute(request);
    }

    public Response<Boolean> updateSellerRealNameByUserId(Long userId, String realName, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userId", userId);
        request.setParam("realName", realName);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.UPDATE_SELLER_REAL_NAME_BY_USER_ID.getActionName());
        return distributionService.execute(request);
    }
    
    public Response<Boolean> shuju(Long orderId,String appKey,Long userId) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("orderId", orderId);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setParam("userId", userId);
        baseRequest.setCommand(ActionEnum.FENYONG_ORDER_UNPAID.getActionName());
        Response<Boolean> response = (Response<Boolean>) distributionService.execute(baseRequest);
        return response;

    }
    
    public Response<Boolean> shuju2(Long orderId,String appKey,Long userId) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("orderId", orderId);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setParam("userId", userId);
        baseRequest.setCommand(ActionEnum.FENYONG_ORDER_SUCCESS.getActionName());
        Response<Boolean> response = (Response<Boolean>) distributionService.execute(baseRequest);
        return response;

    }
    
    public Response<Boolean> addSellerLevelApply(SellerLevelApplyDTO sellerLevelApplyDTO,String appKey){
    	BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("appKey", appKey);
        baseRequest.setParam("sellerLevelApplyDTO", sellerLevelApplyDTO);
        baseRequest.setCommand(ActionEnum.ADD_SELLER_LEVEL_APPLY.getActionName());
        Response<Boolean> response = (Response<Boolean>) distributionService.execute(baseRequest);
        return response;
    }
    
    public Response<Map<String, Boolean>> getSellerLevelApply(SellerLevelApplyDTO sellerLevelApplyDTO,String appKey){
    	BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("appKey", appKey);
        baseRequest.setParam("sellerLevelApplyDTO", sellerLevelApplyDTO);
        baseRequest.setCommand(ActionEnum.GET_SELLER_LEVEL_APPLY.getActionName());
        Response<Map<String, Boolean>> response = (Response<Map<String, Boolean>>) distributionService.execute(baseRequest);
        return response;
    }
    
    public Response<List<SellerLevelApplyDTO>> querySellerLevelApply(SellerLevelApplyQTO sellerLevelApplyQTO,String appKey){
    	BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("appKey", appKey);
        baseRequest.setParam("sellerLevelApplyQTO", sellerLevelApplyQTO);
        baseRequest.setCommand(ActionEnum.QUERY_SELLER_LEVEL_APPLY.getActionName());
        Response<List<SellerLevelApplyDTO>> response = (Response<List<SellerLevelApplyDTO>>) distributionService.execute(baseRequest);
        return response;
    }
    
    public Response<Boolean> updateSellerLevelApply(SellerLevelApplyDTO sellerLevelApplyDTO,String appKey){
    	BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("appKey", appKey);
        baseRequest.setParam("sellerLevelApplyDTO", sellerLevelApplyDTO);
        baseRequest.setCommand(ActionEnum.UPDATE_SELLER_LEVEL_APPLY.getActionName());
        Response<Boolean> response = (Response<Boolean>) distributionService.execute(baseRequest);
        return response;
    }
    
    public  Response<DistRecordDTO> fenyongTongJi(DistRecordQTO distRecordQTO,String appKey,Long userId){
    	BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("distRecordQTO", distRecordQTO);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setParam("userId", userId);
        baseRequest.setCommand(ActionEnum.RECORDSTATISTICS.getActionName());
        Response<DistRecordDTO> response = (Response<DistRecordDTO>) distributionService.execute(baseRequest);
        return response;
    }
    
    public Response<Boolean> addRelationship(Long userId,Long parentId, String appKey){
    	BaseRequest baseRequest = new BaseRequest();
    	baseRequest.setParam("userId", userId);
    	baseRequest.setParam("parentId", parentId);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.CREATE_RELATION_SHIP.getActionName());
        Response<Boolean> response = (Response<Boolean>) distributionService.execute(baseRequest);
        return response;
    }
    
    public Response<Boolean> userSeller(String appKey){
    	BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.USERSELLER.getActionName());
        Response<Boolean> response = (Response<Boolean>) distributionService.execute(baseRequest);
        return response;
    }

    public Response<List<ItemSkuDistPlanDTO>> getDistByItemSkuId(ItemSkuDistPlanQTO itemSkuDistPlanQTO, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("itemSkuDistPlanQTO",itemSkuDistPlanQTO);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.GET_DIST_PLAN_BY_ITEM_SKU_ID.getActionName());
        Response<List<ItemSkuDistPlanDTO>> response = distributionService.execute(baseRequest);
        return response;
    }


    public Response<List<FansDistDTO>> queryDistListFromFans(String appKey, FansDistQTO fansDistQTO) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("fansDistQTO",fansDistQTO);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.QUERY_FANS_AND_DIST.getActionName());
        Response<List<FansDistDTO>> response = distributionService.execute(baseRequest);
        return response;
    }
}

