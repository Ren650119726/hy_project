package com.mockuai.itemcenter.core.manager;

import com.mockuai.itemcenter.common.domain.dto.SkuPropertyValueDTO;
import com.mockuai.itemcenter.core.domain.SkuPropertyValueDO;
import com.mockuai.itemcenter.core.exception.ItemException;

import java.util.List;

/**
 * Created by zengzhangqiang on 8/9/15.
 */
public interface SkuPropertyValueManager {
    /**
     * 新增sku属性可选值列表
     * @param skuPropertyValueDOs
     * @throws com.mockuai.itemcenter.core.exception.ItemException
     */
    public void addSkuPropertyValues(List<SkuPropertyValueDO> skuPropertyValueDOs) throws ItemException;

    /**
     * 更新指定sku属性可选值信息
     * @param skuPropertyValueDTO
     * @throws ItemException
     */
    public int updateSkuPropertyValue(SkuPropertyValueDTO skuPropertyValueDTO) throws ItemException;

    /**
     * 删除指定sku属性可选值
     * @param propertyValueIds
     * @throws ItemException
     */
    public int deleteSkuPropertyValues(SkuPropertyValueDO skuPropertyValueDO) throws ItemException;


    List<SkuPropertyValueDTO> querySkuPropertyValue(Long tmplId, String bizCode) throws ItemException;

    Long deleteSkuPropertyValue(Long id, String bizCode);
}
