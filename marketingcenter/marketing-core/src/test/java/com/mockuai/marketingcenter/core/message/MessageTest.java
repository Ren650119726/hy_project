package com.mockuai.marketingcenter.core.message;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by duke on 15/9/28.
 */
public class MessageTest {
    public static void main(String[] args) throws MQClientException, InterruptedException {
        /**
         * 一个应用创建一个Producer，由应用来维护此对象，可以设置为全局对象或者单例<br>
         * 注意：ProducerGroupName需要由应用来保证唯一<br>
         * ProducerGroup这个概念发送普通的消息时，作用不大，但是发送分布式事务消息时，比较关键，
         * 因为服务器会回查这个Group下的任意一个Producer
         */
        DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName");
        producer.setNamesrvAddr("192.168.31.181:9876");
        producer.setInstanceName("BaseProducer");

        /**
         * Producer对象在使用之前必须要调用start初始化，初始化一次即可<br>
         * 注意：切记不可以在每次发送消息时，都调用start方法
         */
        producer.start();

        /**
         * 下面这段代码表明一个Producer对象可以发送多个topic，多个tag的消息。
         * 注意：send方法是同步调用，只要不抛异常就标识成功。但是发送成功也可会有多种状态，<br>
         * 例如消息写入Master成功，但是Slave不成功，这种情况消息属于成功，但是对于个别应用如果对消息可靠性要求极高，<br>
         * 需要对这种情况做处理。另外，消息可能会存在发送失败的情况，失败重试由应用来处理。
         */
        for (int i = 0; i < 1; i++) {
            try {

                OrderDTO orderDTO = new OrderDTO();
                orderDTO.setBizCode("mockuai_demo");
                orderDTO.setConsignTime(new Date());
                orderDTO.setId(2575L);
                orderDTO.setSellerId(38699L);
                orderDTO.setUserId(38760L);
                orderDTO.setOrderItems(new ArrayList<OrderItemDTO>());
                OrderItemDTO orderItemDTO = new OrderItemDTO();
                orderItemDTO.setItemId(101537L);
                orderItemDTO.setItemSkuId(1278L);
                orderItemDTO.setItemType(14);
                orderDTO.getOrderItems().add(orderItemDTO);

                Message msg = new Message("trade",// topic
                        "orderFinishedNotify",// tag
                        "OrderID001",// key
                        JSONObject.toJSONString(orderDTO).getBytes());// body
                SendResult sendResult = producer.send(msg);
                System.out.println(sendResult);
            } catch (Exception e) {
                e.printStackTrace();
            }
            TimeUnit.MILLISECONDS.sleep(1000);
        }

        /**
         * 应用退出时，要调用shutdown来清理资源，关闭网络连接，从MetaQ服务器上注销自己
         * 注意：我们建议应用在JBOSS、Tomcat等容器的退出钩子里调用shutdown方法
         */
        producer.shutdown();
    }
}
