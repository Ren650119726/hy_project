package com.mockuai.itemcenter.core.dao.impl;

import com.mockuai.itemcenter.common.domain.qto.ItemSalesVolumeQTO;
import com.mockuai.itemcenter.core.dao.ItemSalesVolumeDAO;
import com.mockuai.itemcenter.core.domain.ItemSalesVolumeDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class ItemSalesVolumeDAOImpl extends SqlMapClientDaoSupport implements ItemSalesVolumeDAO {


    @Override
    public Long addItemSalesVolume(ItemSalesVolumeDO itemSalesVolumeDO) {
        return (Long) getSqlMapClientTemplate().insert("ItemSalesVolumeDAO.addItemSalesVolume", itemSalesVolumeDO);
    }

    @Override
    public Long updateSalesVolume(ItemSalesVolumeDO itemSalesVolumeDO) {
        return Long.valueOf(getSqlMapClientTemplate().update("ItemSalesVolumeDAO.updateItemSalesVolume", itemSalesVolumeDO));
    }

    @Override
    public ItemSalesVolumeDO getItemSalesVolume(ItemSalesVolumeDO itemSalesVolumeDO) {
        return (ItemSalesVolumeDO) getSqlMapClientTemplate().queryForObject("ItemSalesVolumeDAO.getItemSalesVolume", itemSalesVolumeDO);
    }

    @Override
    public Map<Long, Long> querySalesVolume(ItemSalesVolumeQTO itemSalesVolumeQTO) {
        return (Map<Long, Long>) getSqlMapClientTemplate().queryForObject("ItemSalesVolumeDAO.querySalesVolume", itemSalesVolumeQTO);
    }

    @Override
    public ItemSalesVolumeDO getItemSalesVolumeByItemId(ItemSalesVolumeDO query) {
        return (ItemSalesVolumeDO) getSqlMapClientTemplate().queryForObject("ItemSalesVolumeDAO.getItemSalesVolumeByItemId", query);

    }
}