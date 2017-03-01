package com.mockuai.virtualwealthcenter.core.manager.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mockuai.tradecenter.client.OrderClient;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.TradeManager;
import com.mockuai.virtualwealthcenter.core.util.JsonUtil;

/**
 * Created by edgar.zr on 12/16/15.
 */
public class TradeManagerImpl implements TradeManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(TradeManagerImpl.class);

    @Autowired
    private OrderClient orderClient;

    @Override
    public OrderDTO getOrder(Long orderId, Long userId, String appKey) throws VirtualWealthException {
        com.mockuai.tradecenter.common.api.Response<OrderDTO> response = orderClient.getOrder(orderId, userId, appKey);
        if (!response.isSuccess()) {
            LOGGER.error("get order error, errCode: {}, errMsg: {}", response.getCode(), response.getMessage());
            throw new VirtualWealthException(ResponseCode.SERVICE_EXCEPTION);
        }
        return response.getModule();
    }

    @Override
    public List<OrderDTO> queryOrder(OrderQTO orderQTO, String appKey) throws VirtualWealthException {
        try {
            com.mockuai.tradecenter.common.api.Response<List<OrderDTO>> response = orderClient.queryOrder(orderQTO, appKey);
            if (response.isSuccess()) {
                return response.getModule();
            }
            LOGGER.error("error to queryOrder, orderQTO : {}, appKey : {}, resCode : {}, resMsg : {}",
                    JsonUtil.toJson(orderQTO), appKey, response.getCode(), response.getMessage());
            throw new VirtualWealthException(ResponseCode.SERVICE_EXCEPTION, response.getMessage());
        } catch (VirtualWealthException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error to queryOrder, orderQTO : {}, appKey : {}",
                    JsonUtil.toJson(orderQTO), appKey, e);
            throw new VirtualWealthException(ResponseCode.SERVICE_EXCEPTION);
        }
    }
}