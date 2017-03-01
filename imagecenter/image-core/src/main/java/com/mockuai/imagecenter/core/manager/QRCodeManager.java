package com.mockuai.imagecenter.core.manager;

import com.alibaba.fastjson.JSON;
import com.mockuai.distributioncenter.client.DistributionClient;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.domain.dto.DistShopDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.imagecenter.common.constant.ResponseCode;
import com.mockuai.imagecenter.core.api.impl.OSSClientAPI;
import com.mockuai.imagecenter.core.dao.QrcodeConfigDAO;
import com.mockuai.imagecenter.core.domain.ImageDO;
import com.mockuai.imagecenter.core.exception.ImageException;
import com.mockuai.imagecenter.core.util.OSSFileLinkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;

/**
 * Created by 冠生 on 16/5/23.
 * 生成平台二维码 比如 推荐二维码 商品二维码 店铺二维码
 */
public abstract class QRCodeManager {
    protected static final Logger log = LoggerFactory.getLogger(QRCodeManager.class);


    @Autowired
    private OSSClientAPI ossClientAPI;
    public final static  String RECOMMEND = "RECOMMEND";
    public final static String SHOP ="SHOP";
    public final static String ITEM ="ITEM";
    public final static  String INVITEREGISTER = "INVITEREGISTER";
    @Autowired
    protected DistributionClient distributionClient;
    @Autowired
    protected QrcodeConfigDAO qrcodeConfigDAO;

    /**
     * @param appKey
     * 返回oss上图片地址
     * @return
     * @throws ImageException
     */



    protected SellerDTO getSellerByUserId(Long userId ,String appKey ) throws ImageException {


        Response<SellerDTO> sellerDTOResponse =  distributionClient.getSellerByUserId(userId,appKey);
        if(!sellerDTOResponse .isSuccess()){

            log.error("occur {} error to distributionClient getSellerByUserId : {} appKey : {}, code:{}, msg:{}",
                 getClass().getSimpleName(),   userId, appKey, sellerDTOResponse.getCode(), sellerDTOResponse.getMessage());
            throw new ImageException(ResponseCode.SYS_E_SERVICE_EXCEPTION,sellerDTOResponse.getMessage());
        }
        try {
            log.info("occur {}   distributionClient  getSellerByUserId :  userId : {}, sellerDTO:{}",
                    getClass().getSimpleName(),userId, JSON.toJSON(sellerDTOResponse.getModule()));
        }catch (Exception e){
            log.error("read distributionClient getSellerByUserId ",e);
        }
        return sellerDTOResponse.getModule();
    }
    protected DistShopDTO getShopBySellerId(Long sellerId ,String appKey) throws  ImageException{
        Response<DistShopDTO>  shopDTOResponse =   distributionClient.getShopBySellerId(sellerId,appKey);
        if(!shopDTOResponse .isSuccess()){
            log.error("occur {} ,error to distributionClient, queryShop : sellerId: {} appKey : {}, code:{}, msg:{}",
                  getClass().getSimpleName(),  sellerId, appKey, shopDTOResponse.getCode(), shopDTOResponse.getMessage());
            throw new ImageException(ResponseCode.SYS_E_SERVICE_EXCEPTION,shopDTOResponse.getMessage());

          }
        try {
            log.info("occur {}   distributionClient  getShopBySellerId :  userId : {}, sellerDTO:{}",
                    getClass().getSimpleName(),sellerId, JSON.toJSON(shopDTOResponse.getModule()));
        }catch (Exception e){
            log.error("read distributionClient getSellerByUserId ",e);
        }
        return shopDTOResponse.getModule();
    }

    protected  ImageDO generateImageDO(BaseRender render,long userId,Long itemId,  String type,String content ){
        String subfix = ".jpg";
        String time = String.format("?d=%s", System.currentTimeMillis());
        int hours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        String key = String.format("%s/%s/%s/%s%s",type,render.getTime(),hours,render.getKey(),subfix);
        //上传
        log.info("上传文件：{}",key);
        ossClientAPI.uploadFile( key,render.getTmpFile(type));
        //删除图片
        render.deleteFile(type);
        ImageDO imageDO  = new ImageDO();
        imageDO.setUserId(userId);
        imageDO.setItemId(itemId);
        imageDO.setImageUrl(OSSFileLinkUtil.generateFileLink(ossClientAPI.getBucketName(),key)+time);
        imageDO.setContent(content);
        imageDO.setpKey(render.getKey());
        log.info("save imageDO:{}",JSON.toJSON(imageDO));
        return imageDO;
    }


    protected  ImageDO generateImageDO(BaseRender render,long userId, String type,String content ){

        return generateImageDO(render, userId,null, type, content);
    }


}
