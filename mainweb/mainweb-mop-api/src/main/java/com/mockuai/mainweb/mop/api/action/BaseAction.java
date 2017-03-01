package com.mockuai.mainweb.mop.api.action;

import com.mockuai.mainweb.common.api.MainWebService;
import com.mockuai.mop.common.constant.ResponseFormat;
import com.mockuai.mop.common.service.action.Action;

public abstract class BaseAction implements Action {
    private MainWebService MainWebService;

    public MainWebService getMainWebService() {
        return MainWebService;
    }

    public void setMainWebService(MainWebService MainWebService) {
        this.MainWebService = MainWebService;
    }

    public ResponseFormat getResponseFormat() {
        return ResponseFormat.STANDARD;
    }
}