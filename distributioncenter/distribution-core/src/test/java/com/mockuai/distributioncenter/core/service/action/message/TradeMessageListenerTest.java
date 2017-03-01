package com.mockuai.distributioncenter.core.service.action.message;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.mockuai.distributioncenter.core.message.listener.TradeMessageListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.nio.charset.Charset;

/**
 * Created by duke on 16/6/2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TradeMessageListenerTest {
    @Autowired
    private TradeMessageListener tradeMessageListener;

    @Test
    public void test() throws Exception {
        MessageExt msg = new MessageExt();
        msg.setTags("paySuccessNotify");
        msg.setTopic("trade");
        msg.setMsgId("1234567890");
        JSONObject json = new JSONObject();
        json.put("id", "55002");
        json.put("userId", "38699");
        json.put("bizCode", "hanshu");
        msg.setBody(json.toJSONString().getBytes(Charset.forName("UTF-8")));
        // paySuccessListener.consumeMessage(Collections.singletonList(msg), null);
    }
}
