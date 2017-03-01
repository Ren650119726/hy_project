package com.mockuai.rainbowcenter.core.manager;

import com.mockuai.rainbowcenter.common.constant.BizType;
import com.mockuai.rainbowcenter.common.dto.MessageRecordDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by yeliming on 16/4/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/applicationContext.xml"})
public class MessageRecordManagerTest {

    @Resource
    private MessageRecordManager messageRecordManager;

    @Test
    public void addRecord() throws Exception {
        MessageRecordDTO messageRecordDTO = new MessageRecordDTO();
        messageRecordDTO.setBizType(1);
        messageRecordDTO.setIdentify("123");
        messageRecordDTO.setMsgId("ba");
        Long id = this.messageRecordManager.addRecord(messageRecordDTO);
        Assert.assertNotNull(id);
    }

    @Test
    public void getRecordByIdentifyAndBizCode() throws Exception {
        String identify = "123";
        Integer bizType = 1;
        MessageRecordDTO messageRecordDTO = this.messageRecordManager.getRecordByIdentifyAndBizType(identify, bizType);
        Assert.assertNotNull(messageRecordDTO);
    }

    @Test
    public void updateMessageBizTypeTest() throws Exception{
        Long id = 2L;
        Integer n = this.messageRecordManager.updateRecord(id, BizType.PAY_SUCCESS_MESSAGE_RECORD_UNCONSUME.getValue());
        Assert.assertNotNull(n);
    }

}