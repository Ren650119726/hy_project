package com.mockuai.itemcenter.core.dao;

import com.mockuai.itemcenter.common.domain.qto.RCategorySettlementQTO;
import com.mockuai.itemcenter.core.domain.RCategorySettlementDO;

import java.util.List;

public interface RCategorySettlementDAO {

    List<RCategorySettlementDO> queryRCategorySettlement(RCategorySettlementQTO query);

    Long addRCategorySettlement(RCategorySettlementDO rCategorySettlementDO);

    Long deleteRCategorySettlementByConfig(Long id, String bizCode);
}