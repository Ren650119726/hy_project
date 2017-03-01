package com.mockuai.virtualwealthcenter.core.dao.impl;

import com.mockuai.virtualwealthcenter.common.domain.qto.VirtualWealthQTO;
import com.mockuai.virtualwealthcenter.core.dao.VirtualWealthDAO;
import com.mockuai.virtualwealthcenter.core.domain.VirtualWealthDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VirtualWealthDAOImpl extends SqlMapClientDaoSupport implements VirtualWealthDAO {

    public long addVirtualWealth(VirtualWealthDO virtualWealthDO) {

        return ((Long) getSqlMapClientTemplate().insert("virtual_wealth.addVirtualWealth", virtualWealthDO)).longValue();
    }

    public int deleteVirtualWealth(long id, long userId) {

        Map params = new HashMap();
        params.put("userId", Long.valueOf(userId));
        params.put("id", Long.valueOf(id));
        return getSqlMapClientTemplate().update("virtual_wealth.deleteVirtualWealth", params);
    }

    public VirtualWealthDO getVirtualWealth(long id, long userId) {

        Map params = new HashMap();
        params.put("userId", Long.valueOf(userId));
        params.put("id", Long.valueOf(id));
        return (VirtualWealthDO) getSqlMapClientTemplate().queryForObject("virtual_wealth.getVirtualWealth", params);
    }

    @Override
    public VirtualWealthDO getVirtualWealth(VirtualWealthDO virtualWealthDO) {
        return (VirtualWealthDO) getSqlMapClientTemplate().queryForObject("virtual_wealth.getVirtualWealth", virtualWealthDO);
    }

    @Override
    public List<VirtualWealthDO> queryVirtualWealth(VirtualWealthQTO virtualWealthQTO) {

        return (List<VirtualWealthDO>) getSqlMapClientTemplate().queryForList("virtual_wealth.queryVirtualWealth", virtualWealthQTO);
    }

    @Override
    public int increaseVirtualWealth(long id, long amount) {
        Map params = new HashMap();
        params.put("id", id);
        params.put("amount", amount);
        return getSqlMapClientTemplate().update("virtual_wealth.increaseVirtualWealth", params);
    }

    @Override
    public int increaseGrantedVirtualWealth(long id, long amount) {
        Map params = new HashMap();
        params.put("id", id);
        params.put("grantedAmount", amount);
        return getSqlMapClientTemplate().update("virtual_wealth.increaseGrantedVirtualWealth", params);
    }

    @Override
    public int updateVirtualWealth(VirtualWealthDO virtualWealthDO) {
        return getSqlMapClientTemplate().update("virtual_wealth.updateVirtualWealth", virtualWealthDO);
    }
}