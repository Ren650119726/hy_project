package com.mockuai.distributioncenter.core.service.action.relationship;

import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.DistributionService;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.core.dao.SellerRelationshipDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by duke on 16/6/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class BuildRelationshipTest {
    private static final String appKey = "27c7bc87733c6d253458fa8908001eef";

    @Autowired
    private DistributionService distributionService;

    @Autowired
    private SellerRelationshipDAO sellerRelationshipDAO;

    @Test
    public void test() throws Exception {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.BUILD_RELATION_SHIP.getActionName());
        distributionService.execute(baseRequest);
    }

    @Test
    public void testTotalCount() throws Exception {
        List<Long> userIds = Arrays.asList(1L, 1841973L);
        List<Map<String, Long>> maps = sellerRelationshipDAO.queryTotalCountByUserIds(userIds);
        System.out.println();
    }
}
