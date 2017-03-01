package com.mockuai.tradecenter.core.manager.impl;

import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.core.dao.OrderInvoiceDAO;
import com.mockuai.tradecenter.core.domain.OrderInvoiceDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.OrderInvoiceManager;

import javax.annotation.Resource;

/**
 * Created by zengzhangqiang on 5/23/15.
 */
public class OrderInvoiceManagerImpl implements OrderInvoiceManager{
    @Resource
    private OrderInvoiceDAO orderInvoiceDAO;

    public Long addOrderInvoice(OrderInvoiceDO orderInvoiceDO) throws TradeException{
        try{
            Long invoiceId = this.orderInvoiceDAO.addOrderInvoice(orderInvoiceDO);
            return invoiceId;
        }catch(Exception e){
            throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
        }
    }

    public OrderInvoiceDO getOrderInvoice(Long orderId, Long userId) throws TradeException{
        try{
            OrderInvoiceDO invoice = this.orderInvoiceDAO.getOrderInvoice(orderId, userId);
            return invoice;
        }catch(Exception e){
            throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
        }

    }
}
