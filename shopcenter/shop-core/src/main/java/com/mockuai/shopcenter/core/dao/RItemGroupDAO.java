package com.mockuai.shopcenter.core.dao;

import com.mockuai.shopcenter.core.domain.RItemGroupDO;
import com.mockuai.shopcenter.core.domain.RItemGroupDOExample;
import com.mockuai.shopcenter.domain.dto.RItemGroupDTO;
import com.mockuai.shopcenter.domain.dto.ShopItemGroupDTO;

import java.util.List;
import java.util.Map;

public interface RItemGroupDAO {

    Long addRItemGroup(RItemGroupDO rItemGroupDO);

    Long deleteRItemGroup(RItemGroupDO rItemGroupDO);

    Long getRItemGroup(RItemGroupDO rItemGroupDO);

    List<RItemGroupDO> queryRItemGroupByGroupId(RItemGroupDO rItemGroupDO);

    Long deleteRItemGroupByGroupId(RItemGroupDO rItemGroupDO);

    Map<Long, ShopItemGroupDTO> queryGroupItemCount(List<Long> groupIdList, Long sellerId, String bizCode);
}