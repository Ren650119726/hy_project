package com.mockuai.tradecenter.core.service.action.order;

import com.mockuai.deliverycenter.common.dto.fee.RegionDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.suppliercenter.client.StoreItemSkuClient;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuQTO;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.*;
import com.mockuai.tradecenter.common.domain.distributor.DistributorOrderItemDTO;
import com.mockuai.tradecenter.common.enums.EnumOrderStatus;
import com.mockuai.tradecenter.common.enums.EnumOrderType;
import com.mockuai.tradecenter.common.util.DateUtil;
import com.mockuai.tradecenter.core.dao.TradeConfigDAO;
import com.mockuai.tradecenter.core.domain.*;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.*;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.util.ModelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


@Service
public class QueryAllOrderBg implements Action{
	private static final Logger log = LoggerFactory.getLogger(QueryAllOrderBg.class);
//push
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
    private DistributionManager distributionManager;

	@Resource
	private OrderConsigneeManager orderConsigneeManager;

	@Resource
	private OrderDiscountInfoManager orderDiscountInfoManager;

    @Resource
    private StoreItemSkuClient  storeItemSkuClient;

	public TradeResponse<List<OrderDTO>> execute(RequestContext context) throws TradeException {

		Request request = context.getRequest();
		TradeResponse<List<OrderDTO>> response = null;
		if(request.getParam("orderQTO") == null){
			log.error("orderQTO is null ");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"orderQTO is null");
		}
		/*String appDomain = (String) context.get("appDomain");*/
		OrderQTO orderQTO = (OrderQTO)request.getParam("orderQTO");

		log.info(" QueryUserOrders start orderStatus ["+orderQTO.getOrderStatus()+"]");

		String bizCode = (String) context.get("bizCode");
		String appKey = (String) context.get("appKey");
		//入参校验
		//检查userId
		/*if(orderQTO.getUserId() == null){
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"userId is null");
		}*/

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
			result = this.orderManager.queryAllOrderBg(orderQTO);
			// TODO 前端不展示，考虑性能，暂时屏蔽 相同条件的总数
			totalCount = this.orderManager.queryAllOrderCountBg(orderQTO);
		}catch(TradeException e){
			log.error("db error: " ,e);
			return ResponseUtils.getFailResponse(e.getResponseCode());
		}

		/*订单取消时间：分*/
		Long payTimeOut= 0l ;
		if(orderQTO.getOrderStatus()==null || EnumOrderStatus.UNPAID.getCode().equals(orderQTO.getOrderStatusStr())){
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
			

			/*获取订单的优惠信息*/
			List<OrderDiscountInfoDO> orderDiscountInfoDOs =
					orderDiscountInfoManager.queryOrderDiscountInfo(orderDO.getId(), orderDO.getUserId());
			
			/*订单优惠信息填充到订单对象*/
			orderDTOtmp.setOrderDiscountInfoDTOs(ModelUtil.convert2OrderDiscountInfoDTOList(orderDiscountInfoDOs));

			orderDTOs.add(orderDTOtmp);
			orderIdList.add(orderDO.getId());
		}


		//查询指定订单商品列表
		OrderItemQTO orderItemQTO = new OrderItemQTO();
		orderItemQTO.setOrderIdList(orderIdList);
		orderItemQTO.setUserId(orderQTO.getUserId());
		orderItemQTO.setBizCode(bizCode);
		List<OrderItemDO> orderItems = this.orderItemManager.queryOrderItemBg(orderItemQTO);

		//对订单商品列表按照订单id进行分组
		List orderItemDTOs = new ArrayList();
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

				List<DistributorOrderItemDTO> distributorOrderItemList = new ArrayList<DistributorOrderItemDTO>();
				Map<Long, List<OrderItemDTO>> distributorItemMap = new HashMap<Long, List<OrderItemDTO>>();
				Map<Long, String> distributorNameMap = new HashMap<Long, String>();

				List<OrderItemDTO> orderItemDTOList = orderItemMap.get(orderDTO.getId());

                orderDTO.setOrderItems(orderItemDTOList);
		        for(OrderItemDTO orderItemDTO:orderItemDTOList){

                    orderItemDTO.setItemSn(queryItemSn(orderDTO,orderItemDTO,appKey));

					/*分销商商品信息分类*/
				/*	if(!distributorItemMap.containsKey(orderItemDTO.getDistributorId())){
						distributorItemMap.put(orderItemDTO.getDistributorId(), new ArrayList<OrderItemDTO>());
						distributorNameMap.put(orderItemDTO.getDistributorId(), orderItemDTO.getDistributorName());
					}
					distributorItemMap.get(orderItemDTO.getDistributorId()).add(orderItemDTO);

		        	if(null == orderItemDTO.getOriginalSkuId() ){
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

		        }
           /*     List<Long> distributorIdList = new ArrayList<>(distributorItemMap.keySet());
                List<SellerDTO> sellerDTOList =  distributionManager.queryDistSeller(distributorIdList,appKey);
		        *//*遍历组装分销商商品信息*//*
		        int i=1;
		        for(Map.Entry<Long, List<OrderItemDTO>> entry:distributorItemMap.entrySet() ){
		        	DistributorOrderItemDTO distributorOrderItemDTO = new DistributorOrderItemDTO();
	        	    log.info(" for distributorItemMap DistributorId ["+ i++ +"]: "+entry.getKey());
		        	distributorOrderItemDTO.setDistributorId(entry.getKey());
		        	distributorOrderItemDTO.setDistributorName(distributorNameMap.get(entry.getKey()));
		        	distributorOrderItemDTO.setOrderItemList(entry.getValue());
                    String mobile =      findSellerDTO(sellerDTOList,entry.getKey());
                    distributorOrderItemDTO.setMobile(mobile);
		        	distributorOrderItemList.add(distributorOrderItemDTO);
		        }

		        orderDTO.setDistributorOrderItemList(distributorOrderItemList);*/
//				orderDTO.setOrderItems(orderItemMap.get(orderDTO.getId()));
		        /*暂时不需要*/
//		        orderDTO.setOrderItems(returnOrderItemList);

			/*	List<OrderDiscountInfoDO> orderDiscountInfoDOs =
						orderDiscountInfoManager.queryOrderDiscountInfo(orderDTO.getId(), orderQTO.getUserId());
				orderDTO.setOrderDiscountInfoDTOs(ModelUtil.convert2OrderDiscountInfoDTOList(orderDiscountInfoDOs));*/
			}
		}

//		System.out.println(" linyue orderDTOs:"+JSONObject.toJSONString(orderDTOs));

		log.info(" QueryUserOrders end orderStatus ["+orderQTO.getOrderStatus()+"]");

		response = ResponseUtils.getSuccessResponse(orderDTOs);
		// TODO 总记录数
		response.setTotalCount(totalCount);
		return response;
	}

    private String findSellerDTO(List<SellerDTO> data,long id){
        for(SellerDTO sellerDTO : data){
            if(sellerDTO.getId().longValue() == id)
                return sellerDTO.getUserName();
        }
        throw  null;
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
	 * @return
	 */
	private Date dateAddMinute(Date date,Long minu){
		GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minu.intValue());
		return cal.getTime();
	}


    private String queryItemSn(OrderDTO orderDTO ,OrderItemDTO orderItem,String appKey){
        StoreItemSkuQTO storeItemSkuQTO = new StoreItemSkuQTO();
        storeItemSkuQTO.setStoreId(orderDTO.getStoreId());
        storeItemSkuQTO.setItemSkuId(orderItem.getItemSkuId());
        try {
            com.mockuai.suppliercenter.common.api.Response<StoreItemSkuDTO> itemSkuDTOResponse = this.storeItemSkuClient.getItemSku(storeItemSkuQTO, appKey);
            if (itemSkuDTOResponse != null && itemSkuDTOResponse.isSuccess() ) {
                return itemSkuDTOResponse.getModule().getSupplierItmeSkuSn();
            }
        }catch (Exception e){
            log.error("queryItemSn error",e);
        }

        return null;
    }


	@Override
	public String getName() {
		return ActionEnum.QUERY_ALL_ORDER_BG.getActionName();
	}
}
