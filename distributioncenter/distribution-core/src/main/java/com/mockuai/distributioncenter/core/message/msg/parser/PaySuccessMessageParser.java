package com.mockuai.distributioncenter.core.message.msg.parser;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.aliyun.openservices.ons.api.Message;
import com.mockuai.distributioncenter.common.domain.dto.DistributionItemDTO;
import com.mockuai.distributioncenter.common.domain.dto.DistributionOrderDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.AppManager;
import com.mockuai.distributioncenter.core.manager.HeadsingleManager;
import com.mockuai.distributioncenter.core.manager.TradeManager;
import com.mockuai.distributioncenter.core.message.msg.PaySuccessMsg;
import com.mockuai.distributioncenter.core.util.JsonUtil;
import com.mockuai.headsinglecenter.common.domain.dto.HeadSingleSubDTO;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderDiscountInfoDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.common.enums.EnumPaymentMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by duke on 16/5/27.
 */
@Service
public class PaySuccessMessageParser {
    private static final Logger log = LoggerFactory.getLogger(PaySuccessMessageParser.class);

    @Autowired
    private AppManager appManager;

    @Autowired
    private TradeManager tradeManager;

    @Autowired
    private HeadsingleManager headsingleManager;
    public PaySuccessMsg parse(Message msg) {
        String msgStr = new String(msg.getBody(), Charset.forName("UTF-8"));
        JSONObject json = JSONObject.parseObject(msgStr);
        Long orderId = json.getLong("id");
        Long buyerId = json.getLong("userId");
        String bizCode = json.getString("bizCode");
        String appKey;
        try {
            appKey = appManager.getAppKeyByBizCode(bizCode);
        } catch (DistributionException e) {
            log.error("get appKey error, bizCode: {}", bizCode);
            return null;
        }

        OrderDTO orderDTO;
        try {
            orderDTO = tradeManager.getOrder(orderId, buyerId, appKey);
        } catch (DistributionException e) {
            log.error("get order by orderId: {}, buyerId: {} error", orderId, buyerId);
            return null;
        }

        PaySuccessMsg paySuccessMsg = new PaySuccessMsg();
        paySuccessMsg.setAppKey(appKey);
        paySuccessMsg.setMsg(msg);

        DistributionOrderDTO distributionOrderDTO = new DistributionOrderDTO();
        distributionOrderDTO.setOrderId(orderId);
        distributionOrderDTO.setOrderSn(orderDTO.getOrderSn());
        distributionOrderDTO.setAppKey(appKey);
        distributionOrderDTO.setUserId(buyerId);
        distributionOrderDTO.setPaymentId(orderDTO.getPaymentId());
        List<DistributionItemDTO> distributionItemDTOs =  new ArrayList<DistributionItemDTO>();
        for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
            DistributionItemDTO distributionItemDTO = new DistributionItemDTO();
            distributionItemDTO.setItemId(orderItemDTO.getItemId());
            distributionItemDTO.setItemSkuId(orderItemDTO.getItemSkuId());
            distributionItemDTO.setNumber(orderItemDTO.getNumber());
            distributionItemDTO.setUnitPrice(orderItemDTO.getUnitPrice());
            distributionItemDTO.setSellerId(orderItemDTO.getDistributorId());
            distributionItemDTO.setItemName(orderItemDTO.getItemName());
            distributionItemDTOs.add(distributionItemDTO);
        }
        distributionOrderDTO.setItemDTOs(distributionItemDTOs);

        paySuccessMsg.setDistributionOrderDTO(distributionOrderDTO);

        paySuccessMsg.setFilter(true);
        //首单立减 设置是否分拥
        List<OrderDiscountInfoDTO> list = orderDTO.getOrderDiscountInfoDTOs();
        for (OrderDiscountInfoDTO orderDiscountInfoDTO : list) {
        	   if(orderDiscountInfoDTO.getDiscountCode().equals("FirstOrderDiscount")){
        		   Long marketActivityId =  orderDiscountInfoDTO.getMarketActivityId();
        		   log.info("queryHeadSingleSubById HeadSingleSubDTO , marketActivityId: {}", marketActivityId);
        		   HeadSingleSubDTO  HeadSingleSubDTO = headsingleManager.queryHeadSingleSubById(marketActivityId, appKey);
        		   log.info("queryHeadSingleSubById HeadSingleSubDTO: {}", JsonUtil.toJson(HeadSingleSubDTO));
        		   if(HeadSingleSubDTO != null){
        			   if(HeadSingleSubDTO.getDiscomStatus() != 1){
        				   paySuccessMsg.setFilter(false);
        			   }
        		   }
        	   }
		}
        
        log.info("parse paySuccess msg: {}", JsonUtil.toJson(paySuccessMsg.getDistributionOrderDTO()));
        return paySuccessMsg;
    }
}
