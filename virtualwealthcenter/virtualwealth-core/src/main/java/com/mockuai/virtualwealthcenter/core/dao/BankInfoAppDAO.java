package com.mockuai.virtualwealthcenter.core.dao;

import java.util.List;

import com.mockuai.virtualwealthcenter.common.domain.qto.BankInfoQTO;
import com.mockuai.virtualwealthcenter.core.domain.BankInfoAppDO;

public interface BankInfoAppDAO {

	
	Long addBankInfo(BankInfoAppDO bankInfoAppDO);
	
	BankInfoAppDO getBankInfodetails(Long userId,String bankNo);
	
	List<BankInfoAppDO> getWithdrawalsItem(Long userId);
	
	Long deleteBankInfo(Long id,Long userId);
	
	BankInfoAppDO selectDetailsBankInfo(Long id,Long userId);
	
	Long updateBankInfoIsDefalut(Long id,Long userId);
	
	BankInfoAppDO selectBankInfoDelStatus(String bankNo,Long userId);
	
	Long updateBankInfoDelStatus(Long id,Long userId);
	
	BankInfoAppDO getBankInfoExists(String bankNo);
	
	List<BankInfoAppDO> findCustomerBankInfoPageList(BankInfoQTO bankInfoQTO);
	
}
