package com.mockuai.itemcenter.core.manager.impl;

import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.OrderManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.tradecenter.client.OrderClient;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 15/11/30.
 */

@Service
public class OrderManagerImpl implements OrderManager{

    private static final Logger log = LoggerFactory.getLogger(OrderManagerImpl.class);

    @Resource
    private OrderClient orderClient;

    @Override
    public OrderDTO getOrderDTO(Long orderId,Long userId, String appKey) {

        Response<OrderDTO> response = orderClient.getOrder(orderId,userId,appKey);

        if(response.getCode()== ResponseCode.SUCCESS.getCode()){
            return response.getModule();
        }else{
            log.error("没有查询到订单 code : {} message : {} orderId {} user Id{}",response.getCode(),response.getMessage(),orderId,userId);
            return null;
        }
    }

    @Override
    public List<OrderDTO> queryOrder(OrderQTO orderQTO, String appKey) throws ItemException{

        Response<List<OrderDTO>> response;

        try {
            response = orderClient.queryOrder(orderQTO, appKey);
        }catch (Exception e){
            throw ExceptionUtil.getException(ResponseCode.SYS_E_SERVICE_EXCEPTION,"itemcenter: 查询订单时出错");
        }

        if(response.getCode()== ResponseCode.SUCCESS.getCode()){
            return response.getModule();
        }else{
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEPENDENT_SERVICE_EXCEPTION,response.getMessage());
        }
    }
}
