package com.mockuai.marketingcenter.core.dao;

import com.mockuai.marketingcenter.core.domain.LimitedUserCorrelationDO;

/**活动商品用户中间表操作
 * Created by huangsiqian on 2016/10/19.
 */
public interface LimitedUserCorrelationDAO {
    Long addUserMsg(LimitedUserCorrelationDO limitedUserCorrelationDO);
    Integer updatePurchaseQuantity(LimitedUserCorrelationDO limitedUserCorrelationDO);
    Integer orderCancelledgoods(LimitedUserCorrelationDO limitedUserCorrelationDO);
    LimitedUserCorrelationDO selectUserMsg(LimitedUserCorrelationDO limitedUserCorrelationDO);

}
