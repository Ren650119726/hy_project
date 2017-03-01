package com.mockuai.distributioncenter.core.dao.impl;

import com.mockuai.distributioncenter.common.domain.qto.SellerQTO;
import com.mockuai.distributioncenter.core.dao.BaseDAO;
import com.mockuai.distributioncenter.core.dao.SellerDAO;
import com.mockuai.distributioncenter.core.domain.SellerDO;
import com.mockuai.distributioncenter.core.util.JsonUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by duke on 15/10/19.
 */
@Repository
public class SellerDAOImpl extends BaseDAO implements SellerDAO {
    private static final Logger log = LoggerFactory.getLogger(SellerDAOImpl.class);

    @Override
    public Long add(SellerDO sellerDO) {
        return (Long) getSqlMapClientTemplate().insert("dist_seller.add", sellerDO);
    }

    @Override
    public List<SellerDO> query(SellerQTO sellerQTO) {
    	sellerQTO.setTotalCount((Long) getSqlMapClientTemplate().queryForObject("dist_seller.totalCount", sellerQTO));
        return getSqlMapClientTemplate().queryForList("dist_seller.query", sellerQTO);
    }

    @Override
    public Long totalCount(SellerQTO sellerQTO) {
        return (Long) getSqlMapClientTemplate().queryForObject("dist_seller.totalCount", sellerQTO);
    }

    @Override
    public SellerDO get(Long id) {
        return (SellerDO) getSqlMapClientTemplate().queryForObject("dist_seller.get", id);
    }

    @Override
    public SellerDO getByUserId(Long userId) {
        SellerDO sellerDO =
                (SellerDO) getSqlMapClientTemplate().queryForObject("dist_seller.getByUserId", userId);
        log.info("get seller by userId: {}, result: {}", userId, JsonUtil.toJson(sellerDO));
        return sellerDO;
    }

    @Override
    public Integer update(SellerDO sellerDO) {
        return getSqlMapClientTemplate().update("dist_seller.update", sellerDO);
    }

    @Override
    public List<SellerDO> queryByUserIds(List<Long> userIds) {
        return getSqlMapClientTemplate().queryForList("dist_seller.queryByUserIds", userIds);
    }

    @Override
    public SellerDO getByInviterCode(String inviterCode) {
        return (SellerDO) getSqlMapClientTemplate().queryForObject("dist_seller.getByInviterCode", inviterCode);
    }

    @Override
    public Integer updateByUserId(Long userId, SellerDO sellerDO) {
        sellerDO.setUserId(userId);
        return getSqlMapClientTemplate().update("dist_seller.updateByUserId", sellerDO);
    }
}
