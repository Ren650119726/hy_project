package com.mockuai.marketingcenter.core.manager;

import com.mockuai.marketingcenter.common.domain.dto.PropertyDTO;
import com.mockuai.marketingcenter.common.domain.qto.PropertyTmplQTO;
import com.mockuai.marketingcenter.core.domain.PropertyTmplDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;

import java.util.List;

public interface PropertyTmplManager {

    long addPropertyTmpl(PropertyTmplDO paramPropertyTmplDO)
            throws MarketingException;

    int deletePropertyTmpl(Long paramLong1, Long paramLong2)
            throws MarketingException;

    int updatePropertyTmpl(PropertyTmplDO paramPropertyTmplDO)
            throws MarketingException;

    List<PropertyTmplDO> queryPropertyTmpl(PropertyTmplQTO paramPropertyTmplQTO)
            throws MarketingException;

    long queryPropertyTmplCount(PropertyTmplQTO paramPropertyTmplQTO)
            throws MarketingException;

    /**
     * 验证当前创建活动是否包含了必须的属性
     *
     * @param propertyDTOs
     * @param ownerId      模版的属主，目前只是工具
     * @throws MarketingException
     */
    void validateMustIncludeProperty(List<PropertyDTO> propertyDTOs, Long ownerId) throws MarketingException;
}