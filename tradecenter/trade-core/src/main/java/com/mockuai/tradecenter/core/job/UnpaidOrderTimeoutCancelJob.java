package com.mockuai.tradecenter.core.job;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.suppliercenter.common.dto.OrderStockDTO;
import com.mockuai.suppliercenter.common.dto.OrderStockDTO.OrderSku;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.api.TradeService;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.common.domain.PaymentUrlDTO;
import com.mockuai.tradecenter.core.base.ClientExecutor;
import com.mockuai.tradecenter.core.base.ClientExecutorFactory;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.AppManager;
import com.mockuai.tradecenter.core.manager.CacheManager;
import com.mockuai.tradecenter.core.manager.ItemManager;
import com.mockuai.tradecenter.core.manager.MsgQueueManager;
import com.mockuai.tradecenter.core.manager.OrderItemManager;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.manager.SupplierManager;
import com.mockuai.tradecenter.core.service.AppContext;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.TradeRequest;
import com.mockuai.tradecenter.core.service.job.BaseJob;
import com.mockuai.tradecenter.core.util.ModelUtil;
import com.mockuai.tradecenter.core.util.TradeCoreConfig;

public class UnpaidOrderTimeoutCancelJob extends BaseJob {

	private static final long serialVersionUID = -7685784354881160578L;

	private static final Logger logger = Logger.getLogger(UnpaidOrderTimeoutCancelJob.class);

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

	@Resource
	private SupplierManager supplierManager;

	@Autowired
	private OrderItemManager orderItemManager;

	@Resource
	private ItemManager itemManager;
	
	@Autowired
	MsgQueueManager msgQueueManager;

	@Autowired
	private ClientExecutorFactory clientExecutorFactory;

	public void setOrderManager(OrderManager orderManager) {
		this.orderManager = orderManager;
	}

	public void setTradeService(TradeService tradeService) {
		this.tradeService = tradeService;
	}
	
	

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		doProcessTimeoutCancel();
		
	}

	public void doProcessTimeoutCancel() {

		logger.info(" UnpaidOrderTimeoutCancelJob start ");
		
		OrderQTO query = new OrderQTO();

		query.setTimeoutMinuteNumber(tradeCoreConfig.getTimeoutCancelMinuteNumber());

		
		
		try {
			// 预单处理
			query.setOffset(0);
			query.setCount(50);
			List<OrderDO> needCancelPreOrders = orderManager.queryTimeoutCancelPreOrders(query);
			
//			logger.info(" needCancelPreOrders :"+JSONObject.toJSONString(needCancelPreOrders));
			
			if(null!=needCancelPreOrders&&needCancelPreOrders.size()>0){
				for( OrderDO orderDO:needCancelPreOrders ){


					String appkey = (String) cacheManager.get(orderDO.getBizCode());
					if (null==appkey||StringUtils.isBlank(appkey)) {
						if(null==orderDO.getAppType())
							orderDO.setAppType(3);
						AppInfoDTO appInfo = appManager.getAppInfoByBizCode(orderDO.getBizCode(),orderDO.getAppType());
						if (null != appInfo) {
							appkey = appInfo.getAppKey();
							cacheManager.set(orderDO.getBizCode(), 7200, appkey);
						}else{
							continue;
						}
					}


					OrderItemQTO orderItemQuery = new OrderItemQTO();
					orderItemQuery.setOrderId(orderDO.getId());
					orderItemQuery.setUserId(orderDO.getUserId());
					List<OrderItemDO> orderItemDOList = orderItemManager.queryOrderItem(orderItemQuery);
					
//					logger.info(" orderItemDOList : "+JSONObject.toJSONString(orderItemDOList));
					
					if(null!=orderItemDOList&&orderItemDOList.size()==1){
                        //TODO 暂时先注释掉未下单取消预单逻辑
//						Boolean thawStockResult = itemManager.thawStock(orderItemDOList.get(0).getItemSkuId(), orderDO.getSellerId(), orderItemDOList.get(0).getNumber(), appkey);
//						Boolean thawStockResult = itemManager.thawStock(orderDO.getOrderSn(), orderDO.getUserId(), appkey);
						OrderStockDTO orderStockDTO = new OrderStockDTO();
			            orderStockDTO.setOrderSn(orderDO.getOrderSn());
			            orderStockDTO.setSellerId(orderDO.getSellerId());
			            
			            List<OrderSku> orderSkus = new ArrayList<OrderSku>();
			    		
			    		for(OrderItemDO orderItemDO :orderItemDOList){
			    			OrderSku orderSku = new OrderSku();						
			    			orderSku.setSkuId(orderItemDO.getItemSkuId());
			    			orderSku.setNumber(orderItemDO.getNumber());
			    			orderSku.setStoreId(orderDO.getStoreId());
			    			orderSku.setSupplierId(orderDO.getSupplierId());
			    			orderSkus.add(orderSku);
			    		}
			    		orderStockDTO.setOrderSkuList(orderSkus);
			    		try {
			    			Boolean thawStockResult = supplierManager.thawOrderSkuStock(orderStockDTO, appkey);
						} catch (Exception e) {
							logger.error(e);
						}						
//						if(thawStockResult){
							orderManager.deletePreOrder(orderDO,orderItemDOList.get(0).getItemSkuId());
//						}
					}
					OrderDTO orderDTO = ModelUtil.convert2OrderDTO(orderDO);
					msgQueueManager.sendOrderMessage("preOrderCancel", orderDTO);
				}
			}
			
			
			// 普通订单处理
			logger.info(" query : " + JSONObject.toJSONString(query));
			List<OrderDO> unpaidOrders = orderManager.queryTimeoutUnpaidOrders(query);

			logger.info(" UnpaidOrderTimeoutCancelJob unpaidOrders size: "+unpaidOrders.size());
			
			for (OrderDO order : unpaidOrders) {
				try {
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
					Request request = new BaseRequest();
					boolean needCancel = true;
					try{
						AppContext appContext = new AppContext();
						TradeRequest tradeRequest = new TradeRequest();
						RequestContext requestContext = new RequestContext(appContext, tradeRequest);
						requestContext.put("out_trade_no", order.getSellerId()+"_"+order.getUserId()+"_"+order.getId());

						//getBizInfo
						BizInfoDTO bizInfo = appManager.getBizInfo(order.getBizCode());
						requestContext.put("bizInfo", bizInfo);
						requestContext.put("bizPropertyMap", bizInfo.getBizPropertyMap());
						ClientExecutor querySingleQueryExecutor = clientExecutorFactory.getExecutor(order.getPaymentId()+"_PaymentSingleQuery");
						PaymentUrlDTO paymentUrlDTO = querySingleQueryExecutor.getPaymentUrl(requestContext);
						if(paymentUrlDTO.isPaid()){

							//TODO 需要改订单状态
							logger.info("支付成功了");
							needCancel = false;
						}
					}catch(Exception e){

					}

					if(needCancel){
						logger.info("order_id :" + order.getId() + ",user_id:" + order.getUserId() + " do cancel order");

						request.setCommand(ActionEnum.CANCEL_ORDER.getActionName());
						request.setParam("orderId", order.getId());
						request.setParam("userId", order.getUserId());

						request.setParam("appKey", appkey);
						Response response = tradeService.execute(request);
						logger.info("cancel order response:" + response);
					}


				} catch (Exception e) {
					logger.error("cancel order error:", e);
				}
			}

			logger.info(" UnpaidOrderTimeoutCancelJob end ");

		} catch (TradeException e) {
			logger.error("OrderTimeoutCancelJob.doProcessTimeoutCancel error", e);
		}

	}

}
