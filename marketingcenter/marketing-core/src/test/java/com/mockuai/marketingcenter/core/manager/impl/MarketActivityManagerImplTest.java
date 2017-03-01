package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.marketingcenter.common.constant.ActivityScope;
import com.mockuai.marketingcenter.common.constant.ActivityStatus;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.qto.ActivityItemQTO;
import com.mockuai.marketingcenter.common.domain.qto.MarketActivityQTO;
import com.mockuai.marketingcenter.core.BaseTest;
import com.mockuai.marketingcenter.core.domain.MarketActivityDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.MarketActivityManager;
import com.mockuai.marketingcenter.core.util.DateUtils;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import com.mockuai.marketingcenter.core.util.ModelUtil;
import org.testng.annotations.Test;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by edgar.zr on 8/6/15.
 */
public class MarketActivityManagerImplTest extends BaseTest {

    @Resource
    private MarketActivityManager activityManager;

    @Test
    public void testQueryActivityForSeller() throws Exception {

        MarketActivityDTO marketActivityDTO = new MarketActivityDTO();
        marketActivityDTO.setBizCode("yangdongxi");
        marketActivityDTO.setCreatorId(91L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        marketActivityDTO.setStartTime(simpleDateFormat.parse("2015-08-23 12:00:00"));
        marketActivityDTO.setEndTime(simpleDateFormat.parse("2015-08-24 12:00:00"));

        MarketActivityQTO marketActivityQTO = new MarketActivityQTO();
        marketActivityQTO.setBizCode(marketActivityDTO.getBizCode());
        marketActivityQTO.setCreatorId(marketActivityDTO.getCreatorId());
        marketActivityQTO.setStatus(ActivityStatus.NORMAL.getValue());
        marketActivityQTO.setParentId(0L);

        // 当前商家创建的所有优惠活动
        List<MarketActivityDO> allMarketActivityDOs = activityManager.queryActivityForSeller(marketActivityQTO);
        System.err.println(JsonUtil.toJson(allMarketActivityDOs));

        ActivityItemQTO activityItemQTO = new ActivityItemQTO();
        activityItemQTO.setBizCode(marketActivityDTO.getBizCode());
        activityItemQTO.setActivityCreatorId(marketActivityDTO.getCreatorId());
        activityItemQTO.setActivityIdList(new ArrayList<Long>());

        for (MarketActivityDO marketActivityDO : allMarketActivityDOs) {
            // 与当前创建活动有时间交集的优惠活动
            if (DateUtils.isOverlappingDates(marketActivityDTO.getStartTime(), marketActivityDTO.getEndTime(),
                    marketActivityDO.getStartTime(), marketActivityDO.getEndTime())) {
                activityItemQTO.getActivityIdList().add(marketActivityDO.getId());
                // 不能有同时进行超过 3 个的优惠活动
                if (activityItemQTO.getActivityIdList().size() > 2) {
//                    throw new MarketingException(ResponseCode.OVERLAPPING_ACTIVITIES_OUT_OF_LIMITATION);
                }
                // 只要有进行中的全店活动,就不能创建任一其它优惠活动 或者 当前创建的活动为全店活动但是前面已经由任一类型活动创建
                if (marketActivityDO.getScope().intValue() == ActivityScope.SCOPE_SHOP.getValue()
                        || ActivityScope.SCOPE_SHOP.getValue() == marketActivityDTO.getScope().intValue()) {
                    throw new MarketingException(ResponseCode.ACTIVITY_ITEM_NOT_UNIQUE);
                }
            }
        }
    }

    @Test
    public void testAddActivity() {

        MarketActivityDTO marketActivityDTO = new MarketActivityDTO();

        try {
            activityManager.addActivity(ModelUtil.genMarketActivityDO(marketActivityDTO));
        } catch (MarketingException e) {
        }

    }
}