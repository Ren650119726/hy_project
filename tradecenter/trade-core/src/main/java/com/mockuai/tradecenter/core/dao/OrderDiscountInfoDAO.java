package com.mockuai.tradecenter.core.dao;

import com.mockuai.tradecenter.common.domain.SaleRankDTO;
import com.mockuai.tradecenter.common.domain.StatisticsActivityInfoDTO;
import com.mockuai.tradecenter.core.domain.OrderDiscountInfoDO;
import java.util.List;

/**
 * Created by zengzhangqiang on 5/24/15.
 */
public interface OrderDiscountInfoDAO {
    public Long addOrderDiscountInfo(OrderDiscountInfoDO orderDiscountInfoDO);

    public List<OrderDiscountInfoDO> queryOrderDiscountInfo(Long orderId, Long userId);

    StatisticsActivityInfoDTO queryActivityOrder(OrderDiscountInfoDO orderDiscountInfoDO);


    List<SaleRankDTO> querySaleRank(Long activityId);
}
