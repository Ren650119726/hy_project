package com.mockuai.virtualwealthcenter.core.manager.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
import com.mockuai.virtualwealthcenter.common.domain.qto.WithdrawalsItemQTO;
import com.mockuai.virtualwealthcenter.core.dao.WithdrawalsItemAppDAO;
import com.mockuai.virtualwealthcenter.core.domain.WithdrawalsItemAppDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.WithdrawalsItemAppManager;
import com.mockuai.virtualwealthcenter.core.util.JsonUtil;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class WithdrawalsItemAppManagerImpl implements WithdrawalsItemAppManager{

	@Autowired
	private WithdrawalsItemAppDAO withdrawalsItemAppDAO;
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WithdrawalsItemAppManagerImpl.class);

	@Override
	public Long addWithdrawalsItem(WithdrawalsItemAppDO withdrawalsItemAppDO) throws VirtualWealthException {
		try {
            return withdrawalsItemAppDAO.addWithdrawalsItem(withdrawalsItemAppDO);
        } catch (Exception e) {
            if (e.getCause().getCause() instanceof MySQLIntegrityConstraintViolationException) {
                throw new VirtualWealthException(ResponseCode.DB_OP_ERROR_OF_DUPLICATE_ENTRY);
            }
            LOGGER.error("failed when adding wealthAccount : {}", JsonUtil.toJson(withdrawalsItemAppDO), e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
	}

	
	/**
	 * 客户管理 余额流水 提现记录
	 */
	@Override
	public List<WithdrawalsItemAppDO> findCustomerWithdrawalsPageList(
			WithdrawalsItemQTO withdrawalsItemQTO)
			throws VirtualWealthException {
		try {
            return withdrawalsItemAppDAO.findCustomerWithdrawalsPageList(withdrawalsItemQTO);
        } catch (Exception e) {
            if (e.getCause().getCause() instanceof MySQLIntegrityConstraintViolationException) {
                throw new VirtualWealthException(ResponseCode.DB_OP_ERROR_OF_DUPLICATE_ENTRY);
            }
            LOGGER.error("failed when adding wealthAccount : {}", JsonUtil.toJson(withdrawalsItemQTO), e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
	}
	
}
