package com.mockuai.distributioncenter.core.dao;

import com.mockuai.distributioncenter.common.domain.qto.DistShopQTO;
import com.mockuai.distributioncenter.core.domain.DistShopDO;

import java.util.List;

/**
 * Created by lotmac on 16/3/9.
 */
public interface ShopDAO {
    /**
     * 添加店铺
     */
    Long add(DistShopDO distShopDO);

    /**
     * 查找店铺
     */
    List<DistShopDO> query(DistShopQTO distShopQTO);

    /**
     * 获得店铺
     */
    DistShopDO get(Long id);

    /**
     * 更新店铺信息
     */
    Integer update(DistShopDO distShopDO);

    /**
     * 通过卖家ID获得店铺
     */

    void updateQrcodeUrl(DistShopQTO shop);

    DistShopDO getBySellerId(Long sellerId);
}
