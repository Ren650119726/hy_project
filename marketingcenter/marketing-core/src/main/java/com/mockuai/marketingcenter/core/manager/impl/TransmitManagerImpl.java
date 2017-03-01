package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.TransmitDTO;
import com.mockuai.marketingcenter.core.dao.TransmitDAO;
import com.mockuai.marketingcenter.core.domain.TransmitDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.TransmitManager;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by duke on 15/9/23.
 */
@Service
public class TransmitManagerImpl implements TransmitManager {
    @Resource
    private TransmitDAO transmitDAO;

    @Override
    public Long addTransmit(TransmitDTO transmitDTO) throws MarketingException {
        if (transmitDTO == null) {
            throw new MarketingException(ResponseCode.PARAMETER_NULL, "transmitDTO is null");
        }
        TransmitDO transmitDO = new TransmitDO();
        BeanUtils.copyProperties(transmitDTO, transmitDO);
        return transmitDAO.addTransmit(transmitDO);
    }

    @Override
    public int updateTransmit(TransmitDTO transmitDTO) throws MarketingException {
        if (transmitDTO == null) {
            throw new MarketingException(ResponseCode.PARAMETER_NULL, "transmitDTO is null");
        }

        TransmitDO transmitDO = new TransmitDO();
        BeanUtils.copyProperties(transmitDTO, transmitDO);
        int affectRow = transmitDAO.updateTransmit(transmitDO);
        return affectRow;
    }

    @Override
    public TransmitDTO getTransmit(String bizCode) throws MarketingException {
        TransmitDO transmitDO = transmitDAO.getTransmitByBizCode(bizCode);
        TransmitDTO transmitDTO = new TransmitDTO();
        if (transmitDO != null) {
            BeanUtils.copyProperties(transmitDO, transmitDTO);
            return transmitDTO;
        } else {
            return null;
        }
    }
}
