package com.mockuai.marketingcenter.core.manager;

import com.mockuai.marketingcenter.common.domain.dto.GrantedCouponDTO;
import com.mockuai.marketingcenter.common.domain.qto.GrantedCouponQTO;
import com.mockuai.marketingcenter.core.domain.GrantedCouponDO;
import com.mockuai.marketingcenter.core.domain.GrantedCouponSumDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GrantedCouponManager {

    void addGrantedCoupon(GrantedCouponDO paramGrantedCouponDO) throws MarketingException;

    int batchAddGrantedCoupon(List<GrantedCouponDO> paramList) throws MarketingException;

    int updateGrantedCoupon(GrantedCouponDTO grantedCouponDTO) throws MarketingException;

    int updateCouponReceiverId(List<Long> couponIdList,
                               Long fromUserId, Long toUserId) throws MarketingException;

    int updateCouponStatus(List<Long> idList, long userId,
                           int fromStatus, int toStatus) throws MarketingException;

    List<GrantedCouponDO> queryGrantedCoupon(GrantedCouponQTO grantedCouponQTO) throws MarketingException;

    /**
     * 根据优惠码值查询
     * 优惠码的状态已经限制
     *
     * @param grantedCouponQTO
     * @return
     * @throws MarketingException
     */
    List<GrantedCouponDO> queryGrantedCouponForCouponCode(GrantedCouponQTO grantedCouponQTO) throws MarketingException;

    List<GrantedCouponSumDO> queryGrantedCouponSum(GrantedCouponQTO grantedCouponQTO) throws MarketingException;

    /**
     * 查询用户已领取优惠券状态,与 {@GrantedCouponManager.queryGrantedCoupon} 的区别在于:<br>
     * 此接口表示的状态: 30 未使用(无过期和无失效)\ 50 已使用(状态为40\50的所有)\ 60已过期(30下的过期和失效的)
     * 对比接口的状态: 30 \ 40 \ 50
     *
     * @param grantedCouponQTO
     * @return
     * @throws MarketingException
     */
    List<GrantedCouponDO> queryGrantedCouponSecond(GrantedCouponQTO grantedCouponQTO) throws MarketingException;

    int queryGrantedCouponCount(GrantedCouponQTO paramGrantedCouponQTO) throws MarketingException;

    /**
     * 判断用户当前是否已经领满了优惠券
     * 此接口是 {@GrantedCouponManager.queryGrantedCouponCount 的替代}
     *
     * @param couponId
     * @param userId
     * @param limit
     * @return
     * @throws MarketingException
     */
    Boolean isOutOfUserReceiveLimit(Long couponId, Long userId, Integer limit) throws MarketingException;
}