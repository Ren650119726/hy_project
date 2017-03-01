package com.mockuai.marketingcenter.core.manager;

import com.mockuai.marketingcenter.common.domain.dto.ActivityCouponDTO;
import com.mockuai.marketingcenter.common.domain.dto.CouponCodeDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.qto.CouponCodeQTO;
import com.mockuai.marketingcenter.core.domain.CouponCodeDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;

import java.util.List;

public interface CouponCodeManager {

    void batchAddCouponCode(ActivityCouponDTO activityCouponDTO, MarketActivityDTO marketActivityDTO) throws MarketingException;

    /**
     * 根据 code 查询 优惠码
     *
     * @param code
     * @return
     * @throws MarketingException
     */
    CouponCodeDO getByCode(String code) throws MarketingException;

    List<CouponCodeDTO> queryCouponCode(ActivityCouponDTO activityCouponDTO, CouponCodeQTO couponCodeQTO, String appKey) throws MarketingException;

    /**
     * 填充已被领取的优惠券的用户信息
     *
     * @param couponCodeDTOs
     * @param userIds
     * @param appKey
     * @throws MarketingException
     */
    void fillUpUserName(List<CouponCodeDTO> couponCodeDTOs, List<Long> userIds, String appKey) throws MarketingException;


    int updateCouponCode(CouponCodeDO couponCodeDO) throws MarketingException;
}