package com.mockuai.imagecenter.core.manager;

import com.mockuai.imagecenter.common.domain.dto.ImageDTO;
import com.mockuai.imagecenter.core.domain.ImageDO;
import com.mockuai.imagecenter.core.exception.ImageException;

/**
 * Created by 冠生 on 16/5/23.
 */
public interface ImageManager {


    /**
     * @param id 如 推荐二维码userId 店铺 userId   商品 itemId
     * @throws ImageException
     */
    ImageDO generateShopCode(String id, String content, String appKey) throws ImageException;

    ImageDO generateRecommendCode(String id, String content, String appKey) throws ImageException;

    ImageDO generateShopCode(String id, String appKey) throws ImageException;

    ImageDO generateRecommendCode(String id, String appKey) throws ImageException;

    /**
     * 生产邀请注册二维码
     * @param id 邀请人id
     * @param appKey
     * @return
     * @throws ImageException
     */
    ImageDO generateInviteRegisterQrcode (String id, String appKey) throws ImageException;

    ImageDO generateItemCode(String id, Long sellerId, Long distributorId, String bizcode, String appKey) throws ImageException;

    void batchGenerateItemCode(Long itemId, String bizCode, String appKey) throws ImageException;


    ImageDTO queryByKey(String key) throws ImageException;

    void deleteByKey(String key);

    void deleteByItemId(long itemId) throws ImageException;

}
