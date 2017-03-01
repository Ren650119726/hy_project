package com.mockuai.marketingcenter.core.dao;

import com.mockuai.marketingcenter.common.domain.qto.CouponCodeQTO;
import com.mockuai.marketingcenter.core.domain.CouponCodeDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;

import java.util.List;

public interface CouponCodeDAO {

    void batchAddCouponCodes(List<CouponCodeDO> couponCodeDOs) throws MarketingException;

    List<CouponCodeDO> queryByCode(String code);

    List<CouponCodeDO> queryCouponCode(CouponCodeQTO couponCodeQTO);

    Integer queryCouponCodeCount(CouponCodeQTO couponCodeQTO);

    int update(CouponCodeDO couponCodeDO);
}