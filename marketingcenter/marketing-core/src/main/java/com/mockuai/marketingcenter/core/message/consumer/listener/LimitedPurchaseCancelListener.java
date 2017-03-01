package com.mockuai.marketingcenter.core.message.consumer.listener;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.RMQMessageType;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.constant.ToolType;
import com.mockuai.marketingcenter.common.domain.dto.LimitedUserCorrelationDTO;
import com.mockuai.marketingcenter.core.domain.LimitedUserCorrelationDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.LimitedUserCorrelationManager;
import com.mockuai.marketingcenter.core.message.consumer.BaseListener;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import com.mockuai.tradecenter.client.OrderClient;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderDiscountInfoDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangsiqian on 2016/11/11.
 */
@Component
public class LimitedPurchaseCancelListener extends BaseListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(LimitedPurchaseCancelListener.class);
    @Resource
    private OrderClient orderClient;
    @Resource
    private LimitedUserCorrelationManager limitedUserCorrelationManager;

    @Override
    public void init() {

    }

    @Override
    public String getName() {
        return RMQMessageType.REFUND_SUCCESS.combine();
    }

    @Override
    public void consumeMessage(JSONObject msg, String appKey) throws MarketingException {
        Long orderId = msg.getLong("orderId");
        Long userId = msg.getLong("userId");
        String bizCode = msg.getString("bizCode");
        LOGGER.info("orderId:{},userId:{}",orderId,userId);


        //查询订单信息
        Response<OrderDTO> orderDtoRe = orderClient.getOrder(orderId, userId, appKey);

        OrderDTO orderDTO = orderDtoRe.getModule();
        LOGGER.info("orderCancelMSG:{}", JsonUtil.toJson(orderDTO));

        //判断订单活动类型
        List<OrderDiscountInfoDTO> OrderDiscountInfoDTOs = orderDTO.getOrderDiscountInfoDTOs();
        if(OrderDiscountInfoDTOs.isEmpty()){
            return ;
        }
        List<Long> skuIdList = new ArrayList<>();
        for(OrderDiscountInfoDTO orderDiscountInfo:OrderDiscountInfoDTOs){
            if(ToolType.TIME_RANGE_DISCOUNT.getCode().equals(orderDiscountInfo.getDiscountCode())){
                skuIdList.add(orderDiscountInfo.getItemSkuId());
            }
        }

        List<OrderItemDTO> goodslist = orderDTO.getOrderItems();
        //取出itemDTO与数量对应map

        Map<OrderItemDTO, Long> map = new HashMap<OrderItemDTO, Long>();



        for(OrderItemDTO orderItemDTO :goodslist){
            //限时购信息中skuId和订单信息中skuId对比，有的话取出
            if(skuIdList.contains(orderItemDTO.getItemSkuId())) {
                if (map.get(orderItemDTO.getItemSkuId()) == null) {
                    Long numbers = Long.valueOf(orderItemDTO.getNumber());
                    map.put(orderItemDTO, numbers);
                } else {
                    Long numbers = map.get(orderItemDTO.getItemSkuId()) + Long.valueOf(orderItemDTO.getNumber());
                    map.put(orderItemDTO, numbers);
                }
            }
        }
        List<LimitedUserCorrelationDTO> paylist = new ArrayList<>();
        for (Map.Entry<OrderItemDTO,Long> entry : map.entrySet()) {
            //限时购活动用户商品数量统计中间表
            LimitedUserCorrelationDTO user = new LimitedUserCorrelationDTO();
            user.setUserId(orderDTO.getUserId());
            user.setItemId(entry.getKey().getItemId());
            user.setSkuId(entry.getKey().getItemSkuId());
            user.setPurchaseQuantity(entry.getValue());
            //存入活动id(一个订单只对应一个限购活动，循环只能取出一个activityid)
            for(OrderDiscountInfoDTO orderDiscountInfo:OrderDiscountInfoDTOs){
                if(ToolType.TIME_RANGE_DISCOUNT.getCode().equals(orderDiscountInfo.getDiscountCode())) {
                    if (entry.getKey().getItemSkuId().equals(orderDiscountInfo.getItemSkuId())) {
                        user.setActivityId(orderDiscountInfo.getMarketActivityId());
                    }
                }
            }
            paylist.add(user);
        }
        LOGGER.info("orderCancelInfo:{}",JsonUtil.toJson(paylist));
        List<LimitedUserCorrelationDTO> list = paylist;
        for(LimitedUserCorrelationDTO userDTO : list){
            LimitedUserCorrelationDO user = new LimitedUserCorrelationDO();
            BeanUtils.copyProperties(userDTO == null ? new LimitedUserCorrelationDTO() : userDTO, user);

            //添加到表中
            Boolean flag = limitedUserCorrelationManager.orderCancelledgoods(user);
        }


    }

    @Override
    public Logger getLogger() {
        return this.LOGGER;
    }
}
