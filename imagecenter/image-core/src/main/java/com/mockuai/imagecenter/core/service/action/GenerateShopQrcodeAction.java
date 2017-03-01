package com.mockuai.imagecenter.core.service.action;


import com.mockuai.imagecenter.common.api.action.ImageRequest;
import com.mockuai.imagecenter.common.api.action.ImageResponse;
import com.mockuai.imagecenter.common.constant.ActionEnum;
import com.mockuai.imagecenter.common.constant.ResponseCode;
import com.mockuai.imagecenter.core.manager.ImageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 冠生 on 2016/5/26.
 */
@Service
public class GenerateShopQrcodeAction implements   Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenerateShopQrcodeAction.class.getName());

    @Autowired
    private ImageManager imageManager;
    @Override
    public ImageResponse execute(RequestContext context) {
       ImageRequest request = context.getRequest();
       long userId =  (Long) request.getParam("userId");
       String url =  (String) request.getParam("url");
       String appKey =  (String) request.getParam("appKey");
     /*   try {
            //fixed 上传头像事件， 店铺地址传入成用户头像bug
            if(url != null && url.endsWith(".jpg")){
               url = null;
               LOGGER.info("更新用户头像 ，重新生成店铺二维码 jpg userId:{}",userId);
            }
            if(url != null && url.endsWith(".png")){
                url = null;
                LOGGER.info("更新用户头像 ，重新生成店铺二维码 png userId:{}",userId);
            }
            imageManager.generateShopCode(userId+"",url,appKey);

        } catch (ImageException e) {

            LOGGER.error("GenerateShopQrcodeAction Action failed  , userId : {} ,url:{} ",  userId ,url);
            LOGGER.error("GenerateShopQrcodeAction failed",e);

            return new ImageResponse(e.getCode(), e.getMessage());

        }*/

        return new ImageResponse(ResponseCode.SUCCESS);
    }

    @Override
    public String getName() {
        return ActionEnum.GENERATE_SHOP_QRCODE.getActionName();
    }
}
