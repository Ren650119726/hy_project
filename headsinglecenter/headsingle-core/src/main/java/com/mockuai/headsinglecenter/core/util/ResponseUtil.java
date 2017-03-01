package com.mockuai.headsinglecenter.core.util;

import java.util.List;

import com.mockuai.headsinglecenter.common.api.HeadSingleResponse;
import com.mockuai.headsinglecenter.common.constant.ResponseCode;

public class ResponseUtil {

    public static HeadSingleResponse getResponse(int code, String message) {

    	HeadSingleResponse response = new HeadSingleResponse(code, message);

        return response;
    }

    public static HeadSingleResponse getResponse(ResponseCode retCodeEnum) {

    	HeadSingleResponse response = new HeadSingleResponse(retCodeEnum);

        return response;
    }

    public static HeadSingleResponse getResponse(Object model) {

    	HeadSingleResponse response = new HeadSingleResponse(model);

        return response;
    }

    public static HeadSingleResponse getResponse(List modelList, long totalCount) {

    	HeadSingleResponse response = new HeadSingleResponse(modelList, totalCount);


        return response;
    }
}