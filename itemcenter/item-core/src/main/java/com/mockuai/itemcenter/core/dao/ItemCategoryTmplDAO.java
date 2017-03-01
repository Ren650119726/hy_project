package com.mockuai.itemcenter.core.dao;

import com.mockuai.itemcenter.common.domain.dto.ItemCategoryTmplDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCategoryTmplQTO;
import com.mockuai.itemcenter.core.domain.ItemCategoryTmplDO;

import java.util.List;

/**
 * Created by yindingyu on 16/3/1.
 */
public interface ItemCategoryTmplDAO {
    List<ItemCategoryTmplDO> queryItemCategoryTmpl(ItemCategoryTmplQTO tmplQTO);

    ItemCategoryTmplDO getItemCategoryTmpl(Long topId);

    Long addItemCategoryTmpl(ItemCategoryTmplDO itemCategoryTmplDO);
}
