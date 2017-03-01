package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.marketingcenter.common.domain.qto.ActivityItemQTO;
import com.mockuai.marketingcenter.core.BaseTest;
import com.mockuai.marketingcenter.core.domain.ActivityItemDO;
import com.mockuai.marketingcenter.core.manager.ActivityItemManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by edgar.zr on 5/28/2016.
 */
public class ActivityItemManagerImplTest extends BaseTest {

    @Autowired
    private ActivityItemManager activityItemManager;

    @Test
    public void testQueryActivityItemForActivity() throws Exception {
        ActivityItemQTO activityItemQTO = new ActivityItemQTO();
        activityItemQTO.setActivityIds(Arrays.asList(2302L, 2306L, 2303L, 2307L, 2304L, 2305L, 2311L, 2312L, 2313L, 2318L, 2316L, 2317L));
        activityItemQTO.setOffset(0);
        activityItemQTO.setCount(5000);
        List<ActivityItemDO> activityItemDOs = activityItemManager.queryActivityItemForActivity(activityItemQTO);
        System.err.println("");
    }
}