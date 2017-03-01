package com.mockuai.itemcenter.core.manager;

import com.mockuai.itemcenter.common.domain.dto.ItemSearchDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;

import java.util.List;

/**
 * Created by yindingyu on 16/1/30.
 */
public interface ItemSalesVolumeManager {
    Long getItemSalesVolume(ItemSearchDTO itemSearchDTO) throws ItemException;

    Long getItemSalesVolume(Long itemId, String bizCode) throws ItemException;

    Long getItemSalesVolume(Long itemId, Long sellerId, String bizCode) throws ItemException;

    List<OrderItemDTO> updateItemSalesVolume(List<OrderItemDTO> orderItemDTOs) throws ItemException;
}
