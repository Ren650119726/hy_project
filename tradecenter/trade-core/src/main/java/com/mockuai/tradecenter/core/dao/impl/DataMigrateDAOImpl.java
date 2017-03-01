package com.mockuai.tradecenter.core.dao.impl;

import com.mockuai.tradecenter.core.dao.DataMigrateDAO;
import com.mockuai.tradecenter.core.domain.OrderConsigneeDO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.domain.OrderPaymentDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

/**
 * Created by zengzhangqiang on 7/20/15.
 */
public class DataMigrateDAOImpl extends SqlMapClientDaoSupport implements DataMigrateDAO{


    public Long insertOrder(OrderDO orderDO) {
        Long id = (Long) getSqlMapClientTemplate().insert("data_migrate.insertOrder", orderDO);
        return id;
    }

    public Long insertOrderConsignee(OrderConsigneeDO orderConsigneeDO) {
        Long id = (Long) getSqlMapClientTemplate().insert("data_migrate.insertOrderConsignee", orderConsigneeDO);
        return id;
    }

    public Long insertOrderPayment(OrderPaymentDO orderPaymentDO) {
        Long id = (Long) getSqlMapClientTemplate().insert("data_migrate.insertOrderPayment", orderPaymentDO);
        return id;
    }

    public Long insertOrderItem(OrderItemDO orderItemDO) {
        Long id = (Long) getSqlMapClientTemplate().insert("data_migrate.insertOrderItem", orderItemDO);
        return id;
    }
}
