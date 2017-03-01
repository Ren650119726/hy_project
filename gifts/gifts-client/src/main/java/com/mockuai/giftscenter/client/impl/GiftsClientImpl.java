package com.mockuai.giftscenter.client.impl;

import com.mockuai.giftscenter.client.GiftsClient;
import com.mockuai.giftscenter.common.api.BaseRequest;
import com.mockuai.giftscenter.common.api.GiftsService;
import com.mockuai.giftscenter.common.api.Response;
import com.mockuai.giftscenter.common.constant.ActionEnum;
import com.mockuai.giftscenter.common.domain.dto.*;
import com.mockuai.giftscenter.common.domain.qto.ActionGiftQTO;
import com.mockuai.giftscenter.common.domain.qto.GiftsPacketQTO;
import com.mockuai.giftscenter.common.domain.qto.GrantCouponRecordQTO;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by edgar.zr on 12/2/15.
 */
public class GiftsClientImpl implements GiftsClient {

    @Resource
    private GiftsService giftsService;

    public Response<Long> addGifts(GiftsPacketDTO giftsPacketDTO, String appKey,Long sellerId) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.ADD_GIFTS.getActionName());
        baseRequest.setParam("giftsPacketDTO", giftsPacketDTO);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setParam("sellerId", sellerId);
        Response<Long> response = (Response<Long>) giftsService.execute(baseRequest);
        return response;
    }

     public Response<GiftsPacketDTO> getGifts(Long giftsID, String appKey){
    	 BaseRequest baseRequest = new BaseRequest();
    	 baseRequest.setCommand(ActionEnum.GET_GIFTS.getActionName());
         baseRequest.setParam("giftsID", giftsID);
         baseRequest.setParam("appKey", appKey);
         Response<GiftsPacketDTO> response = (Response<GiftsPacketDTO>) giftsService.execute(baseRequest);
    	 return response;
     }
     
     public Response<Integer> updateGifts(GiftsPacketDTO giftsPacketDTO, String appKey,Long sellerId){
    	 BaseRequest baseRequest = new BaseRequest();
    	 baseRequest.setCommand(ActionEnum.UPDATE_GIFTS.getActionName());
    	 baseRequest.setParam("giftsPacketDTO", giftsPacketDTO);
         baseRequest.setParam("appKey", appKey);
         baseRequest.setParam("sellerId", sellerId);
         Response<Integer> response = (Response<Integer>) giftsService.execute(baseRequest);
    	 return response;
     }
     
     public Response<Integer> deleteGifts(GiftsPacketDTO giftsPacketDTO, String appKey){
    	 BaseRequest baseRequest = new BaseRequest();
    	 baseRequest.setCommand(ActionEnum.DELETE_GIFTS.getActionName());
    	 baseRequest.setParam("giftsPacketDTO", giftsPacketDTO);
    	 baseRequest.setParam("appKey", appKey);
         Response<Integer> response = (Response<Integer>) giftsService.execute(baseRequest);
    	 return response;
     }
     public  Response<List<GiftsPacketDTO>> queryGifts(GiftsPacketQTO giftsPacketQTO, String appKey){
    	 BaseRequest baseRequest = new BaseRequest();
    	 baseRequest.setCommand(ActionEnum.QUERY_GIFTS.getActionName());
    	 baseRequest.setParam("giftsPacketQTO", giftsPacketQTO);
    	 baseRequest.setParam("appKey", appKey);
         Response<List<GiftsPacketDTO>> response = (Response<List<GiftsPacketDTO>>) giftsService.execute(baseRequest);
    	 return response;
     }
     
     public Response<GiftsPacketDTO> getGiftsItem(Long  itemId,Long itemSkuId,String appKey){
    	 BaseRequest baseRequest = new BaseRequest();
    	 baseRequest.setCommand(ActionEnum.ITEM_GIFTS.getActionName());
         baseRequest.setParam("itemId", itemId);
         baseRequest.setParam("itemSkuId", itemSkuId);
         baseRequest.setParam("appKey", appKey);
         Response<GiftsPacketDTO> response = (Response<GiftsPacketDTO>) giftsService.execute(baseRequest);
    	 return response;
     }
     
     public  Response<GiftsPacketProfitDTO> giftsPoints(Long itemId,Long levelId,String appKey){
    	 BaseRequest baseRequest = new BaseRequest();
    	 baseRequest.setCommand(ActionEnum.GIFTS_POINTS.getActionName());
         baseRequest.setParam("itemId", itemId);
         baseRequest.setParam("levelId", levelId);
         baseRequest.setParam("appKey", appKey);
         Response<GiftsPacketProfitDTO> response = (Response<GiftsPacketProfitDTO>) giftsService.execute(baseRequest);
    	 return response;
     }

    public Response<Void> grantActionGift(Long receiverId,String mobile, int appType, int actionType, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.GRANT_ACTION_GIFT.getActionName());
        baseRequest.setParam("receiverId", receiverId);
        baseRequest.setParam("actionType", actionType);
        baseRequest.setParam("appType",appType);
        baseRequest.setParam("mobile",mobile);
        baseRequest.setParam("appKey", appKey);
        Response<Void>  response =   giftsService.execute(baseRequest);
        return response;
    }

    public Response<ActionGiftDTO> getActionGift(int actionType, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.GET_ACTION_GIFT.getActionName());
        baseRequest.setParam("actionType", actionType);
        baseRequest.setParam("appKey", appKey);
        return   giftsService.execute(baseRequest);
    }

    public Response<Long> editActionGift(ActionGiftQTO actionGiftQTO, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.EDIT_ACTION_GIFT.getActionName());
        baseRequest.setParam("actionGiftQTO", actionGiftQTO);
        baseRequest.setParam("appKey", appKey);
        return   giftsService.execute(baseRequest);
    }

    public Response<PageDTO<List<GrantCouponRecordDTO>>> queryGrantCouponRecord(GrantCouponRecordQTO grantCouponRecordQTO, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.QUERY_GRANT_COUPON_RECORD.getActionName());
        baseRequest.setParam("grantCouponRecordQTO", grantCouponRecordQTO);
        baseRequest.setParam("appKey", appKey);
        return   giftsService.execute(baseRequest);
    }

}