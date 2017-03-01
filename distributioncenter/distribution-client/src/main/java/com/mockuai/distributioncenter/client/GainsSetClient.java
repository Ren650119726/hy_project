package com.mockuai.distributioncenter.client;

import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.domain.dto.GainsSetDTO;

/**
 * Created by lizg on 2016/8/29.
 */
public interface GainsSetClient {

    /**
     * 添加收益设置
     */
    Response<GainsSetDTO> addGainsSet(GainsSetDTO gainsSetDTO, String appKey);

    /**
     * 获取收益设置
     * @param appKey
     * @return
     */
    Response<GainsSetDTO> getGainsSet(String appKey);
}
