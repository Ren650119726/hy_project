package com.mockuai.itemcenter.core.dao;

import com.mockuai.itemcenter.common.domain.qto.ValueAddedServiceQTO;
import com.mockuai.itemcenter.core.domain.ValueAddedServiceDO;

import java.util.List;

public interface ValueAddedServiceDAO {

    List<ValueAddedServiceDO> queryValueAddedService(ValueAddedServiceQTO valueAddedServiceQTO);

    Long addValueAddedService(ValueAddedServiceDO valueAddedServiceDO);

    ValueAddedServiceDO getValueAddedService(ValueAddedServiceDO valueAddedServiceDO);

    Long deleteValueAddedServiceByType(Long id, Long sellerId, String bizCode);

    Long deleteValueAddedService(ValueAddedServiceDO query);

    Long updateValueAddedService(ValueAddedServiceDO valueAddedServiceDO);
}