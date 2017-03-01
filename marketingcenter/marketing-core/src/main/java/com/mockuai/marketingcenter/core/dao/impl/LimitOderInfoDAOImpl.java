package com.mockuai.marketingcenter.core.dao.impl;

import com.mockuai.marketingcenter.core.dao.LimitOderInfoDAO;
import com.mockuai.marketingcenter.core.domain.LimitOderInfoDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangsiqian on 2016/11/3.
 */
public class LimitOderInfoDAOImpl  extends SqlMapClientDaoSupport implements LimitOderInfoDAO {
    @Override
    public List<LimitOderInfoDO> queryInfoDtoById(Long activityId) {
       Map map = new HashMap();
        map.put("activityId",activityId);
        return (List<LimitOderInfoDO>)getSqlMapClientTemplate().queryForList("limit_order_info.queryInfoDtoById",map);
    }

    @Override
    public LimitOderInfoDO  queryLimitOderInfos(LimitOderInfoDO limitOderInfoDO) {


        return (LimitOderInfoDO) getSqlMapClientTemplate().queryForObject("limit_order_info.queryLimitInfos",limitOderInfoDO);
    }


    @Override
    public Long addLimitOderInfo(LimitOderInfoDO limitOderInfoDO) {
        return (Long)getSqlMapClientTemplate().insert("limit_order_info.addLimitInfo",limitOderInfoDO);
    }
}
