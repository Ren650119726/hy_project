package com.mockuai.tradecenter.core.job;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.api.TradeService;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.AppManager;
import com.mockuai.tradecenter.core.manager.CacheManager;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.service.job.BaseJob;
import com.mockuai.tradecenter.core.util.TradeCoreConfig;

public class DeliveriedOrderTimeoutSignOffJob extends BaseJob {

	private static final Logger logger = Logger.getLogger(DeliveriedOrderTimeoutSignOffJob.class);
	@Resource
	OrderManager orderManager;
	@Resource
	TradeService tradeService;

	@Autowired
	private TradeCoreConfig tradeCoreConfig;

	@Autowired
	private AppManager appManager;

	@Resource
	private CacheManager cacheManager;

	public void setOrderManager(OrderManager orderManager) {
		this.orderManager = orderManager;
	}

	public void setTradeService(TradeService tradeService) {
		this.tradeService = tradeService;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		doProcessTimeoutSignOff();
		
	}

	public void doProcessTimeoutSignOff() {

		logger.info("begin process deliveriedOrderTimeoutSignOffJob");

		try {
			List<OrderDO> orders = orderManager.querySignOffOverTimeOrders(tradeCoreConfig.getTimeoutDeliveryDay());

			for (OrderDO order : orders) {
				try {
					String appkey = (String) cacheManager.get(order.getBizCode());
					if (StringUtils.isBlank(appkey)) {
						if(null==order.getAppType())
							order.setAppType(3);
						AppInfoDTO appInfo = appManager.getAppInfoByBizCode(order.getBizCode(),order.getAppType());
						if (null != appInfo) {
							appkey = appInfo.getAppKey();
							cacheManager.set(order.getBizCode(), 7200, appkey);
						} else {
							continue;
						}
					}

					logger.info("order_id :" + order.getId() + ",user_id:" + order.getUserId()
							+ " do  signoff  timeout order");

					Request request = new BaseRequest();
					request.setCommand(ActionEnum.CONFIRM_RECEIVAL.getActionName());
					request.setParam("appKey", appkey);

					request.setParam("userId", order.getUserId());
					request.setParam("orderId", order.getId());

					Response response = tradeService.execute(request);
					logger.info("signoff order response:" + response);

				} catch (Exception e) {
					logger.error("  order signoff error:", e);
				}
			}

		} catch (TradeException e) {
			logger.error("OrderTimeoutCancelJob.deliveriedOrderTimeoutSignOffJob error", e);
		}

		logger.info("exit process deliveriedOrderTimeoutSignOffJobs");
	}

}
