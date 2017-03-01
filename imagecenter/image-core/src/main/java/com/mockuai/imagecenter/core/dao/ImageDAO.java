package com.mockuai.imagecenter.core.dao; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.mockuai.imagecenter.common.domain.dto.ImageDTO;
import com.mockuai.imagecenter.core.domain.ImageDO;

import java.sql.SQLException;

/**
 * Created by hy on 2016/5/24.
 */
public interface ImageDAO  {


    void add(ImageDO imageDO);

    ImageDTO queryByKey(String key) throws SQLException;

     void deleteByKey(String key);

    void updateByKey(ImageDO imageDO) throws SQLException;

    /**
     * 更新店铺二维码时候  同时将对应的商品二维码删除
     * @param userId
     */
    void deleteItemCodeByUserId(long userId) throws SQLException;

    /**
     * 更新店铺二维码时候  同时将对应的商品二维码删除
     * @param itemId
     */
    void deleteItemCodeByItemId(long itemId) throws SQLException;


    }
