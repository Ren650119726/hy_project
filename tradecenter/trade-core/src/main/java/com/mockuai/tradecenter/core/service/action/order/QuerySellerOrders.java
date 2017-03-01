package com.mockuai.tradecenter.core.service.action.order;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.deliverycenter.common.dto.fee.RegionDTO;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.*;
import com.mockuai.tradecenter.common.enums.EnumOrderStatus;
import com.mockuai.tradecenter.common.util.DateUtil;
import com.mockuai.tradecenter.common.util.MoneyUtil;
import com.mockuai.tradecenter.core.dao.TradeConfigDAO;
import com.mockuai.tradecenter.core.domain.*;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.*;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.util.DozerBeanService;
import com.mockuai.tradecenter.core.util.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
@Service
public class QuerySellerOrders implements Action{
	private static final Logger log = LoggerFactory.getLogger(QuerySellerOrders.class);

	private static final int DEFAULT_START = 0;
	private static final int DEFAULT_PAGE_SIZE = 20;

	@Resource
	private OrderManager orderManager;
	@Resource
	private OrderItemManager orderItemManager;
	
	@Resource
	private OrderConsigneeManager orderConsigneeManager;

	@Resource
    private UserManager userManager;
	
	@Resource
	private DeliveryManager deliveryManager;
	@Resource
	OrderPaymentManager orderPaymentManager;
	
	@Resource
	private StoreManager storeManager;
	
	@Resource
	private DozerBeanService  dozerBeanService;
	
	@Resource
	private OrderDiscountInfoManager orderDiscountInfoManager;
	
	@Resource
	private OrderServiceManager orderServiceManager;
	
	@Resource
	private TradeConfigDAO tradeConfigDAO;
	  
	public TradeResponse<List<OrderDTO>> execute(RequestContext context) throws TradeException {
		
//		log.info(" query seller order start ： "+System.currentTimeMillis());
		
		Request request = context.getRequest();
		TradeResponse<List<OrderDTO>> response = null;
		if(request.getParam("orderQTO") == null){
			log.error("orderQTO is null ");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"orderQTO is null");
		}
		
		final String appKey = (String) context.get("appKey");
		final String bizCode = (String) context.get("bizCode");
		OrderQTO orderQTO = (OrderQTO)request.getParam("orderQTO");
		
		if(orderQTO.getOrderStatus()!=null){
			if(EnumOrderStatus.CANCELED.getCode().equals(orderQTO.getOrderStatus()+"")){
				orderQTO.setOrderStatusStr(EnumOrderStatus.CANCELED.getCode()+","+EnumOrderStatus.SELLER_CLOSE.getCode());
			}else{
				orderQTO.setOrderStatusStr(orderQTO.getOrderStatus()+"");
			}
		}
		
		if(null==orderQTO.getCount()){
			orderQTO.setCount(DEFAULT_PAGE_SIZE);
		}
		
		if(null == orderQTO.getOffset()){
			orderQTO.setOffset(DEFAULT_START);
		}
		
		
		List<OrderDO> result ;
		int totalCount = 0;
		try{
			orderQTO.setBizCode(bizCode);
			orderQTO.setDeleteMark(0);
			log.info(" params orderQTO : " + JSONObject.toJSONString(orderQTO));
			/*获取分页的订单数据集合*/
			result = this.orderManager.querySellerOrders(orderQTO);
			// 相同条件的订单总数
			totalCount = this.orderManager.queryUserOrdersCount(orderQTO);
			//// TODO: 2016/9/3

			/**
			 * 获取分享人id
			 * 0903
			 */

		
			List<OrderDTO> module = new ArrayList<OrderDTO>();
			
//			log.info(" 循环体耗时开始： "+System.currentTimeMillis());
			
			for(OrderDO orderDO : result){
				OrderDTO orderDTO = ModelUtil.convert2OrderDTO(orderDO);
				/*log.info("QUERY_SELLER_ORDER_TRACE, orderDTO:{}, orderDO:{}",
						JsonUtil.toJson(orderDTO), JsonUtil.toJson(orderDO));*/

				
				/*商城分佣，用不到*/
				/*if(orderDTO.getMallCommission()==null)
					orderDTO.setMallCommission(0L);*/
				/*店铺收入：订单总金额-商城分佣，用不到*/
				/*orderDTO.setShopIncome(orderDTO.getTotalAmount()-orderDTO.getMallCommission());*/
				
				/*订单金额字符格式，分为单位*/
				orderDTO.setTotalAmountStr(MoneyUtil.getMoneyStr(orderDTO.getTotalAmount()));
				/*订单下单时间字符格式，yyyy-MM-dd HH:mm:ss*/
				orderDTO.setOrderTimeStr(DateUtil.getDateTime( "yyyy-MM-dd HH:mm:ss",orderDTO.getOrderTime()));
				
				/*订单商品返回列表*/
				List<OrderItemDO> orderItems = Collections.EMPTY_LIST;
				/*查询订单商品信息*/
				OrderItemQTO orderItemQTO = new OrderItemQTO();
				orderItemQTO.setOrderId(orderDTO.getId());
				orderItemQTO.setUserId(orderDTO.getUserId());

				orderItems = this.orderItemManager.queryOrderItem(orderItemQTO);

	
				if(orderItems!=null && orderItems.size()>0){

					orderDTO.setOrderItems(ModelUtil.convert2OrderItemDTOListForRefund(orderItems));
					// sellerid sellername 嗨云用不到
					//根据seller_id归类订单商品，填充seller_name
					//fillSellerInfo(orderDTO.getOrderItems(), appKey);
				}else{
					orderDTO.setOrderItems(new ArrayList<OrderItemDTO>());
				}
				
				OrderConsigneeDO orderConsigneeDO = null;
			
				//查询订单收货地址信息
				orderConsigneeDO = orderConsigneeManager.getOrderConsignee(orderDO.getId(), orderDO.getUserId());
						    
				if(orderConsigneeDO == null){
					//TODO error handle
					log.warn(" order_id :{} orderConsigneeDO is null ",orderDO.getId());
				}else{
					OrderConsigneeDTO orderConsigneeDTO = ModelUtil.convert2OrderConsigneeDTO(orderConsigneeDO);
					/*填充收货地址区域信息*/
					orderConsigneeDTO = fillOrderConsigneeRegion(orderConsigneeDTO,appKey);
					orderDTO.setOrderConsigneeDTO(orderConsigneeDTO);
				}
							
				// 订单增值服务 嗨云暂时用不到
				//填充订单商品服务信息
				/*if(null!=orderDTO.getOrderItems()&&orderDTO.getOrderItems().size()>0){
					for(OrderItemDTO orderItemDTO:orderDTO.getOrderItems()){
						List<OrderServiceDO> orderServiceDOList = orderServiceManager.queryOrderService(orderItemDTO.getOrderId(), orderItemDTO.getId());
						if(null!=orderServiceDOList){
							List<OrderServiceDTO> orderServiceDTOList = dozerBeanService.coverList(orderServiceDOList, OrderServiceDTO.class);
							orderItemDTO.setOrderServiceList(orderServiceDTOList);
						}
					}
				}*/
	
				/*订单导出时用到 start*/
				
				/*订单的支付单信息*/
				OrderPaymentDO orderPaymentDO = orderPaymentManager.getOrderPayment(orderDO.getId(), orderDO.getUserId());

				OrderPaymentDTO orderPaymentDTO = ModelUtil.convert2OrderPaymentDTO(orderPaymentDO);
				orderDTO.setOrderPaymentDTO(orderPaymentDTO);
				
				/*获取订单的优惠信息*/
				List<OrderDiscountInfoDO> orderDiscountInfoDOs =
						orderDiscountInfoManager.queryOrderDiscountInfo(orderDO.getId(), orderDO.getUserId());
				
				/*订单优惠信息填充到订单对象*/
				orderDTO.setOrderDiscountInfoDTOs(ModelUtil.convert2OrderDiscountInfoDTOList(orderDiscountInfoDOs));
				
				/*订单导出时用到 end*/
				
				
				/*代金券折扣金额*/
				/*Long voucherDiscountAmount = getVouchersDiscountAmountDiscountAmount(orderDiscountInfoDOs);*/
				/*积分折扣金额*/
				/*Long pointDiscountAmount = getPointDiscountAmount(orderDiscountInfoDOs);
				if(null==pointDiscountAmount){
					pointDiscountAmount = 0L;
				}*/
				/*优惠券折扣金额*/
				/*Long couponsDiscountAmount = orderDO.getDiscountAmount()-voucherDiscountAmount-pointDiscountAmount;
				orderDTO.setPointDiscountAmount(pointDiscountAmount);
				orderDTO.setVouchersDiscountAmount(voucherDiscountAmount);
				orderDTO.setCouponsDiscountAmount(couponsDiscountAmount);*/
				/*积分*/
				/*orderDTO.setPoint(orderDO.getPoint());
				if(null==orderDO.getPoint()){
					orderDTO.setPoint(0L);
				}				
				Long mallCouponDiscountAmt = getMallCouponDiscountAmount(orderDiscountInfoDOs);
				Long mallAllDiscountAmt = mallCouponDiscountAmt+pointDiscountAmount+voucherDiscountAmount;
				Long shopAllDiscountAmt = orderDTO.getDiscountAmount()-mallAllDiscountAmt;
				orderDTO.setShopAllDiscountAmount(shopAllDiscountAmt);
				orderDTO.setMallAllDiscountAmount(mallAllDiscountAmt);*/
				
				/* TODO 门店信息，可不管*/
				/*OrderStoreDO orderStore = storeManager.getOrderStore(orderDO.getId());
				if( null!= orderStore ){
					OrderStoreDTO orderStoreDTO = dozerBeanService.cover(orderStore, OrderStoreDTO.class);
					orderDTO.setOrderStoreDTO(orderStoreDTO);
				}*/				
				
				module.add(orderDTO);
			}
			
//			log.info(" 循环体耗时结束： "+System.currentTimeMillis());
	
			response = ResponseUtils.getSuccessResponse(module);
			response.setTotalCount(totalCount); // 总记录数
			
//			log.info(" query seller order end :"+System.currentTimeMillis());
		
		}catch(TradeException e){
			log.error("db error: " ,e);
			response = ResponseUtils.getFailResponse(e.getResponseCode());
			return response;
		}
		
		return response;
		
	}
	
	/**
	 * 填充收货地址区域信息
	 * @param orderConsigneeDTO
	 * @return
	 */
	private OrderConsigneeDTO fillOrderConsigneeRegion(OrderConsigneeDTO orderConsigneeDTO,String appkey) {

		try{
			//查询区域信息，并填充收货地址中的区域名称
			Set<String> regionCodes = new HashSet<String>();
			regionCodes.add(orderConsigneeDTO.getCountryCode());
			regionCodes.add(orderConsigneeDTO.getProvinceCode());
			regionCodes.add(orderConsigneeDTO.getCityCode());
			regionCodes.add(orderConsigneeDTO.getAreaCode());
			regionCodes.add(orderConsigneeDTO.getTownCode());
			List<RegionDTO> regionDTOs = deliveryManager.queryRegion(new ArrayList<String>(regionCodes),appkey);
			Map<String,RegionDTO> regionDTOMap = new HashMap<String, RegionDTO>();
			for(RegionDTO regionDTO: regionDTOs){
				regionDTOMap.put(regionDTO.getCode(), regionDTO);
			}

			if(regionDTOMap.containsKey(orderConsigneeDTO.getCountryCode())){
				orderConsigneeDTO.setCountry(regionDTOMap.get(orderConsigneeDTO.getCountryCode()).getName());
			}

			if(regionDTOMap.containsKey(orderConsigneeDTO.getProvinceCode())){
				orderConsigneeDTO.setProvince(regionDTOMap.get(orderConsigneeDTO.getProvinceCode()).getName());
			}

			if(regionDTOMap.containsKey(orderConsigneeDTO.getCityCode())){
				orderConsigneeDTO.setCity(regionDTOMap.get(orderConsigneeDTO.getCityCode()).getName());
			}

			if(regionDTOMap.containsKey(orderConsigneeDTO.getAreaCode())){
				orderConsigneeDTO.setArea(regionDTOMap.get(orderConsigneeDTO.getAreaCode()).getName());
			}

			if(regionDTOMap.containsKey(orderConsigneeDTO.getTownCode())){
				orderConsigneeDTO.setTown(regionDTOMap.get(orderConsigneeDTO.getTownCode()).getName());
			}
		}catch (TradeException e){
			//TODO error handle
		}

		return orderConsigneeDTO;
	}

	private void fillSellerInfo(List<OrderItemDTO> orderItemDTOs, String appKey) throws TradeException{
		Map<Long,List<OrderItemDTO>> orderItemDTOMap = new HashMap<Long, List<OrderItemDTO>>();
		/*根据seller_id对同一订单下不同店铺卖家的商品归类*/
		for(OrderItemDTO orderItemDTO: orderItemDTOs){
			if(orderItemDTOMap.containsKey(orderItemDTO.getSellerId())){
				orderItemDTOMap.get(orderItemDTO.getSellerId()).add(orderItemDTO);
			}else{
				List<OrderItemDTO> orderItemDTOList = new ArrayList<OrderItemDTO>();
				orderItemDTOList.add(orderItemDTO);
				orderItemDTOMap.put(orderItemDTO.getSellerId(), orderItemDTOList);
			}
		}
		/*根据seller_id归类号的集合填充卖家姓名*/
		for(Map.Entry<Long,List<OrderItemDTO>> entry: orderItemDTOMap.entrySet()){
			Long sellerId = entry.getKey();
			String sellerName = userManager.getUserName(sellerId, appKey);
			List<OrderItemDTO> orderItemDTOList = entry.getValue();
			for(OrderItemDTO orderItemDTO: orderItemDTOList){
				orderItemDTO.setSellerName(sellerName);
			}
		}
	}
	
	private Long getMallCouponDiscountAmount(List<OrderDiscountInfoDO> orderDiscountInfoDOs){
		if(orderDiscountInfoDOs == null){
            return 0L;
        }
		 long totalDiscountAmount = 0L;
	        for(OrderDiscountInfoDO orderDiscountInfoDO: orderDiscountInfoDOs){
	        	if(orderDiscountInfoDO.getDiscountType()==1&&
	        			orderDiscountInfoDO.getDiscountCode().equals("SYS_MARKET_TOOL_000001")){
	        		totalDiscountAmount += orderDiscountInfoDO.getDiscountAmount();
	        	}
	        }
	        return totalDiscountAmount;
	}
	
	//代金券优惠额
	  private Long getVouchersDiscountAmountDiscountAmount(List<OrderDiscountInfoDO> orderDiscountInfoDOs){
	        if(orderDiscountInfoDOs == null){
	            return 0L;
	        }

	        long totalDiscountAmount = 0L;
	        for(OrderDiscountInfoDO orderDiscountInfoDO: orderDiscountInfoDOs){
	        	if(orderDiscountInfoDO.getDiscountType()==2&&
	        			orderDiscountInfoDO.getDiscountCode().equals("1")){
	        		totalDiscountAmount += orderDiscountInfoDO.getDiscountAmount();
	        	}
	        }
	        return totalDiscountAmount;
	    }
	  
	  
	  
	  private Long getPointDiscountAmount(List<OrderDiscountInfoDO> orderDiscountInfoDOs){
			if(orderDiscountInfoDOs == null){
	            return 0L;
	        }

	        long totalDiscountAmount = 0L;
	        for(OrderDiscountInfoDO orderDiscountInfoDO: orderDiscountInfoDOs){
	        	if(orderDiscountInfoDO.getDiscountType()==2&&
	        			orderDiscountInfoDO.getDiscountCode().equals("2")){
	        		totalDiscountAmount += orderDiscountInfoDO.getDiscountAmount();
	        	}
	        }
	        return totalDiscountAmount;
		}

		private Long getShareUserId(){
			return null;
		}


	@Override
	public String getName() {
		return ActionEnum.QUERY_SELLER_ORDER.getActionName();
	}
}
