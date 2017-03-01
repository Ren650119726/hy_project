package com.mockuai.distributioncenter.core.manager.impl;

import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.TradeManager;
import com.mockuai.tradecenter.client.OrderClient;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.domain.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.List;

/**
 * Created by duke on 16/3/18.
 */
@Component
public class TradeManagerImpl implements TradeManager {
    private static final Logger log = LoggerFactory.getLogger(TradeManagerImpl.class);

    @Autowired
    private OrderClient orderClient;

    @Override
    public OrderDTO getOrder(Long orderId, Long userId, String appKey) throws DistributionException {
        Response<OrderDTO> response = orderClient.getOrder(orderId, userId, appKey);
        if (!response.isSuccess()) {
            log.error("get order error, errCode: {}, errMsg: {}", response.getCode(), response.getMessage());
            throw new DistributionException(ResponseCode.INVOKE_SERVICE_EXCEPTION);
        }
        return response.getModule();
    }

    @Override
    public List<OrderDTO> queryOrder(OrderQTO orderQTO, String appKey) throws DistributionException {
        Response<List<OrderDTO>> response = orderClient.queryOrder(orderQTO, appKey);
        if (!response.isSuccess()) {
            log.error("get order error, errCode: {}, errMsg: {}", response.getCode(), response.getMessage());
            throw new DistributionException(ResponseCode.INVOKE_SERVICE_EXCEPTION);
        }
        return response.getModule();
    }

    @Override
    public List<OrderTrackDTO> queryOrderTrack(Long orderId, Long userId, String appKey) throws DistributionException {
        Response<List<OrderTrackDTO>> response = orderClient.queryOrderTrack(orderId, userId, appKey);
        if (!response.isSuccess()) {
            log.error("query order track error, errCode: {}, errMsg: {}", response.getCode(), response.getMessage());
            throw new DistributionException(ResponseCode.INVOKE_SERVICE_EXCEPTION);
        }
        return response.getModule();
    }
    
    @Override
    public List<OrderDTO> getUsers(OrderQTO orderQTO,String appKey) throws DistributionException{
    	Response<List<OrderDTO>> response = orderClient.getUsers(orderQTO, appKey);
        if (!response.isSuccess()) {
            log.error("query order track error, errCode: {}, errMsg: {}", response.getCode(), response.getMessage());
            throw new DistributionException(ResponseCode.INVOKE_SERVICE_EXCEPTION);
        }
        return response.getModule();
    }
}
