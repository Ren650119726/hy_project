package com.mockuai.distributioncenter.core.dao.impl;

import com.mockuai.distributioncenter.core.dao.BaseDAO;
import com.mockuai.distributioncenter.core.dao.CollectionShopDAO;
import com.mockuai.distributioncenter.core.domain.CollectionShopDO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by duke on 16/5/21.
 */
@Repository
public class CollectionShopDAOImpl extends BaseDAO implements CollectionShopDAO {
    @Override
    public Long add(CollectionShopDO collectionShopDO) {
        return (Long) getSqlMapClientTemplate().insert("collection_shop.add", collectionShopDO);
    }

    @Override
    public int delete(Long id) {
        return getSqlMapClientTemplate().update("collection_shop.delete", id);
    }

    @Override
    public List<CollectionShopDO> queryByUserId(Long userId) {
        return getSqlMapClientTemplate().queryForList("collection_shop.queryByUserId", userId);
    }
}
