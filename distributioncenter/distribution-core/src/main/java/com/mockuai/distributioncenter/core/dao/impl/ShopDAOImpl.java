package com.mockuai.distributioncenter.core.dao.impl;

import com.mockuai.distributioncenter.common.domain.qto.DistShopQTO;
import com.mockuai.distributioncenter.core.dao.BaseDAO;
import com.mockuai.distributioncenter.core.dao.ShopDAO;
import com.mockuai.distributioncenter.core.domain.DistShopDO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lotmac on 16/3/9.
 */
@Repository
public class ShopDAOImpl extends BaseDAO implements ShopDAO {

    @Override
    public Long add(DistShopDO distShopDO) {
        return (Long) getSqlMapClientTemplate().insert("dist_shop.add", distShopDO);
    }

    @Override
    public List<DistShopDO> query(DistShopQTO distShopQTO) {
        return getSqlMapClientTemplate().queryForList("dist_shop.query", distShopQTO);
    }

    @Override
    public DistShopDO get(Long id) {
        return (DistShopDO) getSqlMapClientTemplate().queryForObject("dist_shop.get", id);
    }

    @Override
    public Integer update(DistShopDO distShopDO) {
        return getSqlMapClientTemplate().update("dist_shop.update", distShopDO);
    }

    @Override
    public DistShopDO getBySellerId(Long sellerId) {
        return (DistShopDO) getSqlMapClientTemplate().queryForObject("dist_shop.getBySellerId", sellerId);
    }

    @Override
    public void updateQrcodeUrl(DistShopQTO shop) {
        getSqlMapClientTemplate().update("dist_shop.updateQrcodeUrl", shop);
    }

}
