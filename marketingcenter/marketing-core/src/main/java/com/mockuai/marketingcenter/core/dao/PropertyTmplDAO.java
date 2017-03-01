package com.mockuai.marketingcenter.core.dao;

import com.mockuai.marketingcenter.common.domain.qto.PropertyTmplQTO;
import com.mockuai.marketingcenter.core.domain.PropertyTmplDO;

import java.util.List;

public abstract interface PropertyTmplDAO {

    public abstract long addPropertyTmpl(PropertyTmplDO paramPropertyTmplDO);

    public abstract int deletePropertyTmpl(Long paramLong1, Long paramLong2);

    public abstract int updatePropertyTmpl(PropertyTmplDO paramPropertyTmplDO);

    public abstract List<PropertyTmplDO> queryPropertyTmpl(PropertyTmplQTO paramPropertyTmplQTO);

    public abstract long queryPropertyTmplCount(PropertyTmplQTO paramPropertyTmplQTO);
}