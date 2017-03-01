package com.mockuai.distributioncenter.core.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.mockuai.distributioncenter.common.domain.qto.SellerLevelApplyQTO;
import com.mockuai.distributioncenter.core.dao.SellerLevelApplyDAO;
import com.mockuai.distributioncenter.core.domain.SellerLevelApplyDO;

/**
 * Created by yeliming on 16/5/18.
 */
@Component
public class SellerLevelApplyDAOImpl extends SqlMapClientDaoSupport implements SellerLevelApplyDAO {

    @Override
    public List<SellerLevelApplyDO> querySellerLevelApply(SellerLevelApplyQTO sellerLevelApplyQTO) {
    	sellerLevelApplyQTO.setTotalCount((Integer) getSqlMapClientTemplate().queryForObject("seller_level_apply.countOfSellerLevelApplyPacket", sellerLevelApplyQTO));
        return this.getSqlMapClientTemplate().queryForList("seller_level_apply.query_seller_level_apply", sellerLevelApplyQTO);
    }

    @Override
    public int updateSellerLevelApply(SellerLevelApplyDO sellerLevelApplyDO) {
        return this.getSqlMapClientTemplate().update("seller_level_apply.update_seller_level_apply", sellerLevelApplyDO);
    }
    
    @Override
    public Long addSellerLevelApply(SellerLevelApplyDO sellerLevelApplyDO) {
        return ((Long) getSqlMapClientTemplate().insert("seller_level_apply.add_seller_level_apply", sellerLevelApplyDO));
    }
    
    @Override
    public SellerLevelApplyDO getSellerLevelApply(SellerLevelApplyDO sellerLevelApplyDO) {
        return (SellerLevelApplyDO) getSqlMapClientTemplate().queryForObject("seller_level_apply.get_seller_level_apply", sellerLevelApplyDO);
    }
}
