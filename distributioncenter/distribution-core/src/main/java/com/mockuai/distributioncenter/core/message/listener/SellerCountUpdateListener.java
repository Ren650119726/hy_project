package com.mockuai.distributioncenter.core.message.listener;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.DistributionService;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.domain.dto.OperationDTO;
import com.mockuai.distributioncenter.core.message.msg.SellerCountUpdateMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.Charset;

/**
 * Created by duke on 16/5/20.
 */
public class SellerCountUpdateListener implements MessageListener {
    private static final Logger log = LoggerFactory.getLogger(SellerCountUpdateListener.class);

    @Autowired
    private DistributionService distributionService;

    @Override
    public Action consume(Message msg, ConsumeContext context) {
        log.info("receive a message, msgId: {}, key: {}", msg.getMsgID(), msg.getKey());

        // parse message body
        JSONObject jsonObject = JSONObject.parseObject(new String(msg.getBody(), Charset.forName("UTF-8")));
        String appKey = jsonObject.getString("appKey");
        Integer operator = jsonObject.getInteger("operator");
        Long directCount = jsonObject.getLong("directCount");
        Long groupCount = jsonObject.getLong("groupCount");
        Long userId = jsonObject.getLong("userId");
        String msgIdentify = jsonObject.getString("msgIdentify");

        OperationDTO operationDTO = new OperationDTO();
        operationDTO.setGroupCount(groupCount);
        operationDTO.setDirectCount(directCount);
        operationDTO.setUserId(userId);
        operationDTO.setOperator(operator);
        operationDTO.setMsgIdentify(msgIdentify);

        SellerCountUpdateMsg sellerCountUpdateMsg = new SellerCountUpdateMsg();
        sellerCountUpdateMsg.setMsg(msg);
        sellerCountUpdateMsg.setOperationDTO(operationDTO);

        BaseRequest request = new BaseRequest();
        request.setParam("appKey", appKey);
        request.setParam("sellerCountUpdateMsg", sellerCountUpdateMsg);
        request.setCommand(ActionEnum.UPDATE_SELLER_COUNT.getActionName());

        Response<Boolean> response = distributionService.execute(request);

        if (response.isSuccess() && response.getModule()) {
            // 消息处理成功
            log.info("consume message success, msgId: {}", msg.getMsgID());
            return Action.CommitMessage;
        } else {
            // 消息处理失败
            log.error("consume message failed, msgId: {}, err: {}, re consume later",
                    msg.getMsgID(), response.getMessage());
            return Action.CommitMessage;
        }
    }
}
