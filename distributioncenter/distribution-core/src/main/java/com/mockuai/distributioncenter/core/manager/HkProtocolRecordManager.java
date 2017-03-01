package com.mockuai.distributioncenter.core.manager;

import com.mockuai.distributioncenter.common.domain.dto.HkProtocolRecordDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;

import java.util.List;

/**
 * Created by lizg on 2016/8/29.
 */
public interface HkProtocolRecordManager {

    Long add(HkProtocolRecordDTO hkProtocolRecordDTO) throws DistributionException;

    HkProtocolRecordDTO get(HkProtocolRecordDTO hkProtocolRecordDTO) throws DistributionException;

    List<HkProtocolRecordDTO> getByUserId(HkProtocolRecordDTO hkProtocolRecordDTO) throws DistributionException;

}
