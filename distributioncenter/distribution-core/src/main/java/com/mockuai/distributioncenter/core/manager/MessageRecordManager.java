package com.mockuai.distributioncenter.core.manager;

import com.mockuai.distributioncenter.common.domain.dto.MessageRecordDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;

/**
 * Created by duke on 16/2/19.
 */
public interface MessageRecordManager {
    /**
     * 添加消息记录
     * @param messageRecordDTO
     * */
    Long addRecord(MessageRecordDTO messageRecordDTO) throws DistributionException;

    /**
     * 查询消息记录
     * @param identify
     * */
    MessageRecordDTO getRecordByIdentifyAndBizType(String identify, Integer bizType) throws DistributionException;
}
