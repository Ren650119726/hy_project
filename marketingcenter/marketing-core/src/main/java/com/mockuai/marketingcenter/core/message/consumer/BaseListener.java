package com.mockuai.marketingcenter.core.message.consumer;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.marketingcenter.common.api.MarketingService;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.MarketActivityManager;
import com.mockuai.marketingcenter.core.manager.OrderRecordManager;
import com.mockuai.marketingcenter.core.manager.PropertyManager;
import com.mockuai.marketingcenter.core.manager.TradeManager;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by edgar.zr on 12/15/15.
 */
public abstract class BaseListener implements Listener {

	@Autowired
	protected MarketingService marketingService;

	@Autowired
	protected MarketActivityManager marketActivityManager;

	@Autowired
	protected PropertyManager propertyManager;

	@Autowired
	protected TradeManager tradeManager;

	@Autowired
	protected OrderRecordManager orderRecordManager;

	@Override
	public String execute(JSONObject msg, String appKey) throws MarketingException {

		consumeMessage(msg, appKey);
		return null;
	}

	public abstract void consumeMessage(JSONObject msg, String appKey) throws MarketingException;

	public abstract Logger getLogger();
}