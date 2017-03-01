package com.mockuai.toolscenter.core.message.listener;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.mockuai.appcenter.common.constant.AppTypeEnum;
import com.mockuai.toolscenter.common.api.BaseRequest;
import com.mockuai.toolscenter.common.api.Response;
import com.mockuai.toolscenter.common.api.ToolsService;
import com.mockuai.toolscenter.common.constant.ActionEnum;
import com.mockuai.toolscenter.common.domain.UserBehaviourDTO;
import com.mockuai.toolscenter.core.manager.AppManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.nio.charset.Charset;

/**
 * Created by 冠生 on 15/11/18.
 */
public class CheckBeHaviourListener implements MessageListener {
    private static final Logger log = LoggerFactory.getLogger(CheckBeHaviourListener.class);

    @Autowired
    private ToolsService toolsService;

    @Resource
    private AppManager appManager;

    @Override
    public Action consume(Message msg, ConsumeContext consumeContext) {

        log.info("接受用户行为消息 msgId: {}, key: {},tag:{}", msg.getMsgID(), msg.getKey(),msg.getTag());
        String msgStr = new String(msg.getBody(), Charset.forName("UTF-8"));
        try {
            //log.info("receive  userBehaviourDTO  msg:{}",msgStr);
            UserBehaviourDTO userBehaviourDTO =  JSONObject.parseObject(msgStr, UserBehaviourDTO.class);
            BaseRequest request = new BaseRequest();
            request.setParam("userBehaviourDTO",userBehaviourDTO);
            request.setParam("appKey", appManager.getAppInfoByType("hanshu", AppTypeEnum.APP_WAP).getAppKey());
            request.setCommand(ActionEnum.ADD_USER_BEHAVIOUR.getActionName());
            Response response =  toolsService.execute(request);
            if(!response.isSuccess()){
                log.error("consume  save userBehaviourDTO  errMsg: {}", response.getMessage());
                return Action.CommitMessage;
            }
        }catch (Exception e){
            log.error("consume  save userBehaviourDTO errMsg: {}", e);
            return Action.CommitMessage;
        }

        return Action.CommitMessage;

    }
}
