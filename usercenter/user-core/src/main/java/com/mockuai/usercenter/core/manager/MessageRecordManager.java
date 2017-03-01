package com.mockuai.usercenter.core.manager;

import com.mockuai.usercenter.common.dto.MessageRecordDTO;
import com.mockuai.usercenter.core.domain.MessageRecordDO;
import com.mockuai.usercenter.core.exception.UserException;

/**
 * Created by duke on 15/11/19.
 */
public interface MessageRecordManager {
    /**
     * 添加消息记录
     * @param messageRecordDTO
     * */
    Long addRecord(MessageRecordDTO messageRecordDTO) throws UserException;

    /**
     * 查询消息记录
     * @param identify
     * */
    MessageRecordDTO getRecordByIdentifyAndBizType(String identify, Integer bizType) throws UserException;
}
