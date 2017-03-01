package com.mockuai.itemcenter.core.manager;

import com.mockuai.itemcenter.common.domain.dto.ItemDescTmplDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemDescTmplQTO;
import com.mockuai.itemcenter.core.exception.ItemException;

import java.util.List;

/**
 * Created by yindingyu on 16/3/8.
 */
public interface ItemDescTmplManager {
    public Long addItemDescTmpl(ItemDescTmplDTO itemDescTmplDTO) throws ItemException;

    Long updateItemDescTmpl(ItemDescTmplDTO itemDescTmplDTO) throws ItemException;

    ItemDescTmplDTO getItemDescTmpl(Long itemDescTmplId, Long sellerId, String bizCode) throws ItemException;

    Long deleteItemDescTmpl(Long itemDescTmplId, Long sellerId, String bizCode) throws ItemException;

    List<ItemDescTmplDTO> queryItemDescTmpl(ItemDescTmplQTO itemDescTmplQTO) throws ItemException;
}
