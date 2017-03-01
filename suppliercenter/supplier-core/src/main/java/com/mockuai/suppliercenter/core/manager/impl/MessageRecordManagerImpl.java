package com.mockuai.suppliercenter.core.manager.impl;

import com.mockuai.suppliercenter.common.dto.MessageRecordDTO;
import com.mockuai.suppliercenter.core.dao.MessageRecordDAO;
import com.mockuai.suppliercenter.core.domain.MessageRecordDO;
import com.mockuai.suppliercenter.core.exception.SupplierException;
import com.mockuai.suppliercenter.core.manager.MessageRecordManager;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by duke on 15/11/19.
 */
@Service
public class MessageRecordManagerImpl implements MessageRecordManager {
    @Resource
    private MessageRecordDAO messageRecordDAO;

    @Override
    public Long addRecord(MessageRecordDTO messageRecordDTO) throws SupplierException {
        if (messageRecordDTO != null) {
            MessageRecordDO messageRecordDO = new MessageRecordDO();
            BeanUtils.copyProperties(messageRecordDTO, messageRecordDO);
            return messageRecordDAO.addRecord(messageRecordDO);
        }
        return null;
    }

    @Override
    public MessageRecordDTO getRecordByIdentifyAndBizType(String identify, Integer bizType) throws SupplierException {
        MessageRecordDO messageRecordDO = messageRecordDAO.getRecordByIdentifyAndBizType(identify, bizType);
        if (messageRecordDO != null) {
            MessageRecordDTO messageRecordDTO = new MessageRecordDTO();
            BeanUtils.copyProperties(messageRecordDO, messageRecordDTO);
            return messageRecordDTO;
        } else {
            return null;
        }
    }
}
