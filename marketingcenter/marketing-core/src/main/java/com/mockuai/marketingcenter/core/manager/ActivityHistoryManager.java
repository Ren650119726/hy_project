package com.mockuai.marketingcenter.core.manager;

import com.mockuai.marketingcenter.common.domain.dto.ActivityHistoryDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.core.domain.ActivityHistoryDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.tradecenter.common.domain.OrderDTO;

import java.util.List;

/**
 * Created by edgar.zr on 12/3/15.
 */
public interface ActivityHistoryManager {

    void addActivityHistory(ActivityHistoryDTO activityHistoryDTO) throws MarketingException;

    /**
     * 更新换购
     *
     * @param activityId
     * @param orderId
     * @param status
     * @param bizCode
     * @throws MarketingException
     */
    void updateActivityHistoryStatus(Long activityId, Long orderId, Integer status, String bizCode) throws MarketingException;

    List<ActivityHistoryDTO> queryActivityHistoryByActivity(Long activityId, String bizCode) throws MarketingException;

    /**
     * 统计换购活动已售卖的商品数量
     *
     * @param marketActivityDTOs
     * @param bizCode
     * @return
     * @throws MarketingException
     */
    void fillUpActivityWithSales(List<MarketActivityDTO> marketActivityDTOs, String bizCode) throws MarketingException;

    /**
     * 用户参与指定活动成功购买商品的数量
     *
     * @param userId
     * @param activityId
     * @param bizCode
     * @return
     * @throws MarketingException
     */
    Long getNumOfSkuBuyByUser(Long userId, Long activityId, String bizCode) throws MarketingException;

    /**
     * 根据 activityId 划分参与记录
     *
     * @param orderDTO
     * @return
     * @throws MarketingException
     */
    List<ActivityHistoryDO> filterTradeMessage(OrderDTO orderDTO) throws MarketingException;
}