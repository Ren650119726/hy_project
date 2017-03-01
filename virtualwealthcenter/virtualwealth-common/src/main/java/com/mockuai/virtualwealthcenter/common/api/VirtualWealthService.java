package com.mockuai.virtualwealthcenter.common.api;

public abstract interface VirtualWealthService<T> {

    public abstract Response<T> execute(Request request);
}