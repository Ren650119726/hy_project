package com.mockuai.distributioncenter.core.dao.impl;

import com.mockuai.distributioncenter.common.domain.qto.SellerConfigQTO;
import com.mockuai.distributioncenter.core.dao.SellerConfigDAO;
import com.mockuai.distributioncenter.core.domain.SellerConfigDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by yeliming on 16/5/18.
 */
@Component
public class SellerConfigDAOImpl extends SqlMapClientDaoSupport implements SellerConfigDAO {
    @Override
    public Long addSellerConfig(SellerConfigDO sellerConfigDO) {
        return (Long) this.getSqlMapClientTemplate().insert("seller_config.add_seller_config", sellerConfigDO);
    }

    @Override
    public SellerConfigDO getSellerConfig(Long id) {
        return (SellerConfigDO) this.getSqlMapClientTemplate().queryForObject("seller_config.get_seller_config", id);
    }

    @Override
    public List<SellerConfigDO> querySellerConfig(SellerConfigQTO sellerConfigQTO) {
        return this.getSqlMapClientTemplate().queryForList("seller_config.query_seller_config", sellerConfigQTO);
    }

    @Override
    public int updateSellerConfig(SellerConfigDO sellerConfigDO) {
        return this.getSqlMapClientTemplate().update("seller_config.update_seller_config", sellerConfigDO);
    }
}
