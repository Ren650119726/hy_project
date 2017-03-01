package com.mockuai.marketingcenter.core.manager;

import com.mockuai.marketingcenter.core.domain.LimitedUserCorrelationDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;

/**
 * Created by huangsiqian on 2016/10/19.
 */
public interface LimitedUserCorrelationManager {
    Boolean addUserMsg(LimitedUserCorrelationDO limitedUserCorrelationDO)throws MarketingException;
    Boolean updatePurchaseQuantity(LimitedUserCorrelationDO limitedUserCorrelationDO)throws MarketingException;
    Boolean orderCancelledgoods(LimitedUserCorrelationDO limitedUserCorrelationDO)throws MarketingException;
    LimitedUserCorrelationDO selectUserMsg(LimitedUserCorrelationDO limitedUserCorrelationDO)throws MarketingException;

}
