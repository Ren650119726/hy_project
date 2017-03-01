package com.mockuai.itemcenter.core.dao;

import com.mockuai.itemcenter.common.domain.qto.ItemSettlementConfigQTO;
import com.mockuai.itemcenter.core.domain.ItemSettlementConfigDO;

import java.util.List;

public interface ItemSettlementConfigDAO {

    ItemSettlementConfigDO getItemSettlementConfig(ItemSettlementConfigDO query);

    Long enableItemSettlementConfig(ItemSettlementConfigDO query);

    Long disableItemSettlementConfig(ItemSettlementConfigDO query);

    Long deleteItemSettlementConfig(ItemSettlementConfigDO query);

    List<ItemSettlementConfigDO> queryItemSettlementConfig(ItemSettlementConfigQTO query);

    Long addItemSettlementConfig(ItemSettlementConfigDO itemSettlementConfigDO);

    Long updateItemSettlementConfig(ItemSettlementConfigDO itemSettlementConfigDO);
}