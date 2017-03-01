package com.mockuai.giftscenter.mop.api.action;

import com.mockuai.giftscenter.common.api.GiftsService;
import com.mockuai.mop.common.constant.ResponseFormat;
import com.mockuai.mop.common.service.action.Action;

public abstract class BaseAction implements Action {
    private GiftsService giftsService;

    public GiftsService getGiftsService() {
        return giftsService;
    }

    public void setGiftsService(GiftsService giftsService) {
        this.giftsService = giftsService;
    }

    public ResponseFormat getResponseFormat() {
        return ResponseFormat.STANDARD;
    }
}