package com.mockuai.distributioncenter.client;

import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.domain.dto.SellerConfigDTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerConfigQTO;

import java.util.List;

/**
 * Created by yeliming on 16/5/18.
 */
public interface SellerConfigClient {
    /**
     * 添加卖家配置
     */
    Response<Long> addSellerConfig(SellerConfigDTO sellerConfigDTO, String appKey);

    /**
     * 修改卖家配置
     */
    Response<Boolean> updateSellerConfig(List<SellerConfigDTO> sellerConfigDTOs, String appKey);

    /**
     * 查询卖家配置
     */
    Response<List<SellerConfigDTO>> querySellerConfig(SellerConfigQTO sellerConfigQTO, String appKey);

    /**
     * 获取卖家配置
     */
    Response<SellerConfigDTO> getSellerConfig(Long id, String appKey);

}
