package com.mockuai.imagecenter.core.service.action;


import com.mockuai.imagecenter.common.api.action.ImageRequest;
import com.mockuai.imagecenter.common.api.action.ImageResponse;
import com.mockuai.imagecenter.common.constant.ActionEnum;
import com.mockuai.imagecenter.common.domain.dto.ImageDTO;
import com.mockuai.imagecenter.core.domain.ImageDO;
import com.mockuai.imagecenter.core.exception.ImageException;
import com.mockuai.imagecenter.core.manager.ImageManager;
import com.mockuai.imagecenter.core.util.KeyGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 冠生 on 2016/5/26.
 */
@Service
public class GetQrcodeAction implements   Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetQrcodeAction.class.getName());

    @Autowired
    private ImageManager imageManager;

    private final static Object lock = new Object();

    @Override
    public ImageResponse execute(RequestContext context) {
       ImageRequest request = context.getRequest();
       String type = (String) request.getParam("type");
       // RECOMMEND userId  SHOP userId  item  sellerId
        Long id = (Long) request.getParam("id");
        Long itemId = (Long) request.getParam("itemId");
        String appKey = (String) request.getParam("appKey");
        ImageDTO imageDO = null;
        try {
            String key = "ITEM".equals(type)? (itemId+"_"+id) : id+"";
            imageDO = imageManager.queryByKey(KeyGenerator.generatorKey(type,key));
            if(imageDO != null && imageDO.getImageUrl() != null  ){
                return new ImageResponse<>(imageDO.getImageUrl());

            }

            synchronized (lock){
                imageDO = imageManager.queryByKey(KeyGenerator.generatorKey(type,key));
                if(imageDO == null){
                    ImageDO result = null;
                    switch (type){
                        case "RECOMMEND":
                          //  result =    imageManager.generateRecommendCode(id+"",appKey);
                            break;
                        case "SHOP":
                           // result =    imageManager.generateShopCode(id+"",appKey);
                            break;
                    }
                    //return new ImageResponse<>(result.getImageUrl());
                    return new ImageResponse<>("");
                }


            }

        } catch (ImageException e) {

            LOGGER.error("GetQrcodeAction  Action failed, ,type:{}, id:{},itemId:{} ",
                     type,id,itemId);
            LOGGER.error("GetQrcodeAction  Action failed ",
                    e);
            return new ImageResponse(e.getCode(), e.getMessage());
        }
        return new ImageResponse<>("");
    }

    @Override
    public String getName() {
        return ActionEnum.GET_QRCODE.getActionName();
    }
}
