package com.mockuai.imagecenter.core.message.listener;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.mockuai.imagecenter.common.api.ImageService;
import com.mockuai.imagecenter.common.api.action.BaseRequest;
import com.mockuai.imagecenter.common.api.action.Response;
import com.mockuai.imagecenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.MessageTagEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.Charset;

/**
 * Created by 冠生 on 15/11/18.
 */
public class ItemDeleteListener implements MessageListener {
    private static final Logger log = LoggerFactory.getLogger(ItemDeleteListener.class);

    @Autowired
    private ImageService imageService;

    @Override
    public Action consume(Message msg, ConsumeContext consumeContext) {
        log.info("receive a message, msgId: {}, key: {},tag:{}", msg.getMsgID(), msg.getKey(),msg.getTag());
        //如果不是更新商品信息则，不更新二维码图片
        if(!MessageTagEnum.ITEM_UPDATE.getTag().equals(msg.getTag())){
            log.info("不是更新商品基本信息消息 msgId: {}, key: {},tag:{}", msg.getMsgID(), msg.getKey(),msg.getTag());
            return Action.CommitMessage;
        }
        log.info("更新商品基本信息消息 msgId: {}, key: {},tag:{}", msg.getMsgID(), msg.getKey(),msg.getTag());
        String msgStr = new String(msg.getBody(), Charset.forName("UTF-8"));
        try {
            log.info("receive sync  itemDTO  msg:{}",msgStr);
            ItemDTO jsonObject = JSONObject.parseObject(msgStr, ItemDTO.class);
            BaseRequest request = new BaseRequest();
            request.setParam("itemId",jsonObject.getId());

            request.setCommand(ActionEnum.DELETE_ITEM.getActionName());
            Response response =  imageService.execute(request);
            if(!response.isSuccess()){
                log.error("consume delete item qrcode finished message error, errMsg: {}", response.getMessage());
                return Action.CommitMessage;
            }
        }catch (Exception e){
            log.error("consume delete item qrcode finished message error, errMsg: {}", e);
            return Action.CommitMessage;
        }

        return Action.CommitMessage;

    }
}
