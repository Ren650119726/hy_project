package com.mockuai.virtualwealthcenter.core.manager;

import java.util.List;

import com.mockuai.virtualwealthcenter.common.domain.qto.BankInfoQTO;
import com.mockuai.virtualwealthcenter.core.domain.BankInfoAppDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;

public interface BankInfoAppManager {

	Long addBankInfo(BankInfoAppDO bankInfoAppDO) throws VirtualWealthException;
	
	List<BankInfoAppDO> getWithdrawalsItem(Long userId) throws VirtualWealthException;
	
	Long deleteBankInfo(Long id, Long userId) throws VirtualWealthException;
	
	BankInfoAppDO selectDetailsBankInfo(Long id, Long userId) throws VirtualWealthException;
	
	BankInfoAppDO getBankInfodetails(Long userId, String bankNo) throws VirtualWealthException;
	
	Long updateBankInfoIsDefalut(Long id,Long userId) throws VirtualWealthException;
	
	BankInfoAppDO selectBankInfoDelStatus(String bankNo,Long userId) throws VirtualWealthException;
	
	Long updateBankInfoDelStatus(Long id,Long userId) throws VirtualWealthException;
	
	BankInfoAppDO getBankInfoExists(String bankNo) throws VirtualWealthException;
	
	List<BankInfoAppDO> findCustomerBankInfoPageList(BankInfoQTO bankInfoQTO) throws VirtualWealthException;;
}
