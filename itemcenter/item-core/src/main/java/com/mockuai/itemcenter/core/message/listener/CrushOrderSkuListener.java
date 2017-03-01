package com.mockuai.itemcenter.core.message.listener;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.mockuai.appcenter.common.constant.AppTypeEnum;
import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.AppManager;
import com.mockuai.itemcenter.core.service.action.itemsku.CrushOrderSkuStockAction;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

/**
 * Created by yindingyu on 16/6/24.
 */
public class CrushOrderSkuListener implements MessageListener {

    private static final Logger log = LoggerFactory.getLogger(CrushOrderSkuListener.class);

    @Resource
    TransactionTemplate transactionTemplate;

    @Resource
    private CrushOrderSkuStockAction crushOrderSkuStockAction;

    @Resource
    private AppManager appManager;

    @Override
    public Action consume(Message msg, final ConsumeContext consumeContext) {


        log.info("Enter [{}]: ", getClass().getName());

        log.info("收到了消息 msg : {}", new String(msg.getBody()));

        log.info("msg topic = {}, tag = {}, body = {}", msg.getTopic(), msg.getTag(), new String(msg.getBody()).toString());

        OrderDTO orderDTO = JSONObject.parseObject(new String(msg.getBody()), OrderDTO.class);


        try {

            final String orderSn = orderDTO.getOrderSn();

            final String bizCode = orderDTO.getBizCode();

            final String appKey = appManager.getAppInfoByType(bizCode,AppTypeEnum.APP_WAP).getAppKey();

            transactionTemplate.execute(new TransactionCallback() {

                public Object doInTransaction(TransactionStatus status) {
                    try {
                        ItemResponse itemResponse = crushOrderSkuStockAction.crush(orderSn,bizCode,appKey);
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
            log.error("更新商品销量索引失败 error_code :{} msg : {}", e.getCode(), e.getMessage());
        }

        return Action.CommitMessage;
    }
}
