package com.mockuai.imagecenter.mop.api.action;

import com.mockuai.imagecenter.common.api.ImageService;
import com.mockuai.mop.common.constant.ResponseFormat;
import com.mockuai.mop.common.service.action.Action;

public abstract class BaseAction implements Action {
    private ImageService imageService;

    public ImageService getImageService() {
        return imageService;
    }

    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }

    public ResponseFormat getResponseFormat() {
        return ResponseFormat.STANDARD;
    }
}