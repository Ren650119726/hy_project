package com.mockuai.virtualwealthcenter.core.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.mockuai.virtualwealthcenter.common.domain.qto.BankInfoQTO;
import com.mockuai.virtualwealthcenter.core.dao.BankInfoAppDAO;
import com.mockuai.virtualwealthcenter.core.domain.BankInfoAppDO;

public class BankInfoAppDAOImpl extends SqlMapClientDaoSupport implements BankInfoAppDAO{
	
	/**
	 * 添加银行卡
	 */
	@Override
	public Long addBankInfo(BankInfoAppDO bankInfoAppDO) {
		return ((Long) getSqlMapClientTemplate().insert("bank_info.addBankInfo", bankInfoAppDO)).longValue();
	}

	/**
	 * 准备选择某张银行卡提现
	 */
	@Override
	public BankInfoAppDO getBankInfodetails(Long userId, String bankNo) {
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("bankNo", bankNo);
		return (BankInfoAppDO) getSqlMapClientTemplate().queryForObject("bank_info.getBankInfodetails",map);
	}

	/**
	 * 选择提现按钮或者获取银行卡列表，获取该用户下所绑定的银行卡
	 */
	@Override
	public List<BankInfoAppDO> getWithdrawalsItem(Long userId) {
		Map map = new HashMap();
		map.put("userId", userId);
		return (List<BankInfoAppDO>) getSqlMapClientTemplate().queryForList("bank_info.getWithdrawalsItem",map);
	}

	/**
	 * 根据用户ID和银行卡ID删除某张卡
	 */
	@Override
	public Long deleteBankInfo(Long id, Long userId) {
		Map map = new HashMap();
		map.put("id", id);
		map.put("userId", userId);
		return (long) getSqlMapClientTemplate().update("bank_info.deleteBankInfo",map);
	}

	/**
	 * 查看银行卡详情
	 */
	@Override
	public BankInfoAppDO selectDetailsBankInfo(Long id, Long userId) {
		Map map = new HashMap();
		map.put("id", id);
		map.put("userId", userId);
		return (BankInfoAppDO)getSqlMapClientTemplate().queryForObject("bank_info.selectDetailsBankInfo",map);
	}

	
	/**
	 * 默认更新最后一张卡
	 */
	@Override
	public Long updateBankInfoIsDefalut(Long id, Long userId) {
		Map map = new HashMap();
		map.put("id", id);
		map.put("userId", userId);
		return (long) getSqlMapClientTemplate().update("bank_info.updateBankInfoIsDefalut",map);
	}

	@Override
	public BankInfoAppDO selectBankInfoDelStatus(String bankNo, Long userId) {
		Map map = new HashMap();
		map.put("bankNo", bankNo);
		map.put("userId", userId);
		return (BankInfoAppDO)getSqlMapClientTemplate().queryForObject("bank_info.selectBankInfoDelStatus",map);
	}

	@Override
	public Long updateBankInfoDelStatus(Long id, Long userId) {
		Map map = new HashMap();
		map.put("id", id);
		map.put("userId", userId);
		return (long) getSqlMapClientTemplate().update("bank_info.updateBankInfoDelStatus",map);
	}

	@Override
	public BankInfoAppDO getBankInfoExists(String bankNo) {
		Map map = new HashMap();
		map.put("bankNo", bankNo);
		return (BankInfoAppDO)getSqlMapClientTemplate().queryForObject("bank_info.getBankInfoExists",map);
	}

	
	/**
	 * 客户管理 银行卡管理
	 */
	@Override
	public List<BankInfoAppDO> findCustomerBankInfoPageList(BankInfoQTO bankInfoQTO) {
		bankInfoQTO.setTotalCount((Integer) getSqlMapClientTemplate().queryForObject("bank_info.findCustomerBankInfoCount",bankInfoQTO));
        return getSqlMapClientTemplate().queryForList("bank_info.findCustomerBankInfoPageList", bankInfoQTO);
	}
	
	
}
