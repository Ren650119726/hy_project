package com.mockuai.imagecenter.core.service.action;


import com.mockuai.imagecenter.common.api.action.ImageRequest;
import com.mockuai.imagecenter.common.api.action.ImageResponse;
import com.mockuai.imagecenter.common.constant.ActionEnum;
import com.mockuai.imagecenter.common.constant.ResponseCode;
import com.mockuai.imagecenter.core.exception.ImageException;
import com.mockuai.imagecenter.core.manager.ImageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 冠生 on 2016/5/26.
 * 批量生成
 */
@Service
public class BatchGenerateItemQrcodeAction implements   Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(BatchGenerateItemQrcodeAction.class.getName());

    @Autowired
    private ImageManager imageManager;

    private final static String bizCode = "hanshu";

    @Override
    public ImageResponse execute(RequestContext context) {
       ImageRequest request = context.getRequest();
       long itemId =  (Long) request.getParam("itemId");
       String appKey =  (String) request.getParam("appKey");

        try {
            imageManager.batchGenerateItemCode(itemId,bizCode,appKey);

        } catch (ImageException e) {
            LOGGER.error("BatchGenerateItemQrcodeAction Action failed, itemId : {} ,appKey:{} ",
                     itemId , appKey);
            LOGGER.error("BatchGenerateItemQrcodeAction failed",e);
            return new ImageResponse(e.getCode(), e.getMessage());
        }

        return new ImageResponse(ResponseCode.SUCCESS);
    }

    @Override
    public String getName() {
        return ActionEnum.GENERATE_ITEM_QRCODE.getActionName();
    }
}
