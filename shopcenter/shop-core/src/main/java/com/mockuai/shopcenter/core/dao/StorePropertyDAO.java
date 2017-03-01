package com.mockuai.shopcenter.core.dao;

import com.mockuai.shopcenter.core.domain.StorePropertyDO;

import java.util.List;
import java.util.Map;

public interface StorePropertyDAO {

    StorePropertyDO getStoreProperty(StorePropertyDO query);

    Long addStoreProperty(StorePropertyDO query);

    Long updateStoreProperty(StorePropertyDO query);

    List<StorePropertyDO> queryStoreProperties(StorePropertyDO query);

    Map<String,String> queryStorePropertiesMap(List<String> keys, Long sellerId, Long storeId, String bizCode);

    Integer batchInsertProperty(List<Long> storeIds, Long sellerId, String key, String value, String bizCode);

    Integer deleteStoreProperties(StorePropertyDO query);
}