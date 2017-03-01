package com.mockuai.tradecenter.core.service.action.order;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.deliverycenter.common.dto.fee.RegionDTO;
import com.mockuai.itemcenter.common.constant.ItemType;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderConsigneeDTO;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.common.domain.OrderServiceDTO;
import com.mockuai.tradecenter.common.domain.OrderStoreDTO;
import com.mockuai.tradecenter.common.domain.TradeConfigQTO;
import com.mockuai.tradecenter.common.domain.distributor.DistributorOrderItemDTO;
import com.mockuai.tradecenter.common.enums.EnumOrderStatus;
import com.mockuai.tradecenter.common.enums.EnumOrderType;
import com.mockuai.tradecenter.common.enums.EnumPaymentMethod;
import com.mockuai.tradecenter.common.enums.EnumRefundStatus;
import com.mockuai.tradecenter.common.util.DateUtil;
import com.mockuai.tradecenter.core.dao.TradeConfigDAO;
import com.mockuai.tradecenter.core.domain.OrderConsigneeDO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderDiscountInfoDO;
import com.mockuai.tradecenter.core.domain.OrderInvoiceDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.domain.OrderPaymentDO;
import com.mockuai.tradecenter.core.domain.OrderServiceDO;
import com.mockuai.tradecenter.core.domain.OrderStoreDO;
import com.mockuai.tradecenter.core.domain.TradeConfigDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.DeliveryManager;
import com.mockuai.tradecenter.core.manager.ItemManager;
import com.mockuai.tradecenter.core.manager.OrderConsigneeManager;
import com.mockuai.tradecenter.core.manager.OrderDiscountInfoManager;
import com.mockuai.tradecenter.core.manager.OrderInvoiceManager;
import com.mockuai.tradecenter.core.manager.OrderItemManager;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.manager.OrderPaymentManager;
import com.mockuai.tradecenter.core.manager.OrderServiceManager;
import com.mockuai.tradecenter.core.manager.ShopManager;
import com.mockuai.tradecenter.core.manager.StoreManager;
import com.mockuai.tradecenter.core.manager.UserManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.util.DozerBeanService;
import com.mockuai.tradecenter.core.util.ModelUtil;
import com.mockuai.tradecenter.core.util.TradeUtil;

@Service
public class GetOrder implements Action{
	private static final Logger log = LoggerFactory.getLogger(GetOrder.class);
	
	@Resource 
	private OrderManager orderManager;
	
	@Resource
	private OrderItemManager orderItemManager;

	@Resource
	private OrderConsigneeManager orderConsigneeManager;

	@Resource
	private OrderDiscountInfoManager orderDiscountInfoManager;

	@Resource
	private OrderInvoiceManager orderInvoiceManager;
	@Resource
	private DeliveryManager deliveryManager;
	@Resource
	private OrderPaymentManager orderPaymentManager;
	@Resource
	private UserManager userManager;
	
	@Resource
	private ShopManager shopManager;
	
	@Resource
	private StoreManager storeManager;
	 
	@Resource
	private DozerBeanService  dozerBeanService;
	
	@Resource
	private OrderServiceManager orderServiceManager;
	
	@Resource
	ItemManager itemManager;
	
	@Resource
	private TradeConfigDAO tradeConfigDAO;

	public TradeResponse<OrderDTO> execute(RequestContext context) throws TradeException {
		
		log.info(" get order start ");
		
		Request request = context.getRequest();
		TradeResponse<OrderDTO> response = null;
		
		/*if(request.getParam("userId") == null){
			log.error("userId is null ");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"userId is null");
		}*/
		if(request.getParam("orderId") == null){
			log.error("orderId is null ");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"orderId is null");
		}
		
		Long orderId = (Long) request.getParam("orderId");
		Long userId = (Long) request.getParam("userId");
		String appKey = (String) context.get("appKey");
		
		String bizCode = (String) context.get("bizCode");
		String openRightsKey = "open_rights_mark";
		
		
		OrderDO orderDO = null;
		List<OrderItemDO> orderItems = Collections.EMPTY_LIST;
		OrderConsigneeDO orderConsigneeDO = null;
		try{
			orderDO = this.orderManager.getOrder(orderId, userId);

			if(orderDO == null){
				log.warn("order doesn't exist, orderId={}, userId={}", orderId, userId);
				return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST,"order doesn't exist");
			}

			OrderDTO orderDTO = new OrderDTO();
			orderDTO = ModelUtil.convert2OrderDTO(orderDO);

			/*待支付状态设置订单超时时间*/
			if(EnumOrderStatus.UNPAID.getCode().equals(orderDTO.getOrderStatus()+"")){
				/*订单取消时间：分*/
				Long payTimeOut=0l ;
				/*获取系统配置的订单取消时间*/
				TradeConfigQTO query = new TradeConfigQTO();
				query.setAttrKey("cancel_timeout_minutes");
				query.setBizCode(bizCode);
				List<TradeConfigDO> tradeConfigDOs = tradeConfigDAO.queryTradeConfig(query);
				try {
					if(CollectionUtils.isEmpty(tradeConfigDOs)){
						log.error("tradeConfigDOs is null or empty");
					}else{
						if(tradeConfigDOs.get(0)== null){
							log.error("TradeConfigDO is null or empty");
						}
					}
					
					payTimeOut = Long.parseLong(tradeConfigDOs.get(0).getAttrValue());
				} catch (Exception e) {
					log.error("cancel_timeout_minutes格式转换错误");
				}
				/*服务器时间和下单时间的时间差*/
				if(new Date().before(orderDO.getOrderTime())){
					log.error("下单时间["+DateUtil.convertDateToString("yyyy-MM-dd HH:mm:ss", orderDO.getOrderTime())+"]大于服务器时间["+DateUtil.convertDateToString("yyyy-MM-dd HH:mm:ss", new Date())+"]");
				}
				/*下单时间到现在的秒数*/
		        long sec =(new Date().getTime()-orderDO.getOrderTime().getTime())/1000;  
		        
		        int remainSec = (int)(payTimeOut*60-sec) ;
		        /*倒计时大于0且非注册礼包的订单返回相关的倒计时时间*/
		        if(remainSec > 0 && orderDO.getType() != EnumOrderType.GIFT_PACK.getCode() ){
					/*订单取消时间剩余秒数*/
		        	orderDTO.setPayTimeout(remainSec);
			        /*订单取消时间点*/
			        orderDTO.setCancelOrderTime(dateAddMinute(orderDO.getOrderTime(),payTimeOut));
		        }
		        /*订单倒计时时间到非注册礼包订单的订单状态假改为已取消*/
		        if(remainSec <= 0 && orderDO.getType() != EnumOrderType.GIFT_PACK.getCode() ){
		        	orderDTO.setOrderStatus(Integer.parseInt(EnumOrderStatus.CANCELED.getCode()));
		        }
			}
			
			if(orderDTO.getMallCommission()==null){
				orderDTO.setMallCommission(0L);
			}

			orderDTO.setShopIncome(orderDTO.getTotalAmount()-orderDTO.getMallCommission());
			
			// 查询订单明细
			OrderItemQTO orderItemQTO = new OrderItemQTO();
			orderItemQTO.setOrderId(orderId);
			orderItemQTO.setUserId(orderDO.getUserId());
			orderItems = this.orderItemManager.queryOrderItem(orderItemQTO);
			
			TradeConfigDO tradeOpenRightsConfig = tradeConfigDAO.getTradeConfig(bizCode, openRightsKey);
			Integer opemRefundMark = 0;
			if(null!=tradeOpenRightsConfig&&tradeOpenRightsConfig.getAttrValue().equals("1")){
				opemRefundMark = 1;
			}

			TradeConfigDO tradeRightsTimeOutConfig = tradeConfigDAO.getTradeConfig(bizCode, "rights_timeout_days");
			Long rightsTimeoutDays = 15l;
			if(null!=tradeRightsTimeOutConfig){
				rightsTimeoutDays = Long.parseLong(tradeRightsTimeOutConfig.getAttrValue());
			}
			

//			List<DistributorOrderItemDTO> distributorOrderItemList = new ArrayList<DistributorOrderItemDTO>();
//			Map<Long, List<OrderItemDTO>> distributorItemMap = new HashMap<Long, List<OrderItemDTO>>();
//			Map<Long, String> distributorNameMap = new HashMap<Long, String>();
			
			if(orderItems==null || orderItems.isEmpty()){
				//TODO 
				log.warn("订单 orderSn:"+orderDTO.getOrderSn()+"商品信息为空，确认是否为分仓分单的订单 originalOrder:"+orderDO.getOriginalOrder());
			}else{
//				List<OrderItemDTO> orderItemDTOList = ModelUtil.convert2OrderItemDTOListForRefund(orderItems);
				List<OrderItemDTO> orderItemDTOList = ModelUtil.convert2OrderItemDTOList(orderItems);
				for(OrderItemDTO orderItemDTO:orderItemDTOList){
					List<OrderServiceDO> orderServiceDOList = orderServiceManager.queryOrderService(orderId, orderItemDTO.getId());
					if(null!=orderServiceDOList){
						List<OrderServiceDTO> orderServiceDTOList = dozerBeanService.coverList(orderServiceDOList, OrderServiceDTO.class);
						orderItemDTO.setOrderServiceList(orderServiceDTOList);
					}
					
					/*订单确认时间到现在的秒数*/
			        long receiptSec = 0l;
			        if(orderDO.getReceiptTime()!= null){
			        	receiptSec = (new Date().getTime()-orderDO.getReceiptTime().getTime())/1000;
			        }
			        
					/*未支付，买家取消，卖家取消，订单已完成(超过维权期receiptSec>=(rightsTimeoutDays*24*60*60))，礼包订单,秒杀商品,实付金额为0,商品实付金额为0，嗨币支付的不允许售后*/
					if( EnumOrderStatus.FINISHED.getCode().equals(orderDTO.getOrderStatus()+"") || EnumOrderStatus.UNPAID.getCode().equals(orderDTO.getOrderStatus()+"") 
							|| EnumOrderStatus.SELLER_CLOSE.getCode().equals(orderDTO.getOrderStatus()+"") || EnumOrderStatus.CANCELED.getCode().equals(orderDTO.getOrderStatus()+"") 
							|| EnumPaymentMethod.HI_COIN_PAY.getCode().equals(orderDTO.getPaymentId()+"") || EnumOrderType.GIFT_PACK.getCode() == orderDTO.getType() 
									|| ItemType.SECKILL.getType() == orderItemDTO.getItemType() || new Long(0).equals(orderDTO.getTotalAmount())
									|| new Long(0).equals(orderItemDTO.getPaymentAmount()) ){
//						log.info(" setCanRefundMark 0 ");
						orderItemDTO.setCanRefundMark(0);
					}else{
//						log.info(" setCanRefundMark start ");
						orderItemDTO.setCanRefundMark(TradeUtil.getCanRefundMark(opemRefundMark,orderItemDTO.getRefundStatus(), 
								orderDTO.getReceiptTime(),rightsTimeoutDays));
//						log.info(" setCanRefundMark end ");
					}					
					
					/* TODO 已发货和已签收的商品退款状态置空，前台不显示之前的售后信息*/
					/*if(EnumOrderStatus.DELIVERIED.getCode().equals(orderDTO.getOrderStatus()+"") || EnumOrderStatus.SIGN_OFF.getCode().equals(orderDTO.getOrderStatus()+"")){
						if(EnumRefundStatus.REFUSE.getCode().equals(orderItemDTO.getRefundStatus()+"")){
							orderItemDTO.setRefundStatus(null);
						}
					}*/
					
					/*分销商商品信息分类*/
					/*if(distributorItemMap.containsKey(orderItemDTO.getDistributorId()) == false){
						distributorItemMap.put(orderItemDTO.getDistributorId(), new ArrayList<OrderItemDTO>());
						distributorNameMap.put(orderItemDTO.getDistributorId(), orderItemDTO.getDistributorName());	
					}
					distributorItemMap.get(orderItemDTO.getDistributorId()).add(orderItemDTO);	*/		
				}
				/*遍历组装分销商商品信息*/			
				/*int i = 1;
		        for(Map.Entry<Long, List<OrderItemDTO>> entry:distributorItemMap.entrySet()){
					DistributorOrderItemDTO distributorOrderItemDTO = new DistributorOrderItemDTO();
//		        	log.info(" for distributorItemMap DistributorId ["+ i++ +"]: "+entry.getKey());
		        	distributorOrderItemDTO.setDistributorId(entry.getKey());
//		        	log.info(" for distributorItemMap DistributorName ["+ i +"]: "+distributorNameMap.get(entry.getKey()));
		        	distributorOrderItemDTO.setDistributorName(distributorNameMap.get(entry.getKey()));
		        	distributorOrderItemDTO.setOrderItemList(entry.getValue());
//		        	log.info(" distributorOrderItemDTO : "+JSONObject.toJSONString(distributorOrderItemDTO));
			        distributorOrderItemList.add(distributorOrderItemDTO);
		        }*/
		        
//		        log.info(" distributorOrderItemList : "+JSONObject.toJSONString(distributorOrderItemList)); 
		        
//		        orderDTO.setDistributorOrderItemList(distributorOrderItemList);
				
				orderDTO.setOrderItems(orderItemDTOList);			

				//填充订单商品卖家信息
//				fillSellerInfo(orderDTO.getOrderItems(), appKey);
				
				//填充sku信息
				fillItemSku(orderDTO.getSellerId(),orderDTO.getOrderItems(),appKey);
			}
			

			//查询订单收货地址信息
			orderConsigneeDO = orderConsigneeManager.getOrderConsignee(orderId, userId);

			if(orderConsigneeDO == null){
				//TODO error handle
			}else{
				OrderConsigneeDTO orderConsigneeDTO = ModelUtil.convert2OrderConsigneeDTO(orderConsigneeDO);
				
				orderConsigneeDTO = fillOrderConsigneeRegion(orderConsigneeDTO,appKey);
				orderDTO.setOrderConsigneeDTO(orderConsigneeDTO);
			}

			if(orderDO.getInvoiceMark().intValue() == 1){
				//TODO 查询订单报销信息
				OrderInvoiceDO orderInvoiceDO = orderInvoiceManager.getOrderInvoice(orderId, userId);
				if(orderInvoiceDO == null){
					//TODO error handle
				}else{
					orderDTO.setOrderInvoiceDTO(ModelUtil.convert2OrderInvoiceDTO(orderInvoiceDO));
				}
			}

			//TODO 查询优惠信息列表
			List<OrderDiscountInfoDO> orderDiscountInfoDOs =
					orderDiscountInfoManager.queryOrderDiscountInfo(orderId, userId);
			orderDTO.setOrderDiscountInfoDTOs(ModelUtil.convert2OrderDiscountInfoDTOList(orderDiscountInfoDOs));

			//代金券优惠额度
			Long voucherDiscountAmount = getVouchersDiscountAmountDiscountAmount(orderDiscountInfoDOs);
			
			Long pointDiscountAmount = getPointDiscountAmount(orderDiscountInfoDOs);
			if(null==pointDiscountAmount){
				pointDiscountAmount = 0L;
			}
			Long couponsDiscountAmount = orderDO.getDiscountAmount()-voucherDiscountAmount-pointDiscountAmount;

			orderDTO.setPointDiscountAmount(pointDiscountAmount);
			orderDTO.setVouchersDiscountAmount(voucherDiscountAmount);
			orderDTO.setCouponsDiscountAmount(couponsDiscountAmount);
			orderDTO.setPoint(orderDO.getPoint());
			
			//TODO 查询订单物流信息
//			List<OrderDeliveryInfoDTO> orderDeliveryInfoDTOs =
//					deliveryManager.queryDeliveryInfo(orderDO.getId(), orderDO.getUserId(),appKey);
//			orderDTO.setOrderDeliveryInfoDTOs(orderDeliveryInfoDTOs);

			//TODO 查询订单支付信息
			OrderPaymentDO orderPaymentDO = orderPaymentManager.getOrderPayment(orderId, userId);
			orderDTO.setOrderPaymentDTO(ModelUtil.convert2OrderPaymentDTO(orderPaymentDO));

			/*shop相关，嗨云用不到*/
			/*ShopDTO shop = null;
			try{
				shop = shopManager.getShopDTO(orderDO.getSellerId(), appKey);
			}catch(Exception e){
				log.error("get shop error, orderId={}, userId={}, sellerId={}",
						orderId, userId, orderDO.getSellerId(), e);
			}
			if(null!=shop){
				orderDTO.setShopId(shop.getId());
				orderDTO.setShopName(shop.getShopName());
			}*/
			
			/*门店信息嗨云用不到*/
			/*OrderStoreDO orderStore = storeManager.getOrderStore(orderId);
			if( null!= orderStore ){
				OrderStoreDTO orderStoreDTO = dozerBeanService.cover(orderStore, OrderStoreDTO.class);
				orderDTO.setOrderStoreDTO(orderStoreDTO);
			}*/

//			log.info(" getorder orderDTO : " + JSONObject.toJSONString(orderDTO));
			
			log.info("get order end, orderId:{}, userId:{}", orderId, userId);
			response = ResponseUtils.getSuccessResponse(orderDTO);
			return response;
		}catch(TradeException e){
			log.error("getOrder error: ",e);
			return ResponseUtils.getFailResponse(e.getResponseCode());
		}
	}
	
	private void fillItemSku(Long sellerId,List<OrderItemDTO> orderItemDTOs,String appKey)throws TradeException{
		List<Long> skuIdList = new ArrayList<Long>();
		List<Long> itemIdList = new ArrayList<Long>();
		  if(orderItemDTOs!=null&&orderItemDTOs.isEmpty()==false){
			  for(OrderItemDTO orderItemDTO:orderItemDTOs){
				  skuIdList.add(orderItemDTO.getItemSkuId());
					itemIdList.add(orderItemDTO.getItemId());
			  }
			  
			  
			  //TODO 兼容 下单后sku被删除的情况
			  List<ItemSkuDTO> itemSkuList = this.itemManager.queryItemSku(skuIdList, sellerId, appKey);
	          Map<Long, ItemSkuDTO> itemSkuMap = new HashMap<Long,ItemSkuDTO>();
	          // 将商品平台查询返回的商品信息封装为 Map 方便处理
	          if(itemSkuList!=null&&itemSkuList.size()>0){
	        	  for (ItemSkuDTO itemSku : itemSkuList) {
		              itemSkuMap.put(itemSku.getId(), itemSku);
		          }
	          }
	         
	          for(OrderItemDTO orderItemDTO:orderItemDTOs){
	        	  ItemSkuDTO itemSku = itemSkuMap.get(orderItemDTO.getItemSkuId());
//
	        	  if(null!=itemSku){
	        		  orderItemDTO.setSku(dozerBeanService.cover(itemSku, com.mockuai.tradecenter.common.domain.ItemSkuDTO.class));
	        	  }
	          }
			  
			  
			  
		  }
		 

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
	
	
	/*private void fillSellerInfo(List<OrderItemDTO> orderItemDTOs, String appKey) throws TradeException{
		Map<Long,List<OrderItemDTO>> orderItemDTOMap = new HashMap<Long, List<OrderItemDTO>>();
		for(OrderItemDTO orderItemDTO: orderItemDTOs){
			if(orderItemDTOMap.containsKey(orderItemDTO.getSellerId())){
				orderItemDTOMap.get(orderItemDTO.getSellerId()).add(orderItemDTO);
			}else{
				List<OrderItemDTO> orderItemDTOList = new ArrayList<OrderItemDTO>();
				orderItemDTOList.add(orderItemDTO);
				orderItemDTOMap.put(orderItemDTO.getSellerId(), orderItemDTOList);
			}
		}

		for(Map.Entry<Long,List<OrderItemDTO>> entry: orderItemDTOMap.entrySet()){
			Long sellerId = entry.getKey();
			String sellerName = userManager.getUserName(sellerId, appKey);
			List<OrderItemDTO> orderItemDTOList = entry.getValue();
			for(OrderItemDTO orderItemDTO: orderItemDTOList){
				orderItemDTO.setSellerName(sellerName);
			}
		}
	}*/
	
	/**
	 * date类型增加秒数
	 * @param date
	 * @param sec
	 * @return
	 */
	private Date dateAddSec(Date date,Long sec){
		GregorianCalendar cal = new GregorianCalendar();  
        cal.setTime(date);  
        cal.add(Calendar.SECOND, sec.intValue());  
		return cal.getTime();
	}
	
	/**
	 * date类型增加分钟
	 * @param date 起始时间
	 * @param sec 分钟数 
	 * @return
	 */
	private Date dateAddMinute(Date date,Long minu){
		GregorianCalendar cal = new GregorianCalendar();  
        cal.setTime(date);  
        cal.add(Calendar.MINUTE, minu.intValue());  
		return cal.getTime();
	}
	
	@Override
	public String getName() {
		return ActionEnum.GET_ORDER.getActionName();
	}
}
