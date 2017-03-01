package com.mockuai.marketingcenter.core.manager;

import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.qto.ActivityItemQTO;
import com.mockuai.marketingcenter.core.domain.ActivityItemDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;

import java.util.List;

/**
 * Created by zengzhangqiang on 7/19/15.
 */
public interface ActivityItemManager {
    /**
     * 批量添加活动商品
     *
     * @param activityItemDOs
     * @return
     * @throws MarketingException
     */
    boolean addActivityItems(List<ActivityItemDO> activityItemDOs, Integer scope) throws MarketingException;

    /**
     * 批量添加活动商品
     *
     * @param marketActivityDTO
     * @return
     * @throws MarketingException
     */
    void addActivityItems(MarketActivityDTO marketActivityDTO) throws MarketingException;

    /**
     * 为活动主体填充 条件商品
     *
     * @param marketActivityDTOs
     * @param bizCode
     * @throws MarketingException
     */
    void fillUpActivityItems(List<MarketActivityDTO> marketActivityDTOs, String bizCode) throws MarketingException;

    /**
     * 查询指定活动的商品列表
     *
     * @param activityId
     * @param activityCreatorId
     * @param bizCode
     * @return
     * @throws MarketingException
     */
    List<ActivityItemDO> queryActivityItem(Long activityId, Long activityCreatorId, String bizCode) throws MarketingException;

    /**
     * 根据商品查询关联的优惠券活动
     *
     * @param activityItemQTO
     * @return
     * @throws MarketingException
     */
    List<ActivityItemDO> queryActivityItemForActivity(ActivityItemQTO activityItemQTO) throws MarketingException;

    /**
     * 查询指定活动列表下的商品列表
     *
     * @param activityItemQTO
     * @return
     * @throws MarketingException
     */
    List<ActivityItemDO> queryActivityItems(ActivityItemQTO activityItemQTO) throws MarketingException;
}