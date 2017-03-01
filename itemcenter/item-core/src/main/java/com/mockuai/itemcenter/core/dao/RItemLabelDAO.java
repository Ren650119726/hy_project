package com.mockuai.itemcenter.core.dao;


import com.mockuai.itemcenter.common.domain.qto.RItemLabelQTO;
import com.mockuai.itemcenter.core.domain.ItemLabelDO;
import com.mockuai.itemcenter.core.domain.RItemLabelDO;

import java.util.List;

public interface RItemLabelDAO {


    List<RItemLabelDO> queryRItemLabel(RItemLabelQTO rItemLabelQTO);
    Long countRItemLabel(RItemLabelQTO rItemLabelQTO);

    Long addRItemLabel(RItemLabelDO rItemLabelDO);

    Long addRList(List<RItemLabelDO> list);

    Long deleteByItem(Long itemId);



    Long deleteByLabel(ItemLabelDO itemLabelDO);
}