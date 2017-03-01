package com.mockuai.virtualwealthcenter.core.util;

import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;

import java.util.List;

public class ResponseUtil {

    public static VirtualWealthResponse getResponse(int code, String message) {

        VirtualWealthResponse response = new VirtualWealthResponse(code, message);

        return response;
    }

    public static VirtualWealthResponse getResponse(ResponseCode retCodeEnum) {

        VirtualWealthResponse response = new VirtualWealthResponse(retCodeEnum);

        return response;
    }

    public static VirtualWealthResponse getResponse(Object model) {

        VirtualWealthResponse response = new VirtualWealthResponse(model);

        return response;
    }

    public static VirtualWealthResponse getResponse(List modelList, long totalCount) {

        VirtualWealthResponse response = new VirtualWealthResponse(modelList, totalCount);


        return response;
    }
}