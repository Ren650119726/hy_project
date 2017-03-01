package com.mockuai.itemcenter.core.dao.impl;

import com.mockuai.itemcenter.common.domain.dto.RItemSuitDTO;
import com.mockuai.itemcenter.core.dao.RItemSuitDAO;
import com.mockuai.itemcenter.core.domain.RItemSuitDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RItemSuitDAOImpl extends SqlMapClientDaoSupport implements RItemSuitDAO {


    @Override
    public Long addRItemSuit(RItemSuitDO rItemSuitDO) {
        return (Long) getSqlMapClientTemplate().insert("RItemSuit.addRItemSuit",rItemSuitDO);
    }

    @Override
    public List<RItemSuitDO> queryBySuitId(Long id, Long sellerId, String bizCode) {

        RItemSuitDO rItemSuitDO = new RItemSuitDO();
        rItemSuitDO.setBizCode(bizCode);
        rItemSuitDO.setSellerId(sellerId);
        rItemSuitDO.setSuitId(id);

        return getSqlMapClientTemplate().queryForList("RItemSuit.queryRItemSuit",rItemSuitDO);
    }

    @Override
    public List<RItemSuitDO> queryRItemSuit(RItemSuitDO rItemSuitDO) {
        return getSqlMapClientTemplate().queryForList("RItemSuit.queryRItemSuit",rItemSuitDO);
    }
}