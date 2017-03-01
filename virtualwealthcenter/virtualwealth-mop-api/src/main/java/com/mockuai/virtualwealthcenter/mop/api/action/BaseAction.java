package com.mockuai.virtualwealthcenter.mop.api.action;

import com.mockuai.mop.common.constant.ResponseFormat;
import com.mockuai.mop.common.service.action.Action;
import com.mockuai.virtualwealthcenter.common.api.VirtualWealthService;

public abstract class BaseAction implements Action {
    private VirtualWealthService virtualWealthService;

    public VirtualWealthService getVirtualWealthService() {
        return virtualWealthService;
    }

    public void setVirtualWealthService(VirtualWealthService virtualWealthService) {
        this.virtualWealthService = virtualWealthService;
    }

    public ResponseFormat getResponseFormat() {
        return ResponseFormat.STANDARD;
    }
}