package com.mockuai.distributioncenter.core.message.msg.parser;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.aliyun.openservices.ons.api.Message;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.AppManager;
import com.mockuai.distributioncenter.core.manager.TradeManager;
import com.mockuai.distributioncenter.core.message.msg.RefundSuccessMsg;
import com.mockuai.distributioncenter.core.util.JsonUtil;
import com.mockuai.tradecenter.common.domain.OrderDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;

/**
 * Created by duke on 16/5/27.
 */
@Service
public class RefundMessageParser {
    private static final Logger log = LoggerFactory.getLogger(RefundMessageParser.class);

    @Autowired
    private AppManager appManager;

    @Autowired
    private TradeManager tradeManager;

    public RefundSuccessMsg parse(Message msg) {
        String msgStr = new String(msg.getBody(), Charset.forName("UTF-8"));
        JSONObject json = JSONObject.parseObject(msgStr);
        Long itemId = json.getLong("itemId");
        Long itemSkuId = json.getLong("itemSkuId");
        Long refundOrderId = json.getLong("id");
        Long orderId = json.getLong("orderId");

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
            orderDTO = tradeManager.getOrder(orderId, null, appKey);
        } catch (DistributionException e) {
            log.error("get order by orderId: {}, buyerId: {} error", orderId);
            return null;
        }
        
        
        RefundSuccessMsg refundSuccessMsg = new RefundSuccessMsg();
        refundSuccessMsg.setAppKey(appKey);
        refundSuccessMsg.setMsg(msg);
        refundSuccessMsg.setOrderId(orderId);
        refundSuccessMsg.setRefundOrderId(refundOrderId);
        refundSuccessMsg.setItemId(itemId);
        refundSuccessMsg.setItemSkuId(itemSkuId);
        refundSuccessMsg.setPaymentId(orderDTO.getPaymentId());
        log.info("parse refundMessage msg: {}", JsonUtil.toJson(refundSuccessMsg));
        return refundSuccessMsg;
    }
}
