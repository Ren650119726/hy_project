package com.mockuai.marketingcenter.core.dao;

import com.mockuai.marketingcenter.common.domain.qto.MarketActivityQTO;
import com.mockuai.marketingcenter.core.domain.MarketActivityDO;

import java.util.List;

public interface MarketActivityDAO {

    List<MarketActivityDO> overlappingByTimeActivity(MarketActivityDO marketActivityDO);

    long addActivity(MarketActivityDO marketActivityDO);

    int updateActivity(MarketActivityDO marketActivityDO);

    int deleteActivity(MarketActivityQTO marketActivityQTO);

    List<MarketActivityDO> queryActivity(MarketActivityQTO marketActivityQTO);

    int queryActivityCount(MarketActivityQTO marketActivityQTO);

    List<MarketActivityDO> queryActivityForSettlement(MarketActivityQTO marketActivityQTO);

    int countOfQueryActivityForSettlement(MarketActivityQTO marketActivityQTO);

    MarketActivityDO getActivity(MarketActivityDO marketActivityDO);
}