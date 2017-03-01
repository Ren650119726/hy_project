package com.mockuai.rainbowcenter.core.manager;


import com.mockuai.rainbowcenter.common.dto.MessageRecordDTO;
import com.mockuai.rainbowcenter.core.exception.RainbowException;

/**
 * Created by duke on 15/12/23.
 */
public interface MessageRecordManager {
    /**
     * 添加消息记录
     *
     * @param messageRecordDTO
     */
    Long addRecord(MessageRecordDTO messageRecordDTO) throws RainbowException;

    /**
     * 查询消息记录
     *
     * @param identify
     * @param bizType
     */
    MessageRecordDTO getRecordByIdentifyAndBizType(String identify, Integer bizType) throws RainbowException;

    /**
     * 更新消息记录状态
     * @param id
     * @param value
     * @return
     */
    Integer updateRecord(Long id, int value);
}
