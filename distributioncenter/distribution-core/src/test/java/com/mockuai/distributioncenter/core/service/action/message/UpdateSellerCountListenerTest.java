package com.mockuai.distributioncenter.core.service.action.message;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.mockuai.distributioncenter.common.constant.Operator;
import com.mockuai.distributioncenter.core.message.listener.SellerCountUpdateListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.Collections;

/**
 * Created by duke on 16/6/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UpdateSellerCountListenerTest {
    @Resource
    private SellerCountUpdateListener sellerCountUpdateListener;

    @Test
    public void test() throws Exception {
        MessageExt msg = new MessageExt();
        msg.setTags("update");
        msg.setTopic("relationship");
        msg.setMsgId("1234567890");
        JSONObject json = new JSONObject();
        json.put("appKey", "27c7bc87733c6d253458fa8908001eef");
        json.put("operator", Operator.ADD.getOp());
        json.put("directCount", 1);
        json.put("groupCount", 1);
        json.put("userId", 287487L);
        json.put("msgIdentify", 105L);
        msg.setBody(json.toJSONString().getBytes(Charset.forName("UTF-8")));
        // sellerCountUpdateListener.consumeMessage(Collections.singletonList(msg), null);
    }
}
