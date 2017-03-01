package com.mockuai.marketingcenter.core.dao.impl;

import com.mockuai.marketingcenter.common.domain.qto.ActivityHistoryQTO;
import com.mockuai.marketingcenter.core.dao.ActivityHistoryDAO;
import com.mockuai.marketingcenter.core.domain.ActivityHistoryDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

/**
 * Created by edgar.zr on 12/3/15.
 */
public class ActivityHistoryIDAOImpl extends SqlMapClientDaoSupport implements ActivityHistoryDAO {

    @Override
    public Long addActivityHistory(ActivityHistoryDO activityHistoryDO) {
        return (Long) getSqlMapClientTemplate().insert("activity_history.addActivityHistory", activityHistoryDO);
    }

    @Override
    public int updateActivityHistoryStatus(ActivityHistoryDO activityHistoryDO) {
        return getSqlMapClientTemplate().update("activity_history.updateActivityHistoryStatus", activityHistoryDO);
    }

    @Override
    public List<ActivityHistoryDO> queryActivityHistory(ActivityHistoryQTO activityHistoryQTO) {
        return getSqlMapClientTemplate().queryForList("activity_history.queryActivityHistory", activityHistoryQTO);
    }
}