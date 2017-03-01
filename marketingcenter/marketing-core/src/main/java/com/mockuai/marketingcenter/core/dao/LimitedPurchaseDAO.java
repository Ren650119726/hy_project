package com.mockuai.marketingcenter.core.dao;

import com.mockuai.marketingcenter.common.domain.qto.TimePurchaseQTO;
import com.mockuai.marketingcenter.core.domain.LimitedGoodsCorrelationDO;
import com.mockuai.marketingcenter.core.domain.LimitedPurchaseDO;

import java.util.List;

/**
 * Created by huangsiqian on 2016/10/7.
 */
public interface LimitedPurchaseDAO {
    LimitedPurchaseDO selectActivities(LimitedPurchaseDO limitedPurchaseDO);
    Long addActivities(LimitedPurchaseDO activityDO);
    List<LimitedPurchaseDO>  activityList(TimePurchaseQTO timePurchaseQTO);
    Integer updateActivity(LimitedPurchaseDO activityDO);
    Integer modifyActivity(LimitedPurchaseDO activityDO);
    LimitedPurchaseDO activityById(Long id);
    Long activityCount(TimePurchaseQTO timePurchaseQTO);
    //发布限时购活动
    Integer startLimtedPurchase(LimitedPurchaseDO activityDO);
    Integer deleteLimitPurchase(Long activityId);
    //定时任务（活动开始自动修改商品）
    Integer updateLimitedPurchaseStatus(String status,Long activityId);
}
