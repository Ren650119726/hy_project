package com.mockuai.marketingcenter.core.engine.activity;

import com.mockuai.marketingcenter.common.constant.ToolType;
import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.dto.PropertyDTO;
import com.mockuai.marketingcenter.core.engine.component.ComponentHelper;
import com.mockuai.marketingcenter.core.engine.component.impl.ExecuteActivityTool;
import com.mockuai.marketingcenter.core.engine.component.impl.SortOfCompositeActivity;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.util.Context;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;

@Service
public class ActivityEngine {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityEngine.class.getName());

    @Autowired
    private ComponentHelper componentHelper;

    public DiscountInfo execute(Context context) throws MarketingException {

        DiscountInfo discountInfo = (DiscountInfo) context.getParam("discountInfo");
        MarketActivityDTO marketActivityDTO = discountInfo.getActivity();
        long itemTotalPrice = (Long) context.getParam("itemTotalPrice");

        //计算优惠信息。简单活动和复合活动的处理逻辑有所区别
        if (marketActivityDTO.getToolType() != null
                && marketActivityDTO.getToolType().intValue() == ToolType.COMPOSITE_TOOL.getValue()) {//复合活动

            componentHelper.execute(SortOfCompositeActivity.wrapParams(discountInfo));
            LOGGER.debug("sorted subMarketActivity : {}", JsonUtil.toJson(marketActivityDTO.getSubMarketActivityList()));

            MarketActivityDTO matchActivity = null;
            //比较商品总价与各子活动的消费门槛，选择最优先满足的门槛最高的活动（默认该活动的优惠力度最高）
            for (MarketActivityDTO subMarketActivity : marketActivityDTO.getSubMarketActivityList()) {
                long consume = Long.valueOf(subMarketActivity.getPropertyMap().get(PropertyDTO.CONSUME).getValue());
                if (itemTotalPrice >= consume) {
                    matchActivity = subMarketActivity;
                    break;
                }
            }

            //没有满足条件的子优惠活动
            if (matchActivity == null) {
                return null;
            }

            // 目前只需要显示最优的子活动数据
            marketActivityDTO.setSubMarketActivityList(Arrays.asList(matchActivity));

            // 添加最优的子活动
            context.setParam("matchActivity", matchActivity);
        }

        discountInfo = componentHelper.execute(ExecuteActivityTool.wrapParams(marketActivityDTO, context));

        return normalizeDiscountInfo(discountInfo);

    }

    private DiscountInfo normalizeDiscountInfo(DiscountInfo discountInfo) {

        if (discountInfo == null) {
            return null;
        }

        if (discountInfo.getSavedPostage() == null) {
            discountInfo.setSavedPostage(Long.valueOf(0L));
        }

        if (discountInfo.getGiftList() == null) {
            discountInfo.setGiftList(Collections.EMPTY_LIST);
        }
        return discountInfo;
    }
}