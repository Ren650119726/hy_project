package com.mockuai.marketingcenter.core.manager;

import com.mockuai.marketingcenter.common.domain.dto.LimitedPurchaseGoodsDTO;
import com.mockuai.marketingcenter.core.domain.LimitedPurchaseGoodsDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;

import java.util.List;

/**
 * Created by huangsiqian on 2016/10/8.
 */

public interface LimitedPurchaseGoodsManager {
    Boolean addActivityGoods(LimitedPurchaseGoodsDO limitedPurchaseGoodsDO) throws  MarketingException;
    List<LimitedPurchaseGoodsDTO> activityGoodsList(LimitedPurchaseGoodsDO limitedPurchaseGoodsDO) throws MarketingException;
    Boolean updateActivityGoodsPrice(LimitedPurchaseGoodsDO limitedPurchaseGoodsDO) throws  MarketingException;
    Boolean updateActivityGoodsNum(LimitedPurchaseGoodsDO limitedPurchaseGoodsDO) throws MarketingException;
    Boolean deleteActivityGoods(LimitedPurchaseGoodsDO limitedPurchaseGoodsDO) throws MarketingException;
    List selectGoodsItemId(Long acitivityId) throws MarketingException;
    Long selectGoodsQuantityById(LimitedPurchaseGoodsDO limitedPurchaseGoodsDO) throws MarketingException;
    //活动失效商品状态变化
    Boolean invalidateActivityGoods(LimitedPurchaseGoodsDO limitedPurchaseGoodsDO) throws  MarketingException;
    Boolean deleteGoods(Long activtiyId)throws MarketingException;
    List selectAllSkuId(Long activityId)throws MarketingException;
}
