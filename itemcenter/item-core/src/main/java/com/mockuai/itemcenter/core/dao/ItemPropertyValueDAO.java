package com.mockuai.itemcenter.core.dao;

import com.mockuai.itemcenter.core.domain.ItemPropertyValueDO;

import java.util.List;

/**
 * Created by zengzhangqiang on 8/9/15.
 */
public interface ItemPropertyValueDAO {
    /**
     * 新增商品属性可选值列表
     * @param itemPropertyValueDOs
     */
    public void addItemPropertyValues(List<ItemPropertyValueDO> itemPropertyValueDOs);

    /**
     * 更新指定商品属性可选值
     * @param itemPropertyValueDO
     * @return
     */
    public int updateItemPropertyValue(ItemPropertyValueDO itemPropertyValueDO);

    /**
     * 删除指定的商品属性可选值
     * @param itemPropertyValueDO
     */
    public int deleteItemPropertyValues(ItemPropertyValueDO itemPropertyValueDO);

    /**
     * 查询指定sku属性模板下的可选值列表
     * @param itemPropertyTmplId
     * @return
     */
    public List<ItemPropertyValueDO> queryItemPropertyValue(Long itemPropertyTmplId);
}
