package com.mockuai.marketingcenter.core.message.producer;

import com.mockuai.marketingcenter.core.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 12/16/15.
 */
public class BaseProducerTest extends BaseTest {

    @Autowired
    private BaseProducer producer;

    @Test
    public void test() {

//        OrderDTO orderDTO = new OrderDTO();
//        orderDTO.setBizCode("mockuai_demo");
//        orderDTO.setSellerId(38699L);
//        try {
//            producer.send("trade", "paySuccessNotify", null, JSONObject.toJSON(orderDTO));
//        } catch (MarketingException e) {
//            System.err.println(e);
//        }
//        try {
//            TimeUnit.SECONDS.sleep(100000L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}