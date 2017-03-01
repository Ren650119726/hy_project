package com.mockuai.distributioncenter.core.dao;

import com.mockuai.distributioncenter.common.domain.qto.HkProtocolQTO;
import com.mockuai.distributioncenter.core.domain.HkProtocolDO;
import com.mockuai.distributioncenter.core.exception.DistributionException;

import java.util.List;

/**
 * Created by lizg on 2016/10/24.
 */
public interface HkProtocolDAO {

    List<HkProtocolDO> getByProModel (HkProtocolQTO hkProtocolQTO) throws DistributionException;

    List<HkProtocolDO> getProModelByUserId (HkProtocolQTO hkProtocolQTO) throws DistributionException;
}
