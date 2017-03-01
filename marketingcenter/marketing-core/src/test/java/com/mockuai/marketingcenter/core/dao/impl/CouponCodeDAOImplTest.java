package com.mockuai.marketingcenter.core.dao.impl;

import com.mockuai.marketingcenter.common.constant.CouponCodeStatus;
import com.mockuai.marketingcenter.common.constant.CouponType;
import com.mockuai.marketingcenter.core.BaseTest;
import com.mockuai.marketingcenter.core.dao.CouponCodeDAO;
import com.mockuai.marketingcenter.core.domain.CouponCodeDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edgar.zr on 11/16/15.
 */
public class CouponCodeDAOImplTest extends BaseTest {

    @Autowired
    private CouponCodeDAO couponCodeDAO;

    @Test()
    public void testBatchAddCouponCodes() throws Exception {

        List<CouponCodeDO> list = new ArrayList<CouponCodeDO>();
        CouponCodeDO couponCodeDO = new CouponCodeDO();
        couponCodeDO.setStatus(CouponCodeStatus.NONE.getValue());
        couponCodeDO.setBizCode("mockuai_demo");
        couponCodeDO.setCouponId(11L);
        couponCodeDO.setUserId(11L);
        couponCodeDO.setActivityCreatorId(11L);
        couponCodeDO.setCode("xxxx");
        couponCodeDO.setActivityId(11L);
        couponCodeDO.setCouponType(CouponType.TYPE_COMMON_CODE.getValue());
        list.add(couponCodeDO);
        couponCodeDAO.batchAddCouponCodes(list);
    }


    private String getEntryName(String errorMsg) {

        String sub = errorMsg.substring(errorMsg.indexOf('\'') + 1);
        return sub.substring(0, sub.indexOf('\''));
    }
}