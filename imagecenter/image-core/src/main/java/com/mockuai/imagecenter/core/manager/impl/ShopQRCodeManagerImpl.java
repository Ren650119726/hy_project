package com.mockuai.imagecenter.core.manager.impl;

import com.alibaba.fastjson.JSON;
import com.mockuai.distributioncenter.common.domain.dto.DistShopDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.imagecenter.common.constant.ResponseCode;
import com.mockuai.imagecenter.core.domain.BizType;
import com.mockuai.imagecenter.core.domain.ImageDO;
import com.mockuai.imagecenter.core.domain.QrcodeConfigDO;
import com.mockuai.imagecenter.core.domain.QrcodeConfigDetailDO;
import com.mockuai.imagecenter.core.exception.ImageException;
import com.mockuai.imagecenter.core.manager.QRCodeManager;
import com.mockuai.imagecenter.core.manager.QrCodeImageGenerator;
import com.mockuai.usercenter.client.UserClient;
import com.mockuai.usercenter.common.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by 冠生 on 16/5/23.
 * 生成店铺二维码
 */
public class ShopQRCodeManagerImpl extends QRCodeManager implements QrCodeImageGenerator {
    private static final Logger log = LoggerFactory.getLogger(ShopQRCodeManagerImpl.class);

    private String defaultShopUrl ;

    public void setDefaultShopUrl(String defaultShopUrl) {
        this.defaultShopUrl = defaultShopUrl;
    }

    @Autowired
    private UserClient userClient;

    @Override
    public ImageDO genQRCode(String id, String appKey) throws ImageException {
        log.info("二维码模块生成店铺二维码");
        return genQRCode(id,null,appKey);
    }

    @Override
    public ImageDO genQRCode(String id,String content, String appKey) throws ImageException {
        long userId = Long.parseLong(id);
        String shopName,imgUrl ;
        String url = content;
        SellerDTO sellerDTO = getSellerByUserId(userId,appKey);

        if(url == null){
            url = defaultShopUrl.replace("%sellerId%",sellerDTO.getId()+"");
        }
        DistShopDTO shopDTO = getShopBySellerId(sellerDTO.getId(),appKey);
        shopName =  shopDTO.getShopName();
        com.mockuai.usercenter.common.api.Response<UserDTO> userDTOResponse = userClient.getUserById(userId,appKey);
         if(!userDTOResponse.isSuccess()){
             log.error("error to userClient, getUserById : {} appKey : {}, code:{}, msg:{}",
                     id, appKey, userDTOResponse.getCode(), userDTOResponse.getMessage());
             throw new ImageException(ResponseCode.SYS_E_SERVICE_EXCEPTION.getComment());
         }
        if(userDTOResponse.getModule() == null
             || userDTOResponse.getModule().getImgUrl() == null
         ){
            imgUrl = null;
        }else{
            imgUrl = userDTOResponse.getModule().getImgUrl();
        }
        log.info("userDTOResponse:{}",JSON.toJSON(userDTOResponse));
        QrcodeConfigDO config = null;
        try {
            config = qrcodeConfigDAO.queryByBizType(BizType.SHOP.name());
            List<QrcodeConfigDetailDO> qrcodeConfigDetailDOList =
                    JSON.parseArray(config.getConfigInfo(), QrcodeConfigDetailDO.class);
            ShopRender shopRender = new ShopRender(userId+"",qrcodeConfigDetailDOList,url,config.getBgImage());
            shopRender.render(imgUrl,shopName);
            return generateImageDO(shopRender,userId,SHOP,url);
        } catch (SQLException e) {
            log.error(ResponseCode.SYS_E_DB_INSERT.getComment(),e);
            throw  new ImageException(ResponseCode.SYS_E_DB_INSERT,e);
        } catch (IOException e) {
            log.error(ResponseCode.GEN_IMAGE_ERROR.getComment(),e);
            throw  new ImageException(ResponseCode.GEN_IMAGE_ERROR,e);
        }
        //distShopDTO.getShopName();

    }



}
