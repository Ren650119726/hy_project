package com.mockuai.distributioncenter.core.manager;

import com.mockuai.distributioncenter.common.domain.dto.HkProtocolDTO;
import com.mockuai.distributioncenter.common.domain.qto.HkProtocolQTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;

import java.util.List;

/**
 * Created by lizg on 2016/10/24.
 */
public interface HkProtocolManager {

    List<HkProtocolDTO> getByProModel(HkProtocolQTO hkProtocolQTO) throws DistributionException;

    List<HkProtocolDTO> getProModelByUserId (HkProtocolQTO hkProtocolQTO) throws DistributionException;
}
