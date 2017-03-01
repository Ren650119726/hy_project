package com.mockuai.tradecenter.core.service.action.order;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.deliverycenter.common.dto.fee.RegionDTO;
import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderConsigneeDTO;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.common.domain.TradeConfigQTO;
import com.mockuai.tradecenter.common.domain.distributor.DistributorOrderItemDTO;
import com.mockuai.tradecenter.common.enums.EnumOrderStatus;
import com.mockuai.tradecenter.common.enums.EnumOrderType;
import com.mockuai.tradecenter.common.util.DateUtil;
import com.mockuai.tradecenter.core.dao.TradeConfigDAO;
import com.mockuai.tradecenter.core.domain.OrderConsigneeDO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderDiscountInfoDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.domain.TradeConfigDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.DeliveryManager;
import com.mockuai.tradecenter.core.manager.OrderConsigneeManager;
import com.mockuai.tradecenter.core.manager.OrderDiscountInfoManager;
import com.mockuai.tradecenter.core.manager.OrderItemManager;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.util.ModelUtil;

public class QueryUserOrders implements Action{
	private static final Logger log = LoggerFactory.getLogger(QueryUserOrders.class);

	private static final int DEFAULT_START = 0;
	private static final int DEFAULT_PAGE_SIZE = 20;
	
	@Resource 
	private OrderManager orderManager;
	@Resource
	private OrderItemManager orderItemManager;
	@Resource
	private TradeConfigDAO tradeConfigDAO;
	@Resource
	private DeliveryManager deliveryManager;
	
	@Resource
	private OrderConsigneeManager orderConsigneeManager;
	
	@Resource
	private OrderDiscountInfoManager orderDiscountInfoManager;

	public TradeResponse<List<OrderDTO>> execute(RequestContext context) throws TradeException {
		
		log.info(" Query User Orders start ");
		
		Request request = context.getRequest();
		TradeResponse<List<OrderDTO>> response = null;
		if(request.getParam("orderQTO") == null){
			log.error("orderQTO is null ");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"orderQTO is null");
		}
		/*String appDomain = (String) context.get("appDomain");*/
		OrderQTO orderQTO = (OrderQTO)request.getParam("orderQTO");
		
		String bizCode = (String) context.get("bizCode");
		String appKey = (String) context.get("appKey");
		//入参校验
		//检查userId
		if(orderQTO.getUserId() == null){
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"userId is null");
		}

		//检查分页参数
		if(orderQTO.getOffset() == null){
			orderQTO.setOffset(0);
		}else if(orderQTO.getOffset() < 0){
			orderQTO.setOffset(0);
		}

		//分页查询每次查询记录数默认值和最大值皆为100
		if(orderQTO.getCount() == null){
			orderQTO.setCount(100);
		}else if(orderQTO.getCount().intValue() > 100){
			orderQTO.setCount(100);
		}

		//查询指定订单列表
		List<OrderDO> result = null;
		int totalCount = 0;
		try{
			orderQTO.setBizCode(bizCode);
			orderQTO.setDeleteMark(0);
			/*老接口*/
			/*result = this.orderManager.queryUserOrders(orderQTO);*/
			
			/*新接口*/
			result = this.orderManager.queryUserOrdersUpgrade(orderQTO);
			
			// TODO 前端不展示，考虑性能，暂时屏蔽 相同条件的总数 
			/*totalCount = this.orderManager.queryUserOrdersCount(orderQTO);*/
		}catch(TradeException e){
			log.error("db error: " ,e);
			response = ResponseUtils.getFailResponse(e.getResponseCode());
			return response;
		}

		/*订单取消时间：分*/
		Long payTimeOut= 0l ;
		if(orderQTO.getOrderStatus()==null || EnumOrderStatus.UNPAID.getCode().equals(orderQTO.getOrderStatus()+"")){
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
//				return ResponseUtils.getFailResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION, "订单支付超时取消时间格式有误");
			}
		}
		
		List<OrderDTO> orderDTOs = new ArrayList<OrderDTO>();
		List<Long> orderIdList = new ArrayList<Long>();
		OrderDTO orderDTOtmp = new OrderDTO();
		for(OrderDO orderDO : result){
			/*OrderDTO orderDTO = new OrderDTO();
			TradeUtils.convert2DTO(orderDO, orderDTO);*/
			orderDTOtmp = ModelUtil.convert2OrderDTO(orderDO);
			
			/*待支付状态相关处理*/
			if(EnumOrderStatus.UNPAID.getCode().equals(orderDO.getOrderStatus()+"")){
				/*服务器时间和下单时间的时间差*/
				if(new Date().before(orderDO.getOrderTime())){
					log.error("下单时间["+DateUtil.convertDateToString("yyyy-MM-dd HH:mm:ss", orderDO.getOrderTime())+"]大于服务器时间["+DateUtil.convertDateToString("yyyy-MM-dd HH:mm:ss", new Date())+"]");
				}
				/*下单时间距离现在的秒数*/
		        long sec =(new Date().getTime()-orderDO.getOrderTime().getTime())/1000;   

		        int remainSec = (int)(payTimeOut*60-sec) ;
		        /*倒计时大于0且非注册礼包的订单返回相关的倒计时时间*/
		        if(remainSec > 0 && orderDO.getType() != EnumOrderType.GIFT_PACK.getCode()){
					/*订单取消时间剩余秒数*/
					orderDTOtmp.setPayTimeout(remainSec);
					/*订单取消时间点*/
					orderDTOtmp.setCancelOrderTime(dateAddMinute(orderDO.getOrderTime(),payTimeOut));	
		        }
		        /*订单倒计时时间到非注册礼包订单的订单状态假改为已取消*/
		        if(remainSec <= 0 && orderDO.getType() != EnumOrderType.GIFT_PACK.getCode() ){
		        	orderDTOtmp.setOrderStatus(Integer.parseInt(EnumOrderStatus.CANCELED.getCode()));
		        }
							
				//查询订单收货地址信息
				OrderConsigneeDO orderConsigneeDO = orderConsigneeManager.getOrderConsignee(orderDO.getId(), orderDO.getUserId());
		        
				if(orderConsigneeDO == null){
					log.error(" 订单["+orderDO.getOrderSn()+"]收货地址为空 ");
				}else{
					OrderConsigneeDTO orderConsigneeDTO = ModelUtil.convert2OrderConsigneeDTO(orderConsigneeDO);
					/*填充收货地址区域信息*/
					orderConsigneeDTO = fillOrderConsigneeRegion(orderConsigneeDTO,appKey);
					orderDTOtmp.setOrderConsigneeDTO(orderConsigneeDTO);
				}
			}
			
			orderDTOs.add(orderDTOtmp);
			orderIdList.add(orderDO.getId());
		}


		//查询指定订单商品列表
		OrderItemQTO orderItemQTO = new OrderItemQTO();
		orderItemQTO.setOrderIdList(orderIdList);
		orderItemQTO.setUserId(orderQTO.getUserId());
		orderItemQTO.setBizCode(bizCode);
		
//		log.info(" orderItemQTO : "+JSONObject.toJSONString(orderItemQTO));
		
		List<OrderItemDO> orderItems = this.orderItemManager.queryOrderItem(orderItemQTO);
		
//		log.info(" orderItems : "+JSONObject.toJSONString(orderItems));

		//对订单商品列表按照订单id进行分组
		/*List orderItemDTOs = new ArrayList();*/
		Map<Long, List<OrderItemDTO>> orderItemMap = new HashMap<Long, List<OrderItemDTO>>();
		for (OrderItemDO item : orderItems) {
			if(orderItemMap.containsKey(item.getOrderId()) == false){
				orderItemMap.put(item.getOrderId(), new ArrayList<OrderItemDTO>());
			}

			orderItemMap.get(item.getOrderId()).add(ModelUtil.convert2OrderItemDTO(item));
		}

		//将订单相关的商品列表填充到指定订单中

		for (OrderDTO orderDTO : orderDTOs) {
			
			if(orderItemMap.containsKey(orderDTO.getId())){

//				List<DistributorOrderItemDTO> distributorOrderItemList = new ArrayList<DistributorOrderItemDTO>();
//				Map<Long, List<OrderItemDTO>> distributorItemMap = new HashMap<Long, List<OrderItemDTO>>();
//				Map<Long, String> distributorNameMap = new HashMap<Long, String>();
				
				List<OrderItemDTO> orderItemDTOList = orderItemMap.get(orderDTO.getId());
				
				/*List<OrderItemDTO> returnOrderItemList = new ArrayList<OrderItemDTO>();*/
			        
		        //for(OrderItemDTO orderItemDTO:orderItemDTOList){
		        	
					/*分销商商品信息分类*/
					/*if(!distributorItemMap.containsKey(orderItemDTO.getDistributorId())){
						distributorItemMap.put(orderItemDTO.getDistributorId(), new ArrayList<OrderItemDTO>());
						distributorNameMap.put(orderItemDTO.getDistributorId(), orderItemDTO.getDistributorName());
					}
					distributorItemMap.get(orderItemDTO.getDistributorId()).add(orderItemDTO);*/		
		        	
		        	/*if(null == orderItemDTO.getOriginalSkuId() ){
		        		if( null!=orderItemDTO.getItemType() && ( orderItemDTO.getItemType().intValue() == DBConst.SUIT_ITEM.getCode()||
		        			orderItemDTO.getActivityId()!=null)){
		        			List<OrderItemDTO> subOrderItemList = new ArrayList<OrderItemDTO>();
		            		for(OrderItemDTO orderItemDTO1:orderItemDTOList){
		            			if(orderItemDTO1.getOriginalSkuId()!=null&&orderItemDTO1.getOriginalSkuId().longValue()==orderItemDTO.getItemSkuId()){
		            				OrderItemDTO subOrderItem = new OrderItemDTO();
		            				 BeanUtils.copyProperties(orderItemDTO1, subOrderItem);
		            				 subOrderItemList.add(subOrderItem);
		            			}
		            		}
		            		orderItemDTO.setItemList(subOrderItemList);
		        		}
		        		returnOrderItemList.add(orderItemDTO);
		        		
		        	}*/
		        	
		        //}
		        /*遍历组装分销商商品信息*/
		        /*int i=1;
		        for(Map.Entry<Long, List<OrderItemDTO>> entry:distributorItemMap.entrySet() ){
		        	DistributorOrderItemDTO distributorOrderItemDTO = new DistributorOrderItemDTO();
//		        	log.info(" for distributorItemMap DistributorId ["+ i++ +"]: "+entry.getKey());
		        	distributorOrderItemDTO.setDistributorId(entry.getKey());
		        	distributorOrderItemDTO.setDistributorName(distributorNameMap.get(entry.getKey()));
		        	distributorOrderItemDTO.setOrderItemList(entry.getValue());
		        	distributorOrderItemList.add(distributorOrderItemDTO);
		        }*/
		        
		        //注释店铺逻辑
//		        orderDTO.setDistributorOrderItemList(distributorOrderItemList);

		        /*暂时不需要*/
//		        orderDTO.setOrderItems(returnOrderItemList);
		        
		        orderDTO.setOrderItems(orderItemDTOList);
		        
				List<OrderDiscountInfoDO> orderDiscountInfoDOs =
						orderDiscountInfoManager.queryOrderDiscountInfo(orderDTO.getId(), orderQTO.getUserId());
				orderDTO.setOrderDiscountInfoDTOs(ModelUtil.convert2OrderDiscountInfoDTOList(orderDiscountInfoDOs));
			}
			
//			log.info(" orderDTO "+JSONObject.toJSONString(orderDTO));
			
		}
		
//		System.out.println(" linyue orderDTOs:"+JSONObject.toJSONString(orderDTOs));

		log.info(" Query User Orders end ");
		
		response = ResponseUtils.getSuccessResponse(orderDTOs);
		// TODO 总记录数 
		/*response.setTotalCount(totalCount);*/ 
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
			log.error("", e);
		}

		return orderConsigneeDTO;
	}
	
	/**
	 * date类型增加秒数
	 * @param date
	 * @param sec
	 * @return
	 */
	private Date dateAddSec(Date date,Long sec){
		GregorianCalendar cal = new GregorianCalendar();  
        cal.setTime(date);  
        cal.add(13, sec.intValue());  
		return cal.getTime();
	}

	/**
	 * date类型增加分钟
	 * @param date
	 * @param sec
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
		return ActionEnum.QUERY_USER_ORDER.getActionName();
	}
}
