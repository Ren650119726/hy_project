package com.mockuai.virtualwealthcenter.core.dao.impl;

import com.mockuai.virtualwealthcenter.common.domain.qto.UsedLogQTO;
import com.mockuai.virtualwealthcenter.core.dao.UsedLogDAO;
import com.mockuai.virtualwealthcenter.core.domain.UsedLogDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by edgar.zr on 5/16/2016.
 */
public class UsedLogDAOImpl extends SqlMapClientDaoSupport implements UsedLogDAO {

    @Override
    public Integer batchAddUsedLog(List<UsedLogDO> usedLogDOs) {
        Map<String, Object> map = new HashMap<>();
        map.put("usedLogs", usedLogDOs);
        return (Integer) getSqlMapClientTemplate().insert("used_log.batchAddUsedLog", map);
    }

    @Override
    public List<UsedLogDO> queryUsedLog(UsedLogQTO usedLogQTO) {
        return getSqlMapClientTemplate().queryForList("used_log.queryUsedLog", usedLogQTO);
    }

    @Override
    public int updateUsedLog(UsedLogDO usedLogDO) {
        return getSqlMapClientTemplate().update("used_log.updateUsedLog", usedLogDO);
    }
}