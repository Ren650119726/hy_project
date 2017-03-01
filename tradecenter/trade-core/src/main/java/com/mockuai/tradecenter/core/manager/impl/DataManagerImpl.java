package com.mockuai.tradecenter.core.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.datacenter.client.DataClient;
import com.mockuai.datacenter.common.api.Response;
import com.mockuai.datacenter.common.constant.RequestParamTypeEnum;
import com.mockuai.tradecenter.common.domain.DataQTO;
import com.mockuai.tradecenter.common.domain.ItemCommentDTO;
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.common.domain.SalesRatioDTO;
import com.mockuai.tradecenter.common.domain.SalesTotalDTO;
import com.mockuai.tradecenter.common.util.MoneyUtil;
import com.mockuai.tradecenter.core.dao.DataDAO;
import com.mockuai.tradecenter.core.dao.OrderDAO;
import com.mockuai.tradecenter.core.dao.OrderViewDAO;
import com.mockuai.tradecenter.core.domain.DailyDataDO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.domain.OrderTogetherDTO;
import com.mockuai.tradecenter.core.domain.OrderViewDO;
import com.mockuai.tradecenter.core.domain.SalesRatioDO;
import com.mockuai.tradecenter.core.domain.TOPItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.AppManager;
import com.mockuai.tradecenter.core.manager.BaseService;
import com.mockuai.tradecenter.core.manager.DataManager;
import com.mockuai.tradecenter.core.manager.OrderItemManager;
import com.mockuai.tradecenter.core.util.DozerBeanService;
import com.mockuai.tradecenter.core.util.TradeUtil;

public class DataManagerImpl extends BaseService implements DataManager{
	 private static final Logger log = LoggerFactory.getLogger(DataManagerImpl.class);
	@Resource
	private DataDAO dataDAO;
	@Resource
	private DozerBeanService  dozerBeanService;
	
	@Resource
	private DataClient dataClient;
	
	@Resource
	private OrderViewDAO orderViewDAO;
	
	@Resource
	private OrderDAO orderDAO;
	
	@Resource
	private OrderItemManager orderItemManager;
	
	@Resource
	private AppManager appManager;
	
	@Override
	public Long getTotalAmount(DataQTO dataQTO) {
		return dataDAO.getTotalAmount(dataQTO);
	}

	@Override
	public Long getTotalOrderCount(DataQTO dataQTO) {
		return dataDAO.getTotalOrderCount(dataQTO);
	}

	@Override
	public Long getPaidOrderCount(DataQTO dataQTO) {
		return dataDAO.getPaidOrderCount(dataQTO);
	}

	@Override
	public Long getTotalUserCount(DataQTO dataQTO) {
		return dataDAO.getTotalUserCount(dataQTO);
	}

	@Override
	public Long getPaidUserCount(DataQTO dataQTO) {
		return dataDAO.getPaidUserCount(dataQTO);
	}

	@Override
	public List<TOPItemDO> getTOP10Item(DataQTO dataQTO) {
		return dataDAO.getTOP10Item(dataQTO);
	}

	@Override
	public Long getOldUserCount(DataQTO dataQTO) {
		return dataDAO.getOldUserCount(dataQTO);
	}

	@Override
	public List<DailyDataDO> getTotalAmountDaily(DataQTO dataQTO) {
		return dataDAO.getTotalAmountDaily(dataQTO);
	}

	@Override
	public List<DailyDataDO> getTotalOrderCountDaily(DataQTO dataQTO) {
		return dataDAO.getTotalOrderCountDaily(dataQTO);
	}

	@Override
	public List<DailyDataDO> getPaidOrderCountDaily(DataQTO dataQTO) {
		return dataDAO.getPaidOrderCountDaily(dataQTO);
	}

	@Override
	public List<DailyDataDO> getTotalUserCountDaily(DataQTO dataQTO) {
		return dataDAO.getTotalUserCountDaily(dataQTO);
	}

	@Override
	public List<DailyDataDO> getPaidUserCountDaily(DataQTO dataQTO) {
		return dataDAO.getPaidUserCountDaily(dataQTO);
	}

	@Override
	public SalesTotalDTO querySalesRatioByCategory(DataQTO query)throws TradeException {
		
		List<SalesRatioDO> list = dataDAO.querySalesRatioByCategory(query);
		Long totalAmount = dataDAO.getAllTotalAmount(query);
		
		SalesTotalDTO totalDTO = new SalesTotalDTO();
		totalDTO.setTotalAmount(totalAmount);
		
		List<SalesRatioDTO> returnList = new ArrayList();
		for(SalesRatioDO salesDO:list){
			double rate = MoneyUtil.div(salesDO.getTotalAmount()+"", totalAmount+"");
			SalesRatioDTO salesRatioDTO = dozerBeanService.cover(salesDO, SalesRatioDTO.class);
			salesRatioDTO.setSalesRatio(rate);
			salesRatioDTO.setSalesAmount(salesDO.getTotalAmount());
			returnList.add(salesRatioDTO);
		}
		totalDTO.setSalesRatioList(returnList);
		return totalDTO;
	}

	@Override
	public SalesTotalDTO querySalesRatioByBrand(DataQTO query) throws TradeException {
		List<SalesRatioDO> list = dataDAO.querySalesRatioByBrand(query);
		Long totalAmount = dataDAO.getAllTotalAmount(query);
		List<SalesRatioDTO> returnList = new ArrayList();
		SalesTotalDTO totalDTO = new SalesTotalDTO();
		totalDTO.setTotalAmount(totalAmount);
		for(SalesRatioDO salesDO:list){
			double rate = MoneyUtil.div(salesDO.getTotalAmount()+"", totalAmount+"");
			SalesRatioDTO salesRatioDTO = dozerBeanService.cover(salesDO, SalesRatioDTO.class);
			salesRatioDTO.setSalesRatio(rate);
			salesRatioDTO.setSalesAmount(salesDO.getTotalAmount());
			returnList.add(salesRatioDTO);
		}
		totalDTO.setSalesRatioList(returnList);
		return totalDTO;
	}

	@Override
	public void doAddOrderBuriedPoint( List<OrderTogetherDTO> orders, String appkey) {
		printIntoService(log,"doAddOrderBuriedPoint",orders,"");
		if(null!=orders&&orders.size()>0){
			for(OrderTogetherDTO order:orders){
				try{
					Response<?> response = dataClient.buriedPoint(order.getOrderDO().getSellerId(), 
							"orderTotal", TradeUtil.genBuriedPointMapKey(RequestParamTypeEnum.PROPERTY.getValue(), "1"), new Date().getTime(), appkey);
					
					printIntoService(log,"buriedPoint response",response,"");
					
					Response<?> response1 = dataClient.buriedPoint(order.getOrderDO().getSellerId(), "orderUser", 
							TradeUtil.genBuriedPointMapKey(RequestParamTypeEnum.PROPERTY.getValue(), order.getOrderDO().getUserId()+""), new Date().getTime(), appkey);
					
					printIntoService(log,"buriedPoint response1",response1,"");
				}catch(Exception e){
					log.error("doAddOrderBuriedPoint error",e);
				}catch(Throwable e){
					log.error("doAddOrderBuriedPoint error",e);
				}
				
			}
		}
	}

	@Override
	public void doPaySuccessBuriedPoint(OrderDO order) {
		printIntoService(log,"doPaySuccessBuriedPoint",order,"");
		try{
			AppInfoDTO appInfoDTO = appManager.getAppInfoByBizCode(order.getBizCode(),order.getAppType());
			
			String appkey = appInfoDTO.getAppKey();
			
			
				OrderQTO query = new OrderQTO();
				query.setOriginalOrder(order.getId());
				List<OrderDO> subOrders = orderDAO.querySubOrdersByOriginalOrder(query);
				if(null!=subOrders&&subOrders.size()>0){
					for(OrderDO subOrder:subOrders){
						dataClient.buriedPoint(subOrder.getSellerId(), "orderPayUser", TradeUtil.genBuriedPointMapKey(RequestParamTypeEnum.PROPERTY.getValue(),subOrder.getUserId()+""), new Date().getTime(), appkey);
						dataClient.buriedPoint(subOrder.getSellerId(), "orderPay", TradeUtil.genBuriedPointMapKey(RequestParamTypeEnum.PROPERTY.getValue(),"1"), new Date().getTime(), appkey);
						dataClient.buriedPoint(subOrder.getSellerId(), "orderMoney", TradeUtil.genBuriedPointMapKey(RequestParamTypeEnum.PROPERTY.getValue(),String.format("%.2f", ((double) (subOrder.getTotalAmount()) / 100))), new Date().getTime(), appkey);
						dataClient.buriedPoint(subOrder.getSellerId(), "repeatPayUser", TradeUtil.genBuriedPointMapKey(RequestParamTypeEnum.PROPERTY.getValue(),subOrder.getUserId()+""), new Date().getTime(), appkey);
					}
				}else{
					
					dataClient.buriedPoint(order.getSellerId(), "orderPay", TradeUtil.genBuriedPointMapKey(RequestParamTypeEnum.PROPERTY.getValue(),1+""), new Date().getTime(), appkey);
					dataClient.buriedPoint(order.getSellerId(), "orderPayUser", TradeUtil.genBuriedPointMapKey(RequestParamTypeEnum.PROPERTY.getValue(),order.getUserId()+""), new Date().getTime(), appkey);
					dataClient.buriedPoint(order.getSellerId(), "orderMoney", TradeUtil.genBuriedPointMapKey(RequestParamTypeEnum.PROPERTY.getValue(),String.format("%.2f", ((double) (order.getTotalAmount()) / 100))), new Date().getTime(), appkey);
					dataClient.buriedPoint(order.getSellerId(), "repeatPayUser", TradeUtil.genBuriedPointMapKey(RequestParamTypeEnum.PROPERTY.getValue(),order.getUserId()+""), new Date().getTime(), appkey);
				}
				//top ten 商品
				OrderItemQTO orderItemQTO = new OrderItemQTO();
				orderItemQTO.setOrderId(order.getId());
				orderItemQTO.setUserId(order.getUserId());
				List<OrderItemDO> itemList = orderItemManager.queryOrderItem(orderItemQTO);
				for(OrderItemDO orderItem:itemList){
					
					dataClient.buriedPoint(orderItem.getSellerId(), "itemSales", 
							TradeUtil.genBuriedPointMapKey(RequestParamTypeEnum.OBJECT.getValue(),
									orderItem.getItemId()+"",
									RequestParamTypeEnum.PROPERTY.getValue(),
									orderItem.getNumber()+""), new Date().getTime(), appkey);
					if(null!=orderItem.getItemBrandId()) {
						Response<?> response = dataClient.buriedPoint(orderItem.getSellerId(),
								"itemBrandSales", TradeUtil.genBuriedPointMapKey(RequestParamTypeEnum.OBJECT.getValue(),
										orderItem.getItemBrandId()+"",RequestParamTypeEnum.PROPERTY.getValue(),orderItem.getNumber()+""), new Date().getTime(), appkey);
						printInvokeService(log,"do itemBrandId buriedPoint",response,"");
					}
						
						
					if(null!=orderItem.getCategoryId()){//itemCategorySales
						Response<?> response = dataClient.buriedPoint(orderItem.getSellerId(), "itemCategorySales", 
								TradeUtil.genBuriedPointMapKey(RequestParamTypeEnum.OBJECT.getValue(),
										orderItem.getCategoryId()+"",RequestParamTypeEnum.PROPERTY.getValue(),orderItem.getNumber()+""),  new Date().getTime(), appkey);
						printInvokeService(log,"do categoryId buriedPoint",response,"");
					}
					}
				Response<?> response = dataClient.buriedPoint(order.getSellerId(), "waitDelivery", TradeUtil.genBuriedPointMapKey(RequestParamTypeEnum.PROPERTY.getValue(),"1"), new Date().getTime(), appkey);
				printInvokeService(log,"add waitDelivery buriedPoint",response,"");
				
				
		}
		catch(Exception e){
			log.error("doPaySuccessBuriedPoint error",e);
		}catch(Throwable e){
			log.error("doPaySuccessBuriedPoint error",e);
		}
		
	}

	@Override
	public void doCancelOrderBuriedPoint(OrderDO order,String appkey) {
		printIntoService(log,"doCancelOrderBuriedPoint",order,"");
		try{
			//总订单数据	orderTotal	每次用户下单时进行数据埋点	  下单人数		orderUser userId 	每次用户下单后埋点	
//			dataClient.buriedPoint(order.getSellerId(),"orderTotal", TradeUtil.genBuriedPointMapKey(RequestParamTypeEnum.PROPERTY.getValue(),"-1"), new Date().getTime(), appkey);
//			dataClient.buriedPoint(order.getSellerId(), "orderUser", TradeUtil.genBuriedPointMapKey(RequestParamTypeEnum.PROPERTY.getValue(),"-1"), new Date().getTime(), appkey);
		}catch(Exception e){
			log.error("doCancelOrderBuriedPoint error",e);
		}catch(Throwable e){
			log.error("doCancelOrderBuriedPoint error",e);
		}
	}


	@Override
	public void addWaitCommentStatusBuriedPoint(OrderDO order, String appkey) {
		printIntoService(log,"waitCommentStatusBuriedPoint",order,"");
		try{
			dataClient.buriedPoint(order.getSellerId(), "waitEvaluate", TradeUtil.genBuriedPointMapKey(RequestParamTypeEnum.PROPERTY.getValue(),"1"), new Date().getTime(), appkey);
		}catch(Exception e){
			log.error("waitCommentStatusBuriedPoint error",e);
		}catch(Throwable e){
			log.error("waitCommentStatusBuriedPoint error",e);
		}
	}

	@Override
	public void doHasDeliveryedBuriedPoint(OrderDO order, String appkey) {
		printIntoService(log,"doHasDeliveryedBuriedPoint",order,"");
		try{
			dataClient.buriedPoint(order.getSellerId(),"waitDelivery", TradeUtil.genBuriedPointMapKey(RequestParamTypeEnum.PROPERTY.getValue(),"-1"), new Date().getTime(), appkey);
		}catch(Exception e){
			log.error("doHasDeliveryedBuriedPoint error",e);
		}catch(Throwable e){
			log.error("doHasDeliveryedBuriedPoint error",e);
		}
	}

	@Override
	public void doOrderCommentBuriedPoint(List<ItemCommentDTO> list, String appkey) {
		printIntoService(log,"doOrderCommentBuriedPoint",list,"");
		try{
			for(ItemCommentDTO commentDTO : list){
				//待评价-1
				dataClient.buriedPoint(commentDTO.getSellerId(), "waitEvaluate", TradeUtil.genBuriedPointMapKey(RequestParamTypeEnum.PROPERTY.getValue(),"-1"), new Date().getTime(), appkey);
				Map params = null;
				String key = "negativeComment";
				if(commentDTO.getScore()==5){
					key = "positiveComment";
					params = TradeUtil.genBuriedPointMapKey(RequestParamTypeEnum.PROPERTY.getValue(),"1");
				}else if(commentDTO.getScore()==3){
					key = "moderateComment";
					params = TradeUtil.genBuriedPointMapKey(RequestParamTypeEnum.PROPERTY.getValue(),"1");
				}else if(commentDTO.getScore()==1){
					key = "negativeComment";
					params = TradeUtil.genBuriedPointMapKey(RequestParamTypeEnum.PROPERTY.getValue(),"1");
				}
				Response<?> response = dataClient.buriedPoint(commentDTO.getSellerId(), key, params, new Date().getTime(), appkey);
				printInvokeService(log,"order comment buriedPoint",response,"");
			}
			
		}catch(Exception e){
			log.error("doOrderCommentBuriedPoint error",e);
		}catch(Throwable e){
			log.error("doOrderCommentBuriedPoint error",e);
		}
	}
	
	public void doRefundBuriedPoint(OrderItemDO orderItemDO,String appkey){
		printIntoService(log,"doRefundBuriedPoint",orderItemDO,"");
		try{
			Response<?>  response = dataClient.buriedPoint(orderItemDO.getSellerId(), "refundOrder", TradeUtil.genBuriedPointMapKey(RequestParamTypeEnum.PROPERTY.getValue(),"1"), new Date().getTime(), appkey);
			printInvokeService(log,"order comment buriedPoint",response,"");
		}catch(Exception e){
			log.error("doRefundBuriedPoint error",e);
		}catch(Throwable e){
			log.error("doRefundBuriedPoint error",e);
		}
	}
	
	public void doRefundFinishedBuriedPoint(OrderItemDO orderItemDO,String appkey){
		printIntoService(log,"doRefundFinishedBuriedPoint",orderItemDO,"");
		try{
			Response<?>  response = dataClient.buriedPoint(orderItemDO.getSellerId(), "refundOrder", TradeUtil.genBuriedPointMapKey(RequestParamTypeEnum.PROPERTY.getValue(),"-1"), new Date().getTime(), appkey);
			printInvokeService(log,"doRefundFinishedBuriedPoint response",response,"");
		}catch(Exception e){
			log.error("doRefundFinishedBuriedPoint error",e);
		}catch(Throwable e){
			log.error("doRefundFinishedBuriedPoint error",e);
		}
	}

	@Override
	public SalesTotalDTO getSalesVolumes(Long itemId) throws TradeException {
		return null;
	}


	@Override
	public Long getItemCount(DataQTO query) throws TradeException {
		printIntoService(log,"getItemCount",query,"");
		return dataDAO.getItemCount(query);
	}

	@Override
	public Long getPaidItemCount(DataQTO query) throws TradeException {
		printIntoService(log,"getPaidItemCount",query,"");
		return dataDAO.getPaidItemCount(query);
	}

	@Override
	public Long getPaidOrderCountBySkuId(Long skuId) throws TradeException {
		DataQTO query = new DataQTO();
		query.setItemSkuId(skuId);
		return dataDAO.getPaidCountBySkuId(query);
	}

	@Override
	public void doPaySuccessBuriedPoint(List<OrderTogetherDTO> orders,String appkey) {
		printIntoService(log,"doPaySuccessBuriedPoint",orders,"");
		try{
			
				if(orders.isEmpty()==false){
					for(OrderTogetherDTO order:orders){
						OrderDO orderDO = order.getOrderDO();
						
						dataClient.buriedPoint(orderDO.getSellerId(), "orderPay", TradeUtil.genBuriedPointMapKey(RequestParamTypeEnum.PROPERTY.getValue(),1+""), new Date().getTime(), appkey);
						dataClient.buriedPoint(orderDO.getSellerId(), "orderPayUser", TradeUtil.genBuriedPointMapKey(RequestParamTypeEnum.PROPERTY.getValue(),orderDO.getUserId()+""), new Date().getTime(), appkey);
						dataClient.buriedPoint(orderDO.getSellerId(), "orderMoney", TradeUtil.genBuriedPointMapKey(RequestParamTypeEnum.PROPERTY.getValue(),String.format("%.2f", ((double) (orderDO.getTotalAmount()) / 100))), new Date().getTime(), appkey);
						dataClient.buriedPoint(orderDO.getSellerId(), "repeatPayUser", TradeUtil.genBuriedPointMapKey(RequestParamTypeEnum.PROPERTY.getValue(),orderDO.getUserId()+""), new Date().getTime(), appkey);
					//top ten 商品
					OrderItemQTO orderItemQTO = new OrderItemQTO();
					orderItemQTO.setOrderId(orderDO.getId());
					orderItemQTO.setUserId(orderDO.getUserId());
					List<OrderItemDO> itemList = orderItemManager.queryOrderItem(orderItemQTO);
					for(OrderItemDO orderItem:itemList){
						
						dataClient.buriedPoint(orderItem.getSellerId(), "itemSales", 
								TradeUtil.genBuriedPointMapKey(RequestParamTypeEnum.OBJECT.getValue(),
										orderItem.getItemId()+"",
										RequestParamTypeEnum.PROPERTY.getValue(),
										orderItem.getNumber()+""), new Date().getTime(), appkey);
						if(null!=orderItem.getItemBrandId()) {
							Response<?> response = dataClient.buriedPoint(orderItem.getSellerId(),
									"itemBrandSales", TradeUtil.genBuriedPointMapKey(RequestParamTypeEnum.OBJECT.getValue(),
											orderItem.getItemBrandId()+"",RequestParamTypeEnum.PROPERTY.getValue(),orderItem.getNumber()+""), new Date().getTime(), appkey);
							printInvokeService(log,"do itemBrandId buriedPoint",response,"");
						}
							
							
						if(null!=orderItem.getCategoryId()){//itemCategorySales
							Response<?> response = dataClient.buriedPoint(orderItem.getSellerId(), "itemCategorySales", 
									TradeUtil.genBuriedPointMapKey(RequestParamTypeEnum.OBJECT.getValue(),
											orderItem.getCategoryId()+"",RequestParamTypeEnum.PROPERTY.getValue(),orderItem.getNumber()+""),  new Date().getTime(), appkey);
							printInvokeService(log,"do categoryId buriedPoint",response,"");
						}
						}
					Response<?> response = dataClient.buriedPoint(orderDO.getSellerId(), "waitDelivery", TradeUtil.genBuriedPointMapKey(RequestParamTypeEnum.PROPERTY.getValue(),"1"), new Date().getTime(), appkey);
					printInvokeService(log,"add waitDelivery buriedPoint",response,"");
					}
				}
		}
		catch(Exception e){
			log.error("doPaySuccessBuriedPoint error",e);
		}catch(Throwable e){
			log.error("doPaySuccessBuriedPoint error",e);
		}
		
	}
	
	@Override
	public Long getSumOrderTotalPrice(DataQTO dataQTO) {
			printIntoService(log,"getSumOrderTotalPrice",dataQTO,"");
			return dataDAO.getSumOrderTotalPrice(dataQTO);
	}
	

}
