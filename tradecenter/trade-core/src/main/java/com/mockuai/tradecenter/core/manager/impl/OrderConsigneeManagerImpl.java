package com.mockuai.tradecenter.core.manager.impl;

import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderConsigneeDTO;
import com.mockuai.tradecenter.core.dao.OrderConsigneeDAO;
import com.mockuai.tradecenter.core.domain.OrderConsigneeDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.OrderConsigneeManager;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

/**
 * Created by zengzhangqiang on 5/23/15.
 */
public class OrderConsigneeManagerImpl implements OrderConsigneeManager{
    @Resource
    private OrderConsigneeDAO orderConsigneeDAO;
    private static final Logger log = LoggerFactory.getLogger(OrderConsigneeManagerImpl.class);

    public Long addOrderConsignee(OrderConsigneeDO orderConsigneeDO) throws TradeException{
        try{
            Long orderConsigneeId = this.orderConsigneeDAO.addOrderConsignee(orderConsigneeDO);
            return orderConsigneeId;
        }catch(Exception e){
        	log.error("addOrderConsignee error",e);
            throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
        }

    }

    public OrderConsigneeDO getOrderConsignee(Long orderId, Long userId) throws TradeException{
        try{
            OrderConsigneeDO orderConsigneeDO = this.orderConsigneeDAO.getOrderConsignee(orderId, userId);
            return orderConsigneeDO;
        }catch(Exception e){
            throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
        }

    }

    @Override
    public int updateOrderConsignee(OrderConsigneeDTO orderConsigneeDTO) throws TradeException {
        OrderConsigneeDO orderConsigneeDO = new OrderConsigneeDO();
        BeanUtils.copyProperties(orderConsigneeDTO, orderConsigneeDO);
        return orderConsigneeDAO.updateOrderConsignee(orderConsigneeDO);
    }
}
