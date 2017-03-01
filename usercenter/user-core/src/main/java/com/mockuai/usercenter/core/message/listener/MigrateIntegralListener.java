//package com.mockuai.usercenter.core.message.listener;
//
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
//import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
//import com.alibaba.rocketmq.common.message.MessageExt;
//import com.mockuai.marketingcenter.common.constant.SourceType;
//import com.mockuai.marketingcenter.common.constant.WealthType;
//import com.mockuai.usercenter.common.constant.BizType;
//import com.mockuai.usercenter.common.dto.MessageRecordDTO;
//import com.mockuai.usercenter.core.exception.UserException;
//import com.mockuai.usercenter.core.employee.AppManager;
//import com.mockuai.usercenter.core.employee.MarketingManager;
//import com.mockuai.usercenter.core.employee.MessageRecordManager;
//import com.mockuai.usercenter.core.employee.MigrateIntegralManager;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.annotation.Resource;
//import java.util.List;
//
///**
// * Created by duke on 15/11/18.
// */
//public class MigrateIntegralListener implements ConcurrentMessageListener {
//    private static final Logger log = LoggerFactory.getLogger(MigrateIntegralListener.class);
//
//    @Resource
//    private MigrateIntegralManager migrateIntegralManager;
//
//    @Resource
//    private MarketingManager marketingManager;
//
//    @Resource
//    private AppManager appManager;
//
//    @Resource
//    private MessageRecordManager messageRecordManager;
//
//    @Override
//    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
//        log.info("Enter Listener [{}]", getClass().getName());
//
//        if (!list.isEmpty()) {
//            MessageExt msg = list.get(0);
//            if (msg.getTopic().equals("user") && msg.getTags().equals("createUser")) {
//                log.info("MsgId: [{}] [{}:{}] receive a message: {}", msg.getMsgId(), msg.getTopic(), msg.getTags(), new String(msg.getBody()));
//
//                JSONObject jsonObject = JSONObject.parseObject(new String(msg.getBody()));
//                Long userId = jsonObject.getLong("id");
//                String bizCode = jsonObject.getString("biz_code");
//                String mobile = jsonObject.getString("mobile");
//                // 只处理话机的消息
//                if (!bizCode.equals("huajishijie")) {
//                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//                }
//
//                try {
//                    // 在消费消息前，检查该消息是否已经被消费过了
//                    MessageRecordDTO messageRecordDTO =
//                            messageRecordManager.getRecordByIdentifyAndBizType(String.valueOf(userId),
//                                    BizType.ROCKET_MQ_MESSAGE_MIGRATE_USER.getValue());
//                    if (messageRecordDTO != null) {
//                        // 如果是已经被消费过的消息，则不进行消费
//                        log.warn("reduplicate message msgId: {}", msg.getMsgId());
//                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//                    }
//
//                    log.info("migrate integral, userId: {}, bizCode: {}, mobile: {}", userId, bizCode, mobile);
//                    String appKey = appManager.getAppKeyByBizCode(bizCode);
//                    if (appKey == null) {
//                        log.error("appKey is null, bizCode: {}", bizCode);
//                    }
//                    Long integral = migrateIntegralManager.getIntergalByMobile(mobile);
//                    log.info("fetch integral: {}", integral);
//                    marketingManager.grantVirtualWealth(null,
//                            WealthType.CREDIT.getValue(),
//                            SourceType.OTHER.getValue(),
//                            integral,
//                            userId, appKey);
//
//                    // 记录已经被消费的消息
//                    messageRecordDTO = new MessageRecordDTO();
//                    messageRecordDTO.setMsgId(msg.getMsgId());
//                    messageRecordDTO.setIdentify(String.valueOf(userId));
//                    messageRecordDTO.setBizType(BizType.ROCKET_MQ_MESSAGE_MIGRATE_USER.getValue());
//                    messageRecordManager.addRecord(messageRecordDTO);
//
//                    log.info("Exit Listener [{}]", getClass().getName());
//                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//                } catch (UserException e) {
//                    log.error("migrate integral error, errCode: {}, errMsg: {}", e.getResponseCode(), e.getMessage());
//                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
//                }
//            }
//        }
//        log.info("Exit Listener [{}]", getClass().getName());
//        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
//    }
//}
