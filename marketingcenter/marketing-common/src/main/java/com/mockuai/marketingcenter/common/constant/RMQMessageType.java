package com.mockuai.marketingcenter.common.constant;

/**
 * Created by edgar.zr on 12/16/15.
 */
public enum RMQMessageType {
	/**
	 * 订单支付成功
	 */
	TRADE_PAY_SUCCESS_NOTIFY("trade", "paySuccessNotify"),
	/**
	 * 订单取消
	 */
	TRADE_ORDER_CANCEL("trade", "orderCancel"),
	/**
	 * 订单最终结束
	 */
	TRADE_ORDER_FINISHED_NOTIFY("trade", "orderFinishedNotify"),
	/**
	 * 订单未支付
	 */
	TRADE_ORDER_UNPAID("trade", "orderUnpaid"),
	/**
	 * 退单成功
	 */
	REFUND_SUCCESS("trade","refundSuccess");
	private String topic;
	private String tag;

	RMQMessageType(String topic, String tag) {
		this.topic = topic;
		this.tag = tag;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getTag() {
		return tag;
	}

	public String combine() {
		return getTopic() + "*" + getTag();
	}
}