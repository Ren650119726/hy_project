package com.mockuai.imagecenter.core.service.action;


import com.mockuai.imagecenter.common.api.action.ImageRequest;
import com.mockuai.imagecenter.common.api.action.ImageResponse;
import com.mockuai.imagecenter.common.constant.ActionEnum;
import com.mockuai.imagecenter.common.constant.ResponseCode;
import com.mockuai.imagecenter.common.domain.dto.ImageDTO;
import com.mockuai.imagecenter.core.exception.ImageException;
import com.mockuai.imagecenter.core.manager.ImageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 冠生 on 2016/5/26.
 */
@Service
public class GenerateItemQrcodeAction implements   Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenerateItemQrcodeAction.class.getName());

    @Autowired
    private ImageManager imageManager;

    @Override
    public ImageResponse execute(RequestContext context) {
       ImageRequest request = context.getRequest();
       long itemId =  (Long) request.getParam("itemId");
       long sellerId =  (Long) request.getParam("id");
       long distributorId =(Long) request.getParam("distributorId");
       String url =  (String) request.getParam("url");
        String appKey =  (String) request.getParam("appKey");

        LOGGER.info("execute  GenerateItemQrcodeAction  itemId : {} , sellerId:{},distributorId:{},url:{} ",
                itemId ,sellerId ,distributorId,url);
        ImageDTO imageDO = null;
        try {


            imageManager.generateItemCode(itemId+"",sellerId,distributorId,url,appKey);
        } catch (ImageException e) {

            LOGGER.error("GenerateItemQrcodeAction Action failed, itemId : {} , sellerId:{},distributorId:{},url:{} ",
                     itemId ,sellerId ,distributorId,url);
            LOGGER.error("GenerateItemQrcodeAction failed",e);
            return new ImageResponse(e.getCode(), e.getMessage());

        }

        return new ImageResponse(ResponseCode.SUCCESS);
    }

    @Override
    public String getName() {
        return ActionEnum.BATCH_GENAERATE_ITEM_QRCODE.getActionName();
    }
}
