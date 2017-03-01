package com.mockuai.tradecenter.core.manager;

import com.mockuai.marketingcenter.common.domain.dto.SettlementInfo;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.common.domain.SaleRankDTO;
import com.mockuai.tradecenter.common.domain.StatisticsActivityInfoDTO;
import com.mockuai.tradecenter.core.domain.OrderDiscountInfoDO;
import com.mockuai.tradecenter.core.exception.TradeException;

import java.util.List;

/**
 * Created by zengzhangqiang on 5/24/15.
 */
public interface OrderDiscountInfoManager {
    public Long addOrderDiscountInfo(OrderDiscountInfoDO orderDiscountInfoDO) throws TradeException;

    public List<OrderDiscountInfoDO> queryOrderDiscountInfo(Long orderId, Long userId) throws TradeException;
    
    /**
     * 调用营销平台得到折扣信息
     * @param userId
     * @param list
     * @param appkey
     * @return
     * @throws TradeException
     */
    public List<OrderDiscountInfoDO> getSettlementDiscountInfo(boolean hasSubOrder,OrderDTO orderDTO, List<OrderItemDTO> giftList,String appkey)throws TradeException;


    public OrderDiscountInfoDO genMemberDiscount(SettlementInfo settlementInfo,long userId,String bizCode)throws TradeException;

    /**
     * 满减送活动统计
     * @param orderDiscountInfoDO
     * @return
     */
    StatisticsActivityInfoDTO queryActivityOrder(OrderDiscountInfoDO orderDiscountInfoDO);

    List<SaleRankDTO> querySaleRank(Long activityId);
}
