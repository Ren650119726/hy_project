package com.mockuai.distributioncenter.core.service.action.summary;

import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.DistributionService;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.core.service.action.UnitTestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by duke on 16/5/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SellerSummaryActionTest {
    private static final String appKey = "6562b5ddf0aed2aad8fe471ce2a2c8a0";

    @Autowired
    private DistributionService distributionService;

    @Test
    public void testGetSellerSummary() throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("userId", 1L);
        baseRequest.setParam("startTime", df.parse("2016-05-01"));
        baseRequest.setParam("endTime", df.parse("2016-06-30"));
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.GET_SELLER_SUMMARY.getActionName());
        UnitTestUtils.assertAndPrint(distributionService.execute(baseRequest));
    }
}
