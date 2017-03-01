package com.mockuai.itemcenter.core.dao;

import com.mockuai.itemcenter.common.domain.qto.RShopSettlementQTO;
import com.mockuai.itemcenter.core.domain.RShopSettlementDO;

import java.util.List;

public interface RShopSettlementDAO {

    List<RShopSettlementDO> queryRShopSettlement(RShopSettlementQTO query);

    Long addRShopSettlement(RShopSettlementDO rShopSettlementDO);

    Long deleteRShopSettlementByConfig(Long id, String bizCode);
}