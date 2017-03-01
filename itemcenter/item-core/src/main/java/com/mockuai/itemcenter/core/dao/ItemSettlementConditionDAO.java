package com.mockuai.itemcenter.core.dao;

import com.mockuai.itemcenter.common.domain.qto.ItemSettlementConditionQTO;
import com.mockuai.itemcenter.core.domain.ItemSettlementConditionDO;

import java.util.List;

/**
 * Created by yindingyu on 16/1/19.
 */
public interface ItemSettlementConditionDAO {
    List<ItemSettlementConditionDO> queryItemSettlementCondition(ItemSettlementConditionQTO itemSettlementConditionQTO);

    Long deleteItemSettlementConditionByConfig(Long id, String bizCode);

    Object addItemSettlementCondition(ItemSettlementConditionDO itemSettlementConditionDO);
}
