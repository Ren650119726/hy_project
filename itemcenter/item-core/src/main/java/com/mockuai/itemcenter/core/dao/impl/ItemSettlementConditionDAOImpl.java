package com.mockuai.itemcenter.core.dao.impl;

import com.mockuai.itemcenter.common.domain.qto.ItemSettlementConditionQTO;
import com.mockuai.itemcenter.core.dao.ItemSettlementConditionDAO;
import com.mockuai.itemcenter.core.domain.ItemSettlementConditionDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemSettlementConditionDAOImpl extends SqlMapClientDaoSupport implements ItemSettlementConditionDAO {


    @Override
    public List<ItemSettlementConditionDO> queryItemSettlementCondition(ItemSettlementConditionQTO itemSettlementConditionQTO) {
        return getSqlMapClientTemplate().queryForList("ItemSettlementCondition.queryItemSettlementCondition",itemSettlementConditionQTO);
    }

    @Override
    public Long deleteItemSettlementConditionByConfig(Long id, String bizCode) {

        ItemSettlementConditionDO itemSettlementConditionDO = new ItemSettlementConditionDO();
        itemSettlementConditionDO.setParentId(id);
        itemSettlementConditionDO.setBizCode(bizCode);
        return Long.valueOf(getSqlMapClientTemplate().update("ItemSettlementCondition.deleteItemSettlementConditionByConfig",itemSettlementConditionDO));
    }

    @Override
    public Long addItemSettlementCondition(ItemSettlementConditionDO itemSettlementConditionDO) {
        return (Long) getSqlMapClientTemplate().insert("ItemSettlementCondition.addItemSettlementCondition",itemSettlementConditionDO);
    }
}