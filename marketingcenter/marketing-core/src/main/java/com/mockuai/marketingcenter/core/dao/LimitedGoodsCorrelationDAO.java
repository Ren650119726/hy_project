package com.mockuai.marketingcenter.core.dao;

import com.mockuai.marketingcenter.core.domain.LimitedGoodsCorrelationDO;

import java.util.List;

/**活动商品中间表逻辑
 * Created by huangsiqian on 2016/10/19.
 */
public interface LimitedGoodsCorrelationDAO {
    int stopActivity(LimitedGoodsCorrelationDO limitedGoodsCorrelationDO);
    Long addActivityGoods(LimitedGoodsCorrelationDO limitedGoodsCorrelationDO);
    List<LimitedGoodsCorrelationDO> selectMsgByItemId(Long itemId);
    List<LimitedGoodsCorrelationDO> selectMsgByGoods(LimitedGoodsCorrelationDO limitedGoodsCorrelationDO);
    Integer deleteGoods(Long activityId);
    LimitedGoodsCorrelationDO selectCurrentActivityId(Long itemId);
}
