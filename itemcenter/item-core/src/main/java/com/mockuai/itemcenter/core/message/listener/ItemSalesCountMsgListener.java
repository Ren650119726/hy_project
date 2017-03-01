package com.mockuai.itemcenter.core.message.listener;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.core.manager.AppManager;
import com.mockuai.itemcenter.core.message.msg.PaySuccessMsg;
import com.mockuai.itemcenter.core.message.parser.PaySuccessMessageParser;

/**
 * 商品销量统计 
 * 
 * @author csy
 *
 */
public class ItemSalesCountMsgListener implements Listener {
    private static final Logger log = LoggerFactory.getLogger(ItemSalesCountMsgListener.class);
    
    @Resource
    private PaySuccessMessageParser paySuccessMessageParser;
    
    @Resource
    private AppManager appManager;
    
    @Resource
    private ItemService itemService;

    @SuppressWarnings("unchecked")
	@Override
    public Action consume(Message msg, ConsumeContext consumeContext) {
        log.info("Enter 商品ItemSalesCountMsgListener销量[{}]: ", getClass().getName());
        
        //只接受支付成功和退款的消息
        if(!msg.getTag().equals("paySuccessNotify") && !msg.getTag().equals("refundSuccess")){
        	return Action.CommitMessage;
        }
        
        //初始化数据
        PaySuccessMsg paySuccessMsg = paySuccessMessageParser.parse(msg, msg.getTag());
        
        if (paySuccessMsg == null) {
            log.error("parse paySuccessMsg error");
            return Action.CommitMessage;
        }
		
		//封装公共参数
		BaseRequest request = new BaseRequest();
		request.setParam("paySuccessMsg", paySuccessMsg);
		request.setParam("appKey", paySuccessMsg.getAppKey());
		
		//·1·支付成功消息***********************************
        if (msg.getTag() != null && msg.getTag().equals("paySuccessNotify")) {
        	log.info("msg topic = {}, tag = {}, body = {}", msg.getTopic(), msg.getTag(), new String(msg.getBody()).toString()); 
        	
        	request.setParam("salesParam", "add");
            request.setCommand(ActionEnum.ITEM_SALESCOUNT.getActionName());           
            Response<Boolean> response = itemService.execute(request);
			
            if (!response.isSuccess()) {
                log.error("consume paySuccess message error, errMsg: {}", response.getMessage());
            } else {
                if (response.getModule()) {
                    log.info("consume paySuccess message successful");
                } else {
                    log.info("consume later");
                    return Action.ReconsumeLater;
                }
            }            
		//·2·退款成功消息*********************************************
        }else if(msg.getTag() != null && msg.getTag().equals("refundSuccess")){
            log.info("msg topic = {}, tag = {}, body = {}", msg.getTopic(), msg.getTag(), new String(msg.getBody()).toString());    
                     
            request.setParam("salesParam", "red");
            request.setCommand(ActionEnum.ITEM_SALESCOUNT.getActionName());           
            Response<Boolean> response = itemService.execute(request);
			
            if (!response.isSuccess()) {
                log.error("consume paySuccess message error, errMsg: {}", response.getMessage());
            } else {
                if (response.getModule()) {
                    log.info("consume paySuccess message successful");
                } else {
                    log.info("consume later");
                    return Action.ReconsumeLater;
                }
            }			
        }
        
        return Action.CommitMessage;
    }
}