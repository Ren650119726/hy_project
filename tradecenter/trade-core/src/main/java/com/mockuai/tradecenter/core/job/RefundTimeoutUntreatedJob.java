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
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.AppManager;
import com.mockuai.tradecenter.core.manager.CacheManager;
import com.mockuai.tradecenter.core.manager.OrderItemManager;
import com.mockuai.tradecenter.core.service.job.BaseJob;
import com.mockuai.tradecenter.core.util.TradeCoreConfig;
/**
 * 退款超时未处理
 *
 */
public class RefundTimeoutUntreatedJob extends BaseJob {

	private static final Logger logger = Logger.getLogger(RefundTimeoutUntreatedJob.class);

	@Autowired
	private OrderItemManager orderItemManager;


	@Autowired
	private TradeCoreConfig tradeCoreConfig;

	@Autowired
	private AppManager appManager;

	@Resource
	private CacheManager cacheManager;

	@Resource
	private TradeService tradeService;

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		doProcessTimeoutRefund();
		
	}

	public void doProcessTimeoutRefund() {
		
		logger.info(" timeoutAutoRefundRefuseTask start ");
		
		OrderItemQTO query = new OrderItemQTO();
		query.setTimeoutAutoRefundDay(tradeCoreConfig.getTimeoutAutoRefundDay());


		try {
			List<OrderItemDO> unProcessOrderItemList = orderItemManager.queryTimeoutUntreatedOrderItemList(query);

			for (OrderItemDO orderItem : unProcessOrderItemList) {
				try {
					String appkey = (String) cacheManager.get(orderItem.getBizCode());
					if (StringUtils.isBlank(appkey)) {
						AppInfoDTO appInfo = appManager.getAppInfoByBizCode(orderItem.getBizCode(),1);
						if (null != appInfo) {
							appkey = appInfo.getAppKey();
							cacheManager.set(orderItem.getBizCode(), 7200, appkey);
						}else{
							continue;
						}
					}
					logger.info("refuse refund");
					Request request = new BaseRequest();
					request.setCommand(ActionEnum.AUDIT_RETURN_APPLY.getActionName());
					RefundOrderItemDTO refundOrderItemDTO = new RefundOrderItemDTO();
//					refundOrderItemDTO.setItemSkuId(orderItem.getItemSkuId());
					refundOrderItemDTO.setOrderItemId(orderItem.getId());
					refundOrderItemDTO.setOrderId(orderItem.getOrderId());
					refundOrderItemDTO.setUserId(orderItem.getUserId());
					refundOrderItemDTO.setOrderItemId(orderItem.getId());
					refundOrderItemDTO.setAuditResult(0);
					refundOrderItemDTO.setRefuseReason("超时自动拒绝");
					request.setParam("refundOrderItemDTO", refundOrderItemDTO);		
						
					
					request.setParam("appKey", appkey);
					Response response = tradeService.execute(request);
					logger.info("refuse refund response:" + response);
				} catch (Exception e) {
					logger.error("refuse refund error:", e);
				}
			}

			logger.info(" timeoutAutoRefundRefuseTask end ");
			
		} catch (TradeException e) {
			logger.error("OrderTimeoutCancelJob.doProcessTimeoutCancel error", e);
		}

	}

}
