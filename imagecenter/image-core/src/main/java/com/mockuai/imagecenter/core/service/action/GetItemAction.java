package com.mockuai.imagecenter.core.service.action;


import com.mockuai.imagecenter.common.api.action.ImageRequest;
import com.mockuai.imagecenter.common.api.action.ImageResponse;
import com.mockuai.imagecenter.common.constant.ActionEnum;
import com.mockuai.imagecenter.common.constant.ResponseCode;
import com.mockuai.imagecenter.common.domain.dto.ImageDTO;
import com.mockuai.imagecenter.core.domain.ImageDO;
import com.mockuai.imagecenter.core.exception.ImageException;
import com.mockuai.imagecenter.core.manager.CacheManager;
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
public class GetItemAction implements   Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetItemAction.class.getName());

    @Autowired
    private ImageManager imageManager;

    @Autowired
    private CacheManager cacheManager;


    //观察日志发现整个流程大概2秒就能完成， 加一秒 3秒搞定
    private final static int expired = 3  ;


    private final static Object lock = new Object();

    private final   String bizCode = "hanshu";

    @Override
    public ImageResponse execute(RequestContext context) {
       ImageRequest request = context.getRequest();
       long itemId =  (Long) request.getParam("itemId");
       long sellerId =  (Long) request.getParam("sellerId");
       //long distributorId =(Long) request.getParam("distributorId");

        long userId =(Long) request.getParam("userId");
        String appKey =  (String) request.getParam("appKey");
        String key = itemId+"_"+userId;
        key = KeyGenerator.generatorKey("ITEM",key);
        try {
            ImageDTO imageDO = imageManager.queryByKey(key);
            if(imageDO != null){
                return new ImageResponse<>(imageDO.getImageUrl());
            }
            //使用 memecache 保存状态 确保只有一个服务器在生成图片
            try {
                Object cacheKey =  cacheManager.get(key);
                if(cacheKey == null){
                    cacheManager.set(key, expired,key);
                }else{
                    LOGGER.info("正在生成key:{}二维码",key);
                    return  new ImageResponse(ResponseCode.MULTIPLE_GEN_REQUEST);
                }
            }catch (Exception e){
                LOGGER.error("读取memcached",e);
            }

            synchronized (lock){
                 imageDO = imageManager.queryByKey(key);
                if(imageDO != null){
                    return new ImageResponse<>(imageDO.getImageUrl());
                }

                ImageDO imageDO1 =  imageManager.generateItemCode(itemId+"",sellerId,userId,bizCode,appKey);
                try {
                    cacheManager.remove(key);
                }catch (Exception e){
                   LOGGER.error("",e);
                }
                return new ImageResponse<>(imageDO1.getImageUrl());
            }
        } catch (ImageException e) {
            LOGGER.error("GenerateItemQrcodeAction Action failed, itemId : {} , sellerId:{},userId:{} ",
                    itemId ,sellerId ,userId);
            LOGGER.error("GenerateItemQrcodeAction failed",e);
            return new ImageResponse(e.getCode(), e.getMessage());
        }
    }

    @Override
    public String getName() {
        return ActionEnum.GET_ITEM.getActionName();
    }



}
