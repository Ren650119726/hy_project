package com.mockuai.usercenter.core.manager;

import com.mockuai.usercenter.common.constant.BizType;
import com.mockuai.usercenter.common.dto.MessageRecordDTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by duke on 15/11/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MessageRecordManagerTest {
    @Resource
    private MessageRecordManager messageRecordManager;

    @Test
    public void addReord() throws UserException {
        MessageRecordDTO messageRecordDTO = new MessageRecordDTO();
        messageRecordDTO.setBizType(BizType.ROCKET_MQ_MESSAGE_MIGRATE_USER.getValue());
        messageRecordDTO.setIdentify("1");
        messageRecordDTO.setMsgId("1");
        messageRecordManager.addRecord(messageRecordDTO);
    }

    @Test
    public void getByIdentify() throws UserException {
        MessageRecordDTO messageRecordDTO =
                messageRecordManager.getRecordByIdentifyAndBizType("1",
                        BizType.ROCKET_MQ_MESSAGE_MIGRATE_USER.getValue());
        System.out.println(JsonUtil.toJson(messageRecordDTO));
    }
}
