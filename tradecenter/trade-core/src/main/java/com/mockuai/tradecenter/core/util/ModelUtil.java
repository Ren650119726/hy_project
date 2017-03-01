package com.mockuai.tradecenter.core.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.mockuai.marketingcenter.common.domain.dto.*;
import com.mockuai.tradecenter.common.domain.*;
import com.mockuai.tradecenter.common.domain.HigoExtraInfoDTO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.tradecenter.common.util.MoneyUtil;
import com.mockuai.tradecenter.core.config.MockuaiConfig;
import com.mockuai.tradecenter.core.domain.CartItemDO;
import com.mockuai.tradecenter.core.domain.OrderConsigneeDO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderDiscountInfoDO;
import com.mockuai.tradecenter.core.domain.OrderInvoiceDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.domain.OrderPaymentDO;
import com.mockuai.tradecenter.core.domain.TradePaymentConfigDO;
import com.mockuai.tradecenter.core.manager.OrderSeqManager;

public class ModelUtil {
    private static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyymmdd");

    @Resource
    private OrderSeqManager orderSeqManager;
    
    
    public static String generateItemDetailUrl(String appDomain,Long userId,Long itemId){
    	String uid=userId+"_"+itemId;
    	String itemUrl = appDomain+MockuaiConfig.DEFAULT_DETAIL_URL+"?item_uid="+uid;
    	return itemUrl;
    }

    public static List<OrderDiscountInfoDTO> convert2OrderDiscountInfoDTOList(
            List<OrderDiscountInfoDO> orderDiscountInfoDOs){
        if(orderDiscountInfoDOs == null){
            return Collections.EMPTY_LIST;
        }
        List<OrderDiscountInfoDTO> orderDiscountInfoDTOs = new ArrayList<OrderDiscountInfoDTO>();
        for(OrderDiscountInfoDO orderDiscountInfoDO: orderDiscountInfoDOs){
            orderDiscountInfoDTOs.add(convert2OrderDiscountInfoDTO(orderDiscountInfoDO));
        }
        return orderDiscountInfoDTOs;
    }

    public static OrderDiscountInfoDTO convert2OrderDiscountInfoDTO(OrderDiscountInfoDO orderDiscountInfoDO){
        if(orderDiscountInfoDO == null){
            return null;
        }
        OrderDiscountInfoDTO orderDiscountInfoDTO =  new OrderDiscountInfoDTO();
        BeanUtils.copyProperties(orderDiscountInfoDO, orderDiscountInfoDTO);
        return orderDiscountInfoDTO;
    }

    public static OrderInvoiceDTO convert2OrderInvoiceDTO(OrderInvoiceDO orderInvoiceDO){
        if(orderInvoiceDO == null){
            return null;
        }

        OrderInvoiceDTO orderInvoiceDTO = new OrderInvoiceDTO();
        BeanUtils.copyProperties(orderInvoiceDO, orderInvoiceDTO);
        return orderInvoiceDTO;
    }

    public static TradePaymentConfigDTO convert2TradePaymentConfigDTO(TradePaymentConfigDO tradePaymentConfigDO) {
    	if(tradePaymentConfigDO==null){
    		return null;
    	}
    	TradePaymentConfigDTO tradePaymentConfigDTO = new TradePaymentConfigDTO();
    	BeanUtils.copyProperties(tradePaymentConfigDO, tradePaymentConfigDTO);
    	return tradePaymentConfigDTO;
    }
    
    public static OrderDTO convert2OrderDTO(OrderDO order) {
        if (order == null) {
            return null;
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(order, orderDTO);
        //解析跨境扩展信息
        if(StringUtils.isNotBlank(order.getHigoExtraInfo())){
            orderDTO.setHigoExtraInfoDTO(JsonUtil.parseJson(order.getHigoExtraInfo(), HigoExtraInfoDTO.class));
        }

        return orderDTO;
    }

    public static OrderDO convert2OrderDO(OrderDTO orderDTO) {
        if (orderDTO == null) {
            return null;
        }

        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderDTO, orderDO);
        if(orderDTO.getHigoExtraInfoDTO() != null){
            orderDO.setHigoExtraInfo(JsonUtil.toJson(orderDTO.getHigoExtraInfoDTO()));
        }

        return orderDO;
    }

    public static OrderItemDTO convert2OrderItemDTO(OrderItemDO orderItem) {
        if (orderItem == null) {
            return null;
        }

        OrderItemDTO orderItemDTO = new OrderItemDTO();
        BeanUtils.copyProperties(orderItem, orderItemDTO);
        
        
        
        
        return orderItemDTO;
    }

    public static CartItemDTO convert2CartItemDTO(CartItemDO cartItem,String appDomain) {
        if (cartItem == null) {
            return null;
        }
        CartItemDTO cartItemDTO = new CartItemDTO();
        BeanUtils.copyProperties(cartItem, cartItemDTO);
        String itemUrl = generateItemDetailUrl(appDomain,cartItem.getUserId(),cartItem.getItemId());
        cartItemDTO.setItemUrl(itemUrl);
        
        
        
        return cartItemDTO;
    }

    public static CartItemDO convert2CartItemDO(CartItemDTO cartItemDTO) {
        if (cartItemDTO == null) {
            return null;
        }
        CartItemDO cartItem = new CartItemDO();
        BeanUtils.copyProperties(cartItemDTO, cartItem);
        return cartItem;
    }
    
    public static List<OrderItemDTO> convert2OrderItemDTOListForRefundDetail(List<OrderItemDO> orderItemDOs){

        if(orderItemDOs == null){
            return null;
        }

        List<OrderItemDTO> orderItemsDTOs = new ArrayList<OrderItemDTO>();
        Map<Long,OrderItemDO> skuIdOrderItemMap = new HashMap<Long,OrderItemDO>();
        for(OrderItemDO item: orderItemDOs){
            orderItemsDTOs.add(ModelUtil.convert2OrderItemDTO(item));
            skuIdOrderItemMap.put(item.getItemSkuId(), item);
        }
        List<OrderItemDTO> returnOrderItemList = new ArrayList<OrderItemDTO>();
        
        for(OrderItemDTO orderItemDTO:orderItemsDTOs){
        	if(orderItemDTO.getItemType()==null|| orderItemDTO.getItemType().intValue()==1){
        		if(orderItemDTO.getOriginalSkuId()!=null){
        			OrderItemDO suitOrderItemDO = skuIdOrderItemMap.get(orderItemDTO.getOriginalSkuId());
        			if(null!=suitOrderItemDO){
        				orderItemDTO.setNumber(orderItemDTO.getNumber()*suitOrderItemDO.getNumber());
        				orderItemDTO.setOriginalOrderItemId(suitOrderItemDO.getId());
        			}
        		}
        	}
        	orderItemDTO.setUnitPriceStr(MoneyUtil.getMoneyStr(orderItemDTO.getUnitPrice()));
        	 returnOrderItemList.add(orderItemDTO);
        }
        
        JSONObject.toJSONString("---returnOrderItemList"+returnOrderItemList);
       

        return returnOrderItemList;
    
    	  
    }
    
    
    public static List<OrderItemDTO> convert2OrderItemDTOListForRefund(List<OrderItemDO> orderItemDOs){

        if(orderItemDOs == null){
            return null;
        }

        List<OrderItemDTO> orderItemsDTOs = new ArrayList<OrderItemDTO>();
        Map<Long,OrderItemDO> skuIdOrderItemMap = new HashMap<Long,OrderItemDO>();
        for(OrderItemDO item: orderItemDOs){
            orderItemsDTOs.add(ModelUtil.convert2OrderItemDTO(item));
            skuIdOrderItemMap.put(item.getItemSkuId(), item);
        }
        List<OrderItemDTO> returnOrderItemList = new ArrayList<OrderItemDTO>();
        
        for(OrderItemDTO orderItemDTO:orderItemsDTOs){
        	if(orderItemDTO.getItemType()==null|| orderItemDTO.getItemType().intValue()==1){
        		if(orderItemDTO.getOriginalSkuId()!=null){
        			OrderItemDO suitOrderItemDO = skuIdOrderItemMap.get(orderItemDTO.getOriginalSkuId());
        			if(null!=suitOrderItemDO){
        				orderItemDTO.setNumber(orderItemDTO.getNumber()*suitOrderItemDO.getNumber());
        				orderItemDTO.setOriginalOrderItemId(suitOrderItemDO.getId());
        			}
        		}
        	}
        	orderItemDTO.setUnitPriceStr(MoneyUtil.getMoneyStr(orderItemDTO.getUnitPrice()));
        	 returnOrderItemList.add(orderItemDTO);
        }
        
        JSONObject.toJSONString("---returnOrderItemList"+returnOrderItemList);
       

        return returnOrderItemList;
    
    	  
    }

    public static List<OrderItemDTO> convert2OrderItemDTOList(List<OrderItemDO> orderItemDOs){
        if(orderItemDOs == null){
            return null;
        }
        Date now = new Date();
        List<OrderItemDTO> orderItemsDTOs = new ArrayList<OrderItemDTO>();
        Map<Long,OrderItemDO> skuIdOrderItemMap = new HashMap<Long,OrderItemDO>();
        for(OrderItemDO item: orderItemDOs){
        	
        	OrderItemDTO orderItemDTO = ModelUtil.convert2OrderItemDTO(item);
        	
        	if(StringUtils.isNotBlank(item.getHigoExtraInfo())){
        		HigoExtraInfoDTO higoExtInfoDTO = JsonUtil.parseJson(item.getHigoExtraInfo(), HigoExtraInfoDTO.class);
        		orderItemDTO.setHigoExtraInfoDTO(higoExtInfoDTO);
        	}
           
//        	if(orderItemDTO.getRefundStatus()==null||(orderItemDTO.getRefundStatus()!=null&&orderItemDTO.getRefundStatus()==3)){
//        		if(orderItemDTO.getDeliveryMark()==0){
//        			orderItemDTO.setCanRefundMark(1);
//        		}else{
//        			//是否已签收
//        			if (now.getTime() <= (item.getGmtCreated().getTime() + 15 * 24 * 3600 * 1000)) {
//        				orderItemDTO.setCanRefundMark(1);
//        			}else{
//        				orderItemDTO.setCanRefundMark(0);
//        			}
//        		}
//        		
//        	}else{
//        		orderItemDTO.setCanRefundMark(0);
//        	}
        	
			 orderItemsDTOs.add(orderItemDTO);
            
            skuIdOrderItemMap.put(item.getItemSkuId(), item);
        }
        List<OrderItemDTO> returnOrderItemList = new ArrayList<OrderItemDTO>();
        
        for(OrderItemDTO orderItemDTO:orderItemsDTOs){
        	if(orderItemDTO.getOriginalSkuId()==null){
        		if( null!=orderItemDTO.getItemType()&&(orderItemDTO.getItemType().intValue() == DBConst.SUIT_ITEM.getCode()||
        			orderItemDTO.getActivityId()!=null)){
        			List<OrderItemDTO> subOrderItemList = new ArrayList<OrderItemDTO>();
            		for(OrderItemDTO orderItemDTO1:orderItemsDTOs){
            			if(orderItemDTO1.getOriginalSkuId()!=null&&orderItemDTO1.getOriginalSkuId().longValue()==orderItemDTO.getItemSkuId()){
            				OrderItemDTO subOrderItem = new OrderItemDTO();
            				 BeanUtils.copyProperties(orderItemDTO1, subOrderItem);
            				 subOrderItemList.add(subOrderItem);
            			}
            		}
            		orderItemDTO.setItemList(subOrderItemList);
        		}
        		returnOrderItemList.add(orderItemDTO);
        	}
        	
        	
        }
        
       
        

        return returnOrderItemList;
    }

    public static OrderConsigneeDTO convert2OrderConsigneeDTO(OrderConsigneeDO orderConsigneeDO){
        if(orderConsigneeDO == null){
            return null;
        }

        OrderConsigneeDTO orderConsigneeDTO = new OrderConsigneeDTO();
        BeanUtils.copyProperties(orderConsigneeDO, orderConsigneeDTO);
        return orderConsigneeDTO;
    }

    public static OrderPaymentDTO convert2OrderPaymentDTO(OrderPaymentDO orderPaymentDO){
        if(orderPaymentDO == null){
            return null;
        }
        OrderPaymentDTO orderPaymentDTO = new OrderPaymentDTO();
        BeanUtils.copyProperties(orderPaymentDO, orderPaymentDTO);
        return orderPaymentDTO;
    }

    public static HigoExtraInfoDTO convert2HigoExtraInfoDTO(
            com.mockuai.marketingcenter.common.domain.dto.HigoExtraInfoDTO marketHigoExtraInfo){
        if(marketHigoExtraInfo == null){
            return null;
        }

        HigoExtraInfoDTO higoExtraInfoDTO = new HigoExtraInfoDTO();
        BeanUtils.copyProperties(marketHigoExtraInfo, higoExtraInfoDTO);
        return higoExtraInfoDTO;
    }
}
