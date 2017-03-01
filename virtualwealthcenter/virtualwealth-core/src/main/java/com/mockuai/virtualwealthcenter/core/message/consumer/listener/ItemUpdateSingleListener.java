//package com.mockuai.virtualwealthcenter.core.message.consumer.listener;
//
//import com.alibaba.fastjson.JSONObject;
//import com.mockuai.virtualwealthcenter.common.constant.RMQMessageType;
//import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
//import com.mockuai.virtualwealthcenter.core.message.consumer.BaseListener;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
///**
// * 商品修改，itemDTO 非 4 ，失效，同时判断 itemSKUDTO 是否数量为 0， 为零也失效
// * (itemSKUDTO 在消息中带有)
// * Created by edgar.zr on 12/24/15.
// */
//@Component
//public class ItemUpdateSingleListener extends BaseListener {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(ItemUpdateSingleListener.class);
//
//    @Override
//    public void consumeMessage(JSONObject msg, String appKey) throws VirtualWealthException {
//        LOGGER.debug("{}, appKey : {}", getName(), appKey);
//
//        // 过滤 itemStatus != 4 || skuDTO.stockNum == 0 的商品(把 skuDTO 转换到对应的 itemId)
//        // 找到 itemId 关联的换购活动，更新 itemInvalidTime
//        Long itemId = msg.getLong("id");
//        String bizCode = msg.getString("bizCode");
//
//        if (itemId == null || StringUtils.isBlank(bizCode)) {
//            LOGGER.error("itemId or bizCode is empty");
//            return;
//        }
//
//    }
//
//    @Override
//    public Logger getLogger() {
//        return this.LOGGER;
//    }
//
//    @Override
//    public void init() {
//
//    }
//
//    @Override
//    public String getName() {
//        return RMQMessageType.ITEM_UPDATE_SINGLE.combine();
//    }
//}