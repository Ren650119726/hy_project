package com.mockuai.marketingcenter.core.message.consumer.listener;

import com.alibaba.fastjson.JSONObject;

import com.mockuai.marketingcenter.client.MarketingClient;
import com.mockuai.marketingcenter.client.impl.LimitedPurchaseClientImpl;
import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.RMQMessageType;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.constant.ToolType;
import com.mockuai.marketingcenter.common.domain.dto.LimitedUserCorrelationDTO;
import com.mockuai.marketingcenter.core.domain.LimitOderInfoDO;
import com.mockuai.marketingcenter.core.domain.LimitedUserCorrelationDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.LimitOderInfoManager;
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

/**接受支付成功和退单信息最外层ACTION
 * Created by csy on 15/11/4.
 */
@Component
public class LimitedPurchasePaySuccessListener extends BaseListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(LimitedPurchasePaySuccessListener.class);
    @Resource
    private OrderClient orderClient;


    @Resource
    private LimitOderInfoManager limitOderInfoManager;
    @Resource
    private LimitedUserCorrelationManager limitedUserCorrelationManager;


    @Override
    public void init() {

    }

    @Override
    public String getName() {
        return RMQMessageType.TRADE_ORDER_UNPAID.combine();
    }

    @Override
    public void consumeMessage(JSONObject msg, String appKey) throws MarketingException {
        Long orderId = msg.getLong("id");
        Long userId = msg.getLong("userId");
        String bizCode = msg.getString("bizCode");



        //查询订单信息
        Response<OrderDTO> orderDtoRe = orderClient.getOrder(orderId, userId, appKey);


        OrderDTO orderDTO = orderDtoRe.getModule();
        LOGGER.info("orderMSG:{}", JsonUtil.toJson(orderDTO));

        //判断订单活动类型
        List<OrderDiscountInfoDTO> OrderDiscountInfoDTOs = orderDTO.getOrderDiscountInfoDTOs();



        //循环判断是否存在限时购的订单

        //新建list存skuid
        List<Long> skuIdList = new ArrayList<>();
        for(OrderDiscountInfoDTO orderDiscountInfo:OrderDiscountInfoDTOs){
            if(ToolType.TIME_RANGE_DISCOUNT.getCode().equals(orderDiscountInfo.getDiscountCode())){
                skuIdList.add(orderDiscountInfo.getItemSkuId());
               /* //存入活动id(存之前判断去重,去重条件是activityId)存到orderId统计表中,存入表中参数orderid ,activityId,UserId
                LimitOderInfoDO limitOder = new LimitOderInfoDO();
                //先存订单信息
                LimitOderInfoDO oderInfoDO = new LimitOderInfoDO();
                LOGGER.info("activityId:{}",orderDiscountInfo.getMarketActivityId());
                oderInfoDO.setActivityId(orderDiscountInfo.getMarketActivityId());

                oderInfoDO.setOrderId(orderId);
                oderInfoDO.setUserId(orderDiscountInfo.getUserId());
                limitOder = limitOderInfoManager.queryLimitOderInfos(oderInfoDO);
                if(limitOder==null) {
                    limitOderInfoManager.addLimitOderInfo(oderInfoDO);
                }*/
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
            //存入活动id
            for(OrderDiscountInfoDTO orderDiscountInfo:OrderDiscountInfoDTOs){
                if(ToolType.TIME_RANGE_DISCOUNT.getCode().equals(orderDiscountInfo.getDiscountCode())){
                    if(entry.getKey().getItemSkuId().equals(orderDiscountInfo.getItemSkuId())){
                        user.setActivityId(orderDiscountInfo.getMarketActivityId());
                    }
                }
            }
            paylist.add(user);
        }

        List<LimitedUserCorrelationDTO> list = paylist;
        //保存订单信息

        for(LimitedUserCorrelationDTO userDTO : list){
            LimitedUserCorrelationDO user = new LimitedUserCorrelationDO();


            BeanUtils.copyProperties(userDTO == null ? new LimitedUserCorrelationDTO() : userDTO, user);
            LOGGER.info("get into user:{}", JsonUtil.toJson(user));
            //查询条件是activity_id item_id user_id
            LimitedUserCorrelationDO userIsNull = limitedUserCorrelationManager.selectUserMsg(user);
            LOGGER.info("userIsNull:{}", JsonUtil.toJson(userIsNull));

            Boolean flag = null;
            //添加到表中
            if (userIsNull==null){

                flag = limitedUserCorrelationManager.addUserMsg(user);

            }else {
                flag = limitedUserCorrelationManager.updatePurchaseQuantity(user);
            }
            LOGGER.info("flag:{}", flag);
        }

    }

    @Override
    public Logger getLogger() {
        return this.LOGGER;
    }

}