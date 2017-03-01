package com.mockuai.usercenter.core.manager.impl;

import com.mockuai.usercenter.common.dto.MessageRecordDTO;
import com.mockuai.usercenter.core.dao.MessageRecordDAO;
import com.mockuai.usercenter.core.domain.MessageRecordDO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.MessageRecordManager;
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
    public Long addRecord(MessageRecordDTO messageRecordDTO) throws UserException {
        if (messageRecordDTO != null) {
            MessageRecordDO messageRecordDO = new MessageRecordDO();
            BeanUtils.copyProperties(messageRecordDTO, messageRecordDO);
            return messageRecordDAO.addRecord(messageRecordDO);
        }
        return null;
    }

    @Override
    public MessageRecordDTO getRecordByIdentifyAndBizType(String identify, Integer bizType) throws UserException {
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
