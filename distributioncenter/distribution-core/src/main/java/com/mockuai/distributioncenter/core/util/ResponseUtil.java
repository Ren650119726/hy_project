package com.mockuai.distributioncenter.core.util;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.constant.ResponseCode;

import java.util.List;

/**
 * Created by duke on 15/10/28.
 */
public class ResponseUtil {
    public static DistributionResponse getResponse(int code, String message) {

        DistributionResponse response = new DistributionResponse(code, message);

        return response;
    }

    public static DistributionResponse getResponse(ResponseCode retCodeEnum) {

        DistributionResponse response = new DistributionResponse(retCodeEnum);

        return response;
    }

    public static DistributionResponse getResponse(Object model) {

        DistributionResponse response = new DistributionResponse(model);

        return response;
    }

    public static DistributionResponse getResponse(List modelList, long totalCount) {

        DistributionResponse response = new DistributionResponse(modelList, totalCount);


        return response;
    }
}
