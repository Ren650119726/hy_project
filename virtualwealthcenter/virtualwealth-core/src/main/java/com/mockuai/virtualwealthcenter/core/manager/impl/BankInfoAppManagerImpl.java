package com.mockuai.virtualwealthcenter.core.manager.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
import com.mockuai.virtualwealthcenter.common.domain.qto.BankInfoQTO;
import com.mockuai.virtualwealthcenter.core.dao.BankInfoAppDAO;
import com.mockuai.virtualwealthcenter.core.domain.BankInfoAppDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.BankInfoAppManager;
import com.mockuai.virtualwealthcenter.core.util.JsonUtil;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class BankInfoAppManagerImpl implements BankInfoAppManager{

	  	private static final Logger LOGGER = LoggerFactory.getLogger(BankInfoAppManagerImpl.class.getName());
		@Autowired
		private BankInfoAppDAO bankInfoAppDAO;
		
		@Override
		public Long addBankInfo(BankInfoAppDO bankInfoAppDO) throws VirtualWealthException {
			try {
	            return bankInfoAppDAO.addBankInfo(bankInfoAppDO);
	        } catch (Exception e) {
	            if (e.getCause().getCause() instanceof MySQLIntegrityConstraintViolationException) {
	                throw new VirtualWealthException(ResponseCode.DB_OP_ERROR_OF_DUPLICATE_ENTRY);
	            }
	            LOGGER.error("failed when adding wealthAccount : {}", JsonUtil.toJson(bankInfoAppDO), e);
	            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
	        }
		}
		@Override
		public List<BankInfoAppDO> getWithdrawalsItem(Long userId)
				throws VirtualWealthException {
			try {
	            return bankInfoAppDAO.getWithdrawalsItem(userId);
	        } catch (Exception e) {
	            if (e.getCause().getCause() instanceof MySQLIntegrityConstraintViolationException) {
	                throw new VirtualWealthException(ResponseCode.DB_OP_ERROR_OF_DUPLICATE_ENTRY);
	            }
	            LOGGER.error("failed when adding wealthAccount : {}", JsonUtil.toJson(userId), e);
	            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
	        }
		}
		@Override
		public Long deleteBankInfo(Long id, Long userId)
				throws VirtualWealthException {
			try {
	            return bankInfoAppDAO.deleteBankInfo(id, userId);
	        } catch (Exception e) {
	            if (e.getCause().getCause() instanceof MySQLIntegrityConstraintViolationException) {
	                throw new VirtualWealthException(ResponseCode.DB_OP_ERROR_OF_DUPLICATE_ENTRY);
	            }
	            LOGGER.error("failed when adding wealthAccount : {}", JsonUtil.toJson(userId), e);
	            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
	        }
		}
		
		@Override
		public BankInfoAppDO selectDetailsBankInfo(Long id, Long userId)
				throws VirtualWealthException {
			try {
	            return bankInfoAppDAO.selectDetailsBankInfo(id, userId);
	        } catch (Exception e) {
	            if (e.getCause().getCause() instanceof MySQLIntegrityConstraintViolationException) {
	                throw new VirtualWealthException(ResponseCode.DB_OP_ERROR_OF_DUPLICATE_ENTRY);
	            }
	            LOGGER.error("failed when adding wealthAccount : {}", JsonUtil.toJson(userId), e);
	            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
	        }
		}
		@Override
		public BankInfoAppDO getBankInfodetails(Long userId, String bankNo)
				throws VirtualWealthException {
			try {
	            return bankInfoAppDAO.getBankInfodetails(userId, bankNo);
	        } catch (Exception e) {
	            if (e.getCause().getCause() instanceof MySQLIntegrityConstraintViolationException) {
	                throw new VirtualWealthException(ResponseCode.DB_OP_ERROR_OF_DUPLICATE_ENTRY);
	            }
	            LOGGER.error("failed when adding wealthAccount : {}", JsonUtil.toJson(userId), e);
	            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
	        }
		}
		
		@Override
		public Long updateBankInfoIsDefalut(Long id, Long userId)
				throws VirtualWealthException {
			try {
	            return bankInfoAppDAO.updateBankInfoIsDefalut(id, userId);
	        } catch (Exception e) {
	            if (e.getCause().getCause() instanceof MySQLIntegrityConstraintViolationException) {
	                throw new VirtualWealthException(ResponseCode.DB_OP_ERROR_OF_DUPLICATE_ENTRY);
	            }
	            LOGGER.error("failed when adding wealthAccount : {}", JsonUtil.toJson(userId), e);
	            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
	        }
		}
		
		@Override
		public BankInfoAppDO selectBankInfoDelStatus(String bankNo, Long userId) throws VirtualWealthException {
			try {
	            return bankInfoAppDAO.selectBankInfoDelStatus(bankNo, userId);
	        } catch (Exception e) {
	            if (e.getCause().getCause() instanceof MySQLIntegrityConstraintViolationException) {
	                throw new VirtualWealthException(ResponseCode.DB_OP_ERROR_OF_DUPLICATE_ENTRY);
	            }
	            LOGGER.error("failed when adding wealthAccount : {}", JsonUtil.toJson(userId), e);
	            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
	        }
		}
		@Override
		public Long updateBankInfoDelStatus(Long id, Long userId) throws VirtualWealthException{
			try {
	            return bankInfoAppDAO.updateBankInfoDelStatus(id, userId);
	        } catch (Exception e) {
	            if (e.getCause().getCause() instanceof MySQLIntegrityConstraintViolationException) {
	                throw new VirtualWealthException(ResponseCode.DB_OP_ERROR_OF_DUPLICATE_ENTRY);
	            }
	            LOGGER.error("failed when adding wealthAccount : {}", JsonUtil.toJson(userId), e);
	            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
	        }
		}
		
		@Override
		public BankInfoAppDO getBankInfoExists(String bankNo)
				throws VirtualWealthException {
			try {
	            return bankInfoAppDAO.getBankInfoExists(bankNo);
	        } catch (Exception e) {
	            if (e.getCause().getCause() instanceof MySQLIntegrityConstraintViolationException) {
	                throw new VirtualWealthException(ResponseCode.DB_OP_ERROR_OF_DUPLICATE_ENTRY);
	            }
	            LOGGER.error("failed when adding wealthAccount : {}", JsonUtil.toJson(bankNo), e);
	            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
	        }
		}
		
		/**
		 * 客户管理 银行卡管理
		 */
		@Override
		public List<BankInfoAppDO> findCustomerBankInfoPageList(
				BankInfoQTO bankInfoQTO) throws VirtualWealthException {
			try{
				return bankInfoAppDAO.findCustomerBankInfoPageList(bankInfoQTO);
			}catch(Exception e){
				if (e.getCause().getCause() instanceof MySQLIntegrityConstraintViolationException) {
	                throw new VirtualWealthException(ResponseCode.DB_OP_ERROR_OF_DUPLICATE_ENTRY);
	            }
	            LOGGER.error("failed when adding wealthAccount : {}", JsonUtil.toJson(bankInfoQTO), e);
	            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
			}
		}
}
