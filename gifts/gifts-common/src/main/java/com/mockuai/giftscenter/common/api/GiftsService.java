package com.mockuai.giftscenter.common.api;

public interface GiftsService<T> {
    Response<T> execute(Request request);
}