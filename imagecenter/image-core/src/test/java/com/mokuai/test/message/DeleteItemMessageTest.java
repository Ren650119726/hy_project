package com.mokuai.test.message; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.alibaba.fastjson.JSONObject;
import com.mockuai.imagecenter.core.message.msg.MessageProducer;
import com.mokuai.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by hy on 2016/6/23.
 */
public class DeleteItemMessageTest extends BaseTest {
     @Autowired
    private MessageProducer messageProducer;
    private String topic = "dev-haiyn_item_msg";

    @Test
    public void testDelete(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("itemId","124455");
        messageProducer.send(topic,"item-update","deleteQrcode",jsonObject);
    }
}
