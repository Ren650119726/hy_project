package com.mockuai.imagecenter.core.manager.impl;

import com.alibaba.fastjson.JSON;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.imagecenter.common.constant.ResponseCode;
import com.mockuai.imagecenter.core.domain.BizType;
import com.mockuai.imagecenter.core.domain.ImageDO;
import com.mockuai.imagecenter.core.domain.QrcodeConfigDO;
import com.mockuai.imagecenter.core.domain.QrcodeConfigDetailDO;
import com.mockuai.imagecenter.core.exception.ImageException;
import com.mockuai.imagecenter.core.manager.QRCodeManager;
import com.mockuai.imagecenter.core.manager.QrCodeImageGenerator;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by 冠生 on 16/5/23.
 * 生成推荐二维码
 */
public class RecommendQrCodeManagerImpl extends QRCodeManager implements QrCodeImageGenerator {

    private String defaultRecommendUrl ;


    public void setDefaultRecommendUrl(String defaultRecommendUrl) {
        this.defaultRecommendUrl = defaultRecommendUrl;
    }


    @Override
    public ImageDO genQRCode(String id, String appKey) throws ImageException {

       return  genQRCode(id,null,appKey);
    }

    @Override
    public ImageDO genQRCode(String id,String content, String appKey) throws ImageException {
        Long userId =  Long.parseLong(id);
        SellerDTO sellerDTO = getSellerByUserId(userId,appKey);
        String url = content;
        String realName =  sellerDTO.getRealName();
        //未填写url
        if(url == null){
            url = defaultRecommendUrl.replace("%sellerId%",sellerDTO.getId()+"");
            try {
                url = url.replace("%realName%", URLEncoder.encode( realName,"UTF-8"));
            } catch (UnsupportedEncodingException e) {
                log.error("encode realname",e);
            }
        }
        String tpl = "我是创业者%s,我为嗨云代言";
        String target = sellerDTO.getRealName().length() > 5  ? realName.substring(0,5):realName;
        realName =String.format( tpl,target);
        try {
         QrcodeConfigDO config = qrcodeConfigDAO.queryByBizType(BizType.RECOMMEND.name());
         List<QrcodeConfigDetailDO> qrcodeConfigDetailDOList =
                 JSON.parseArray(config.getConfigInfo(), QrcodeConfigDetailDO.class);
         RecommendRender recommendRender  = new RecommendRender(id,qrcodeConfigDetailDOList,url,config.getBgImage());
         recommendRender.renderRecommend(realName);


         return   generateImageDO(recommendRender,userId,RECOMMEND,url);

        } catch (SQLException e) {
            log.error(ResponseCode.SYS_E_DB_INSERT.getComment(),e);
            throw  new ImageException(ResponseCode.SYS_E_DB_INSERT,e);
        } catch (IOException e) {
            log.error(ResponseCode.GEN_IMAGE_ERROR.getComment(),e);
            throw  new ImageException(ResponseCode.GEN_IMAGE_ERROR,e);
        }
    }



}
