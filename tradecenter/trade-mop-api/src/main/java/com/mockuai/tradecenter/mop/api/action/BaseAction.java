package com.mockuai.tradecenter.mop.api.action;


import com.mockuai.mop.common.constant.ResponseFormat;
import com.mockuai.mop.common.service.action.Action;
import com.mockuai.tradecenter.common.api.TradeService;


public abstract class BaseAction
        implements Action {
    private TradeService tradeService;

    public void setTradeService(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    public TradeService getTradeService() {
        return this.tradeService;
    }

    public ResponseFormat getResponseFormat() {
        return ResponseFormat.STANDARD;
    }
}

/* Location:           /work/tmp/trade-mop-api-0.0.1-20150519.033139-22.jar
 * Qualified Name:     com.mockuai.tradecenter.mop.api.action.BaseAction
 * JD-Core Version:    0.6.2
 */