package com.mockuai.virtualwealthcenter.core.manager.impl;

import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
import com.mockuai.virtualwealthcenter.common.constant.WealthType;
import com.mockuai.virtualwealthcenter.common.domain.dto.VirtualWealthDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.VirtualWealthQTO;
import com.mockuai.virtualwealthcenter.core.dao.VirtualWealthDAO;
import com.mockuai.virtualwealthcenter.core.domain.VirtualWealthDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.VirtualWealthManager;
import com.mockuai.virtualwealthcenter.core.util.JsonUtil;
import com.mockuai.virtualwealthcenter.core.util.ModelUtil;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

public class VirtualWealthManagerImpl implements VirtualWealthManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(VirtualWealthManagerImpl.class);

    @Resource
    private VirtualWealthDAO virtualWealthDAO;

    public long addVirtualWealth(VirtualWealthDO virtualWealthDO) throws VirtualWealthException {

        try {
            return virtualWealthDAO.addVirtualWealth(virtualWealthDO);
        } catch (Exception e) {
            LOGGER.error("failed when adding virtualWealth : {}", JsonUtil.toJson(virtualWealthDO), e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    public int deleteVirtualWealth(long id, long userId) throws VirtualWealthException {

        try {
            return virtualWealthDAO.deleteVirtualWealth(id, userId);
        } catch (Exception e) {
            LOGGER.error("failed when deleting virtualWealth, id : {}, userId : {}",
                    id, userId, e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public VirtualWealthDTO getVirtualWealth(String bizCode, Long creatorId, Integer wealthType) throws VirtualWealthException {
        VirtualWealthDO virtualWealthDO = new VirtualWealthDO();
        virtualWealthDO.setBizCode(bizCode);
        virtualWealthDO.setType(wealthType);
        try {
            VirtualWealthDO virtualWealthDODB = virtualWealthDAO.getVirtualWealth(virtualWealthDO);
            if (virtualWealthDODB == null) {
                virtualWealthDODB = fakeVirtualWealth(bizCode, creatorId, wealthType);
                try {
                    virtualWealthDAO.addVirtualWealth(virtualWealthDODB);
                } catch (Exception e) {
                    if (!(e.getCause().getCause() instanceof MySQLIntegrityConstraintViolationException)) {
                        throw e;
                    }
                    // 如果是 唯一性约束 错误则容错
                }
                virtualWealthDODB = virtualWealthDAO.getVirtualWealth(virtualWealthDO);
            }
            return ModelUtil.genVirtualWealthDTO(virtualWealthDODB);
        } catch (VirtualWealthException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error to getVirtualWealth, creatorId : {}, wealthType : {}, bizCode : {}",
                    creatorId, wealthType, bizCode, e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public List<VirtualWealthDO> queryVirtualWealth(VirtualWealthQTO virtualWealthQTO) throws VirtualWealthException {
        try {
            return virtualWealthDAO.queryVirtualWealth(virtualWealthQTO);
        } catch (Exception e) {
            LOGGER.error("failed when querying virtualWealth, virtualWealthQTO : {}", JsonUtil.toJson(virtualWealthQTO), e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public int increaseVirtualWealth(long id, long amount) throws VirtualWealthException {
        try {
            return virtualWealthDAO.increaseVirtualWealth(id, amount);
        } catch (Exception e) {
            LOGGER.error("failed when increase virtualWealth, id = {}, amount = {}", id, amount, e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public int increaseGrantedVirtualWealth(long id, long amount) throws VirtualWealthException {
        try {
            return virtualWealthDAO.increaseGrantedVirtualWealth(id, amount);
        } catch (Exception e) {
            LOGGER.error("failed when increase grantedVirtualWealth, id = {}, amount = {}", id, amount, e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public void updateVirtualWealth(String bizCode, Long virtualWealthId, Double exchangeRate, Integer upperLimit) throws VirtualWealthException {
        try {
            VirtualWealthDO virtualWealthDO = new VirtualWealthDO();
            virtualWealthDO.setId(virtualWealthId);
            virtualWealthDO.setBizCode(bizCode);
            virtualWealthDO.setExchangeRate(exchangeRate);
            virtualWealthDO.setUpperLimit(upperLimit);
            int opNum = virtualWealthDAO.updateVirtualWealth(virtualWealthDO);
            if (opNum != 1) {
                LOGGER.error("error to updateVirtualWealth, virtualWealthId : {}, exchangeRate : {}, upperLimit : {}",
                        virtualWealthId, exchangeRate, upperLimit);
                throw new VirtualWealthException(ResponseCode.SERVICE_EXCEPTION);
            }
        } catch (VirtualWealthException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error to updateVirtualWealth, virtualWealthId : {}, exchangeRate : {}, upperLimit: {}",
                    virtualWealthId, exchangeRate, upperLimit, e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    public VirtualWealthDO fakeVirtualWealth(String bizCode, Long creatorId, Integer wealthType) throws VirtualWealthException {
        VirtualWealthDO virtualWealthDO = new VirtualWealthDO();
        virtualWealthDO.setType(wealthType);
        virtualWealthDO.setBizCode(bizCode);
        virtualWealthDO.setCreatorId(creatorId);
        virtualWealthDO.setName(WealthType.getByValue(wealthType).getName());
        virtualWealthDO.setTradeMark(1);
        if (wealthType.intValue() == WealthType.CREDIT.getValue()) {
            virtualWealthDO.setAmount(-1L);
            virtualWealthDO.setGrantedAmount(0L);
            virtualWealthDO.setExchangeRate(1D);
            virtualWealthDO.setUpperLimit(0);
        } else if (wealthType.intValue() == WealthType.VIRTUAL_WEALTH.getValue()) {
            virtualWealthDO.setAmount(-1L);
            virtualWealthDO.setGrantedAmount(0L);
            virtualWealthDO.setExchangeRate(1D);
            virtualWealthDO.setUpperLimit(0);
        } else if (wealthType.intValue() == WealthType.HI_COIN.getValue()) {
            virtualWealthDO.setAmount(-1L);
            virtualWealthDO.setGrantedAmount(0L);
            virtualWealthDO.setExchangeRate(1D);
            virtualWealthDO.setUpperLimit(0);
        }
        return virtualWealthDO;
    }
}