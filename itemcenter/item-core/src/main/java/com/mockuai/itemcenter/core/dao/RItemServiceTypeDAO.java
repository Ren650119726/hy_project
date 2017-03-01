package com.mockuai.itemcenter.core.dao;


import com.mockuai.itemcenter.common.domain.qto.RItemServiceTypeQTO;
import com.mockuai.itemcenter.core.domain.RItemServiceTypeDO;
import com.mockuai.itemcenter.core.domain.RItemSuitDO;
import com.mockuai.itemcenter.core.domain.ValueAddedServiceTypeDO;

import java.util.List;

public interface RItemServiceTypeDAO {

    List<RItemServiceTypeDO> queryRItemSuitDO(RItemServiceTypeQTO rItemServiceTypeQTO);

    Long addRItemServiceType(RItemServiceTypeDO rItemServiceTypeDO);

    Long deleteRItemServiceByType(ValueAddedServiceTypeDO serviceTypeDO);
}