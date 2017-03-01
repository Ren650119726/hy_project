package com.mockuai.marketingcenter.core.manager;


import com.mockuai.marketingcenter.common.domain.dto.LimitedPurchaseDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.common.domain.qto.TimePurchaseQTO;
import com.mockuai.marketingcenter.core.domain.LimitedPurchaseDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;

import java.util.List;
import java.util.Map;

/**
 * Created by huangsiqian on 2016/10/7.
 */

public interface LimitedPurchaseManager {
    Long addActivities(TimePurchaseQTO timePurchaseQTO) throws MarketingException;
    List<LimitedPurchaseDTO> activityList(TimePurchaseQTO timePurchaseQTO) throws  MarketingException;
    Boolean updateActivity(LimitedPurchaseDO activityDO) throws  MarketingException;
    Boolean modifyActivity(LimitedPurchaseDO activityDO) throws  MarketingException;
    LimitedPurchaseDTO activityById(Long id ) throws MarketingException;
    Integer activityCount(TimePurchaseQTO timePurchaseQTO) throws MarketingException;
    Boolean startLimtedPurchase(LimitedPurchaseDO activityDO) throws  MarketingException;
    //获取活动未开始时，营销活动传入商品过滤(sku级别传出)
    Map<LimitedPurchaseDTO, List<MarketItemDTO>> getTimeLimitOfItemSkuMsg(List<MarketItemDTO> list)throws MarketingException;
    //获取活动未开始时，营销活动传入商品过滤
    Map<LimitedPurchaseDTO, List<MarketItemDTO>>  getTimeLimitOfItem(List<MarketItemDTO> list) throws  MarketingException;
    //过滤 限购活动一个用户剩余商品购买量
    Map<LimitedPurchaseDTO, List<MarketItemDTO>>  getIteminTimeLimit(List<MarketItemDTO> list,Long userId) throws  MarketingException;
    Boolean deleteLimitPurchase(Long activityId) throws MarketingException;
    Boolean updateLimitedPurchaseStatus(String status , Long activityId) throws MarketingException;
}
