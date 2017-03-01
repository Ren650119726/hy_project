package com.mockuai.distributioncenter.client;

import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.domain.dto.SellerUpgradeDTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerUpgradeQTO;

import java.util.List;

/**
 * Created by yeliming on 16/5/18.
 */
public interface SellerUpgradeClient {

    /**
     * 查询卖家升级申请列表
     */
    Response<List<SellerUpgradeDTO>> querySellerUpgrade(SellerUpgradeQTO sellerUpgradeQTO, String appKey);

    /**
     * 同意卖家升级申请
     */
    Response<Void> agreeSellerUpgrade(Long id, String reason, String appKey);

    /**
     * 拒绝卖家升级申请
     */
    Response<Void> rejectSellerUpgrade(Long id, String reason, String appKey);
}
