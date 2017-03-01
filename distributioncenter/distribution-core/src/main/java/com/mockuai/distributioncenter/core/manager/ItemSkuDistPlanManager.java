package com.mockuai.distributioncenter.core.manager;

import com.mockuai.distributioncenter.common.domain.dto.ItemSkuDistPlanDTO;
import com.mockuai.distributioncenter.common.domain.qto.ItemSkuDistPlanQTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;

import java.util.List;

/**
 * Created by duke on 16/5/12.
 */
public interface ItemSkuDistPlanManager {
    /**
     * 添加分拥方案
     * @param itemDistPlanDTO
     * */
    Long add(ItemSkuDistPlanDTO itemDistPlanDTO) throws DistributionException;

    /**
     * 删除分拥方案
     * @param itemSkuId
     * */
    Integer deleteByItemSkuId(Long itemSkuId) throws DistributionException;

    /**
     * 更新分拥方案
     * @param itemDistPlanDTO
     * */
    Integer update(ItemSkuDistPlanDTO itemDistPlanDTO) throws DistributionException;

    /**
     * 获得分拥方案
     * @param itemSkuId
     * */
    ItemSkuDistPlanDTO getByItemSkuId(Long itemSkuId) throws DistributionException;

    /**
     * 查询分拥方案
     * @param ItemSkuDistPlanQTO
     * */
    List<ItemSkuDistPlanDTO> query(ItemSkuDistPlanQTO ItemSkuDistPlanQTO) throws DistributionException;

    /**
     * 查询总量
     * */
    Long totalCount(ItemSkuDistPlanQTO ItemSkuDistPlanQTO) throws DistributionException;

    /**
     * 通过商品ID查询
     * */
    List<ItemSkuDistPlanDTO> getByItemId(Long itemId) throws DistributionException;


    /**
     * 通过itemSkuIdList获取分拥方案
     * @param itemSkuDistPlanQTO
     * @return
     * @throws DistributionException
     */
    List<ItemSkuDistPlanDTO> getDistByItemSkuId(ItemSkuDistPlanQTO itemSkuDistPlanQTO) throws DistributionException;
}
