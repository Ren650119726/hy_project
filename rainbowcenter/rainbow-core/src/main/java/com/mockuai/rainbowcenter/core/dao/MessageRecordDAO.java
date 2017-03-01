package com.mockuai.rainbowcenter.core.dao;


import com.mockuai.rainbowcenter.core.domain.MessageRecordDO;

/**
 * Created by duke on 15/12/23.
 */
public interface MessageRecordDAO {
    /**
     * 添加消息记录
     *
     * @param messageRecordDO
     */
    Long addRecord(MessageRecordDO messageRecordDO);

    /**
     * 查询消息记录
     *  @param identify
     * @param bizType
     */
    MessageRecordDO getRecordByIdentifyAndBizType(String identify, Integer bizType);

    /**
     * 更新消息记录
     * @param id
     * @param bizType
     * @return
     */
    Integer updateRecord(Long id, int bizType);
}
