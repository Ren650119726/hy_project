package com.mockuai.headsinglecenter.common.api;

public interface HeadSingleService<T> {
    Response<T> execute(Request request);
}