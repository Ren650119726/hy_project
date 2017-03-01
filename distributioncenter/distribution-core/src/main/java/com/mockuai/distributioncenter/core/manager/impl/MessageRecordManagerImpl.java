package com.mockuai.distributioncenter.core.manager.impl;

import com.mockuai.distributioncenter.common.domain.dto.MessageRecordDTO;
import com.mockuai.distributioncenter.core.dao.MessageRecordDAO;
import com.mockuai.distributioncenter.core.domain.MessageRecordDO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.MessageRecordManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by duke on 16/2/19.
 */
@Component
public class MessageRecordManagerImpl implements MessageRecordManager {
    @Autowired
    private MessageRecordDAO messageRecordDAO;

    @Override
    public Long addRecord(MessageRecordDTO messageRecordDTO) throws DistributionException {
        if (messageRecordDTO != null) {
            MessageRecordDO messageRecordDO = new MessageRecordDO();
            BeanUtils.copyProperties(messageRecordDTO, messageRecordDO);
            return messageRecordDAO.addRecord(messageRecordDO);
        }
        return null;
    }

    @Override
    public MessageRecordDTO getRecordByIdentifyAndBizType(String identify, Integer bizType) throws DistributionException {
        MessageRecordDO messageRecordDO = messageRecordDAO.getRecordByIdentifyAndBizType(identify, bizType);
        if (messageRecordDO != null) {
            MessageRecordDTO messageRecordDTO = new MessageRecordDTO();
            BeanUtils.copyProperties(messageRecordDO, messageRecordDTO);
            return messageRecordDTO;
        }
        return null;
    }
}
