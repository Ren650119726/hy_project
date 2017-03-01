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
// * 商品删除, itemDTO 被删除，关联换购直接失效
// * Created by edgar.zr on 12/24/15.
// */
//@Component
//public class ItemDeleteSingleListener extends BaseListener {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(ItemDeleteSingleListener.class);
//
//    @Override
//    public void consumeMessage(JSONObject msg, String appKey) throws VirtualWealthException {
//        LOGGER.debug("{}, appKey : {}", getName(), appKey);
//
//        // 找到换购中 itemId == itemDTO.id
//        // 更新该活动的 itemInvalidTime
//
//        Long itemId = msg.getLong("id");
//        String bizCode = msg.getString("bizCode");
//
//        if (itemId == null || StringUtils.isBlank(bizCode)) {
//            LOGGER.error("itemId or bizCode is empty");
//            return;
//        }
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
//        return RMQMessageType.ITEM_DELETE_SINGLE.combine();
//    }
//}