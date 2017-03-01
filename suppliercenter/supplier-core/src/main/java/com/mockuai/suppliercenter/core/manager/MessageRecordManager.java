package com.mockuai.suppliercenter.core.manager;

import com.mockuai.suppliercenter.common.dto.MessageRecordDTO;
import com.mockuai.suppliercenter.core.exception.SupplierException;

/**
 * Created by duke on 15/11/19.
 */
public interface MessageRecordManager {
    /**
     * 添加消息记录
     *
     * @param messageRecordDTO
     */
    Long addRecord(MessageRecordDTO messageRecordDTO) throws SupplierException;

    /**
     * 查询消息记录
     *
     * @param identify
     */
    MessageRecordDTO getRecordByIdentifyAndBizType(String identify, Integer bizType) throws SupplierException;
}
