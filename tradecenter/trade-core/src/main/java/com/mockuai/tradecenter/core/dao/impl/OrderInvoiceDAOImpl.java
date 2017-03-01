package com.mockuai.tradecenter.core.dao.impl;

import com.mockuai.tradecenter.core.dao.OrderInvoiceDAO;
import com.mockuai.tradecenter.core.domain.OrderConsigneeDO;
import com.mockuai.tradecenter.core.domain.OrderInvoiceDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zengzhangqiang on 5/23/15.
 */
public class OrderInvoiceDAOImpl extends SqlMapClientDaoSupport implements OrderInvoiceDAO {
    public Long addOrderInvoice(OrderInvoiceDO orderInvoiceDO) {
        return (Long)this.getSqlMapClientTemplate().insert(
                "order_invoice.addOrderInvoice",orderInvoiceDO);
    }

    public OrderInvoiceDO getOrderInvoice(Long orderId, Long userId) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("orderId", orderId);
        params.put("userId", userId);
        return (OrderInvoiceDO)this.getSqlMapClientTemplate().queryForObject(
                "order_invoice.getOrderInvoice", params);
    }
}
