package com.mockuai.marketingcenter.core.dao;

import com.mockuai.marketingcenter.common.domain.qto.PropertyQTO;
import com.mockuai.marketingcenter.core.domain.PropertyDO;

import java.util.List;

public abstract interface PropertyDAO {

    public abstract long addProperty(PropertyDO paramPropertyDO);

    public abstract List<PropertyDO> queryProperty(PropertyQTO paramPropertyQTO);
}