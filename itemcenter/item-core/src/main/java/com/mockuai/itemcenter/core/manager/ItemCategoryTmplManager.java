package com.mockuai.itemcenter.core.manager;

import com.mockuai.itemcenter.common.domain.dto.ItemCategoryDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemCategoryTmplDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCategoryTmplQTO;
import com.mockuai.itemcenter.core.exception.ItemException;

import java.util.List;

/**
 * Created by yindingyu on 16/3/1.
 */
public interface ItemCategoryTmplManager {
    public List<ItemCategoryTmplDTO> queryItemCategoryTmpl(ItemCategoryTmplQTO tmplQTO2) throws ItemException;

    Long addOrGetCategoryBytmplId(Long categoryTmplId, String bizCode) throws ItemException;


    Long addItemCategoryTmpl(ItemCategoryTmplDTO itemCategoryTmplDTO);


    Long addItemCategoryTmpl(List<ItemCategoryTmplDTO> itemCategoryTmplDTOList);
}
