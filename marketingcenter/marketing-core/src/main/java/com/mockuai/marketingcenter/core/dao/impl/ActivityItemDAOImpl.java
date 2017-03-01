package com.mockuai.marketingcenter.core.dao.impl;

import com.mockuai.marketingcenter.common.domain.qto.ActivityItemQTO;
import com.mockuai.marketingcenter.core.dao.ActivityItemDAO;
import com.mockuai.marketingcenter.core.domain.ActivityItemDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

/**
 * Created by zengzhangqiang on 7/19/15.
 */
public class ActivityItemDAOImpl extends SqlMapClientDaoSupport implements ActivityItemDAO {

    @Override
    public void addActivityItems(List<ActivityItemDO> activityItemDOs) {
        getSqlMapClientTemplate().insert("activity_item.addActivityItems", activityItemDOs);
    }

    @Override
    public List<ActivityItemDO> queryActivityItem(ActivityItemQTO activityItemQTO) {
        activityItemQTO.setTotalCount((Integer) getSqlMapClientTemplate().queryForObject("activity_item.countOfQueryActivityItem", activityItemQTO));
        return getSqlMapClientTemplate().queryForList("activity_item.queryActivityItem", activityItemQTO);
    }

    @Override
    public List<ActivityItemDO> queryActivityItemForActivity(ActivityItemQTO activityItemQTO) {
        return getSqlMapClientTemplate().queryForList("activity_item.queryActivityItemForActivity", activityItemQTO);
    }
}