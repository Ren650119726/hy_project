package com.mockuai.tradecenter.core.filter.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.ItemCommentDTO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderTogetherDTO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.filter.Filter;
import com.mockuai.tradecenter.core.manager.DataManager;
import com.mockuai.tradecenter.core.service.RequestContext;

/**
 * 交易数据埋点
 */
public class BuriedPointFilter implements Filter {
	private List<String> needBuriedPointActions;
	
	 ExecutorService       executor;
	 
	 private DataManager dataManager;

	public BuriedPointFilter() {
		needBuriedPointActions = new ArrayList<String>();
		needBuriedPointActions.add(ActionEnum.ALIPAY_CALLBACK.getActionName());
		needBuriedPointActions.add(ActionEnum.UNION_PAY_CALLBACK.getActionName());
		needBuriedPointActions.add(ActionEnum.UNION_PAY_CALLBACK_FOR_OLD_VERSION.getActionName());
		needBuriedPointActions.add(ActionEnum.WECHAT_PAY_CALLBACK.getActionName());
		
		
		needBuriedPointActions.add(ActionEnum.ADD_ORDER.getActionName());
		needBuriedPointActions.add(ActionEnum.COMMENT_ORDER.getActionName());
		needBuriedPointActions.add(ActionEnum.DELIVERY_GOODS.getActionName());
	}

	@Override
	public boolean isAccept(RequestContext ctx) {
		return true;
	}

	@Override
	public TradeResponse before(RequestContext ctx) throws TradeException {

		return new TradeResponse(ResponseCode.RESPONSE_SUCCESS);
		
	}

	@Override
	public TradeResponse after(final RequestContext ctx) throws TradeException {

		// 对于指定无需进行appKey校验的action，直接放行
		if (!needBuriedPointActions.contains(ctx.getRequest().getCommand())) {
			return new TradeResponse(ResponseCode.RESPONSE_SUCCESS);
		}
		executor = (ExecutorService) ctx.getAppContext().getBean("executor");
		dataManager = (DataManager) ctx.getAppContext().getBean("dataManager");
		final String appKey = (String) ctx.getRequest().getParam("appKey");
		//下单
		if(ActionEnum.ADD_ORDER.getActionName().equals(ctx.getRequest().getCommand())){
			
			executor.submit(new Thread(){
				public void run(){
					List<OrderTogetherDTO> orders = (List<OrderTogetherDTO>) ctx.get("orders");
//					dataManager.doAddOrderBuriedPoint( orders, appKey  );

					OrderDO orderDO = (OrderDO) ctx.get("orderDO");

					//如果已经使用余额和积分全额支付完成了的话，则添加支付成功的数据埋点
					if (orderDO.getTotalAmount() <= 0) {//订单已支付（这种情况一般发生在优惠或者余额抵现的情况）
						//支付成功数据埋点
//						dataManager.doPaySuccessBuriedPoint(orderDO);

					}

					if (orders != null && orders.isEmpty() == false) {
						for (OrderTogetherDTO subOrder : orders) {
							if (subOrder.getOrderDO().getTotalAmount() <= 0) {
								//支付成功数据埋点
//								dataManager.doPaySuccessBuriedPoint(subOrder.getOrderDO());
							}
						}
					}

				}
			});
			
		}else if( ActionEnum.ALIPAY_CALLBACK.getActionName().equals(ctx.getRequest().getCommand()) ||   //支付回调
				ActionEnum.ALIPAY_CALLBACK.getActionName().equals(ctx.getRequest().getCommand()) ||
						ActionEnum.ALIPAY_CALLBACK.getActionName().equals(ctx.getRequest().getCommand())){
			
			OrderDO orderDO = null;
			orderDO = (OrderDO) ctx.get("orderDO");
//			dataManager.doPaySuccessBuriedPoint(orderDO);
			
			
		}else if( ActionEnum.DELIVERY_GOODS.getActionName().equals(ctx.getRequest().getCommand()) ){
			
			executor.submit(new Thread(){
				public void run(){
					OrderDO order = null;
					order = (OrderDO) ctx.get("order");
					if(null!=order){
//						dataManager.doHasDeliveryedBuriedPoint(order, appKey);
					}
				}
			});
		}else if(  ActionEnum.COMMENT_ORDER.getActionName().equals(ctx.getRequest().getCommand()) ){
			
			executor.submit(new Thread(){
				public void run(){
					List<ItemCommentDTO>  itemCommentDTOList = null;
					
					itemCommentDTOList = (List<ItemCommentDTO>) ctx.get("itemCommentDTOList");
					
					if(null!=itemCommentDTOList){
//						  dataManager.doOrderCommentBuriedPoint(itemCommentDTOList, appKey);
					}
				}
			});
			
		}
		
		return new TradeResponse(ResponseCode.RESPONSE_SUCCESS);
	}
}
