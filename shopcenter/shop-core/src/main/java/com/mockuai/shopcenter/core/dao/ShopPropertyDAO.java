package com.mockuai.shopcenter.core.dao;


import com.mockuai.shopcenter.core.domain.ShopPropertyDO;

import java.util.List;
import java.util.Map;

public interface ShopPropertyDAO {

    ShopPropertyDO getShopProperty(ShopPropertyDO query);

    Long addShopProperty(ShopPropertyDO query);

    Long updateShopProperty(ShopPropertyDO query);

    Map<String,String> queryShopProperties(List<String> keys,Long sellerId,String bizCode);
}