package com.mockuai.distributioncenter.core.manager;


import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerQTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;

import java.util.List;

/**
 * Created by duke on 15/10/19.
 */
public interface SellerManager {
    /**
     * 添加
     * @param sellerDTO
     * */
    Long add(SellerDTO sellerDTO) throws DistributionException;

    /**
     * 查询
     * @param sellerQTO
     * */
    List<SellerDTO> query(SellerQTO sellerQTO) throws DistributionException;

    /**
     * 查询总量
     * @param sellerQTO
     * */
    Long totalCount(SellerQTO sellerQTO) throws DistributionException;

    /**
     * 通过ID获得
     * @param id
     * */
    SellerDTO get(Long id) throws DistributionException;

    /**
     * 通过用户ID获得
     * @param userId
     * */
    SellerDTO getByUserId(Long userId) throws DistributionException;

    /**
     * 更新分销商信息
     * @param sellerDTO
     * */
    Integer update(SellerDTO sellerDTO) throws DistributionException;

    /**
     * 通过用户ID批量查询
     * */
    List<SellerDTO> queryByUserIds(List<Long> userIds) throws DistributionException;

    /**
     * 通过邀请码获得卖家
     * */
    SellerDTO getByInviterCode(String inviterCode) throws DistributionException;

    /**
     * 通过用户ID更新
     * */
    Integer updateByUserId(Long userId, SellerDTO sellerDTO) throws DistributionException;
}
