package com.mockuai.tradecenter.core.job;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.core.base.ClientExecutor;
import com.mockuai.tradecenter.core.base.ClientExecutorFactory;
import com.mockuai.tradecenter.core.dao.OrderItemDAO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.manager.AppManager;
import com.mockuai.tradecenter.core.manager.CacheManager;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.service.AppContext;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.TradeRequest;
import com.mockuai.tradecenter.core.service.job.BaseJob;

/**
 * 微信对账job
 *
 */
public class WechatPayDailyJob extends BaseJob {
	private static final Logger logger = Logger.getLogger(WechatPayDailyJob.class);
	@Resource
	private ClientExecutorFactory clientExecutorFactory;
	
	@Resource
	private OrderItemDAO orderItemDAO;
	
	@Autowired
	private AppManager appManager;

	@Resource
	private CacheManager cacheManager;
	
	@Resource
	private OrderManager orderMng;

	
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		 doProcessDaily();
		
	}

	public void doProcessDaily() {
		try{
			logger.info(" wxpayRefund start ");
			
			ClientExecutor clientExecutor = clientExecutorFactory.getExecutor("wxpayRefundSingleQuery");
			AppContext appContext = new AppContext();
			TradeRequest tradeRequest = new TradeRequest();
			
			
			OrderItemQTO query = new OrderItemQTO();
			
			List<OrderItemDO> orderItemList = (List<OrderItemDO>) orderItemDAO.queryWxPayProcessingRefundItemList(query);
			
			logger.info("need singel query list size:"+orderItemList.size());
			
			for(OrderItemDO orderItemDO:orderItemList){
				try {
					
					OrderDO orderDO = orderMng.getOrder(orderItemDO.getOrderId(), orderItemDO.getUserId());
					if(null==orderDO){
						continue;
					}
					
					RequestContext context = new RequestContext(appContext,tradeRequest);
					BizInfoDTO bizInfo = appManager.getBizInfo(orderItemDO.getBizCode());
					if(null!=bizInfo&&null!=bizInfo.getBizPropertyMap()){
						context.put("bizPropertyMap", bizInfo.getBizPropertyMap());
						context.put("bizName", bizInfo.getBizName());
						context.put("refundItemSn", orderItemDO.getSellerId()+"_"+
								orderItemDO.getUserId()+"_"+ orderItemDO.getId());
						context.put("order", orderDO);
					}
					
					clientExecutor.getPaymentUrl(context);
				}catch (Exception e) {
					logger.error("wechatPayDailyJob error", e);
				}
				
			}

			logger.info(" wxpayRefund end ");
			
		}catch(Exception e){
			logger.error("wechatPayDailyJob error", e);
		}
		
		
		
	}
}
