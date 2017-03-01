package com.mockuai.imagecenter.core.manager.impl;

import com.alibaba.fastjson.JSON;
import com.mockuai.imagecenter.common.constant.ResponseCode;
import com.mockuai.imagecenter.common.domain.dto.ImageDTO;
import com.mockuai.imagecenter.core.dao.ImageDAO;
import com.mockuai.imagecenter.core.domain.ImageDO;
import com.mockuai.imagecenter.core.exception.ImageException;
import com.mockuai.imagecenter.core.manager.ImageManager;
import com.mockuai.imagecenter.core.manager.QrCodeImageGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * Created by 冠生 on 16/5/23.
 */

@Service
public class ImageManagerImpl implements ImageManager {

    @Autowired
    private ImageDAO imageDAO;

    @Autowired
    private RecommendQrCodeManagerImpl recommendQrCodeManager;

    @Autowired
    private ShopQRCodeManagerImpl shopQRCodeManager;

    @Autowired
    @Qualifier("itemQRCodeManagerImpl")
    private ItemQRCodeManagerImpl itemQRCodeManager;

    @Autowired
    private InviteRegisterQrcodeManageImpl inviteRegisterQrcodeManage;

    private static final Logger log = LoggerFactory.getLogger(ImageManager.class);


    private ImageDO addImage(QrCodeImageGenerator qrCodeManager, String id, String content, String appKey) throws ImageException {
        try {

            ImageDO imageDO = qrCodeManager.genQRCode(id, content, appKey);
            updateImage(imageDO);
            return imageDO;
        } catch (Exception e) {
            log.error(ResponseCode.SYS_E_DB_INSERT.getComment(), e);
            throw new ImageException(ResponseCode.SYS_E_DB_INSERT, e);
        }
    }

    private ImageDO addImage(QrCodeImageGenerator qrCodeManager, String id, String appKey) throws ImageException {
        try {

            ImageDO imageDO = qrCodeManager.genQRCode(id, appKey);
            updateImage(imageDO);
            return imageDO;
        } catch (Exception e) {
            log.error(ResponseCode.SYS_E_DB_INSERT.getComment(), e);
            throw new ImageException(ResponseCode.SYS_E_DB_INSERT, e);
        }
    }


    private void deleteItemByUserId(String userId) throws ImageException {
        try {
            log.info("更新店铺二维码，删除店铺下所有的商品二维码 userId:{} ", userId);

            imageDAO.deleteItemCodeByUserId(Long.parseLong(userId));
        } catch (SQLException e) {
            log.error("deleteItemCodeByUserId error", e);
            throw new ImageException(ResponseCode.GEN_IMAGE_ERROR.getCode(), e.getMessage());
        }
    }


    @Override
    public ImageDO generateShopCode(String id, String content, String appKey) throws ImageException {
        deleteItemByUserId(id);
        return addImage(shopQRCodeManager, id, content, appKey);
    }

    @Override
    public ImageDO generateRecommendCode(String id, String content, String appKey) throws ImageException {
        return addImage(recommendQrCodeManager, id, content, appKey);
    }

    @Override
    public ImageDO generateShopCode(String id, String appKey) throws ImageException {
        deleteItemByUserId(id);
        return addImage(shopQRCodeManager, id, appKey);
    }

    @Override
    public ImageDO generateRecommendCode(String id, String appKey) throws ImageException {
        return addImage(recommendQrCodeManager, id, appKey);

    }

    @Override
    public ImageDO generateInviteRegisterQrcode(String id, String appKey) throws ImageException {
        return addImage(inviteRegisterQrcodeManage,id,appKey);
    }

    @Override
    public ImageDO generateItemCode(String id, Long sellerId, Long distributorId, String bizcode, String appKey) throws ImageException {
        ImageDO imageDO = itemQRCodeManager.genQRCode(id, sellerId + "", distributorId, bizcode, appKey);
        updateImage(imageDO);
        return imageDO;
    }


    private void updateImage(ImageDO imageDO) throws ImageException {
        ImageDTO imageDTO = null;
        try {
            imageDTO = imageDAO.queryByKey(imageDO.getpKey());
            if (imageDTO == null || imageDTO.getImageUrl() == null) {
                imageDO.setVersion(1);
                imageDAO.add(imageDO);
            } else {
                Integer version = imageDO.getVersion();
                if (version == null) {
                    version = 1;
                }
                imageDO.setVersion(version + 1);
                imageDAO.updateByKey(imageDO);
            }
        } catch (SQLException e) {
            log.error(ResponseCode.SYS_E_DB_INSERT.getComment(), e);
            throw new ImageException(ResponseCode.SYS_E_DB_INSERT, e);
        }
    }

    @Override
    public void batchGenerateItemCode(Long itemId, String bizCode, String appKey) throws ImageException {
        itemQRCodeManager.genQRCode(itemId, bizCode, appKey);
    }

    @Override
    public ImageDTO queryByKey(String key) throws ImageException {
        try {
            ImageDTO imageDTO = imageDAO.queryByKey(key);
            if (imageDTO != null) {
                log.info("imageDTO  :{}", JSON.toJSON(imageDTO));
            }
            return imageDTO;
        } catch (SQLException e) {
            log.error(ResponseCode.SYS_E_DB_QUERY.getComment(), e);
            throw new ImageException(ResponseCode.SYS_E_DB_QUERY, e);

        }
    }

    @Override
    public void deleteByKey(String key) {
        imageDAO.deleteByKey(key);

    }

    @Override
    public void deleteByItemId(long itemId) throws ImageException {

        try {
            log.info("删除商铺二维码， itemId:{} ", itemId);

            imageDAO.deleteItemCodeByItemId(itemId);
        } catch (SQLException e) {
            log.error("deleteItemCodeByUserId error", e);
            throw new ImageException(ResponseCode.SYS_E_DB_DELETE.getCode(), e.getMessage());
        }
    }


}
