package com.mockuai.deliverycenter.core.service.action.express;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;

import org.apache.noggit.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.appcenter.common.util.JsonUtil;
import com.mockuai.deliverycenter.common.api.DeliveryResponse;
import com.mockuai.deliverycenter.common.api.Request;
import com.mockuai.deliverycenter.common.constant.ActionEnum;
import com.mockuai.deliverycenter.common.constant.RetCodeEnum;
import com.mockuai.deliverycenter.common.dto.express.DeliveryInfoDTO;
import com.mockuai.deliverycenter.common.dto.express.ThirdpartyExpressDetailDTO;
import com.mockuai.deliverycenter.common.qto.express.DeliveryInfoQTO;
import com.mockuai.deliverycenter.common.qto.express.ThirdpartyExpressInfoQTO;
import com.mockuai.deliverycenter.core.domain.ThirdDeliveryInfoResult;
import com.mockuai.deliverycenter.core.exception.DeliveryException;
import com.mockuai.deliverycenter.core.manager.express.DeliveryDetailManager;
import com.mockuai.deliverycenter.core.manager.express.DeliveryInfoManager;
import com.mockuai.deliverycenter.core.manager.trade.TradeManager;
import com.mockuai.deliverycenter.core.service.RequestContext;
import com.mockuai.deliverycenter.core.service.action.TransAction;
import com.mockuai.deliverycenter.core.util.DeliveryInstance;
import com.mockuai.deliverycenter.core.util.GetThirdDeliveryThread;
import com.mockuai.deliverycenter.core.util.ResponseUtil;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;

@Service
public class QueryDeliveryInfoAction extends TransAction {
	
	private static final Logger log = LoggerFactory.getLogger(QueryDeliveryInfoAction.class);
	
	@Resource
	DeliveryInfoManager deliveryInfoManager;
	
	@Resource
	DeliveryDetailManager deliveryDetailManager;
	
	//默认的最大的大小 防止查询数据太大
	private static final int DEFAULT_PAGE_SIZE = 100;
	
	//默认开始页面
	private static final int DEFAULT_START_PAGE = 1;

	
	@Autowired
	private TradeManager  tradeManager;
	
	
	 
	 public static String getDateTime(String aMask, Date aDate) {
	        SimpleDateFormat df = null;
	        String returnValue = "";

	        if (aDate == null) {
	            log.error("aDate is null!");
	        } else {
	            df = new SimpleDateFormat(aMask);
	            returnValue = df.format(aDate);
	        }

	        return (returnValue);
	    }
	
	@Override
	public DeliveryResponse<?> doTransaction(RequestContext context)
			throws DeliveryException {
		Request request =  context.getRequest();
		if(request.getParam("deliveryInfoQTO") ==null){
			return new DeliveryResponse(RetCodeEnum.PARAMETER_NULL,"deliveryInfoQTO is null"); 
		}
		String appKey = (String) context.get("appKey");
		
		DeliveryInfoQTO deliveryInfoQto = (DeliveryInfoQTO)request.getParam("deliveryInfoQTO");
		//防止数据查询过大
		if(deliveryInfoQto.getPageNo() ==null || deliveryInfoQto.getPageNo().intValue() < 0){
			deliveryInfoQto.setPageNo(DEFAULT_START_PAGE);
		}if(deliveryInfoQto.getPageSize() == null || deliveryInfoQto.getPageSize().intValue() > DEFAULT_PAGE_SIZE){
			deliveryInfoQto.setPageSize(DEFAULT_PAGE_SIZE);
		}
		Long startRow = (deliveryInfoQto.getPageNo().intValue() - 1) * deliveryInfoQto.getPageSize().intValue() * 1L;
		deliveryInfoQto.setStartRow(startRow);
	
		log.info("  deliveryInfoQto : "+JsonUtil.toJson(deliveryInfoQto));
		
		List<DeliveryInfoDTO> list = this.deliveryInfoManager.queryDeliveryInfo(deliveryInfoQto);
		
		List<DeliveryInfoDTO> returnList = new ArrayList<DeliveryInfoDTO>();
		OrderDTO orderDTO = tradeManager.getOrderDTO(deliveryInfoQto.getOrderId(), deliveryInfoQto.getUserId(), appKey);
		
		log.info("orderDTO:"+JsonUtil.toJson(orderDTO));
		
		/*if(null!=orderDTO&&null!=orderDTO.getDeliveryId()&&orderDTO.getDeliveryId()==2){
			List<ThirdpartyExpressDetailDTO> thirdpartyExpressList = new ArrayList<ThirdpartyExpressDetailDTO>();
			DeliveryInfoDTO deliveryInfoDTO = new DeliveryInfoDTO();
			if(orderDTO.getOrderStatus()==40){
				ThirdpartyExpressDetailDTO thirdpartyExpressDetailDTO = new ThirdpartyExpressDetailDTO();
				thirdpartyExpressDetailDTO.setContext(orderDTO.getOrderConsigneeDTO().getConsignee()+
						"使用提货码"+orderDTO.getOrderStoreDTO().getPickupCode()+"在"+orderDTO.getOrderStoreDTO().getStoreAddress()+"完成提货");
				
				thirdpartyExpressDetailDTO.setTime(getDateTime("yyyy-MM-dd HH:mm:ss",orderDTO.getConsignTime()));
				
				thirdpartyExpressList.add(thirdpartyExpressDetailDTO);
			}else if(orderDTO.getOrderStatus()>=50){
				ThirdpartyExpressDetailDTO thirdpartyExpressDetailDTO = new ThirdpartyExpressDetailDTO();
				thirdpartyExpressDetailDTO.setContext(orderDTO.getOrderConsigneeDTO().getConsignee()+
						"使用提货码"+orderDTO.getOrderStoreDTO().getPickupCode()+"在"+orderDTO.getOrderStoreDTO().getStoreAddress()+"完成提货");
				thirdpartyExpressDetailDTO.setTime(getDateTime("yyyy-MM-dd HH:mm:ss",orderDTO.getConsignTime()));
				
				
				ThirdpartyExpressDetailDTO thirdpartyExpressDetailDTO1 = new ThirdpartyExpressDetailDTO();
				thirdpartyExpressDetailDTO1.setContext(orderDTO.getOrderConsigneeDTO().getConsignee()+
						"已经确认收货");
				thirdpartyExpressDetailDTO1.setTime(getDateTime("yyyy-MM-dd HH:mm:ss",orderDTO.getReceiptTime()));
				thirdpartyExpressList.add(thirdpartyExpressDetailDTO1);
				thirdpartyExpressList.add(thirdpartyExpressDetailDTO);
			}
			
			
			deliveryInfoDTO.setThirdpartyList(thirdpartyExpressList);
			returnList.add(deliveryInfoDTO);
			return ResponseUtil.getResponse(returnList);
			
			
		}
		
		if(null!=orderDTO&&null!=orderDTO.getDeliveryId()&&orderDTO.getDeliveryId()==3){
			DeliveryInfoDTO deliveryInfoDTO = new DeliveryInfoDTO();
			List<ThirdpartyExpressDetailDTO> thirdpartyExpressList = new ArrayList<ThirdpartyExpressDetailDTO>();
			
			if(orderDTO.getOrderStatus()==40){
				ThirdpartyExpressDetailDTO thirdpartyExpressDetailDTO = new ThirdpartyExpressDetailDTO();
				thirdpartyExpressDetailDTO.setContext("您购买的商品已经由"+orderDTO.getOrderStoreDTO().getStoreName()+"发货");
				
				thirdpartyExpressDetailDTO.setTime(getDateTime("yyyy-MM-dd HH:mm:ss",orderDTO.getConsignTime()));
				
				thirdpartyExpressList.add(thirdpartyExpressDetailDTO);
			}else if(orderDTO.getOrderStatus()>=50){ //增加已评价的订单能看到门店的物流信息
				ThirdpartyExpressDetailDTO thirdpartyExpressDetailDTO1 = new ThirdpartyExpressDetailDTO();
				thirdpartyExpressDetailDTO1.setContext(orderDTO.getOrderConsigneeDTO().getConsignee()+"已经确认收货");
				thirdpartyExpressDetailDTO1.setTime(getDateTime("yyyy-MM-dd HH:mm:ss",orderDTO.getReceiptTime()));
				thirdpartyExpressList.add(thirdpartyExpressDetailDTO1);
				
				ThirdpartyExpressDetailDTO thirdpartyExpressDetailDTO = new ThirdpartyExpressDetailDTO();
				thirdpartyExpressDetailDTO.setContext("您购买的商品已经由"+orderDTO.getOrderStoreDTO().getStoreName()+"发货");
				thirdpartyExpressDetailDTO.setTime(getDateTime("yyyy-MM-dd HH:mm:ss",orderDTO.getConsignTime()));
				thirdpartyExpressList.add(thirdpartyExpressDetailDTO);
			}
			
			deliveryInfoDTO.setThirdpartyList(thirdpartyExpressList);
			returnList.add(deliveryInfoDTO);
			return ResponseUtil.getResponse(returnList);
		}*/
		
		Map<Long,DeliveryInfoDTO> deliveryInfoMap = new HashMap<Long,DeliveryInfoDTO>();
		
		if(null!=list&&list.size()>0){
			
			for(DeliveryInfoDTO deliveryInfoDTO : list){
				
				deliveryInfoMap.put(deliveryInfoDTO.getId(), deliveryInfoDTO);
				
			    if(null!=deliveryInfoDTO.getExpressCode()&&(!deliveryInfoDTO.getExpressCode().equalsIgnoreCase("customer_pickup"))){ //如果不是客户自提  走第三方快递API
			    	//  一个订单一个快递发货号
		        	ThirdpartyExpressInfoQTO query = new ThirdpartyExpressInfoQTO();

					query.setExpressNo(deliveryInfoDTO.getExpressNo());//物流单号
					query.setDeliveryCompanyCode(deliveryInfoDTO.getExpressCode());//物流公司code
					
					GetThirdDeliveryThread getDeliveryThread = new GetThirdDeliveryThread( query );
                    DeliveryInstance.getDeliveryInstance().getEcs().submit( getDeliveryThread );  
                    
				
			    }
			}
			for(DeliveryInfoDTO deliveryInfoDTO : list){
				  if(null!=deliveryInfoDTO.getExpressCode()&&(!deliveryInfoDTO.getExpressCode().equalsIgnoreCase("customer_pickup"))){
					  ThirdDeliveryInfoResult thirdDeliveryResult;
					try {
						thirdDeliveryResult = DeliveryInstance.getDeliveryInstance().getEcs().take().get();
						if(thirdDeliveryResult.getExpressDetailDTOs()!=null){
							 for(DeliveryInfoDTO deliveryInfoDTO2 : list){

								 if(deliveryInfoDTO2.getExpressNo().equals(thirdDeliveryResult.getExpressNo())&&
										 deliveryInfoDTO2.getExpressCode().equals(thirdDeliveryResult.getDeliveryCompanyCode())){
									 deliveryInfoDTO2.setThirdpartyList(thirdDeliveryResult.getExpressDetailDTOs());
								 }
							 }
							
						  }
					} catch (InterruptedException e) {
						log.error("",e);
					} catch (ExecutionException e) {
						log.error("",e);
					}
					  
				  }
			}
			
		}
			
		if(null!=orderDTO){
			List<OrderItemDTO> orderItemList = orderDTO.getOrderItems();
			Map<Long,List<OrderItemDTO>> deliveryInfoIdMap = new HashMap<Long,List<OrderItemDTO>>();
			for(OrderItemDTO orderItemDTO:orderItemList){
				Long deliveryInfoId = orderItemDTO.getDeliveryInfoId();
				if(deliveryInfoId!=null){
					if(deliveryInfoIdMap.get(deliveryInfoId)==null){
						List<OrderItemDTO> orderItems = new ArrayList<OrderItemDTO>();
						orderItems.add(orderItemDTO);
						deliveryInfoIdMap.put(deliveryInfoId, orderItems);
					}else{
						deliveryInfoIdMap.get(deliveryInfoId).add(orderItemDTO);
					}
				}
			}
			
			if(deliveryInfoIdMap.isEmpty()==false){
				for (Map.Entry<Long,List<OrderItemDTO>> entry : deliveryInfoIdMap.entrySet()) {
					DeliveryInfoDTO deliveryInfoDTO = deliveryInfoMap.get(entry.getKey());
					if(null!=deliveryInfoDTO){
						List<OrderItemDTO> orderItems = entry.getValue();
						deliveryInfoDTO.setOrderItemList(orderItems);
						returnList.add(deliveryInfoDTO);
					}
				}
			}else{
				
				for (Map.Entry<Long,DeliveryInfoDTO> entry : deliveryInfoMap.entrySet()) {
					
					DeliveryInfoDTO deliveryInfoDTO = entry.getValue();
						deliveryInfoDTO.setOrderItemList(orderItemList);
						returnList.add(deliveryInfoDTO);
				}
				
			}
		}else{
			returnList = list;
		}
			
		if(returnList.size()==0){
			returnList = list;
		}
			
		// 返回response对象
		return ResponseUtil.getResponse(returnList);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return ActionEnum.QUERY_DELIVERY_INFO.getActionName();
	}
}
