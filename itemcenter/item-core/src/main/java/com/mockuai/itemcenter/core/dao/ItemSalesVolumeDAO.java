package com.mockuai.itemcenter.core.dao;

import com.mockuai.itemcenter.common.domain.qto.ItemSalesVolumeQTO;
import com.mockuai.itemcenter.core.domain.ItemSalesVolumeDO;

import java.util.Map;

public interface ItemSalesVolumeDAO {

    Long addItemSalesVolume(ItemSalesVolumeDO itemSalesVolumeDO);

    Long updateSalesVolume(ItemSalesVolumeDO itemSalesVolumeDO);

    ItemSalesVolumeDO getItemSalesVolume(ItemSalesVolumeDO itemSalesVolumeDO);

    Map<Long, Long> querySalesVolume(ItemSalesVolumeQTO itemSalesVolumeQTO);

    ItemSalesVolumeDO getItemSalesVolumeByItemId(ItemSalesVolumeDO query);
}