package com.mockuai.virtualwealthcenter.core.manager.impl;

import com.mockuai.virtualwealthcenter.common.domain.dto.RechargeRecordDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.RechargeRecordQTO;
import com.mockuai.virtualwealthcenter.core.dao.RechargeRecordDAO;
import com.mockuai.virtualwealthcenter.core.domain.RechargeRecordDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.RechargeRecordManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by duke on 16/4/18.
 */
public class RechargeRecordManagerImpl implements RechargeRecordManager {
    private static final Logger log = LoggerFactory.getLogger(RechargeRecordManagerImpl.class);

    @Resource
    private RechargeRecordDAO rechargeRecordDAO;

    @Override
    public Long addRecord(RechargeRecordDTO rechargeRecordDTO) throws VirtualWealthException {
        RechargeRecordDO rechargeRecordDO = new RechargeRecordDO();
        BeanUtils.copyProperties(rechargeRecordDTO, rechargeRecordDO);
        return rechargeRecordDAO.add(rechargeRecordDO);
    }

    @Override
    public List<RechargeRecordDTO> queryRecord(RechargeRecordQTO rechargeRecordQTO) throws VirtualWealthException {
        List<RechargeRecordDO> rechargeRecordDOs = rechargeRecordDAO.query(rechargeRecordQTO);
        List<RechargeRecordDTO> rechargeRecordDTOs = new ArrayList<>();
        if (!rechargeRecordDOs.isEmpty()) {
            for (RechargeRecordDO rechargeRecordDO : rechargeRecordDOs) {
                RechargeRecordDTO rechargeRecordDTO = new RechargeRecordDTO();
                BeanUtils.copyProperties(rechargeRecordDO, rechargeRecordDTO);
                rechargeRecordDTOs.add(rechargeRecordDTO);
            }
        }
        return rechargeRecordDTOs;
    }

    @Override
    public int updateRecord(RechargeRecordDTO rechargeRecordDTO) throws VirtualWealthException {
        RechargeRecordDO rechargeRecordDO = new RechargeRecordDO();
        BeanUtils.copyProperties(rechargeRecordDTO, rechargeRecordDO);
        return rechargeRecordDAO.update(rechargeRecordDO);
    }

    @Override
    public Long totalCount(RechargeRecordQTO rechargeRecordQTO) throws VirtualWealthException {
        return rechargeRecordDAO.totalCount(rechargeRecordQTO);
    }
}
