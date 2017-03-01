package com.mockuai.rainbowcenter.core.message.listener;

import com.alibaba.fastjson.JSON;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.mockuai.rainbowcenter.common.api.BaseRequest;
import com.mockuai.rainbowcenter.common.api.RainbowService;
import com.mockuai.rainbowcenter.common.constant.*;
import com.mockuai.rainbowcenter.common.api.Response;
import com.mockuai.rainbowcenter.common.dto.MessageRecordDTO;
import com.mockuai.rainbowcenter.common.dto.SysConfigDTO;
import com.mockuai.rainbowcenter.common.qto.SysConfigQTO;
import com.mockuai.rainbowcenter.core.exception.RainbowException;
import com.mockuai.rainbowcenter.core.manager.AppManager;
import com.mockuai.rainbowcenter.core.manager.HsOrderManager;
import com.mockuai.rainbowcenter.core.manager.MessageRecordManager;
import com.mockuai.rainbowcenter.core.manager.SysConfigManager;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONObject;
import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by lizg on 2016/6/1.
 * 通过MQ消息中间件监听支付成功的订单推送给管易ERP
 * 退款退货
 */
public class TradeMessageListener implements MessageListener {
    private static final Logger logger = LoggerFactory.getLogger(TradeMessageListener.class);

    @Resource
    private SysConfigManager sysConfigManager;

    @Resource
    private MessageRecordManager messageRecordManager;

    @Resource
    private AppManager appManager;

    @Resource
    private RainbowService rainbowService;

    @Resource
    private HsOrderManager hsOrderManager;


    @Override
    public Action consume(Message msg, ConsumeContext consumeContext) {
        logger.info("[{}] msg tag:{}",msg.getTag());
        if (msg.getTag().equals("paySuccessNotify")) {
            String body = new String(msg.getBody());
            JSONObject jsonObject = JSONObject.parseObject(body);
            logger.info("start to body:{}",jsonObject);
            String bizCode = jsonObject.getString("bizCode"); //企业标示码
            logger.info("biz code :{}"+bizCode);
            Long orderId = jsonObject.getLong("id");          //订单id
            Long userId = jsonObject.getLong("userId");
            Long sellerId = jsonObject.getLong("sellerId"); //卖家id
            Long payDate = jsonObject.getLong("payTime");
            logger.info("[{}] pay time :{}",payDate);
            //将时间戳转化为Date
            SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date payTime = null;
            if (null != payDate) {
                String date = simpleDateFormat.format(payDate);
                try {
                      payTime = simpleDateFormat.parse(date);

                } catch (ParseException e) {
                    logger.error("errPayTime: {}", e.getMessage());
                    e.printStackTrace();
                }
            }
            if (orderId == null) {
                logger.error("illegal in coming argument: orderId is null, re consume later");
                return Action.CommitMessage;
            }
            if (bizCode == null) {
                logger.error("illegal in coming argument: bizCode is null, re consume later");
                return Action.CommitMessage;
            }
            if (userId == null) {
                logger.error("userId is blank, not a legal userId no, bizCode: {}, orderId: {}, re consume later", bizCode, orderId);
                return Action.CommitMessage;
            }
            Boolean isContain = false; //判断平台的配置是否含有MQ消息传过来的bizCode

            //过滤bizCode，为了区分不同的商户发过来的MQ消息
            SysConfigQTO sysConfigQTO = new SysConfigQTO();
            sysConfigQTO.setType(SysFieldTypeEnum.GYERP.getValue());
            sysConfigQTO.setFieldName(SysFieldNameEnum.SHOP_ID.getValue());

            try {
                List<SysConfigDTO> sysConfigDTOList = this.sysConfigManager.queryConfig(sysConfigQTO);
                for (SysConfigDTO sysConfigDTO : sysConfigDTOList) {
                    if (sysConfigDTO.getBizCode().equals(bizCode)) {
                        isContain = true;
                        break;
                    }

                }

            } catch (RainbowException e) {
                return Action.CommitMessage;
            }
            if (!isContain) {
                return Action.CommitMessage;
            }
            logger.info("receive a message：{}", msg.getMsgID(), msg.getTopic(), msg.getTag(), body);

            Long id = 0L;
            try {

                //检测消息是否被消费过了
                MessageRecordDTO messageRecordDTO = this.messageRecordManager.getRecordByIdentifyAndBizType(String.valueOf(orderId), BizType.PAY_SUCCESS.getValue());
                if (null != messageRecordDTO) {
                    return Action.CommitMessage;
                }

                //检测支付成功MO消息没有被消费过也就是没有被处理过的记录
                MessageRecordDTO unConsumeMessage = this.messageRecordManager.getRecordByIdentifyAndBizType(String.valueOf(orderId), BizType.PAY_SUCCESS_MESSAGE_RECORD_UNCONSUME.getValue());
                if (null == unConsumeMessage) {

                    //检测消息是否记录过
                    MessageRecordDTO messageRecord = this.messageRecordManager.getRecordByIdentifyAndBizType(String.valueOf(orderId), BizType.PAY_SUCCESS_MESSAGE_RECORD.getValue());

                    //如果已经创建了记录，则return
                    if (null != messageRecord) {
                        return Action.CommitMessage;
                    }

                    //反之创建消息记录
                    messageRecord = new MessageRecordDTO();
                    messageRecord.setBizType(BizType.PAY_SUCCESS_MESSAGE_RECORD.getValue());
                    messageRecord.setMsgId(msg.getMsgID());
                    messageRecord.setIdentify(String.valueOf(orderId));
                    id = this.messageRecordManager.addRecord(messageRecord);
                } else {
                    id = unConsumeMessage.getId();
                }

                logger.info("start consume message id:{}",msg.getMsgID());

                //通过bizCode获取appKey
                String appKey = this.appManager.getAppKeyByBizCode(bizCode);
                Response<Boolean> response;
                BaseRequest request = new BaseRequest();

                //推送订单到GYERP（管易ERP）
                OrderDTO orderDTO = new OrderDTO();
                orderDTO.setBizCode(bizCode);
                orderDTO.setSellerId(sellerId);
                orderDTO.setId(orderId);
                orderDTO.setUserId(userId);
                orderDTO.setPayTime(payTime);
                request.setParam("orderDTO", orderDTO);
                request.setParam("appKey", appKey);
                request.setCommand(ActionEnum.DELIVERY_GYERP_ORDER.getActionName());

                response = this.rainbowService.execute(request);

                //判断消息推送是否成功
                if (response.isSuccess() && response.getModule()) {

                    //消息处理成功
                    logger.info("consume message success,msgId：{}",msg.getMsgID());

                    //记录消息消费
                    messageRecordDTO = new MessageRecordDTO();
                    messageRecordDTO.setMsgId(msg.getMsgID());
                    messageRecordDTO.setBizType(BizType.PAY_SUCCESS.getValue());
                    messageRecordDTO.setIdentify(String.valueOf(orderId));
                    this.messageRecordManager.addRecord(messageRecordDTO);
                    return Action.CommitMessage;

                } else { //消息处理失败
                    logger.error("consume message failed,msgId：{}，err：{}，re consume later",msg.getMsgID(), response.getMessage());
                    if (null != id || id != 0) {
                        this.messageRecordManager.updateRecord(id,BizType.PAY_SUCCESS_MESSAGE_RECORD_UNCONSUME.getValue());
                    }
                    return Action.ReconsumeLater;
                }

            } catch (RainbowException e) {
                if (null != id || id != 0) {
                    this.messageRecordManager.updateRecord(id, BizType.PAY_SUCCESS_MESSAGE_RECORD_UNCONSUME.getValue());
                }
                logger.error("consume  message error, msgId: {}, errMsg: {}",msg.getMsgID(), e.getMessage());
            }

        }else if (msg.getTag().equals("refundSuccess")) {
            String body = new String(msg.getBody());
            JSONObject jsonObject = JSONObject.parseObject(body);
            logger.info("[{refund}] body:{}" + jsonObject);
            String bizCode = jsonObject.getString("bizCode");
            Long sellerId = jsonObject.getLong("sellerId");
            Long userId = jsonObject.getLong("userId");
            logger.info("[{}] userId:{}", userId);
            Integer deliveryMark = jsonObject.getInteger("deliveryMark");  //是否有物流消息
            Long orderId = jsonObject.getLong("orderId");
            Long itemSkuId = jsonObject.getLong("itemSkuId");
            Long itemId = jsonObject.getLong("itemId");
            Integer itemCount = jsonObject.getInteger("number");
            Integer refundReasonId = jsonObject.getInteger("refundReasonId"); //退款原因id
            Long refundAmount = jsonObject.getLong("refundAmount");

            if (bizCode == null) {
                logger.error("bizCode is null");
                return Action.CommitMessage;
            }
            if (sellerId == null) {
                logger.error("sellerId is null");
                return Action.CommitMessage;
            }
            if (userId == null) {
                logger.error("userId is null");
                return Action.CommitMessage;
            }
            if (orderId == null) {
                logger.error("orderId is null");
                return Action.CommitMessage;
            }
            Boolean bContain = false;

            //过滤bizcode
            SysConfigQTO sysConfigQTO = new SysConfigQTO();
            sysConfigQTO.setType(SysFieldTypeEnum.GYERP.getValue());
            sysConfigQTO.setFieldName(SysFieldNameEnum.SHOP_ID.getValue());

            try {
                List<SysConfigDTO> sysConfigDTOList = this.sysConfigManager.queryConfig(sysConfigQTO);
                for (SysConfigDTO sysConfigDTO : sysConfigDTOList) {
                    if (sysConfigDTO.getBizCode().equals(bizCode)) {
                        bContain = true;
                        break;
                    }

                }
            } catch (RainbowException e) {
                return Action.CommitMessage;
            }
            if (!bContain) {
                return Action.CommitMessage;
            }
            logger.info("MsgId: [{}] [{}:{}] receive a message: {}", msg.getMsgID(), msg.getTopic(), msg.getTag(), body);

            try {
                logger.info("start consume message id: {} ", msg.getMsgID());
                Integer bizType;
                MessageRecordDTO messageRecordDTO;
                String appKey = this.appManager.getAppKeyByBizCode(bizCode);
                OrderDTO storedOrder = this.hsOrderManager.getOrder(orderId, userId, appKey);

                if (deliveryMark.equals(1)) {   //有物流，说明是退货退款
                    logger.info("start to enter REFUND_WITH_DELIVERY ");
                    bizType = BizType.REFUND_WITH_DELIVERY.getValue();

                    //检测消息是否被消费过了
                    messageRecordDTO = messageRecordManager.getRecordByIdentifyAndBizType(String.valueOf(orderId), bizType);
                    if (null != messageRecordDTO) {
                        logger.info("this message has been consumed, orderId = {}, bizType = {}", orderId, bizType);
                        return Action.CommitMessage;
                    }
                    RefundOrderItemDTO refundOrderItemDTO = new RefundOrderItemDTO();
                    refundOrderItemDTO.setBizCode(bizCode);
                    refundOrderItemDTO.setSellerId(sellerId);
                    refundOrderItemDTO.setItemSkuId(itemSkuId);
                    refundOrderItemDTO.setUserId(userId);
                    refundOrderItemDTO.setOrderId(orderId);
                    refundOrderItemDTO.setItemId(itemId);
                    refundOrderItemDTO.setOrderSn(storedOrder.getOrderSn());
                    refundOrderItemDTO.setNumber(itemCount);
                    refundOrderItemDTO.setRefundAmount(refundAmount);
                    refundOrderItemDTO.setRefundReasonId(refundReasonId);

                    BaseRequest request = new BaseRequest();
                    request.setParam("refundOrderItemDTO", refundOrderItemDTO);
                    request.setParam("appKey", appKey);
                    request.setCommand(ActionEnum.HS_RETURN_ITEM.getActionName());

                    Response response = this.rainbowService.execute(request);
                    logger.info("response: {}" + JSON.toJSONString(response));
                    if (!response.isSuccess()) {
                        throw new RainbowException(ResponseCode.SYS_E_SERVICE_EXCEPTION, response.getMessage());
                    }
                } else {   //仅退款
                    logger.info("start to enter REFUND_WITHOUT_DELIVERY");
                    bizType = BizType.REFUND_WITHOUT_DELIVERY.getValue();

                    //检测消息是否被消费过了
                    messageRecordDTO = this.messageRecordManager.getRecordByIdentifyAndBizType(String.valueOf(orderId), bizType);
                    if (messageRecordDTO != null) {
                        return Action.CommitMessage;
                    }
                    OrderDTO orderDTO = new OrderDTO();
                    orderDTO.setBizCode(bizCode);
                    orderDTO.setSellerId(sellerId);
                    orderDTO.setUserId(userId);
                    orderDTO.setId(orderId);
                    orderDTO.setPayAmount(refundAmount);
                    orderDTO.setOrderSn(storedOrder.getOrderSn());

                    BaseRequest request = new BaseRequest();
                    request.setParam("orderDTO", orderDTO);
                    request.setParam("itemId", itemId);
                    request.setParam("appKey", appKey);
                    request.setCommand(ActionEnum.HS_CANCEL_ORDER.getActionName());

                    Response response = this.rainbowService.execute(request);
                    logger.info("response: {}", JSON.toJSONString(response));

                    if (!response.isSuccess()) {
                        throw new RainbowException(ResponseCode.SYS_E_SERVICE_EXCEPTION, response.getMessage());
                    }

                }

                logger.info("recorde message: {}", msg.getMsgID());

                //记录消息消费
                messageRecordDTO = new MessageRecordDTO();
                messageRecordDTO.setMsgId(msg.getMsgID());
                messageRecordDTO.setBizType(bizType);
                messageRecordDTO.setIdentify(String.valueOf(orderId + itemId));
                this.messageRecordManager.addRecord(messageRecordDTO);
                return Action.CommitMessage;
            } catch (RainbowException e) {
                logger.error("consume trade order message error, msgId: {}, errMsg: {}", msg.getMsgID(), e.getMessage());
            }
        }

        return Action.CommitMessage;
    }
}
