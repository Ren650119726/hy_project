package com.mockuai.marketingcenter.core.dao;



import com.mockuai.marketingcenter.common.domain.dto.LimitedPurchaseGoodsDTO;
import com.mockuai.marketingcenter.core.domain.LimitedPurchaseGoodsDO;

import java.util.List;

/**
 * Created by huangsiqian on 2016/10/8.
 */
public interface LimitedPurchaseGoodsDAO {
    Long addActivityGoods(LimitedPurchaseGoodsDO activityGoodsDO);
    List<LimitedPurchaseGoodsDO> activityGoodsList(LimitedPurchaseGoodsDO activityGoodsDO);
    LimitedPurchaseGoodsDO selectActivityGoods(LimitedPurchaseGoodsDO limitedPurchaseGoodsDO);
    Integer updateActivityGoodsPrice(LimitedPurchaseGoodsDO  activityGoodsDO);
    Integer updateActivityGoodsNum(LimitedPurchaseGoodsDO activityGoodsDO);
    Integer deleteActivityGoods(LimitedPurchaseGoodsDO activityGoodsDO);
    List selectGoodsItemId(Long id);
    Long selectGoodsQuantityById(LimitedPurchaseGoodsDO activityGoodsDO);
    List selectAllActivity();
    //根据item_id查所有活动id
    List selectActivityIdByItemId(Long itemId);
    Integer invalidateActivityGoods(LimitedPurchaseGoodsDO activityGoodsDO);
    Integer deleteGoods(Long activityId);
    //取出同一个活动中，sku商品价格最低的那个
    LimitedPurchaseGoodsDO selectMinGoodsPrice(LimitedPurchaseGoodsDO limitedPurchaseGoodsDO);
    //取出所有的sku_id
    List selectAllSkuId(Long activityId);

}
