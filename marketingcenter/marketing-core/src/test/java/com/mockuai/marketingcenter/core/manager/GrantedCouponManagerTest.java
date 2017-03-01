package com.mockuai.marketingcenter.core.manager;

import com.mockuai.marketingcenter.common.domain.qto.GrantedCouponQTO;
import com.mockuai.marketingcenter.core.BaseTest;
import org.testng.annotations.Test;

import javax.annotation.Resource;

/**
 * Created by edgar.zr on 8/20/15.
 */
public class GrantedCouponManagerTest extends BaseTest {

    @Resource
    private GrantedCouponManager grantedCouponManager;

    @Test
    public void testQueryGrantedCouponCount() throws Exception {
        GrantedCouponQTO grantedCouponQTO = new GrantedCouponQTO();
        grantedCouponQTO.setCouponId(50L);
        grantedCouponQTO.setReceiverId(91L);

        Integer count = grantedCouponManager.queryGrantedCouponCount(grantedCouponQTO);
        System.err.println(count);
    }
}