package com.mockuai.itemcenter.core.manager;

import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemLabelDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemLabelQTO;
import com.mockuai.itemcenter.common.domain.qto.RItemLabelQTO;
import com.mockuai.itemcenter.core.exception.ItemException;

import java.util.List;

/**
 * Created by yindingyu on 15/12/4.
 */
public interface ItemLabelManager {

    List<ItemLabelDTO> queryItemLabelsByItem(ItemDTO itemDTO) throws ItemException;

    Long addItemLabel(ItemLabelDTO itemLabelDTO) throws ItemException;

    Long updateItemLabel(ItemLabelDTO itemLabelDTO) throws ItemException;

    ItemLabelDTO getItemLabel(Long itemLabelId, Long sellerId, String bizCode) throws ItemException;

    Long deleteItemLabel(Long itemLabelId, Long sellerId, String bizCode) throws ItemException;

    List<ItemLabelDTO> queryItemLabel(ItemLabelQTO itemLabelQTO) throws ItemException;

    Long countRItemLabel(RItemLabelQTO rItemLabelQTO);

    Long addRItemLabelList(ItemDTO itemDTO) throws  ItemException;
}
