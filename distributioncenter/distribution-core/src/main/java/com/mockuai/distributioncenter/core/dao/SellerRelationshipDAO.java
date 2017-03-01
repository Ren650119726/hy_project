package com.mockuai.distributioncenter.core.dao;

import com.mockuai.distributioncenter.common.domain.qto.SellerRelationshipQTO;
import com.mockuai.distributioncenter.core.domain.SellerRelationshipDO;

import java.util.List;
import java.util.Map;

/**
 * Created by duke on 15/10/19.
 */
public interface SellerRelationshipDAO {
    /**
     * 添加关系
     * @param sellerRelationshipDO
     * */
    Long add(SellerRelationshipDO sellerRelationshipDO);

    /**
     * 删除关系
     * @param id
     * */
    Integer delete(Long id);

    /**
     * 更新关系
     * @param sellerRelationshipDO
     * */
    Integer update(SellerRelationshipDO sellerRelationshipDO);

    /**
     * 查询关系
     * @param sellerRelationshipQTO
     * */
    List<SellerRelationshipDO> query(SellerRelationshipQTO sellerRelationshipQTO);

    /**
     * 通过用户ID获得关系中的节点
     * @param userId
     * */
    SellerRelationshipDO getByUserId(Long userId);

    /**
     * 获得分销关系
     * @param id
     * */
    SellerRelationshipDO get(Long id);

    /**
     * 关系总量
     * @param sellerRelationshipQTO
     * */
    Long totalCount(SellerRelationshipQTO sellerRelationshipQTO);

    /**
     * 通过用户ID批量查询关系
     * @param userIds
     * */
    List<SellerRelationshipDO> queryByUserIds(List<Long> userIds);

    /**
     * 获得直接下级用户ID列表
     * */
    List<Long> queryPosterityUserIds(SellerRelationshipQTO sellerRelationshipQTO);

    /**
     * 批量获得下级数量
     * */
    List<Map<String, Long>> queryTotalCountByUserIds(List<Long> userIds);
}
