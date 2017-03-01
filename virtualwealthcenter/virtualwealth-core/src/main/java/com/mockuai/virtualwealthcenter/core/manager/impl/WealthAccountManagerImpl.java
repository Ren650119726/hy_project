package com.mockuai.virtualwealthcenter.core.manager.impl;

import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
import com.mockuai.virtualwealthcenter.common.constant.WealthUseStatus;
import com.mockuai.virtualwealthcenter.common.domain.qto.WealthAccountQTO;
import com.mockuai.virtualwealthcenter.core.dao.WealthAccountDAO;
import com.mockuai.virtualwealthcenter.core.domain.UsedWealthDO;
import com.mockuai.virtualwealthcenter.core.domain.WealthAccountDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.UsedWealthManager;
import com.mockuai.virtualwealthcenter.core.manager.WealthAccountManager;
import com.mockuai.virtualwealthcenter.core.util.JsonUtil;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class WealthAccountManagerImpl implements WealthAccountManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(WealthAccountManagerImpl.class);

    @Autowired
    private WealthAccountDAO wealthAccountDAO;

    @Autowired
    private UsedWealthManager usedWealthManager;

    public Long addWealthAccount(WealthAccountDO wealthAccountDO) throws VirtualWealthException {

        try {

            return wealthAccountDAO.addWealthAccount(wealthAccountDO);

        } catch (Exception e) {
            if (e.getCause().getCause() instanceof MySQLIntegrityConstraintViolationException) {
                throw new VirtualWealthException(ResponseCode.DB_OP_ERROR_OF_DUPLICATE_ENTRY);
            }
            LOGGER.error("failed when adding wealthAccount : {}", JsonUtil.toJson(wealthAccountDO), e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    public int increaseAccountBalance(long wealthAccountId, long userId, long amount) throws VirtualWealthException {

        try {
            return wealthAccountDAO.increaseAccountBalance(wealthAccountId, userId, amount);
        } catch (Exception e) {
            LOGGER.error("failed when increasing account balance, wealthAccountId : {}, userId : {}, amount : {}",
                    wealthAccountId, userId, amount, e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public int increaseTotalBalance(Long wealthAccountId, Long amount) throws VirtualWealthException {
        try {
            return wealthAccountDAO.increaseTotalBalance(wealthAccountId, amount);
        } catch (Exception e) {
            LOGGER.error("failed when increaseTotalBalance, wealthAccountId : {}, amount : {}",
                    wealthAccountId, amount, e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public int increaseFrozenBalance(Long wealthAccountId, Long amount) throws VirtualWealthException {
        try {
            return wealthAccountDAO.increaseFrozenBalance(wealthAccountId, amount);
        } catch (Exception e) {
            LOGGER.error("failed to increaseFrozenBalance, wealthAccountId : {}, amount : {}",
                    wealthAccountId, amount, e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public int increaseAccountBalanceBatch(List<WealthAccountDO> wealthAccountDOs) throws VirtualWealthException {
        try {
            return wealthAccountDAO.increaseAccountBalanceBatch(wealthAccountDOs);
        } catch (Exception e) {
            LOGGER.error("error to increaseAccountBalanceBatch, wealthAccountDOs, {}",
                    JsonUtil.toJson(wealthAccountDOs), e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    public int decreaseAccountBalance(long wealthAccountId, long userId, long amount) throws VirtualWealthException {

        try {
            return wealthAccountDAO.decreaseAccountBalance(wealthAccountId, userId, amount);
        } catch (Exception e) {
            LOGGER.error("failed when decreasing account balance, wealthAccountId : {}, userId : {}, amount : {}",
                    wealthAccountId, userId, amount, e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public void deductWealthAccount(Long userId, Integer wealthType, Long amount, String bizCode) throws VirtualWealthException {
        try {

            WealthAccountDO wealthAccountDO = getWealthAccount(userId, wealthType, bizCode);
            if (wealthAccountDO == null) {
                LOGGER.error("error to deductWealthAccount, not found wealthAccount,  userId : {}, wealthType : {}, amount : {}, bizCode : {}",
                        userId, wealthType, amount, bizCode);
                throw new VirtualWealthException(ResponseCode.WEALTH_ACCOUNT_IS_NOT_FOUND);
            }
            if (wealthAccountDO.getAmount().longValue() < amount) {
                LOGGER.error("error to deductWealthAccount, the account balance is not enough, userId : {}, wealthType : {}, amount : {}, bizCode : {}",
                        userId, wealthType, amount, bizCode);
                throw new VirtualWealthException(ResponseCode.ACCOUNT_BALANCE_NOT_ENOUGH);
            }
            int optNum = decreaseAccountBalance(wealthAccountDO.getId(), userId, amount);
            if (optNum != 1) {
                LOGGER.error("error to decrease the wealth account balance, wealthAccountId:{}, userId:{}, amount:{}",
                        wealthAccountDO.getId(), userId, amount);
                throw new VirtualWealthException(ResponseCode.SERVICE_EXCEPTION);
            }
            UsedWealthDO usedWealthDO = new UsedWealthDO();
            usedWealthDO.setBizCode(bizCode);
            usedWealthDO.setUserId(userId);
            usedWealthDO.setAmount(amount);
            usedWealthDO.setWealthAccountId(wealthAccountDO.getId());
            usedWealthDO.setStatus(WealthUseStatus.OTHER.getValue());
            usedWealthManager.addUsedWealth(usedWealthDO);
        } catch (VirtualWealthException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error to deductWealthAccount,userId : {}, wealthType : {}, amount : {}, bizCode : {}",
                    userId, wealthType, amount, bizCode, e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    public WealthAccountDO getWealthAccount(long userId, int wealthType, String bizCode) throws VirtualWealthException {

        try {
            // TODO 更改逻辑，如果 wealthAccountDO 为 null，直接抛出，调用处在根据异常类型决定处理流程
            return wealthAccountDAO.getWealthAccount(userId, wealthType, bizCode);
        } catch (Exception e) {
            LOGGER.error("failed when getting wealthAccount, userId : {}, wealthType : {}, bizCode : {}",
                    userId, wealthType, bizCode, e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public List<WealthAccountDO> queryWealthAccount(WealthAccountQTO wealthAccountQTO) throws VirtualWealthException {
        try {
            return wealthAccountDAO.queryWealthAccount(wealthAccountQTO);
        } catch (Exception e) {
            LOGGER.error("failed when querying wealthAccount, wealthAccountQTO : {}", JsonUtil.toJson(wealthAccountQTO), e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

	@Override
	public WealthAccountDO findCustomerBalanceDetail(Long userId)
			throws VirtualWealthException {
		try {
	        return wealthAccountDAO.findCustomerBalanceDetail(userId);
        } catch (Exception e) {
            LOGGER.error("failed when querying wealthAccount, wealthAccountQTO : {}", JsonUtil.toJson(userId), e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
	}

	@Override
	public WealthAccountDO findCustomerVirtualDetail(Long userId,
			String overTime) throws VirtualWealthException {
		try {
	        return wealthAccountDAO.findCustomerVirtualDetail(userId, overTime);
        } catch (Exception e) {
            LOGGER.error("failed when querying wealthAccount, wealthAccountQTO : {}", JsonUtil.toJson(userId), e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
	}
}