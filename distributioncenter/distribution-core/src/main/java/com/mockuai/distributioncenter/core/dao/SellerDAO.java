package com.mockuai.distributioncenter.core.dao;

import com.mockuai.distributioncenter.common.domain.qto.SellerQTO;
import com.mockuai.distributioncenter.core.domain.SellerDO;

import java.util.List;

/**
 * Created by duke on 15/10/19.
 */
public interface SellerDAO {
    /**
     * 添加卖家
     * @param sellerDO
     * */
    Long add(SellerDO sellerDO);

    /**
     * 查询卖家
     * @param sellerQTO
     * */
    List<SellerDO> query(SellerQTO sellerQTO);

    /**
     * 查询总量
     * @param sellerQTO
     * */
    Long totalCount(SellerQTO sellerQTO);

    /**
     * 通过ID获得卖家
     * @param id
     * */
    SellerDO get(Long id);

    /**
     * 通过用户ID获得卖家
     * @param userId
     * */
    SellerDO getByUserId(Long userId);

    /**
     * 更新分销商信息
     * @param sellerDO
     * */
    Integer update(SellerDO sellerDO);

    /**
     * 通过用户ID批次查询
     * @param userIds
     * */
    List<SellerDO> queryByUserIds(List<Long> userIds);

    /**
     * 通过邀请码获得卖家
     * */
    SellerDO getByInviterCode(String inviterCode);

    /**
     * 通过用户ID更新卖家
     * */
    Integer updateByUserId(Long userId, SellerDO sellerDO);
}
