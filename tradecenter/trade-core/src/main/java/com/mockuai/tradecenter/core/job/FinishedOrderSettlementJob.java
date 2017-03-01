package com.mockuai.tradecenter.core.job;

import java.util.List;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.api.TradeService;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.core.dao.OrderDAO;
import com.mockuai.tradecenter.core.dao.OrderItemDAO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.manager.AppManager;
import com.mockuai.tradecenter.core.manager.BaseService;
import com.mockuai.tradecenter.core.manager.CacheManager;
import com.mockuai.tradecenter.core.manager.OrderItemManager;
import com.mockuai.tradecenter.core.manager.MsgQueueManager;
import com.mockuai.tradecenter.core.manager.SellerTransLogManager;
import com.mockuai.tradecenter.core.manager.UserManager;
import com.mockuai.tradecenter.core.service.job.BaseJob;
import com.mockuai.tradecenter.core.util.DozerBeanService;
import com.mockuai.tradecenter.core.util.TradeCoreConfig;

public class FinishedOrderSettlementJob extends BaseJob{

	private static final Logger log = LoggerFactory.getLogger(FinishedOrderSettlementJob.class);
	@Autowired
	OrderDAO orderDAO;
	@Autowired
	private AppManager appManager;
	@Resource
	private CacheManager cacheManager;
	@Resource
	private SellerTransLogManager sellerTransLogManager;
	@Resource
	private TradeCoreConfig tradeCoreConfig;
	
	@Resource
	private MsgQueueManager msgQueueManager;
	
	@Resource
	private OrderItemDAO orderItemDAO;
	
	@Resource
	TradeService tradeService;

	@Autowired
	private OrderItemManager orderItemManager;
	
	@Resource
	private DozerBeanService dozerBeanService;
	
	@Autowired
	private UserManager  userManager;
	
	/*private SellerTransLogDTO initTransLogForOrderIncome(OrderDO order,boolean isSingleShop){
		SellerTransLogDTO dto = new SellerTransLogDTO();
		dto.setSellerId(order.getSellerId());
		dto.setOrderId(order.getId());
		dto.setOrderSn(order.getOrderSn());
		dto.setBizCode(order.getBizCode());
		dto.setFundInAmount(order.getTotalAmount());
		dto.setPaymentId(order.getPaymentId());
		dto.setUserId(order.getUserId());
		dto.setFundOutAmount(0L);
		dto.setFreezeAmount(0L);
		dto.setSettlementMark("Y");
		dto.setType(EnumInOutMoneyType.INCOME.getCode());
		if(isSingleShop){
			dto.setShopType(ShopType.SINGLE_SHOP);
		}else{
			dto.setShopType(ShopType.SUB_SHOP);
		}
		
		return dto;
	}
	
	private SellerTransLogDTO initTransLogForOrderCommission(OrderDO order){
		SellerTransLogDTO dto = new SellerTransLogDTO();
		dto.setOrderId(order.getId());
		dto.setOrderSn(order.getOrderSn());
		dto.setBizCode(order.getBizCode());
		dto.setFundInAmount(order.getMallCommission());//商城佣金
		dto.setPaymentId(order.getPaymentId());
		dto.setUserId(order.getUserId());
		dto.setFundOutAmount(0L);
		dto.setFreezeAmount(0L);
		dto.setSettlementMark("Y");
		dto.setType(EnumInOutMoneyType.COMMISSION.getCode());
		dto.setShopType(ShopType.MALL);
		dto.setSellerId(order.getSellerId());
		return dto;
	}*/

	public void setTradeService(TradeService tradeService) {
		this.tradeService = tradeService;
	}
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		 doProcess();
		
	}

	public void doProcess() {
		
		log.info(" FinishedOrderSettlementJob start ");
		
		//随机暂停几秒
		/*int millis= (int)(Math.random()*10);
		try {
			Thread.sleep(millis*1000);
		} catch (InterruptedException e1) {
			log.error("interrupted error",e1);
		}*/
				
		OrderQTO query = new OrderQTO();
		
		query.setOffset(0);
		query.setCount(500);
		
		List<OrderDO> orderList = null;
		try{
			orderList =	orderDAO.queryNoSettlementOrders(query);
		}catch(Exception e){
			log.error("queryNoSettlementOrders error",e);
		}
		
		for (OrderDO order : orderList) {
			try{
				
				log.info(" order_id : "+order.getId()+" user_id : "+order.getUserId());
				
				String appkey = (String) cacheManager.get(order.getBizCode());
				if (StringUtils.isBlank(appkey)) {
					if(null==order.getAppType())
						order.setAppType(3);
					AppInfoDTO appInfo = appManager.getAppInfoByBizCode(order.getBizCode(),order.getAppType() );
					if (null != appInfo) {
						appkey = appInfo.getAppKey();
						cacheManager.set(order.getBizCode(), 7200, appkey);
					}else{
						continue;
					}
				}
				
				/*超过维权期更新订单状态*/
				Request request = new BaseRequest();
				request.setCommand(ActionEnum.CLOSE_ORDER.getActionName());
				request.setParam("orderId", order.getId());
				request.setParam("userId", order.getUserId());

				request.setParam("appKey", appkey);
				Response response = tradeService.execute(request);
				
				OrderDTO orderDTO = new OrderDTO(); 
				orderDTO.setId(order.getId());
				orderDTO.setOrderSn(order.getOrderSn());
				orderDTO.setBizCode(order.getBizCode());
				log.info(" finished message orderDTO :" + JSONObject.toJSONString(orderDTO));
				/*发送mq消息*/
				msgQueueManager.sendOrderMessage("orderFinishedNotify", orderDTO);

			}catch(Exception e){
				log.error("order settlement error",e);
				continue;
			}

		}
		
		log.info(" FinishedOrderSettlementJob end ");
		
	}
}
