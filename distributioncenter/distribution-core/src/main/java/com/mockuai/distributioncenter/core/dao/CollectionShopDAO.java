package com.mockuai.distributioncenter.core.dao;

import com.mockuai.distributioncenter.core.domain.CollectionShopDO;

import java.util.List;

/**
 * Created by duke on 16/5/21.
 */
public interface CollectionShopDAO {
    /**
     * 添加
     * */
    Long add(CollectionShopDO collectionShopDO);

    /**
     * 删除
     * */
    int delete(Long id);

    /**
     * 通过用户ID查询
     * */
    List<CollectionShopDO> queryByUserId(Long userId);
}
