package com.mockuai.giftscenter.core.util;

import com.mockuai.giftscenter.common.api.GiftsResponse;
import com.mockuai.giftscenter.common.constant.ResponseCode;

import java.util.List;

public class ResponseUtil {

    public static GiftsResponse getResponse(int code, String message) {

        GiftsResponse response = new GiftsResponse(code, message);

        return response;
    }

    public static GiftsResponse getResponse(ResponseCode retCodeEnum) {

        GiftsResponse response = new GiftsResponse(retCodeEnum);

        return response;
    }

    public static GiftsResponse getResponse(Object model) {

        GiftsResponse response = new GiftsResponse(model);

        return response;
    }

    public static GiftsResponse getResponse(List modelList, long totalCount) {

        GiftsResponse response = new GiftsResponse(modelList, totalCount);


        return response;
    }
}