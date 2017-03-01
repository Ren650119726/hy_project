package com.mockuai.distributioncenter.core.dao;

import com.mockuai.distributioncenter.common.domain.qto.ItemSkuDistPlanQTO;
import com.mockuai.distributioncenter.core.domain.ItemSkuDistPlanDO;

import java.util.List;

/**
 * Created by duke on 16/5/12.
 */
public interface ItemSkuDistPlanDAO {
    /**
     * 添加方案
     * @param itemSkuDistPlanDO
     * */
    Long add(ItemSkuDistPlanDO itemSkuDistPlanDO);

    /**
     * 通过SKU ID删除分拥方案
     * @param itemSkuId
     * */
    Integer deleteByItemSkuId(Long itemSkuId);

    /**
     * 通过商品ID获得分拥方案
     * @param itemSkuId
     * */
    ItemSkuDistPlanDO getByItemSkuId(Long itemSkuId);

    /**
     * 通过sku集合获得分拥方案
     * @author lizg
     * @param itemSkuDistPlanQTO
     * @return
     */
    List<ItemSkuDistPlanDO> getDistByItemSkuId(ItemSkuDistPlanQTO itemSkuDistPlanQTO);

    /**
     * 查询分拥方案
     * @param itemSkuDistPlanQTO
     * */
    List<ItemSkuDistPlanDO> query(ItemSkuDistPlanQTO itemSkuDistPlanQTO);

    /**
     * 更新分拥方案
     * @param itemSkuDistPlanDO
     * */
    Integer update(ItemSkuDistPlanDO itemSkuDistPlanDO);

    /**
     * 查询总量
     * @param itemSkuDistPlanQTO
     * */
    Long totalCount(ItemSkuDistPlanQTO itemSkuDistPlanQTO);

    /**
     * 通过商品ID获得所有sku级别的分拥比率
     * */
    List<ItemSkuDistPlanDO> getByItemId(Long itemId);
}
