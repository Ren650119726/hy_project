package com.mockuai.virtualwealthcenter.core.manager.impl;

import com.mockuai.virtualwealthcenter.common.domain.qto.WithdrawalsItemLogQTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
import com.mockuai.virtualwealthcenter.core.dao.impl.WithdrawalsItemLogDODAO;
import com.mockuai.virtualwealthcenter.core.domain.WithdrawalsItemLogDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.WithdrawalsItemLogManager;
import com.mockuai.virtualwealthcenter.core.util.JsonUtil;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.util.List;

public class WithdrawalsItemLogManagerImpl implements WithdrawalsItemLogManager{

	
	@Autowired
	private WithdrawalsItemLogDODAO withdrawalsItemLogDODAO;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BankInfoAppManagerImpl.class.getName());
	
	/**
	 * 提现日志
	 */
	@Override
	public void insert(WithdrawalsItemLogDO record) throws VirtualWealthException {
		try {
            withdrawalsItemLogDODAO.insert(record);
        } catch (Exception e) {
            if (e.getCause().getCause() instanceof MySQLIntegrityConstraintViolationException) {
                throw new VirtualWealthException(ResponseCode.DB_OP_ERROR_OF_DUPLICATE_ENTRY);
            }
            LOGGER.error("failed when adding wealthAccount : {}", JsonUtil.toJson(record), e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
	}

    @Override
    public List<WithdrawalsItemLogDO> queryWithdrawalsItemLog(WithdrawalsItemLogQTO withdrawalsItemLogQTO)
            throws VirtualWealthException {
        try {
            List<WithdrawalsItemLogDO> withdrawalsItemLogDOs =
                    withdrawalsItemLogDODAO.queryWithdrawalsItemLog(withdrawalsItemLogQTO);
            return withdrawalsItemLogDOs;
        } catch (Exception e) {
            LOGGER.error("error to queryWithdrawalsItemLog, withdrawalsItemLogQTO : {}",
                    JsonUtil.toJson(withdrawalsItemLogQTO), e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }

    }
}
