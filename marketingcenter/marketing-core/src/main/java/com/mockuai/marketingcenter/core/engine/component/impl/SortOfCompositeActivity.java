package com.mockuai.marketingcenter.core.engine.component.impl;

import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.dto.PropertyDTO;
import com.mockuai.marketingcenter.core.engine.component.Component;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.util.Context;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;

import static com.mockuai.marketingcenter.common.constant.ComponentType.SORT_OF_COMPOSITE_ACTIVITY;

/**
 * Created by edgar.zr on 1/27/2016.
 */
@Service
public class SortOfCompositeActivity implements Component {

    public static Context wrapParams(DiscountInfo discountInfo) {
        Context context = new Context();
        context.setParam("discountInfo", discountInfo);
        context.setParam("component", SORT_OF_COMPOSITE_ACTIVITY);
        return context;
    }

    @Override
    public void init() {

    }

    @Override
    public Void execute(Context context) throws MarketingException {
        DiscountInfo discountInfo = (DiscountInfo) context.getParam("discountInfo");

        //根据消费门槛，对子营销活动列表进行降序排列
        Collections.sort(discountInfo.getActivity().getSubMarketActivityList(), new Comparator<MarketActivityDTO>() {
            public int compare(MarketActivityDTO activity1, MarketActivityDTO activity2) {
                long consume1 = Long.valueOf(activity1.getPropertyMap().get(PropertyDTO.CONSUME).getValue());

                long consume2 = Long.valueOf(activity2.getPropertyMap().get(PropertyDTO.CONSUME).getValue());
                if (consume1 > consume2)
                    return -1;
                else if (consume1 < consume2)
                    return 1;
                return 0;
            }
        });
        return null;
    }

    @Override
    public String getComponentCode() {
        return SORT_OF_COMPOSITE_ACTIVITY.getCode();
    }
}