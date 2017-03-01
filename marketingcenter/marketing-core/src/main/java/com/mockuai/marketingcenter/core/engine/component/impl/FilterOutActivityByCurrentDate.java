package com.mockuai.marketingcenter.core.engine.component.impl;

import com.mockuai.marketingcenter.core.domain.MarketActivityDO;
import com.mockuai.marketingcenter.core.util.Context;
import com.mockuai.marketingcenter.core.engine.component.Component;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.mockuai.marketingcenter.common.constant.ComponentType.FILTER_OUT_ACTIVITY_BY_CURRENT_DATE;

/**
 * Created by edgar.zr on 1/28/2016.
 */
@Service
public class FilterOutActivityByCurrentDate implements Component {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterOutActivityByCurrentDate.class);

    public static Context wrapParams(List<MarketActivityDO> marketActivityDOs) {
        Context context = new Context();
        context.setParam("marketActivityDOs", marketActivityDOs);
        context.setParam("component", FILTER_OUT_ACTIVITY_BY_CURRENT_DATE);
        return context;
    }

    @Override
    public void init() {

    }

    @Override
    public List<MarketActivityDO> execute(Context context) throws MarketingException {
        List<MarketActivityDO> marketActivityDOs = (List<MarketActivityDO>) context.getParam("marketActivityDOs");

        List<MarketActivityDO> matchedActivities = new ArrayList<MarketActivityDO>();
        try {
            Date currentDate = new Date();
            for (MarketActivityDO marketActivityDO : marketActivityDOs) {
                //只有在可用期范围内的营销活动方可满足需求，且商品售空时间为空(针对换购)
                if (marketActivityDO.getItemInvalidTime() == null &&
                        marketActivityDO.getStartTime().before(currentDate)
                        && marketActivityDO.getEndTime().after(currentDate)) {
                    matchedActivities.add(marketActivityDO);
                }
            }
        } catch (Exception e) {
            LOGGER.error("error of comparing the duration and current time, marketActivityDOs : {}",
                    JsonUtil.toJson(marketActivityDOs), e);
            return Collections.emptyList();
        }
        return matchedActivities;
    }

    @Override
    public String getComponentCode() {
        return FILTER_OUT_ACTIVITY_BY_CURRENT_DATE.getCode();
    }
}