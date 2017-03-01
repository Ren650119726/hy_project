package com.mockuai.itemcenter.core.message.listener;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.mockuai.appcenter.common.constant.AppTypeEnum;
import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.*;
import com.mockuai.itemcenter.core.service.action.itemsku.CrushOrderSkuStockAction;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.List;

//import com.mockuai.distributioncenter.common.domain.dto.RatioDTO;

/**
 * Created by duke on 15/9/28.
 */
public class TradeMsgListener implements Listener {


    private static final Logger log = LoggerFactory.getLogger(TradeMsgListener.class);

    @Resource
    private ItemSearchManager itemSearchManager;

    @Resource
    private ItemCommentManager itemCommentManager;

    @Resource
    private ItemSuitManager itemSuitManager;

    @Resource
    private ItemSalesVolumeManager itemSalesVolumeManager;

    @Resource
    TransactionTemplate transactionTemplate;

    @Resource
    private CrushOrderSkuStockAction crushOrderSkuStockAction;

    @Resource
    private AppManager appManager;



    public ItemSearchManager getItemSearchManager() {
        return itemSearchManager;
    }

    public void setItemSearchManager(ItemSearchManager itemSearchManager) {
        this.itemSearchManager = itemSearchManager;
    }

    @Override
    public Action consume(Message msg, ConsumeContext consumeContext) {

        log.info("Enter [{}]: ", getClass().getName());

        log.info("收到了消息 msg : {}", new String(msg.getBody()));

        if (msg.getTag() != null && msg.getTag().equals("paySuccessNotify")) {

            log.info("msg topic = {}, tag = {}, body = {}", msg.getTopic(), msg.getTag(), new String(msg.getBody()).toString());

            OrderDTO orderDTO = JSONObject.parseObject(new String(msg.getBody()), OrderDTO.class);

            if (orderDTO.getOrderItems() != null) {
                List<OrderItemDTO> orderItemDTOs = orderDTO.getOrderItems();

                try {

                    itemSalesVolumeManager.updateItemSalesVolume(orderItemDTOs);
                    itemSearchManager.updateItemSalesVolume(orderItemDTOs);
                } catch (ItemException e) {
                    log.error("更新商品销量索引失败 error_code :{} msg : {}", e.getCode(), e.getMessage());
                }

                for (OrderItemDTO orderItemDTO : orderItemDTOs) {
                    if (orderItemDTO.getItemType() == DBConst.SUIT_ITEM.getCode()) {

                        try {
                            itemSuitManager.increaseSuitSalesVolume(orderItemDTO.getItemId(), orderItemDTO.getSellerId(), orderItemDTO.getNumber().longValue());
                        } catch (ItemException e) {
                            log.error("套装销量数据更新失败 error_code :{} msg : {}", e.getCode(), e.getMessage());
                        }
                    }
                }

            }


        }/*else if(msg.getTag() != null && msg.getTag().equals("orderUnpaid")){

            log.info("msg topic = {}, tag = {}, body = {}", msg.getTopic(), msg.getTag(), new String(msg.getBody()).toString());

            OrderDTO orderDTO = JSONObject.parseObject(new String(msg.getBody()), OrderDTO.class);
            try {

                final String orderSn = orderDTO.getOrderSn();

                final String bizCode = orderDTO.getBizCode();

                final String appKey = appManager.getAppInfoByType(bizCode, AppTypeEnum.APP_WAP).getAppKey();

                transactionTemplate.execute(new TransactionCallback() {

                    public Object doInTransaction(TransactionStatus status) {
                        try {
                            ItemResponse itemResponse = crushOrderSkuStockAction.crush(orderSn, bizCode, appKey);
                            if (itemResponse.isSuccess() == false) {
                                log.error("", itemResponse.getMessage());
                                status.setRollbackOnly();
                            }
                            return itemResponse;
                        } catch (ItemException e) {
                            log.error(e.getMessage(), e);
                            status.setRollbackOnly();
                            return ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
                        } catch (Exception e) {
                            log.error(e.getMessage(), e);
                            status.setRollbackOnly();
                            return ResponseUtil.getErrorResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
                        }

                    }

                });

            } catch (ItemException e) {
                log.error("减扣库存失败 error_code :{} msg : {}", e.getCode(), e.getMessage());
            }


        }*/

        return Action.CommitMessage;
    }
}