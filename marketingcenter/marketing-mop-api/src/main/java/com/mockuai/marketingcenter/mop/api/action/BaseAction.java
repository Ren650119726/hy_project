package com.mockuai.marketingcenter.mop.api.action;

import com.mockuai.marketingcenter.common.api.MarketingService;
import com.mockuai.mop.common.constant.ResponseFormat;
import com.mockuai.mop.common.service.action.Action;

public abstract class BaseAction implements Action {
    private MarketingService marketingService;

    public MarketingService getMarketingService() {
        return marketingService;
    }

    public void setMarketingService(MarketingService marketingService) {
        this.marketingService = marketingService;
    }

    public ResponseFormat getResponseFormat() {
        return ResponseFormat.STANDARD;
    }
}