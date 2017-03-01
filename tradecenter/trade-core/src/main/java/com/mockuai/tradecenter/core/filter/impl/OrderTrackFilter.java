package com.mockuai.tradecenter.core.filter.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;

import com.mockuai.tradecenter.common.constant.TradeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.ItemCommentDTO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderTogetherDTO;
import com.mockuai.tradecenter.core.domain.OrderTrackDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.filter.Filter;
import com.mockuai.tradecenter.core.manager.OrderTrackManager;
import com.mockuai.tradecenter.core.manager.UserManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.util.TrackUtil;

/**
 * 订单跟踪
 *
 */
public class OrderTrackFilter implements Filter {
	
	private static final Logger log = LoggerFactory.getLogger(OrderTrackFilter.class);
	
	private List<String> needTrackActions;

	ExecutorService executor;

	OrderTrackManager orderTrackManager;
	
	TrackUtil trackUtil;
	
	UserManager userManager;

	public OrderTrackFilter() {
		needTrackActions = new ArrayList<String>();
		needTrackActions.add(ActionEnum.ALIPAY_CALLBACK.getActionName());
		needTrackActions.add(ActionEnum.UNION_PAY_CALLBACK.getActionName());
		needTrackActions.add(ActionEnum.UNION_PAY_CALLBACK_FOR_OLD_VERSION.getActionName());
		needTrackActions.add(ActionEnum.WECHAT_PAY_CALLBACK.getActionName());

		needTrackActions.add(ActionEnum.ADD_ORDER.getActionName());
		needTrackActions.add(ActionEnum.COMMENT_ORDER.getActionName());
		needTrackActions.add(ActionEnum.DELIVERY_GOODS.getActionName());// 发货
		needTrackActions.add(ActionEnum.CONFIRM_RECEIVAL.getActionName());
		
		needTrackActions.add(ActionEnum.APPLY_RETURN.getActionName());
		
		needTrackActions.add(ActionEnum.CONFIRM_REFUND.getActionName());
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
		if (!needTrackActions.contains(ctx.getRequest().getCommand())) {
			return new TradeResponse(ResponseCode.RESPONSE_SUCCESS);
		}

		//如果action执行失败了，则直接放行
		if(ctx.getResponse().isSuccess() == false){
			log.error("fail to execute action, action:{}, msg:{}",
					ctx.getRequest().getCommand(), ctx.getResponse().getMessage());
			return new TradeResponse(ResponseCode.RESPONSE_SUCCESS);
		}

		//FIXME 嗨云项目，这里bizCode写死即可
		final String bizCode = "hanshu";
		
		
		executor = (ExecutorService) ctx.getAppContext().getBean("executor");
		orderTrackManager = (OrderTrackManager) ctx.getAppContext().getBean("orderTrackManager");
		
		trackUtil = (TrackUtil)ctx.getAppContext().getBean("trackUtil");
		
		final String appKey = (String) ctx.getRequest().getParam("appKey");
		
		userManager = (UserManager) ctx.getAppContext().getBean("userManager");
		
		// 下单
		if (ActionEnum.ADD_ORDER.getActionName().equals(ctx.getRequest().getCommand())) {
			executor.submit(new Thread() {
				public void run() {
					List<OrderTogetherDTO> orders = (List<OrderTogetherDTO>) ctx.get("orders");
					if(null!=orders&&orders.size()>0){
						for(OrderTogetherDTO order:orders){
								addOrderTrack(bizCode,order.getOrderDO().getId(),order.getOrderDO().getOrderStatus(),"系统");
						}
					}
				}
			});

		} else if (ActionEnum.ALIPAY_CALLBACK.getActionName().equals(ctx.getRequest().getCommand()) || // 支付回调
				ActionEnum.WECHAT_PAY_CALLBACK.getActionName().equals(ctx.getRequest().getCommand())
				|| ActionEnum.UNION_PAY_CALLBACK.getActionName().equals(ctx.getRequest().getCommand())) {

			OrderDO order = (OrderDO) ctx.get("orderDO");
			if (order != null) {
				addOrderTrack(bizCode,order.getId(), TradeConstants.Order_Status.PAID, "系统");
			}

		} else if (ActionEnum.DELIVERY_GOODS.getActionName().equals(ctx.getRequest().getCommand())) {

			executor.submit(new Thread() {
				public void run() {
					OrderDO order = null;
					order = (OrderDO) ctx.get("order");
					if (null != order) {
						addOrderTrack(bizCode,order.getId(),40,getUserName(order.getSellerId(),appKey));
					}
				}
			});

		} else if (ActionEnum.COMMENT_ORDER.getActionName().equals(ctx.getRequest().getCommand())) {

			executor.submit(new Thread() {
				public void run() {
					List<ItemCommentDTO> itemCommentDTOList = null;

					itemCommentDTOList = (List<ItemCommentDTO>) ctx.get("itemCommentDTOList");

					if (null != itemCommentDTOList) {
						  for(ItemCommentDTO commentDTO : itemCommentDTOList){
							  addOrderTrack(bizCode,commentDTO.getOrderId(),60,getUserName(commentDTO.getUserId(),appKey));
						  }
					}
				}
			});

		}else if(ActionEnum.CONFIRM_RECEIVAL.getActionName().equals(ctx.getRequest().getCommand())){
			executor.submit(new Thread() {
				public void run() {
					OrderDO order = null;
					order = (OrderDO) ctx.get("order");
					if (null != order) {
						addOrderTrack(bizCode,order.getId(),50,getUserName(order.getUserId(),appKey));
					}
				}
			});
		}

		return new TradeResponse(ResponseCode.RESPONSE_SUCCESS);
	}
	
	private String getUserName(Long userId,String appKey){
		String userName  = "";
		try{
			userName = userManager.getUserName(userId, appKey);
		}catch(Exception e){
			log.error("",e);
		}
		return userName;
	}
	
	private void  addOrderTrack(String bizCode,long oid,int orderStatus,String operator){
		try{
			OrderTrackDO orderTrackDO = new OrderTrackDO();
			orderTrackDO.setOperator(operator);
			orderTrackDO.setOperateTime(new Date());
			orderTrackDO.setTrackInfo(trackUtil.getTrackInfo(orderStatus+""));
			orderTrackDO.setBizCode(bizCode);
			orderTrackDO.setOrderId(oid);
			orderTrackDO.setOrderStatus(orderStatus);
			orderTrackDO.setDeleteMark(0);
			orderTrackManager.addOrderTrack(orderTrackDO);
		}catch(Exception e){
			log.error("",e);
		}
		
	}
}
