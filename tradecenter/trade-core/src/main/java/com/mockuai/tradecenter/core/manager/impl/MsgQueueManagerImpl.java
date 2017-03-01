package com.mockuai.tradecenter.core.manager.impl;

import com.aliyun.openservices.ons.api.*;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.util.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.BaseService;
import com.mockuai.tradecenter.core.manager.MsgQueueManager;

import javax.annotation.Resource;
import java.util.List;
import java.util.Properties;

public class MsgQueueManagerImpl extends BaseService implements MsgQueueManager {

	private static final Logger log = LoggerFactory.getLogger(MsgQueueManagerImpl.class);

	private String topic = "haiyn_trade_msg";
	private String producerId = "PID-haiyn_tradecenter";
	private String accessKey;
	private String secretKey;

	private Producer producer;

	@Resource
	private OrderManager orderManager;

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public void setProducerId(String producerId) {
		this.producerId = producerId;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public void init() {
		Properties properties = new Properties();
		properties.put(PropertyKeyConst.ProducerId, this.producerId);
		properties.put(PropertyKeyConst.AccessKey, this.accessKey);
		properties.put(PropertyKeyConst.SecretKey, this.secretKey);
		this.producer = ONSFactory.createProducer(properties);
		//在发送消息前，必须调用start方法来启动Producer，只需调用一次即可。
		producer.start();
	}

	@Override
	public void sendRefundSuccessMsg(OrderItemDO orderItemDO) throws TradeException{
		String tag = "refundSuccess";
		String key = "orderItemId_" + orderItemDO.getId();
		send(topic, tag, key, orderItemDO);
	}

	@Override
	public void sendApplyRefundMsg(RefundOrderItemDTO refundOrderItemDTO) throws TradeException{
		String tag = "applyRefundNotify";
		String key = "orderItemId_" + refundOrderItemDTO.getOrderItemId();
		send(topic, tag, key, refundOrderItemDTO);
	}

	@Override
	public void sendPaySuccessMsg(OrderDO orderDO) throws TradeException{
		//如果是父订单，则直接针对其下的子订单发送支付成功消息
		if (orderDO.getParentMark() != null && orderDO.getParentMark().intValue() == 1) {
			OrderQTO orderQTO = new OrderQTO();
			orderQTO.setOriginalOrder(orderDO.getId());
			orderQTO.setUserId(orderDO.getUserId());
			List<OrderDO> subOrderList = orderManager.queryUserOrders(orderQTO);
			if (subOrderList == null || subOrderList.isEmpty()) {
				log.error("subOrder list of the mainOrder are empty, orderId:{}, userId:{}",
						orderDO.getId(), orderDO.getUserId());
				throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION,
						"subOrder list of the mainOrder are empty, orderId=" + orderDO.getId());
			}
			for (OrderDO subOrder : subOrderList) {
				sendOrderMessage("paySuccessNotify", ModelUtil.convert2OrderDTO(subOrder));
			}
		}else{
			sendOrderMessage("paySuccessNotify", ModelUtil.convert2OrderDTO(orderDO));
		}
	}

	@Override
	public void sendPaySuccessMsg(List<OrderDO> subOrderList) throws TradeException {
		if (subOrderList == null || subOrderList.isEmpty()) {
			return;
		}

		for (OrderDO subOrder : subOrderList) {
			sendOrderMessage("paySuccessNotify", ModelUtil.convert2OrderDTO(subOrder));
		}
	}

	@Override
	public void sendOrderMessage(String tag, OrderDTO orderDTO) throws TradeException {
		//FIXME 用户id为1238579的用户，是压测账号，直接不发送消息，避免压测给其他系统带来压力
		if (orderDTO.getUserId() != null && orderDTO.getUserId().longValue() == 1238579L) {
			return;
		}
		String key = "orderId_" + orderDTO.getId();
		send(topic, tag, key, orderDTO);
	}

	private void send(String topic, String tag, String key, Object obj) throws TradeException {
		try {
			Message msg = new Message(
					//Message Topic
					topic,
					//Message Tag,
					//可理解为Gmail中的标签，对消息进行再归类，方便Consumer指定过滤条件在ONS服务器过滤
					tag,
					//Message Body
					//任何二进制形式的数据，ONS不做任何干预，需要Producer与Consumer协商好一致的序列化和反序列化方式
					JSONObject.toJSONString(obj).getBytes("UTF-8")
			);
			// 设置代表消息的业务关键属性，请尽可能全局唯一。
			// 以方便您在无法正常收到消息情况下，可通过ONS Console查询消息并补发。
			// 注意：不设置也不会影响消息正常收发
			msg.setKey(key);

			//发送消息，只要不抛异常就是成功
			SendResult sendResult = producer.send(msg);

			log.info("[MESSAGE_TRACE] send mq msg, topic:{}, tag:{}, key:{}, sendResult:{}",
					topic, tag, key, sendResult);
		} catch (Exception e) {
			log.error("[MESSAGE_TRACE] error to send mq msg, topic:{}, tag:{}, key:{}",
					topic, tag, key, e);

			throw new TradeException("mq send message error");
		}

	}

	public static void main(String[] args) {
		Properties properties = new Properties();
		properties.put(PropertyKeyConst.ProducerId, "PID-dev-haiyn_tradecenter");
		properties.put(PropertyKeyConst.AccessKey, "scl16iPO2OUD1goj");
		properties.put(PropertyKeyConst.SecretKey, "1J9wWa1ZSVzZ6pSFZ6nTGVhT8BvjG9");
		Producer producer = ONSFactory.createProducer(properties);

		//在发送消息前，必须调用start方法来启动Producer，只需调用一次即可。
		producer.start();
		Message msg = new Message(
				//Message Topic
				"dev-haiyn_trade_msg",
				//Message Tag,
				//可理解为Gmail中的标签，对消息进行再归类，方便Consumer指定过滤条件在ONS服务器过滤
				"orderUnpaid",
				//Message Body
				//任何二进制形式的数据，ONS不做任何干预，需要Producer与Consumer协商好一致的序列化和反序列化方式
				"Hello ONS".getBytes()
		);

		// 设置代表消息的业务关键属性，请尽可能全局唯一。
		// 以方便您在无法正常收到消息情况下，可通过ONS Console查询消息并补发。
		// 注意：不设置也不会影响消息正常收发
		msg.setKey("ORDERID_100");

		//发送消息，只要不抛异常就是成功
		SendResult sendResult = producer.send(msg);
		System.out.println(sendResult);

		// 在应用退出前，销毁Producer对象
		// 注意：如果不销毁也没有问题
		producer.shutdown();
	}
}
