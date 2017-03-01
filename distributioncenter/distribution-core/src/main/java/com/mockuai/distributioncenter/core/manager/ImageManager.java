package com.mockuai.distributioncenter.core.manager;

import com.mockuai.imagecenter.common.domain.dto.ImageDTO;

/**
 * Created by duke on 16/6/1.
 */
public interface ImageManager {
    /**
     * 生成店铺二维码
     * */
    ImageDTO addShopImage(Long userId, String url, String appKey);
    
    /**
     * 生成推荐二维码
     * */
    ImageDTO addRecommendImage(Long userId, String url, String appKey);

    /**
     * 获取图片
     * */
    String getImage(String type, Long id, String appKey);
}
