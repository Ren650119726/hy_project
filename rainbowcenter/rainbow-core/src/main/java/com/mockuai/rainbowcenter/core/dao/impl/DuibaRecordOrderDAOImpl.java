package com.mockuai.rainbowcenter.core.dao.impl;

import com.alibaba.fastjson.JSON;
import com.mockuai.rainbowcenter.common.util.JsonUtil;
import com.mockuai.rainbowcenter.core.dao.DuibaRecordOrderDAO;
import com.mockuai.rainbowcenter.core.domain.DuibaRecordOrderDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * Created by lizg on 2016/7/19.
 */
@Repository
public class DuibaRecordOrderDAOImpl extends SqlMapClientDaoSupport implements DuibaRecordOrderDAO {

    private static final Logger log = LoggerFactory.getLogger(DuibaRecordOrderDAOImpl.class);
    @Override
    public Long addRecordOrder(DuibaRecordOrderDO duibaRecordOrderDO) {
        Long id = null;
        try {
            id = (Long) getSqlMapClientTemplate().insert("duibaRecordOrder.addRecordOrder", duibaRecordOrderDO);
        } catch (DataAccessException e) {
            log.error("add duiba Order record error, errMsg: {}, duibaRecordOrderDO: {}",
                    e.getMessage(), JsonUtil.toJson(duibaRecordOrderDO));
        }
        return id;
    }

    @Override
    public DuibaRecordOrderDO getRecordByOrderNum(DuibaRecordOrderDO duibaRecordOrderDO) {
        try {
            duibaRecordOrderDO =
                    (DuibaRecordOrderDO) getSqlMapClientTemplate().queryForObject("duibaRecordOrder.getRecordByOrderNum", duibaRecordOrderDO);
            return duibaRecordOrderDO;
        } catch (DataAccessException e) {
            log.error("get record by orderNum error, errMsg: {}, orderNum: {}",
                    e.getMessage(), duibaRecordOrderDO.getOrderNum());
        }
        return duibaRecordOrderDO;
    }

    @Override
    public int updateStatusByorderNum(DuibaRecordOrderDO duibaRecordOrderDO) {
        try {
            return this.getSqlMapClientTemplate().update("duibaRecordOrder.updateStatusByorderNum", duibaRecordOrderDO);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("updateStatusByorderNum exception, duibaRecordOrderDO: {}", JSON.toJSONString(duibaRecordOrderDO));
            return -1;
        }
    }

    @Override
    public int updateRemoveById(DuibaRecordOrderDO duibaRecordOrderDO) {
        try {
            return this.getSqlMapClientTemplate().update("duibaRecordOrder.updateRemoveById", duibaRecordOrderDO);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("updateRemoveById exception, duibaRecordOrderDO: {}", JSON.toJSONString(duibaRecordOrderDO));
            return -1;
        }
    }
}
