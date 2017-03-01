package com.mockuai.itemcenter.core.dao;

import com.mockuai.itemcenter.common.domain.dto.SkuPropertyValueDTO;
import com.mockuai.itemcenter.common.domain.qto.SkuPropertyValueQTO;
import com.mockuai.itemcenter.core.domain.SkuPropertyValueDO;

import java.util.List;

/**
 * Created by zengzhangqiang on 8/9/15.
 */
public interface SkuPropertyValueDAO {
    /**
     * 新增sku属性可选值列表
     * @param skuPropertyValueDOs
     * @throws com.mockuai.itemcenter.core.exception.ItemException
     */
    public void addSkuPropertyValues(List<SkuPropertyValueDO> skuPropertyValueDOs);

    /**
     * 更新指定sku属性可选值信息
     * @param skuPropertyValueDO
     * @throws ItemException
     */
    public int updateSkuPropertyValue(SkuPropertyValueDO skuPropertyValueDO);

    /**
     * 删除指定sku属性可选值
     * @param propertyValueIds
     * @param skuPropertyValueDTO
     * @throws ItemException
     */
    public int deleteSkuPropertyValues(SkuPropertyValueDO skuPropertyValueDTO);


    List<SkuPropertyValueDO> querySkuPropertyValue(SkuPropertyValueQTO skuPropertyValueQTO);

    Long deleteSkuPropertyValue(SkuPropertyValueDO query);
}
