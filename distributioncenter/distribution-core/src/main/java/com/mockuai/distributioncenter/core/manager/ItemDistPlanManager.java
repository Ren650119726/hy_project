package com.mockuai.distributioncenter.core.manager;


import com.mockuai.distributioncenter.common.domain.dto.ItemDistPlanDTO;
import com.mockuai.distributioncenter.common.domain.qto.ItemDistPlanQTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;

import java.util.List;

/**
 * Created by duke on 15/10/27.
 */
public interface ItemDistPlanManager {
    /**
     * 添加分拥方案
     * @param itemDistPlanDTO
     * */
    Long add(ItemDistPlanDTO itemDistPlanDTO) throws DistributionException;

    /**
     * 删除分拥方案
     * @param itemId
     * */
    Integer deleteByItemId(Long itemId) throws DistributionException;

    /**
     * 更新分拥方案
     * @param itemDistPlanDTO
     * */
    Integer update(ItemDistPlanDTO itemDistPlanDTO) throws DistributionException;

    /**
     * 获得分拥方案
     * @param itemId
     * */
    ItemDistPlanDTO getByItemAndLevel(Long itemId, Integer level) throws DistributionException;

    /**
     * 查询分拥方案
     * @param itemDistPlanQTO
     * */
    List<ItemDistPlanDTO> query(ItemDistPlanQTO itemDistPlanQTO) throws DistributionException;

    /**
     * 查询总量
     * */
    Long totalCount(ItemDistPlanQTO itemDistPlanQTO) throws DistributionException;
}
