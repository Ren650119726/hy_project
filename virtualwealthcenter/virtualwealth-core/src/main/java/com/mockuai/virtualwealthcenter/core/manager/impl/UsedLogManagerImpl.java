package com.mockuai.virtualwealthcenter.core.manager.impl;

import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
import com.mockuai.virtualwealthcenter.common.domain.qto.UsedLogQTO;
import com.mockuai.virtualwealthcenter.core.dao.UsedLogDAO;
import com.mockuai.virtualwealthcenter.core.domain.UsedLogDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.UsedLogManager;
import com.mockuai.virtualwealthcenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by edgar.zr on 5/16/2016.
 */
public class UsedLogManagerImpl implements UsedLogManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsedLogManagerImpl.class);
    @Autowired
    private UsedLogDAO usedLogDAO;

    @Override
    public void batchAddUsedLog(List<UsedLogDO> usedLogDOs) throws VirtualWealthException {
        try {
            usedLogDAO.batchAddUsedLog(usedLogDOs);
        } catch (Exception e) {
            LOGGER.error("error to batchAddUsedLog, useLogDOs : {}", JsonUtil.toJson(usedLogDOs), e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public List<UsedLogDO> queryUsedLog(UsedLogQTO usedLogQTO) throws VirtualWealthException {
        try {
            List<UsedLogDO> usedLogDOs = usedLogDAO.queryUsedLog(usedLogQTO);
            return usedLogDOs;
        } catch (Exception e) {
            LOGGER.error("error to queryUsedLog, usedLogQTO : {}", JsonUtil.toJson(usedLogQTO), e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public int updateUsedLog(UsedLogDO usedLogDO) throws VirtualWealthException {
        try {
            int optCount = usedLogDAO.updateUsedLog(usedLogDO);
            return optCount;
        } catch (Exception e) {
            LOGGER.error("error to updateUsedLog, usedLogDO : {}", JsonUtil.toJson(usedLogDO), e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }
}