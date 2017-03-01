package com.mockuai.marketingcenter.core.manager;

import com.mockuai.marketingcenter.common.domain.dto.LimitedGoodsCorrelationDTO;
import com.mockuai.marketingcenter.core.domain.LimitedGoodsCorrelationDO;
import com.mockuai.marketingcenter.core.domain.LimitedUserCorrelationDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;

import java.util.List;

/**
 * Created by huangsiqian on 2016/10/19.
 */
public interface LimitedGoodsCorrelationManager {
    Boolean stopActivity(LimitedGoodsCorrelationDO limitedGoodsCorrelationDO) throws MarketingException;
    Boolean addActivityGoods(LimitedGoodsCorrelationDO limitedGoodsCorrelationDO) throws  MarketingException;
    List<LimitedGoodsCorrelationDTO> selectMsgByItemId(Long itemId) throws  MarketingException;
    List<LimitedGoodsCorrelationDTO> selectMsgByGoods(LimitedGoodsCorrelationDO limitedGoodsCorrelationDO)throws MarketingException;
    Boolean deleteGoods(Long activityId)throws MarketingException;
}
