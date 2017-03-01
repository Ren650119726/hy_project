package com.mockuai.marketingcenter.core.dao;

import com.mockuai.marketingcenter.common.domain.qto.ActivityHistoryQTO;
import com.mockuai.marketingcenter.core.domain.ActivityHistoryDO;

import java.util.List;

/**
 * Created by edgar.zr on 12/3/15.
 */
public interface ActivityHistoryDAO {

    Long addActivityHistory(ActivityHistoryDO activityHistoryDO);

    /**
     * 更新过程中需要判定 status 的状态顺序，RMQ 可能将要发送的数据顺序给颠倒
     * status 的顺序值只能是升序的
     *
     * @param activityHistoryDO
     * @return
     */
    int updateActivityHistoryStatus(ActivityHistoryDO activityHistoryDO);

    List<ActivityHistoryDO> queryActivityHistory(ActivityHistoryQTO activityHistoryQTO);
}