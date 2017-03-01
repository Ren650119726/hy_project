package com.mockuai.marketingcenter.core.dao;

import com.mockuai.marketingcenter.common.domain.dto.GrantedCouponDTO;
import com.mockuai.marketingcenter.common.domain.qto.GrantedCouponQTO;
import com.mockuai.marketingcenter.core.domain.GrantedCouponDO;
import com.mockuai.marketingcenter.core.domain.GrantedCouponSumDO;

import java.util.List;

public interface GrantedCouponDAO {

    long addGrantedCoupon(GrantedCouponDO grantedCouponDO);

    int deleteGrantedCoupon(Long grantedCouponId, Long userId);

    int updateGrantedCoupon(GrantedCouponDTO grantedCouponDTO);

    int updateCouponReceiverId(List<Long> idList, Long fromUserId, Long toUserId);

    int updateCouponStatus(List<Long> idList, long userId, int fromStatus, int toStatus);

    List<GrantedCouponSumDO> queryGrantedCouponSum(GrantedCouponQTO grantedCouponQTO);

    List<GrantedCouponDO> queryGrantedCoupon(GrantedCouponQTO grantedCouponQTO);

    List<GrantedCouponDO> queryGrantedCouponForCouponCode(GrantedCouponQTO grantedCouponQTO);

    Integer queryGrantedCouponCountForCouponCode(GrantedCouponQTO grantedCouponQTO);

    int countOfQueryGrantedCoupon(GrantedCouponQTO grantedCouponQTO);

    List<GrantedCouponDO> queryGrantedCouponSecond(GrantedCouponQTO grantedCouponQTO);
}