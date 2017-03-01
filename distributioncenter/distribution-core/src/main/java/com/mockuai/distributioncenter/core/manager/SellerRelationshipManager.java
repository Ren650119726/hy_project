package com.mockuai.distributioncenter.core.manager;


import com.mockuai.distributioncenter.common.domain.dto.SellerRelationshipDTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerRelationshipQTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;

import java.util.List;
import java.util.Map;

/**
 * Created by duke on 15/10/19.
 */
public interface SellerRelationshipManager {
    /**
     * 添加关系
     * @param sellerRelationshipDTO
     * */
    Long add(SellerRelationshipDTO sellerRelationshipDTO) throws DistributionException;

    /**
     * 删除关系
     * @param id
     * */
    Integer delete(Long id) throws DistributionException;

    /**
     * 更新关系
     * @param sellerRelationshipDTO
     * */
    Integer update(SellerRelationshipDTO sellerRelationshipDTO) throws DistributionException;

    /**
     * 查找关系
     * @param sellerRelationshipQTO
     * */
    List<SellerRelationshipDTO> query(SellerRelationshipQTO sellerRelationshipQTO) throws DistributionException;

    /**
     * 通过用户ID获得关系
     * @param userId
     * */
    SellerRelationshipDTO getByUserId(Long userId) throws DistributionException;

    /**
     * 获得关系
     * @param id
     * */
    SellerRelationshipDTO get(Long id) throws DistributionException;

    /**
     * 总量
     * */
    Long totalCount(SellerRelationshipQTO sellerRelationshipQTO) throws DistributionException;

    /**
     * 通过用户ID批量查询
     * */
    List<SellerRelationshipDTO> queryByUserIds(List<Long> userIds) throws DistributionException;

    /**
     * 查询直接下级用户ID列表
     * */
    List<Long> queryPosterityUserIds(SellerRelationshipQTO sellerRelationshipQTO) throws DistributionException;

    /**
     * 通过用户ID批量查询用户
     * */
    List<Map<String, Long>> queryTotalCountByUserIds(List<Long> userIds) throws DistributionException;
}
