package com.mockuai.distributioncenter.core.dao;

import com.mockuai.distributioncenter.core.domain.MessageRecordDO;

/**
 * Created by duke on 16/2/19.
 */
public interface MessageRecordDAO {
    /**
     * 添加消息记录
     * @param messageRecordDO
     * */
    Long addRecord(MessageRecordDO messageRecordDO);

    /**
     * 查询消息记录
     * @param identify
     * */
    MessageRecordDO getRecordByIdentifyAndBizType(String identify, Integer bizType);
}
