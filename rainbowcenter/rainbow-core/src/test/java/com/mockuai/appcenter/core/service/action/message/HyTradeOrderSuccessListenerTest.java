package com.mockuai.appcenter.core.service.action.message;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Message;
import com.mockuai.rainbowcenter.core.message.listener.TradeMessageListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.nio.charset.Charset;

/**
 * Created by lizg on 2016/6/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class HyTradeOrderSuccessListenerTest {

    @Autowired
    private TradeMessageListener hyTradeOrderSuccessListener;

    @Test
    public void test() throws Exception {
        Message msg = new Message();
        msg.setTag("paySuccessNotify");
        msg.setTopic("trade");
        msg.setMsgID("1234567890");
        JSONObject json = new JSONObject();
        json.put("id", "55002");
        json.put("userId", "38699");
        json.put("bizCode", "hanshu");
        msg.setBody(json.toJSONString().getBytes(Charset.forName("UTF-8")));
       hyTradeOrderSuccessListener.consume(msg, null);
    }
}
