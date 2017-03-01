package com.mockuai.rainbowcenter.core.message;
import com.mockuai.tradecenter.common.domain.OrderDTO;

public interface RocketMQManager {

	/**
	 * 用来发送消息
	 * @param topic
	 * @param tag
	 * @param obj
	 */
	public void send(String topic, String tag, String key, Object obj);
	
	public void sendOrderMessage(String topic, String tag, OrderDTO orderDTO);
	
}
