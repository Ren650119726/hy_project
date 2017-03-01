package com.mockuai.itemcenter.core.dao;

import com.mockuai.itemcenter.common.domain.qto.ItemDescTmplQTO;
import com.mockuai.itemcenter.core.domain.ItemDescTmplDO;

import java.util.List;

public interface ItemDescTmplDAO {

    Object addItemDescTmpl(ItemDescTmplDO itemDescTmplDO);

    Long updateItemDescTmpl(ItemDescTmplDO itemDescTmplDO);

    ItemDescTmplDO getItemDescTmpl(ItemDescTmplDO query);

    Long deleteItemDescTmpl(ItemDescTmplDO query);

    List<ItemDescTmplDO> queryItemDescTmpl(ItemDescTmplQTO itemDescTmplQTO);
}