package com.mockuai.distributioncenter.core.dao;

import com.mockuai.distributioncenter.common.domain.qto.ItemDistPlanQTO;
import com.mockuai.distributioncenter.core.domain.ItemDistPlanDO;

import java.util.List;

/**
 * Created by duke on 15/10/27.
 */
public interface ItemDistPlanDAO {
    /**
     * 添加方案
     * @param itemDistPlanDO
     * */
    Long add(ItemDistPlanDO itemDistPlanDO);

    /**
     * 通过商品ID删除分拥方案
     * @param itemId
     * */
    Integer deleteByItemId(Long itemId);

    /**
     * 通过商品和等级ID获得分拥方案
     * @param itemId
     * */
    ItemDistPlanDO getByItemAndLevel(Long itemId, Integer level);

    /**
     * 查询分拥方案
     * @param itemDistPlanQTO
     * */
    List<ItemDistPlanDO> query(ItemDistPlanQTO itemDistPlanQTO);

    /**
     * 更新分拥方案
     * @param itemDistPlanDO
     * */
    Integer update(ItemDistPlanDO itemDistPlanDO);

    /**
     * 查询总量
     * @param itemDistPlanQTO
     * */
    Long totalCount(ItemDistPlanQTO itemDistPlanQTO);
}
