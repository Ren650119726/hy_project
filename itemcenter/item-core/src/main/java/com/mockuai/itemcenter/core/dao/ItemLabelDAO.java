package com.mockuai.itemcenter.core.dao;


import com.mockuai.itemcenter.common.domain.qto.ItemLabelQTO;
import com.mockuai.itemcenter.core.domain.ItemLabelDO;

import java.util.List;

public interface ItemLabelDAO {


    Long addItemLabel(ItemLabelDO itemLabelDO);

    Long updateItemLabel(ItemLabelDO itemLabelDO);

    ItemLabelDO getItemLabel(ItemLabelDO query);

    Long deleteItemLabel(ItemLabelDO query);

    List<ItemLabelDO> queryItemLabel(ItemLabelQTO itemLabelQTO);
}