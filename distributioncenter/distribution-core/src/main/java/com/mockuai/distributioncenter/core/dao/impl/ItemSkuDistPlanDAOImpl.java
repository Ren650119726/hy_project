package com.mockuai.distributioncenter.core.dao.impl;

import com.mockuai.distributioncenter.common.domain.qto.ItemSkuDistPlanQTO;
import com.mockuai.distributioncenter.core.dao.BaseDAO;
import com.mockuai.distributioncenter.core.dao.ItemSkuDistPlanDAO;
import com.mockuai.distributioncenter.core.domain.ItemSkuDistPlanDO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by duke on 16/5/12.
 */
@Repository
public class ItemSkuDistPlanDAOImpl extends BaseDAO implements ItemSkuDistPlanDAO {
    @Override
    public Long add(ItemSkuDistPlanDO itemSkuDistPlanDO) {
        return (Long) getSqlMapClientTemplate().insert("item_sku_dist_plan.add", itemSkuDistPlanDO);
    }

    @Override
    public Integer deleteByItemSkuId(Long itemSkuId) {
        return getSqlMapClientTemplate().delete("item_sku_dist_plan.deleteByItemSkuId", itemSkuId);
    }

    @Override
    public ItemSkuDistPlanDO getByItemSkuId(Long itemSkuId) {
        return (ItemSkuDistPlanDO) getSqlMapClientTemplate().queryForObject("item_sku_dist_plan.getByItemSkuId", itemSkuId);
    }

    @Override
    public List<ItemSkuDistPlanDO> getDistByItemSkuId(ItemSkuDistPlanQTO itemSkuDistPlanQTO) {
        return getSqlMapClientTemplate().queryForList("item_sku_dist_plan.getDistByItemSkuId",itemSkuDistPlanQTO);
    }

    @Override
    public List<ItemSkuDistPlanDO> query(ItemSkuDistPlanQTO itemSkuDistPlanQTO) {
        return getSqlMapClientTemplate().queryForList("item_sku_dist_plan.query", itemSkuDistPlanQTO);
    }

    @Override
    public Integer update(ItemSkuDistPlanDO itemSkuDistPlanDO) {
        return getSqlMapClientTemplate().update("item_sku_dist_plan.update", itemSkuDistPlanDO);
    }

    @Override
    public Long totalCount(ItemSkuDistPlanQTO itemSkuDistPlanQTO) {
        return (Long) getSqlMapClientTemplate().queryForObject("item_sku_dist_plan.totalCount", itemSkuDistPlanQTO);
    }

    @Override
    public List<ItemSkuDistPlanDO> getByItemId(Long itemId) {
        return getSqlMapClientTemplate().queryForList("item_sku_dist_plan.getByItemId", itemId);
    }
}
