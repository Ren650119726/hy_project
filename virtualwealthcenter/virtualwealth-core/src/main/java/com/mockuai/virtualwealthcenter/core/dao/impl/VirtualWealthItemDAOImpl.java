package com.mockuai.virtualwealthcenter.core.dao.impl;

import com.mockuai.virtualwealthcenter.common.domain.qto.VirtualWealthItemQTO;
import com.mockuai.virtualwealthcenter.core.dao.VirtualWealthItemDAO;
import com.mockuai.virtualwealthcenter.core.domain.VirtualWealthItemDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by duke on 16/4/18.
 */
@Service
public class VirtualWealthItemDAOImpl extends SqlMapClientDaoSupport implements VirtualWealthItemDAO {
    @Override
    public Long add(com.mockuai.virtualwealthcenter.core.domain.VirtualWealthItemDO virtualWealthItemDO) {
        return (Long) getSqlMapClientTemplate().insert("virtual_wealth_item.add", virtualWealthItemDO);
    }

    @Override
    public List<com.mockuai.virtualwealthcenter.core.domain.VirtualWealthItemDO> query(VirtualWealthItemQTO virtualWealthItemQTO) {
        return (List<com.mockuai.virtualwealthcenter.core.domain.VirtualWealthItemDO>) getSqlMapClientTemplate().queryForList("virtual_wealth_item.query", virtualWealthItemQTO);
    }

    @Override
    public int delete(Long id) {
        return getSqlMapClientTemplate().update("virtual_wealth_item.delete", id);
    }

    @Override
    public Long totalCount(VirtualWealthItemQTO virtualWealthItemQTO) {
        return (Long) getSqlMapClientTemplate().queryForObject("virtual_wealth_item.totalCount", virtualWealthItemQTO);
    }

    @Override
    public com.mockuai.virtualwealthcenter.core.domain.VirtualWealthItemDO get(Long id) {
        return (com.mockuai.virtualwealthcenter.core.domain.VirtualWealthItemDO) getSqlMapClientTemplate().queryForObject("virtual_wealth_item.get", id);
    }

    @Override
    public int update(VirtualWealthItemDO virtualWealthItemDO) {
        return getSqlMapClientTemplate().update("virtual_wealth_item.update", virtualWealthItemDO);
    }
}