package com.mockuai.distributioncenter.core.dao.impl;

import com.mockuai.distributioncenter.common.domain.qto.ItemDistPlanQTO;
import com.mockuai.distributioncenter.core.dao.BaseDAO;
import com.mockuai.distributioncenter.core.dao.ItemDistPlanDAO;
import com.mockuai.distributioncenter.core.domain.ItemDistPlanDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by duke on 15/10/27.
 */
@Repository
public class ItemDistPlanDAOImpl extends BaseDAO implements ItemDistPlanDAO {
    @Override
    public Long add(ItemDistPlanDO itemDistPlanDO) {
        return (Long) getSqlMapClientTemplate().insert("item_dist_plan.add", itemDistPlanDO);
    }

    @Override
    public Integer deleteByItemId(Long itemId) {
        return getSqlMapClientTemplate().delete("item_dist_plan.deleteByItemId", itemId);
    }

    @Override
    public ItemDistPlanDO getByItemAndLevel(Long itemId, Integer level) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("itemId", itemId);
        data.put("level", level);
        return (ItemDistPlanDO) getSqlMapClientTemplate().queryForObject("item_dist_plan.getByItemAndLevel", data);
    }

    @Override
    public List<ItemDistPlanDO> query(ItemDistPlanQTO itemDistPlanQTO) {
        return getSqlMapClientTemplate().queryForList("item_dist_plan.query", itemDistPlanQTO);
    }

    @Override
    public Integer update(ItemDistPlanDO itemDistPlanDO) {
        return getSqlMapClientTemplate().update("item_dist_plan.update", itemDistPlanDO);
    }

    @Override
    public Long totalCount(ItemDistPlanQTO itemDistPlanQTO) {
        return (Long) getSqlMapClientTemplate().queryForObject("item_dist_plan.totalCount", itemDistPlanQTO);
    }
}
