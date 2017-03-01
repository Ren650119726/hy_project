//package com.mockuai.tradecenter.core.filter.impl;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ExecutorService;
//
//import com.mockuai.tradecenter.common.api.TradeResponse;
//import com.mockuai.tradecenter.common.constant.ActionEnum;
//import com.mockuai.tradecenter.common.constant.ResponseCode;
//import com.mockuai.tradecenter.common.domain.ItemCommentDTO;
//import com.mockuai.tradecenter.common.domain.OrderDTO;
//import com.mockuai.tradecenter.common.domain.OrderItemDTO;
//import com.mockuai.tradecenter.core.domain.OrderDO;
//import com.mockuai.tradecenter.core.domain.OrderTogetherDTO;
//import com.mockuai.tradecenter.core.exception.TradeException;
//import com.mockuai.tradecenter.core.filter.Filter;
//import com.mockuai.tradecenter.core.manager.DataManager;
//import com.mockuai.tradecenter.core.manager.RocketMQManager;
//import com.mockuai.tradecenter.core.service.RequestContext;
//import com.mockuai.tradecenter.core.util.DozerBeanService;
//
///**
// * 埋点
// *
// */
//public class MQMessageFilter implements Filter {
//	private List<String> needBuriedPointActions;
//	
//	 ExecutorService       executor;
//	 
//	 DataManager dataManager;
//
//	 DozerBeanService dozerBeanService;
//	 
//	 RocketMQManager rocketMQManager;
//	 
//	 private static final String TOPIC = "trade";
//	 
//	public MQMessageFilter() {
//		needBuriedPointActions = new ArrayList<String>();
//		needBuriedPointActions.add(ActionEnum.ALIPAY_CALLBACK.getActionName());
//		needBuriedPointActions.add(ActionEnum.UNION_PAY_CALLBACK.getActionName());
//		needBuriedPointActions.add(ActionEnum.UNION_PAY_CALLBACK_FOR_OLD_VERSION.getActionName());
//		needBuriedPointActions.add(ActionEnum.WECHAT_PAY_CALLBACK.getActionName());
//		
//		
//		needBuriedPointActions.add(ActionEnum.ADD_ORDER.getActionName());
//		needBuriedPointActions.add(ActionEnum.CANCEL_ORDER.getActionName());
//	}
//
//	@Override
//	public boolean isAccept(RequestContext ctx) {
//		return true;
//	}
//
//	@Override
//	public TradeResponse before(RequestContext ctx) throws TradeException {
//
//		return new TradeResponse(ResponseCode.RESPONSE_SUCCESS);
//		
//	}
//
//	@Override
//	public TradeResponse after(final RequestContext ctx) throws TradeException {
//
//		// 对于指定无需进行appKey校验的action，直接放行
//		if (!needBuriedPointActions.contains(ctx.getRequest().getCommand())) {
//			return new TradeResponse(ResponseCode.RESPONSE_SUCCESS);
//		}
//		executor = (ExecutorService) ctx.getAppContext().getBean("executor");
//		dataManager = (DataManager) ctx.getAppContext().getBean("dataManager");
//		dozerBeanService = (DozerBeanService)ctx.getAppContext().getBean("dozerBeanService");
//		rocketMQManager = (RocketMQManager)ctx.getAppContext().getBean("rocketMQManager");
//		
//		//下单
//		if(ActionEnum.ADD_ORDER.getActionName().equals(ctx.getRequest().getCommand())){
//			
//			executor.submit(new Thread(){
//				public void run(){
//					List<OrderTogetherDTO> orders = (List<OrderTogetherDTO>) ctx.get("orders");
//					for(OrderTogetherDTO orderTogetherDTO:orders){
//						OrderDTO orderDTO = dozerBeanService.cover(orderTogetherDTO.getOrderDO(), OrderDTO.class);
//						List<OrderItemDTO> orderItemDTOList = dozerBeanService.coverList(orderTogetherDTO.getItemList(), OrderItemDTO.class);
//						orderDTO.setOrderItems(orderItemDTOList);
//						try {
//							rocketMQManager.send(TOPIC, "orderUnpaid", orderDTO.getId()+"", orderDTO);
//						} catch (TradeException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//				}
//			});
//			
//			
//			
//		}else if( ActionEnum.CANCEL_ORDER.getActionName().equals(ctx.getRequest().getCommand()) ){
//			
////			executor.submit(new Thread(){
////				public void run(){
////					OrderDO order = null;
////					order = (OrderDO) ctx.get("order");
////					if(null!=order){
////						dataManager.doHasDeliveryedBuriedPoint(order, appKey);
////					}
////				}
////			});
//			
//			
//			
//		}
//		
//
//		
//		return new TradeResponse(ResponseCode.RESPONSE_SUCCESS);
//	}
//}
