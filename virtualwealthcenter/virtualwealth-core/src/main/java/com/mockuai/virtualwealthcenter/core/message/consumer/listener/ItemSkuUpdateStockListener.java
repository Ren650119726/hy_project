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
// * sku 变化
// * <p/>
// * 判断 itemSkuDTO 中商品的数量是否 0, 是则表示商品失效
// * <p/>
// * Created by edgar.zr on 12/24/15.
// */
//@Component
//public class ItemSkuUpdateStockListener extends BaseListener {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(ItemSkuUpdateStockListener.class);
//
//    @Override
//    public void consumeMessage(JSONObject msg, String appKey) throws VirtualWealthException {
//        LOGGER.debug("{}, appKey : {}", getName(), appKey);
//
//        // 过滤所有库存数量为 0 的商品
//        // 找到关联 skuId 的换购活动，更新 itemInvalidTime
//
//        Long skuId = msg.getLong("id");
//        String bizCode = msg.getString("bizCode");
//        Long stockNum = msg.getLong("stockNum");
//
//        if (skuId == null || StringUtils.isBlank(bizCode) || stockNum == null) {
//            LOGGER.error("skuId or bizCode is empty or stockNum is null, msg : {}", msg.toJSONString());
//            return;
//        }
//
//        if (stockNum.intValue() != 0) return;
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
//        return RMQMessageType.ITEM_SKU_UPDATE_STOCK.combine();
//    }
//}