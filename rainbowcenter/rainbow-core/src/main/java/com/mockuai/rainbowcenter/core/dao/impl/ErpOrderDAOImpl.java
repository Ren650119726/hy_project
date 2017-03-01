package com.mockuai.rainbowcenter.core.dao.impl;

import com.mockuai.rainbowcenter.common.util.JsonUtil;
import com.mockuai.rainbowcenter.core.dao.ErpOrderDAO;
import com.mockuai.rainbowcenter.core.domain.ErpOrderDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * Created by lizg on 2016/6/15.
 */
@Repository
public class ErpOrderDAOImpl extends SqlMapClientDaoSupport implements ErpOrderDAO {
    private static final Logger log = LoggerFactory.getLogger(ErpOrderDAOImpl.class);

    @Override
    public Long addErpOrder(ErpOrderDO erpOrderDO) {
        Long id = null;
        try {
            id = (Long) getSqlMapClientTemplate().insert("gyerpOrder.addErpOrder", erpOrderDO);
        } catch (DataAccessException e) {
            log.error("add erpOrder record error, errMsg: {}, erpOrderDO: {}",
                    e.getMessage(), JsonUtil.toJson(erpOrderDO));
        }
        return id;
    }

    @Override
    public ErpOrderDO getGyerpCode(ErpOrderDO erpOrderDO) {
        try {
            erpOrderDO =
                    (ErpOrderDO) getSqlMapClientTemplate().queryForObject("gyerpOrder.getGyerpCode", erpOrderDO);
            return erpOrderDO;
        } catch (DataAccessException e) {
            log.error("get code by orderId error, errMsg: {}, orderId: {}",
                    e.getMessage(), erpOrderDO.getOrderId());
        }
        return erpOrderDO;
    }
}
