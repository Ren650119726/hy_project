package com.mockuai.distributioncenter.core.service.action.record;

import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.DistributionService;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.domain.qto.DistRecordQTO;
import com.mockuai.distributioncenter.core.service.action.UnitTestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by duke on 16/6/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ListOrderActionTest {
    private static final String appKey = "27c7bc87733c6d253458fa8908001eef";

    @Autowired
    private DistributionService distributionService;

    @Test
    public void test() throws Exception {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("distRecordQTO", new DistRecordQTO());
        baseRequest.setParam("appKey", appKey);
        baseRequest.setParam("userId", 1L);
        baseRequest.setCommand(ActionEnum.LIST_ORDER.getActionName());
        UnitTestUtils.assertAndPrint(distributionService.execute(baseRequest));
    }
}
