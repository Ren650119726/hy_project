package com.mockuai.usercenter.core.manager.impl;

import com.mockuai.usercenter.common.dto.TradeRecordDTO;
import com.mockuai.usercenter.common.qto.TradeRecordQTO;
import com.mockuai.usercenter.core.dao.TradeRecordDAO;
import com.mockuai.usercenter.core.domain.TradeRecordDO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.TradeRecordManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by duke on 15/9/22.
 */
@Service
public class TradeRecordManagerImpl implements TradeRecordManager {
    private static final Logger log = LoggerFactory.getLogger(TradeRecordManagerImpl.class);

    @Resource
    private TradeRecordDAO tradeRecordDAO;

    @Override
    public Long addTradeRecord(TradeRecordDTO tradeRecordDTO) throws UserException {
        TradeRecordDO tradeRecordDO = new TradeRecordDO();
        BeanUtils.copyProperties(tradeRecordDTO, tradeRecordDO);
        Long id = tradeRecordDAO.addRecord(tradeRecordDO);
        return id;
    }

    @Override
    public TradeRecordDTO queryTradeRecordByUserId(Long userId)
            throws UserException {
        TradeRecordDO tradeRecordDO = tradeRecordDAO.queryRecordByUserId(userId);
        if (tradeRecordDO != null) {
            TradeRecordDTO tradeRecordDTO = new TradeRecordDTO();
            BeanUtils.copyProperties(tradeRecordDO, tradeRecordDTO);
            return tradeRecordDTO;
        } else {
            return null;
        }
    }

    @Override
    public List<TradeRecordDTO> queryAll() throws UserException {
        List<TradeRecordDO> tradeRecordDOs = tradeRecordDAO.queryAll();
        List<TradeRecordDTO> tradeRecordDTOs = new ArrayList<TradeRecordDTO>();
        for (TradeRecordDO tradeRecordDO : tradeRecordDOs) {
            TradeRecordDTO tradeRecordDTO = new TradeRecordDTO();
            BeanUtils.copyProperties(tradeRecordDO, tradeRecordDTO);
            tradeRecordDTOs.add(tradeRecordDTO);
        }
        return tradeRecordDTOs;
    }

    @Override
    public List<TradeRecordDTO> query(TradeRecordQTO tradeRecordQTO) throws UserException {
        List<TradeRecordDO> tradeRecordDOs = tradeRecordDAO.query(tradeRecordQTO);
        List<TradeRecordDTO> tradeRecordDTOs = new ArrayList<TradeRecordDTO>();
        for (TradeRecordDO tradeRecordDO : tradeRecordDOs) {
            TradeRecordDTO tradeRecordDTO = new TradeRecordDTO();
            BeanUtils.copyProperties(tradeRecordDO, tradeRecordDTO);
            tradeRecordDTOs.add(tradeRecordDTO);
        }
        return tradeRecordDTOs;
    }

    @Override
    public Long totalCount(TradeRecordQTO tradeRecordQTO) throws UserException {
        return tradeRecordDAO.totalCount(tradeRecordQTO);
    }

    @Override
    public int deleteByUserId(Long userId) throws UserException {
        return tradeRecordDAO.deleteByUserId(userId);
    }

    @Override
    public int updateByUserId(Long userId, TradeRecordDO tradeRecordDO) throws UserException {
        return tradeRecordDAO.updateByUserId(userId, tradeRecordDO);
    }
}
