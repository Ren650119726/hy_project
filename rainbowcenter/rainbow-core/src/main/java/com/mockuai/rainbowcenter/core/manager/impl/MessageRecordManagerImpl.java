package com.mockuai.rainbowcenter.core.manager.impl;

import com.mockuai.rainbowcenter.common.dto.MessageRecordDTO;
import com.mockuai.rainbowcenter.core.dao.MessageRecordDAO;
import com.mockuai.rainbowcenter.core.domain.MessageRecordDO;
import com.mockuai.rainbowcenter.core.exception.RainbowException;
import com.mockuai.rainbowcenter.core.manager.MessageRecordManager;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;

/**
 * Created by duke on 15/12/23.
 */
public class MessageRecordManagerImpl implements MessageRecordManager {
    @Resource
    private MessageRecordDAO messageRecordDAO;

    @Override
    public Long addRecord(MessageRecordDTO messageRecordDTO) throws RainbowException {
        if (messageRecordDTO != null) {
            MessageRecordDO messageRecordDO = new MessageRecordDO();
            BeanUtils.copyProperties(messageRecordDTO, messageRecordDO);
            return messageRecordDAO.addRecord(messageRecordDO);
        }
        return null;
    }

    @Override
    public MessageRecordDTO getRecordByIdentifyAndBizType(String identify, Integer bizType) throws RainbowException {
        MessageRecordDO messageRecordDO = messageRecordDAO.getRecordByIdentifyAndBizType(identify, bizType);
        if (messageRecordDO != null) {
            MessageRecordDTO messageRecordDTO = new MessageRecordDTO();
            BeanUtils.copyProperties(messageRecordDO, messageRecordDTO);
            return messageRecordDTO;
        }
        return null;
    }

    @Override
    public Integer updateRecord(Long id, int bizType) {
        Integer n = this.messageRecordDAO.updateRecord(id, bizType);
        return n;
    }
}
