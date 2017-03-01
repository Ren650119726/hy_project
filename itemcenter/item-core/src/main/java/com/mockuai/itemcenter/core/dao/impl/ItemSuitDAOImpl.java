package com.mockuai.itemcenter.core.dao.impl;

import com.mockuai.itemcenter.common.domain.qto.ItemSuitQTO;
import com.mockuai.itemcenter.core.dao.ItemSuitDAO;
import com.mockuai.itemcenter.core.domain.FreightTemplateDO;
import com.mockuai.itemcenter.core.domain.ItemDO;
import com.mockuai.itemcenter.core.domain.ItemSuitDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class ItemSuitDAOImpl extends SqlMapClientDaoSupport implements ItemSuitDAO {


    @Override
    public ItemSuitDO getItemSuit(Long id, Long sellerId, String bizCode) {

        ItemSuitDO itemSuitDO = new ItemSuitDO();
        itemSuitDO.setId(id);
        itemSuitDO.setSellerId(sellerId);
        itemSuitDO.setBizCode(bizCode);

        return (ItemSuitDO) getSqlMapClientTemplate().queryForObject("ItemSuit.getItemSuit",itemSuitDO);
    }

    @Override
    public ItemSuitDO getItemSuitByItemId(Long itemId, Long sellerId, String bizCode) {
        ItemSuitDO itemSuitDO = new ItemSuitDO();
        itemSuitDO.setSuitId(itemId);
        itemSuitDO.setSellerId(sellerId);
        itemSuitDO.setBizCode(bizCode);

        return (ItemSuitDO) getSqlMapClientTemplate().queryForObject("ItemSuit.getItemSuitByItemId",itemSuitDO);
    }

    @Override
    public List<ItemSuitDO> queryItemSuit(ItemSuitQTO itemSuitQTO) {
        return getSqlMapClientTemplate().queryForList("ItemSuit.queryItemSuit",itemSuitQTO);
    }

    @Override
    public Integer addItemSuit(ItemSuitDO itemSuitDO) {
        return (Integer) getSqlMapClientTemplate().insert("ItemSuit.addItemSuit",itemSuitDO);
    }

    @Override
    public Long disableItemSuit(Long itemId, Long sellerId, String bizCode) {

        ItemSuitDO itemSuitDO = new ItemSuitDO();
        itemSuitDO.setSellerId(sellerId);
        itemSuitDO.setSuitId(itemId);
        itemSuitDO.setBizCode(bizCode);
        Long result = Long.valueOf(getSqlMapClientTemplate().update("ItemSuit.disableItemSuit", itemSuitDO));

        return result;


        //return Long.valueOf(getSqlMapClientTemplate().update("ItemSuit.disableItemSuit",itemSuitDO));

    }

    @Override
    public void increaseSuitSalesVolume(ItemSuitDO itemSuitDO){
         getSqlMapClientTemplate().update("ItemSuit.increaseSuitSalesVolume",itemSuitDO);
    }

    @Override
    public List<ItemSuitDO> queryItemSuitByLifecycle(ItemSuitQTO itemSuitQTO) {

        if (null != itemSuitQTO.getNeedPaging()) {
            Integer totalCount = (Integer) getSqlMapClientTemplate()
                    .queryForObject("ItemSuit.countItemSuitByLifecycle", itemSuitQTO);// 总记录数
            itemSuitQTO.setTotalCount(totalCount);
            if (totalCount == 0) {
                return Collections.emptyList();
            } else {
                itemSuitQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
            }
            totalCount = null;
        }

        return getSqlMapClientTemplate().queryForList("ItemSuit.queryItemSuitByLifecycle",itemSuitQTO);
    }
}