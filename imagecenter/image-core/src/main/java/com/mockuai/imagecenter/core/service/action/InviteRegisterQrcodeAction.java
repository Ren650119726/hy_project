package com.mockuai.imagecenter.core.service.action;

import com.mockuai.imagecenter.common.api.action.ImageRequest;
import com.mockuai.imagecenter.common.api.action.ImageResponse;
import com.mockuai.imagecenter.common.constant.ActionEnum;
import com.mockuai.imagecenter.common.domain.dto.ImageDTO;
import com.mockuai.imagecenter.core.domain.ImageDO;
import com.mockuai.imagecenter.core.exception.ImageException;
import com.mockuai.imagecenter.core.manager.ImageManager;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by lizg on 2016/11/30.
 */

@Service
public class InviteRegisterQrcodeAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(InviteRegisterQrcodeAction.class.getName());

    @Autowired
    private ImageManager imageManager;

    @Override
    public ImageResponse execute(RequestContext context) throws ImageException {
        ImageRequest request = context.getRequest();
        Long userId = (Long) request.getParam("shareUserId");
        String appKey = (String) request.getParam("appKey");

         ImageDTO imageDTO;
        try {

            ImageDO   imageDO = imageManager.generateInviteRegisterQrcode(userId + "", appKey);

            imageDTO = new ImageDTO();
            imageDTO.setImageUrl(imageDO.getImageUrl());
            imageDTO.setContent(imageDO.getContent());
            imageDTO.setUserId(imageDO.getUserId());


        } catch (ImageException e) {
            logger.error("generateInviteRegisterQrcode Action failed, , userId : {}", userId);
            logger.error("generateInviteRegisterQrcode failed", e);
            return new ImageResponse(e.getCode(), e.getMessage());
        }
        return new ImageResponse(imageDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.INVITE_REGISTER_QRCODE.getActionName();
    }
}
