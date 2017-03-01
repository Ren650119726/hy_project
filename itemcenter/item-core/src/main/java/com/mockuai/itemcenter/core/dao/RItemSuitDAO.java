package com.mockuai.itemcenter.core.dao;

import com.mockuai.itemcenter.common.domain.dto.RItemSuitDTO;
import com.mockuai.itemcenter.core.domain.RItemSuitDO;

import java.util.List;

public interface RItemSuitDAO {


    Long addRItemSuit(RItemSuitDO rItemSuitDO);

    List<RItemSuitDO> queryBySuitId(Long id, Long sellerId, String bizCode);

    List<RItemSuitDO> queryRItemSuit(RItemSuitDO rItemSuitDO);
}