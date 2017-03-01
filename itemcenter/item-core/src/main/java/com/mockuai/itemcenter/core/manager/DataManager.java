package com.mockuai.itemcenter.core.manager;

import java.util.Map;

/**
 * Created by yindingyu on 16/1/27.
 */
public interface DataManager {

    public void buriedPoint(Long sellerId, String key, Map<String, String> params, Long buriedTime, String appKey);
}
