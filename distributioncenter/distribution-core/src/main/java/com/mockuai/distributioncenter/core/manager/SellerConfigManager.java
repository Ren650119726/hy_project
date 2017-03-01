package com.mockuai.distributioncenter.core.manager;

import com.mockuai.distributioncenter.common.domain.dto.SellerConfigDTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerConfigQTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;

import java.util.List;

/**
 * Created by yeliming on 16/5/18.
 */
public interface SellerConfigManager {
    Long addSellerConfig(SellerConfigDTO sellerConfigDTO);

    SellerConfigDTO getSellerConfig(Long id);

    List<SellerConfigDTO> querySellerConfig(SellerConfigQTO sellerConfigQTO);

    Boolean updateSellerConfig(SellerConfigDTO sellerConfigDTO) throws DistributionException;
}
