package com.mockuai.distributioncenter.core.dao.impl;

import com.mockuai.distributioncenter.common.domain.qto.SellerRelationshipQTO;
import com.mockuai.distributioncenter.core.dao.BaseDAO;
import com.mockuai.distributioncenter.core.dao.SellerRelationshipDAO;
import com.mockuai.distributioncenter.core.domain.SellerRelationshipDO;
import com.mockuai.distributioncenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by duke on 15/10/19.
 */
@Repository
public class SellerRelationshipDAOImpl extends BaseDAO implements SellerRelationshipDAO {
    private static final Logger log = LoggerFactory.getLogger(SellerRelationshipDAOImpl.class);

    @Override
    public Long add(SellerRelationshipDO sellerRelationshipDO) {
        Long id = (Long) getSqlMapClientTemplate().insert("dist_seller_relationship.add", sellerRelationshipDO);
        log.info("add relationship, data: {}, id: {}", JsonUtil.toJson(sellerRelationshipDO), id);
        return id;
    }

    @Override
    public Integer delete(Long id) {
        return getSqlMapClientTemplate().update("dist_seller_relationship.delete", id);
    }

    @Override
    public Integer update(SellerRelationshipDO sellerRelationshipDO) {
        return getSqlMapClientTemplate().update("dist_seller_relationship.update", sellerRelationshipDO);
    }

    @Override
    public List<SellerRelationshipDO> query(SellerRelationshipQTO sellerRelationshipQTO) {
        List<SellerRelationshipDO> relationshipDOs =
                getSqlMapClientTemplate().queryForList("dist_seller_relationship.query", sellerRelationshipQTO);
        log.info("query relationship, query: {}, result: {}",
                JsonUtil.toJson(sellerRelationshipQTO), JsonUtil.toJson(relationshipDOs));
        return relationshipDOs;
    }

    @Override
    public SellerRelationshipDO getByUserId(Long userId) {
        SellerRelationshipDO relationshipDO =
                (SellerRelationshipDO) getSqlMapClientTemplate()
                        .queryForObject("dist_seller_relationship.getByUserId", userId);
        log.info("get relationship by userId: {}, result: {}", userId, JsonUtil.toJson(relationshipDO));
        return relationshipDO;
    }

    @Override
    public SellerRelationshipDO get(Long id) {
        SellerRelationshipDO relationshipDO =
                (SellerRelationshipDO) getSqlMapClientTemplate().queryForObject("dist_seller_relationship.get", id);
        log.info("get relationship by id: {}, result: {}", id, JsonUtil.toJson(relationshipDO));
        return relationshipDO;
    }

    @Override
    public Long totalCount(SellerRelationshipQTO sellerRelationshipQTO) {
        return (Long) getSqlMapClientTemplate().queryForObject(
                "dist_seller_relationship.totalCount", sellerRelationshipQTO);
    }

    @Override
    public List<SellerRelationshipDO> queryByUserIds(List<Long> userIds) {
        List<SellerRelationshipDO> relationshipDOs =
                getSqlMapClientTemplate().queryForList("dist_seller_relationship.queryByUserIds", userIds);
        log.info("query by user id list: {}, result: {}", JsonUtil.toJson(userIds), JsonUtil.toJson(relationshipDOs));
        return relationshipDOs;
    }

    @Override
    public List<Long> queryPosterityUserIds(SellerRelationshipQTO sellerRelationshipQTO) {
        return getSqlMapClientTemplate().queryForList(
                "dist_seller_relationship.queryPosterityUserIds", sellerRelationshipQTO);
    }

    @Override
    public List<Map<String, Long>> queryTotalCountByUserIds(List<Long> userIds) {
        return getSqlMapClientTemplate().queryForList("dist_seller_relationship.queryTotalCountByUserIds", userIds);
    }
}
