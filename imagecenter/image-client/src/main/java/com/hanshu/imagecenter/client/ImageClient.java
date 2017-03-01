package com.hanshu.imagecenter.client;

import com.mockuai.imagecenter.common.api.action.Response;
import com.mockuai.imagecenter.common.domain.dto.ImageDTO;
/**
 * Created by yindingyu on 16/5/20.
 */
public interface ImageClient {


    /**
     * 将url地址链接中的图片上传到oss,并返回上传后的图片信息
     * @param appKey
     * @return
     */
    Response<ImageDTO> addShopImage(Long userId, String url, String appKey);
    Response<ImageDTO> addRecommendImage(Long userId, String  url, String appKey);
    Response<ImageDTO> addItemImage(Long  itemId,  Long sellerId,Long distributorId, String url, String appKey);

    Response<String> getImage(String type ,Long id,String appKey);


    /**
     * 通过key获取已上传的图片信息
     * @return
     */
    Response<String> getImage(String type ,Long id,Long itemId,String appKey);
}
