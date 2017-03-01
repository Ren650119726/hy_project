package com.mockuai.itemcenter.core.manager;

import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.core.exception.ItemException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by yindingyu on 15/12/15.
 */
public interface SpecialItemExtraInfoManager {
    Object getSpecialItemExtraInfo(Long skuId, Long sellerId,Long userId, Integer itemType,String appKey) throws ItemException;

    Map<Long,Object> querySpecialItemExtraInfo(ItemQTO itemQTO, String appKey) throws ItemException;
}
