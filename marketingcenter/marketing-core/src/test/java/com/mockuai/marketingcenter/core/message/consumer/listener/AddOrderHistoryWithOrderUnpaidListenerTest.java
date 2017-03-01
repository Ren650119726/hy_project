package com.mockuai.marketingcenter.core.message.consumer.listener;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.marketingcenter.core.BaseTest;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.message.consumer.Listener;
import com.mockuai.marketingcenter.core.message.consumer.ListenerHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by edgar.zr on 7/26/2016.
 */
public class AddOrderHistoryWithOrderUnpaidListenerTest extends BaseTest {
	@Autowired
	private ListenerHolder listenerHolder;

	@Test
	public void test() {
		List<Listener> listeners = listenerHolder.getListener(getTopicTag("dev-haiyn_trade_msg", "orderUnpaid"));
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("userId", 593L);
			jsonObject.put("id", 1L);
			String appKey = "1b0044c3653b89673bc5beff190b68a1";
			listeners.get(0).execute(jsonObject, appKey);
		} catch (MarketingException e) {
			e.printStackTrace();
		}
	}

	private String getTopicTag(String topic, String tag) {
		return topic + "*" + tag;
	}
}