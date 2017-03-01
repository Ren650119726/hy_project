package com.mockuai.distributioncenter.core.service.action.distribution.finder;

import com.mockuai.distributioncenter.core.manager.SellerManager;
import com.mockuai.distributioncenter.core.manager.SellerRelationshipManager;

import java.util.Map;

/**
 * Created by duke on 16/5/16.
 */
public abstract class AbstractFinder<T> implements Finder<T> {
    // 缓存遍历过的卖家节点，减少再次遍历的IO操作，但是缓存的生命周期在一次分拥请求中
    protected Map<Long, T> cache;
    protected Map<Long, Long> treeMap;
    protected SellerRelationshipManager relationshipManager;
    protected SellerManager sellerManager;

    public AbstractFinder(SellerRelationshipManager relationshipManager, SellerManager sellerManager, Map<Long, T> cache, Map<Long, Long> treeMap) {
        this.cache = cache;
        this.treeMap = treeMap;
        this.relationshipManager = relationshipManager;
        this.sellerManager = sellerManager;
    }
}
