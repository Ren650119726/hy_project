package com.mockuai.itemcenter.core.manager;

import com.mockuai.itemcenter.common.domain.dto.ItemPropertyValueDTO;
import com.mockuai.itemcenter.core.domain.ItemPropertyValueDO;
import com.mockuai.itemcenter.core.exception.ItemException;

import java.util.List;

/**
 * Created by zengzhangqiang on 8/9/15.
 */
public interface ItemPropertyValueManager {
    /**
     * 新增商品属性可选值列表
     * @param itemPropertyValueDOs
     * @throws ItemException
     */
    public void addItemPropertyValues(List<ItemPropertyValueDO> itemPropertyValueDOs) throws ItemException;

    /**
     * 更新指定商品属性可选值
     * @param itemPropertyValueDO
     * @return
     * @throws ItemException
     */
    public int updateItemPropertyValue(ItemPropertyValueDO itemPropertyValueDO) throws ItemException;

    /**
     * 删除指定的商品属性可选值
     * @param propertyValueIds
     * @throws ItemException
     */
    public int deleteItemPropertyValues(ItemPropertyValueDO propertyValueIds) throws ItemException;

    /**
     * 查询指定商品属性模板下的可选值列表
     * @param itemPropertyTmplId
     * @return
     * @throws ItemException
     */
    public List<ItemPropertyValueDO> queryItemPropertyValue(Long itemPropertyTmplId) throws ItemException;
}
