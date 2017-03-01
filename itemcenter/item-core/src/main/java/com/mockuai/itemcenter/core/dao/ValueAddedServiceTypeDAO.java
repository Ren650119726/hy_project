package com.mockuai.itemcenter.core.dao;


import com.mockuai.itemcenter.common.domain.dto.ValueAddedServiceDTO;
import com.mockuai.itemcenter.common.domain.qto.ValueAddedServiceTypeQTO;
import com.mockuai.itemcenter.core.domain.ValueAddedServiceTypeDO;
import com.mockuai.itemcenter.core.exception.ItemException;

import java.util.List;

public interface ValueAddedServiceTypeDAO {

    ValueAddedServiceTypeDO getValueAddedServiceType(ValueAddedServiceTypeDO query) throws ItemException;

    Long addValueAddedServiceType(ValueAddedServiceTypeDO serviceTypeDO);

    List<ValueAddedServiceTypeDO> queryValueAddedServiceType(ValueAddedServiceTypeQTO serviceTypeQTO);

    Long updateValueAddedServiceType(ValueAddedServiceTypeDO serviceTypeDO);

    Long deleteValueAddedServiceType(ValueAddedServiceTypeDO query);
}