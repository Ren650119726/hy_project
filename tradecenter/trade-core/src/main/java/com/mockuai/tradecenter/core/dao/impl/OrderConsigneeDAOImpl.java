package com.mockuai.tradecenter.core.dao.impl;

import com.mockuai.tradecenter.core.dao.OrderConsigneeDAO;
import com.mockuai.tradecenter.core.domain.OrderConsigneeDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zengzhangqiang on 5/23/15.
 */
public class OrderConsigneeDAOImpl extends SqlMapClientDaoSupport implements OrderConsigneeDAO{

    public Long addOrderConsignee(OrderConsigneeDO orderConsigneeDO) {
        return (Long)this.getSqlMapClientTemplate().insert(
                "order_consignee.addOrderConsignee",orderConsigneeDO);
    }

    public OrderConsigneeDO getOrderConsignee(Long orderId, Long userId) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("orderId", orderId);
        params.put("userId", userId);
        return (OrderConsigneeDO)this.getSqlMapClientTemplate().queryForObject(
                "order_consignee.getOrderConsignee",params);
    }

    @Override
    public int updateOrderConsignee(OrderConsigneeDO orderConsigneeDO) {
        return this.getSqlMapClientTemplate().update("order_consignee.updateOrderConsignee",orderConsigneeDO);
    }
}
