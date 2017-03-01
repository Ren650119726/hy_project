package com.hanshu.imagecenter.client.impl;

import com.hanshu.imagecenter.client.ImageClient;
import com.mockuai.imagecenter.common.api.ImageService;
import com.mockuai.imagecenter.common.api.action.BaseRequest;
import com.mockuai.imagecenter.common.api.action.Response;
import com.mockuai.imagecenter.common.constant.ActionEnum;
import com.mockuai.imagecenter.common.domain.dto.ImageDTO;

import javax.annotation.Resource;

/**
 * Created by yindingyu on 16/5/20.
 */
public class ImageClientImpl implements ImageClient{

    @Resource
    private ImageService imageService;
    public Response<ImageDTO> addShopImage(Long userId, String url, String appKey) {
         BaseRequest request = new BaseRequest();
         request.setParam("userId",userId);
         request.setParam("url",url);
         request.setParam("appKey",appKey);
         request.setCommand(ActionEnum.GENERATE_SHOP_QRCODE.getActionName());
         Response<ImageDTO> response =  imageService.execute(request);
         return response;
    }

    public Response<ImageDTO> addRecommendImage(Long userId, String url, String appKey) {
        BaseRequest request = new BaseRequest();
        request.setParam("userId",userId);
        request.setParam("url",url);
        request.setParam("appKey",appKey);
        request.setCommand(ActionEnum.GENERATE_RECOMMEND_QRCODE.getActionName());
        Response<ImageDTO> response =  imageService.execute(request);
        return response;
    }

    public Response<ImageDTO> addItemImage(Long itemId, Long sellerId,Long distributorId, String url, String appKey) {
        BaseRequest request = new BaseRequest();
        request.setParam("itemId",itemId);
        request.setParam("id",sellerId);
        request.setParam("url",url);
        request.setParam("distributorId",distributorId);
        request.setParam("appKey",appKey);
        request.setCommand(ActionEnum.GENERATE_ITEM_QRCODE.getActionName());
        Response<ImageDTO> response =  imageService.execute(request);
        return response;
    }

    public Response<String> getImage(String type,Long id,String appKey) {
      return getImage(type,id,null,appKey);
    }

    public Response<String> getImage(String type,Long id,Long itemId,String appKey) {
        BaseRequest request = new BaseRequest();
        request.setParam("type",type);
        request.setParam("id",id);
        request.setParam("itemId",itemId);
        request.setParam("appKey",appKey);
        request.setCommand(ActionEnum.GET_QRCODE.getActionName());
        Response<String> response =  imageService.execute(request);
        return response;
    }



}
