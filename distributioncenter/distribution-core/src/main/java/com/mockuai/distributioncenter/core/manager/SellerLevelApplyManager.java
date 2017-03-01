package com.mockuai.distributioncenter.core.manager;

import java.util.List;

import com.mockuai.distributioncenter.common.domain.dto.SellerConfigDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerLevelApplyDTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerLevelApplyQTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;

/**
 * Created by wgl on 16/7/18.
 */
public interface SellerLevelApplyManager {
    Long addSellerLevelApply(SellerLevelApplyDTO sellerLevelApplyDTO) throws DistributionException;

    SellerLevelApplyDTO getSellerLevelApply(SellerLevelApplyDTO sellerLevelApplyDTO) throws DistributionException;

    List<SellerLevelApplyDTO> querySellerLevelApply(SellerLevelApplyQTO sellerLevelApplyQTO) throws DistributionException;

    Boolean updateSellerConfig(SellerLevelApplyDTO sellerLevelApplyDTO) throws DistributionException;
}
