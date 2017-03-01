package com.mockuai.imagecenter.core.service.action; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

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
 * Created by 冠生 2016/6/23.
 */
@Service
public class DeleteItemAction implements   Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteItemAction.class.getName());
    @Autowired
    private ImageManager imageManager;


    @Override
    public ImageResponse execute(RequestContext context) {
        ImageRequest request = context.getRequest();
        long itemId = (long) request.getParam("itemId");
        try {


            imageManager.deleteByItemId(itemId);

        } catch (ImageException e) {

            LOGGER.error("DeletetItemAction Action failed, itemId : {}  ",
                    itemId );
            LOGGER.error("GenerateItemQrcodeAction exception",e);
            return new ImageResponse(e.getCode(), e.getMessage());
        }
        return new ImageResponse(ResponseCode.SUCCESS);
    }

    @Override
    public String getName() {
        return ActionEnum.DELETE_ITEM.getActionName();
    }
}
