package com.mockuai.virtualwealthcenter.core.dao.impl;

import com.mockuai.virtualwealthcenter.common.domain.qto.WealthAccountQTO;
import com.mockuai.virtualwealthcenter.core.dao.WealthAccountDAO;
import com.mockuai.virtualwealthcenter.core.domain.WealthAccountDO;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WealthAccountDAOImpl extends SqlMapClientDaoSupport implements WealthAccountDAO {

    public long addWealthAccount(WealthAccountDO wealthAccountDO) {

        return ((Long) getSqlMapClientTemplate().insert("wealth_account.addWealthAccount", wealthAccountDO)).longValue();
    }

    public int increaseAccountBalance(long wealthAccountId, long userId, long amount) {

        Map params = new HashMap();
        params.put("userId", userId);
        params.put("wealthAccountId", wealthAccountId);
        params.put("amount", amount);
        return getSqlMapClientTemplate().update("wealth_account.increaseAccountBalance", params);
    }

    @Override
    public int increaseTotalBalance(Long wealthAccountId, Long total) {
        Map params = new HashMap();
        params.put("wealthAccountId", wealthAccountId);
        params.put("total", total);
        return getSqlMapClientTemplate().update("wealth_account.increaseTotalBalance", params);
    }

    @Override
    public int increaseFrozenBalance(Long wealthAccountId, Long amount) {
        Map params = new HashMap();
        params.put("wealthAccountId", wealthAccountId);
        params.put("amount", amount);
        return getSqlMapClientTemplate().update("wealth_account.increaseFrozenBalance", params);
    }

    @Override
    public int increaseAccountBalanceBatch(List<WealthAccountDO> wealthAccountDOs) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("wealthAccountDOs", wealthAccountDOs);
        return getSqlMapClientTemplate().update("wealth_account.increaseAccountBalanceBatch", params);
    }

    public int decreaseAccountBalance(long wealthAccountId, long userId, long amount) {

        Map params = new HashMap();
        params.put("userId", userId);
        params.put("wealthAccountId", wealthAccountId);
        params.put("amount", amount);

        return getSqlMapClientTemplate().update("wealth_account.decreaseAccountBalance", params);
    }

    public WealthAccountDO getWealthAccount(long userId, int wealthType, String bizCode) {

        Map params = new HashMap();
        params.put("userId", Long.valueOf(userId));
        params.put("wealthType", wealthType);
        params.put("bizCode", bizCode);
        return (WealthAccountDO) getSqlMapClientTemplate().queryForObject("wealth_account.getWealthAccount", params);
    }

    @Override
    public List<WealthAccountDO> queryWealthAccount(WealthAccountQTO wealthAccountQTO) {
        wealthAccountQTO.setTotalCount((Integer) getSqlMapClientTemplate().queryForObject("wealth_account.countOfQueryUserWealthAccount", wealthAccountQTO));
        return (List<WealthAccountDO>) getSqlMapClientTemplate().queryForList("wealth_account.queryUserWealthAccount", wealthAccountQTO);
    }

    
    /**
     *  客户管理 余额流水 统计
     */
	@Override
	public WealthAccountDO findCustomerBalanceDetail(Long userId) {
		Map map = new HashMap();
		map.put("userId", userId);
		return (WealthAccountDO) getSqlMapClientTemplate().queryForObject("wealth_account.findCustomerBalanceDetail",map);
	}

	/**
	 * 客户管理 嗨币 详情  overTime = 当前时间-10个月
	 */
	@Override
	public WealthAccountDO findCustomerVirtualDetail(Long userId,
			String overTime) {
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("overTime", overTime);
		return (WealthAccountDO) getSqlMapClientTemplate().queryForObject("wealth_account.findCustomerVirtualDetail",map);
	}
}