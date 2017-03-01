package com.mockuai.rainbowcenter.core.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.mockuai.tradecenter.common.domain.OrderDTO;

public class RocketMQManagerImpl extends BaseService implements RocketMQManager,InitializingBean {

	private static final Logger log = LoggerFactory.getLogger(RocketMQManagerImpl.class);

	private String namesrvAddr;

	private String producerGroupName;

	DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName");

	public void setNamesrvAddr(String namesrvAddr) {
		this.namesrvAddr = namesrvAddr;
	}

	public void setProducerGroupName(String producerGroupName) {
		this.producerGroupName = producerGroupName;
	}

	public void setNamesrvAddr() {
		producer.setNamesrvAddr(this.namesrvAddr);
		producer.setInstanceName(this.producerGroupName);
		producer.setProducerGroup(this.producerGroupName);
	}

	public void init() {
		setNamesrvAddr();
		try {
			producer.start();
		} catch (MQClientException e) {
			log.error("RocketMQManagerImpl init error", e);
		}
	}

	@Override
	public void send(String topic, String tag, String key, Object obj){

		printIntoService(log,"send message to mq",obj,"");
		
		try {
			Message msg = new Message(topic, // topic
					tag, // tag
					key, // key
					JSONObject.toJSONString(obj).getBytes());// body
			SendResult sendResult = producer.send(msg);
			System.out.println(sendResult);
		} catch (Exception e) {
			log.error("mq send message error", e);
		}

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
	}

	@Override
	public void sendOrderMessage(String topic, String tag, OrderDTO orderDTO) {
			try{
				//
				Message msg = new Message(topic, // topic
						tag, // tag
						orderDTO.getId()+"", // key
//						JsonUtil.toJson(orderDTO).getBytes());// body
						JSONObject.toJSONString(orderDTO).getBytes());// body
				SendResult sendResult = producer.send(msg);
				
				System.out.println(sendResult); 
				
			}catch (Exception e) {
				log.error("mq send order message error", e);
			}
	}
	

}
