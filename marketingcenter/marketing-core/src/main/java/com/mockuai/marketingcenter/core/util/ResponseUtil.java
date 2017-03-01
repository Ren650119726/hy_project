package com.mockuai.marketingcenter.core.util;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ResponseCode;

import java.util.List;

public class ResponseUtil {

    public static MarketingResponse getResponse(int code, String message) {

        MarketingResponse response = new MarketingResponse(code, message);

        return response;
    }

    public static MarketingResponse getResponse(ResponseCode retCodeEnum) {

        MarketingResponse response = new MarketingResponse(retCodeEnum);

        return response;
    }

    public static MarketingResponse getResponse(Object model) {

        MarketingResponse response = new MarketingResponse(model);

        return response;
    }

    public static MarketingResponse getResponse(List modelList, long totalCount) {

        MarketingResponse response = new MarketingResponse(modelList, totalCount);


        return response;
    }
}