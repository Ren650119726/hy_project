package com.mockuai.marketingcenter.core.dao;

import com.mockuai.marketingcenter.common.domain.qto.ActivityItemQTO;
import com.mockuai.marketingcenter.core.domain.ActivityItemDO;

import java.util.List;

/**
 * Created by zengzhangqiang on 7/19/15.
 */
public interface ActivityItemDAO {
    /**
     * 批量添加活动商品
     *
     * @param activityItemDOs
     * @return
     */
    void addActivityItems(List<ActivityItemDO> activityItemDOs);

    /**
     * 查询指定的活动商品列表
     *
     * @param activityItemQTO
     * @return
     */
    List<ActivityItemDO> queryActivityItem(ActivityItemQTO activityItemQTO);

    List<ActivityItemDO> queryActivityItemForActivity(ActivityItemQTO activityItemQTO);
}