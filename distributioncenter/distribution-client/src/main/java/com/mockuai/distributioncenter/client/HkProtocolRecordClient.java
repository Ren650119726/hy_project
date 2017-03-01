package com.mockuai.distributioncenter.client;

import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.domain.dto.HkProtocolRecordDTO;

/**
 * Created by lizg on 2016/8/29.
 */
public interface HkProtocolRecordClient {

   
    Response<Boolean> addHkProtocolRecord(HkProtocolRecordDTO hkProtocolRecordDTO, String appKey);

    
    Response<HkProtocolRecordDTO> getHkProtocolRecord(HkProtocolRecordDTO hkProtocolRecordDTO,String appKey);
}
