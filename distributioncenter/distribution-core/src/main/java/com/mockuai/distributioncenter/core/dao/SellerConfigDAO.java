package com.mockuai.distributioncenter.core.dao;

import com.mockuai.distributioncenter.common.domain.qto.SellerConfigQTO;
import com.mockuai.distributioncenter.core.domain.SellerConfigDO;

import java.util.List;

/**
 * Created by yeliming on 16/5/18.
 */
public interface SellerConfigDAO {
    Long addSellerConfig(SellerConfigDO sellerConfigDO);

    SellerConfigDO getSellerConfig(Long id);

    List<SellerConfigDO> querySellerConfig(SellerConfigQTO sellerConfigQTO);

    int updateSellerConfig(SellerConfigDO sellerConfigDO);
}
