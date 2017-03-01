package com.mockuai.rainbowcenter.mop.api.action;


import com.mockuai.mop.common.constant.ResponseFormat;
import com.mockuai.mop.common.service.action.Action;
import com.mockuai.rainbowcenter.common.api.RainbowService;

/**
 * Created by lizg on 16/7/16.
 */
public abstract class BaseAction implements Action {

    private RainbowService rainbowService;

    public RainbowService getRainbowService() {
        return rainbowService;
    }

    public void setRainbowService(RainbowService rainbowService) {
        this.rainbowService = rainbowService;
    }

    public ResponseFormat getResponseFormat() {
        return ResponseFormat.STANDARD;
    }
}
