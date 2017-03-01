package com.mockuai.marketingcenter.core.message.consumer.listener;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.marketingcenter.common.constant.RMQMessageType;
import com.mockuai.marketingcenter.common.constant.ToolType;
import com.mockuai.marketingcenter.core.domain.LimitOderInfoDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.LimitOderInfoManager;
import com.mockuai.marketingcenter.core.message.consumer.BaseListener;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import com.mockuai.tradecenter.client.OrderClient;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderDiscountInfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**接受支付成功的订单信息，并保存定单信息
 * Created by huangsiqian on 2016/11/15.
 */
@Component
public class LimitPurchasePayListener extends BaseListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(LimitPurchasePayListener.class);
    @Resource
    private OrderClient orderClient;


    @Resource
    private LimitOderInfoManager limitOderInfoManager;

    @Override
    public void init() {

    }

    @Override
    public String getName() {
        return RMQMessageType.TRADE_PAY_SUCCESS_NOTIFY.combine();
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
                //存入活动id(存之前判断去重,去重条件是activityId)存到orderId统计表中,存入表中参数orderid ,activityId,UserId
                LimitOderInfoDO limitOder = new LimitOderInfoDO();
                //先存订单信息
                LimitOderInfoDO oderInfoDO = new LimitOderInfoDO();
                LOGGER.info("activityId:{}",orderDiscountInfo.getMarketActivityId());
                oderInfoDO.setActivityId(orderDiscountInfo.getMarketActivityId());

                oderInfoDO.setOrderId(orderId);
                oderInfoDO.setUserId(orderDiscountInfo.getUserId());
                //先查表中是否有该定单信息如果没有就添加
                limitOder = limitOderInfoManager.queryLimitOderInfos(oderInfoDO);
                if(limitOder==null) {
                    limitOderInfoManager.addLimitOderInfo(oderInfoDO);
                }
            }
        }
    }

    @Override
    public Logger getLogger() {
        return this.LOGGER;
    }
}
