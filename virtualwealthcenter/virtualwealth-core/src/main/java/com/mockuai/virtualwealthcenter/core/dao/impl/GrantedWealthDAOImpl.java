package com.mockuai.virtualwealthcenter.core.dao.impl;

import com.mockuai.virtualwealthcenter.common.domain.qto.GrantedWealthQTO;
import com.mockuai.virtualwealthcenter.core.dao.GrantedWealthDAO;
import com.mockuai.virtualwealthcenter.core.domain.GrantedWealthDO;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 5/25/15.
 */
public class GrantedWealthDAOImpl extends SqlMapClientDaoSupport implements GrantedWealthDAO {

    @Override
    public Long addGrantedWealth(GrantedWealthDO grantedWealthDO) {
        return ((Long) getSqlMapClientTemplate().insert("granted_wealth.addGrantedWealth", grantedWealthDO));
    }

    @Override
    public GrantedWealthDO getGrantedWealth(GrantedWealthDO grantedWealthDO) {
        return (GrantedWealthDO) getSqlMapClientTemplate().queryForObject("granted_wealth.getGrantedWealth", grantedWealthDO);
    }

    @Override
    public int updateGrantedWealth(GrantedWealthDO grantedWealthDO) {
        return getSqlMapClientTemplate().update("granted_wealth.updateGrantedWealth", grantedWealthDO);
    }

    @Override
    public int batchUpdateStatus(List<GrantedWealthDO> grantedWealthDOs) {
        Map<String, Object> map = new HashMap<>();
        map.put("grantedWealthDOs", grantedWealthDOs);
        map.put("status", grantedWealthDOs.get(0).getStatus());
        return getSqlMapClientTemplate().update("granted_wealth.batchUpdateStatus", map);
    }

    @Override
    public int decreaseAmount(List<GrantedWealthDO> grantedWealthDOs) {
        Map map = new HashMap();
        map.put("grantedWealthDOs", grantedWealthDOs);
        return getSqlMapClientTemplate().update("granted_wealth.decreaseAmount", map);
    }

    @Override
    public Long addGrantedWealths(List<GrantedWealthDO> grantedWealthDOs) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("grantedWealthDOs", grantedWealthDOs);
        return (Long) getSqlMapClientTemplate().insert("granted_wealth.addGrantedWealths", params);
    }

    @Override
    public List<GrantedWealthDO> queryGrantedWealth(GrantedWealthQTO grantedWealthQTO) {
        grantedWealthQTO.setTotalCount((Integer)
                getSqlMapClientTemplate().queryForObject("granted_wealth.countOfQueryGrantedWealth", grantedWealthQTO));
        return getSqlMapClientTemplate().queryForList("granted_wealth.queryGrantedWealth", grantedWealthQTO);
    }

    
    /**
     * 客户管理 余额和嗨币的收入（2接口）
     */
	@Override
	public List<GrantedWealthDO> findCustomerGrantedPageList(
			GrantedWealthQTO grantedWealthQTO) {
		grantedWealthQTO.setTotalCount((Integer) getSqlMapClientTemplate().queryForObject("granted_wealth.findCustomerGrantedCount",grantedWealthQTO));
        return getSqlMapClientTemplate().queryForList("granted_wealth.findCustomerGrantedPageList", grantedWealthQTO);
	}
	
}