package com.mockuai.marketingcenter.common.api;

public abstract interface MarketingService<T> {
    
    public abstract Response<T> execute(Request request);
}