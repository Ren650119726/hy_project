package com.mockuai.distributioncenter.core.message.msg.parser;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Message;
import com.mockuai.distributioncenter.common.constant.DistributeSource;
import com.mockuai.distributioncenter.common.domain.dto.DistributionItemDTO;
import com.mockuai.distributioncenter.common.domain.dto.DistributionOrderDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.AppManager;
import com.mockuai.distributioncenter.core.manager.DistErrorLogManager;
import com.mockuai.distributioncenter.core.manager.HeadsingleManager;
import com.mockuai.distributioncenter.core.manager.SellerManager;
import com.mockuai.distributioncenter.core.manager.ShopManager;
import com.mockuai.distributioncenter.core.manager.TradeManager;
import com.mockuai.distributioncenter.core.manager.UserManager;
import com.mockuai.distributioncenter.core.message.msg.OrderUnpaidMsg;
import com.mockuai.distributioncenter.core.util.JsonUtil;
import com.mockuai.headsinglecenter.common.domain.dto.HeadSingleSubDTO;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderDiscountInfoDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.usercenter.common.dto.UserDTO;

/**
 * Created by duke on 16/5/27.
 */
@Service
public class OrderUnpaidMessageParser {
    private static final Logger log = LoggerFactory.getLogger(OrderUnpaidMessageParser.class);

    @Autowired
    private TradeManager tradeManager;

    @Autowired
    private AppManager appManager;

    @Autowired
    private ShopManager shopManager;

    @Autowired
    private HeadsingleManager headsingleManager;
    
    @Autowired
    private SellerManager sellerManager;
    
    @Autowired
    private UserManager userManager;
    
    @Resource
    private DistErrorLogManager distErrorLogManager;
    
    
    public OrderUnpaidMsg parse(Message msg) {
        String msgStr = new String(msg.getBody(), Charset.forName("UTF-8"));
        JSONObject json = JSONObject.parseObject(msgStr);
        Long orderId = json.getLong("id");
        Long buyerId = json.getLong("userId");
//        log.info("[{}] buyerId:{}",buyerId);
        String bizCode = json.getString("bizCode");
        String appKey;
        try {
            appKey = appManager.getAppKeyByBizCode(bizCode);
        } catch (DistributionException e) {
            log.error("get appKey error, bizCode: {}", bizCode);
            return null;
        }

        //暂停一秒
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			log.error("get order item error:",orderId);
			return null;
		}
        
        OrderDTO orderDTO;
        try {
            orderDTO = tradeManager.getOrder(orderId, buyerId, appKey);
            log.info("[{}] orderDTO:{}",JsonUtil.toJson(orderDTO));
        } catch (DistributionException e) {
            log.error("get order by orderId: {}, buyerId: {} error", orderId, buyerId);
            return null;
        }

        OrderUnpaidMsg orderUnpaidMsg = new OrderUnpaidMsg();
        orderUnpaidMsg.setMsg(msg);
        orderUnpaidMsg.setAppKey(appKey);
        // 获得订单中的商品
        
        DistributionOrderDTO distributionOrderDTO =  new DistributionOrderDTO();
        List<DistributionItemDTO> distributionItemDTOs =  new ArrayList<DistributionItemDTO>();
        for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {        	
        	
            DistributionItemDTO distributionItemDTO = new DistributionItemDTO();
            
            distributionItemDTO.setOrderId(orderId);
            distributionItemDTO.setOrderSn(orderDTO.getOrderSn());
            distributionItemDTO.setUserId(buyerId);
            distributionItemDTO.setAppKey(appKey);
            //判断是否是分享商品
            Long shareUserId = orderItemDTO.getShareUserId();
//            log.info("[{}] shareUserId:{}",shareUserId);
            Long role = null;
            // 设置分拥来源：嗨客分享，非海客分享，海客自购，非海客自购
//            if(shareUserId == null || shareUserId == 0L){
            
			try {
				UserDTO  userDTO = userManager.getUserByUserId(buyerId, appKey);
				role = userDTO.getRoleMark();
			} catch (DistributionException e) {
				log.error(" userManager.getUserByUserId error userid:{} orderId:{} msg:{} ",buyerId,orderId,e.getMessage());
				try {
					int result = this.addDistErrorMsg(buyerId,orderId,e);
					return null;
				} catch (DistributionException e2) {
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e2.printStackTrace(pw);
					log.error(" addDistErrorMsg error :{}",sw.toString());					
				}			
			}
        	if(role == 2){
        		distributionItemDTO.setSource(DistributeSource.PURCHASE_DIST.getSource());
        	}else{
        		distributionItemDTO.setSource(DistributeSource.NOPURCHASE_DIST.getSource());
        	}
        	
            /*}else{
            	try {
            		UserDTO  userDTO = userManager.getUserByUserId(shareUserId, appKey);
					role = userDTO.getRoleMark();
				} catch (DistributionException e) {
					e.printStackTrace();
				}
            	if(role == 2){
            		distributionItemDTO.setSource(DistributeSource.SHARE_DIST.getSource());
            	}else{
            		distributionItemDTO.setSource(DistributeSource.NOSHARE_DIST.getSource());

            	}
            }*/
            log.info("ceshi shuju2,userDTO:{}",distributionItemDTO.getSource());
            distributionItemDTO.setShareUserId(shareUserId);
            distributionItemDTO.setItemId(orderItemDTO.getItemId());
            distributionItemDTO.setItemSkuId(orderItemDTO.getItemSkuId());
            distributionItemDTO.setNumber(orderItemDTO.getNumber());
            distributionItemDTO.setUnitPrice(orderItemDTO.getPaymentAmount());
            distributionItemDTOs.add(distributionItemDTO);
        }
        distributionOrderDTO.setItemDTOs(distributionItemDTOs);
        distributionOrderDTO.setOrderId(orderId);
        distributionOrderDTO.setOrderSn(orderDTO.getOrderSn());
        distributionOrderDTO.setUserId(buyerId);
        orderUnpaidMsg.setDistributionOrderDTO(distributionOrderDTO);
        orderUnpaidMsg.setFilter(true);
        //首单立减 设置是否分拥
        List<OrderDiscountInfoDTO> list = orderDTO.getOrderDiscountInfoDTOs();
        for (OrderDiscountInfoDTO orderDiscountInfoDTO : list) {
        	   if(orderDiscountInfoDTO.getDiscountCode().equals("FirstOrderDiscount")){
        		   Long marketActivityId =  orderDiscountInfoDTO.getMarketActivityId();
        		   log.info("queryHeadSingleSubById HeadSingleSubDTO , marketActivityId: {}", marketActivityId);
        		   HeadSingleSubDTO  HeadSingleSubDTO = headsingleManager.queryHeadSingleSubById(marketActivityId, appKey);
        		   log.info("queryHeadSingleSubById HeadSingleSubDTO: {}", JsonUtil.toJson(HeadSingleSubDTO));
        		   if(HeadSingleSubDTO != null){
        			   if(HeadSingleSubDTO.getDiscomStatus() != 1){
        				   orderUnpaidMsg.setFilter(false);
        			   }
        		   }
        	   }
		}
        
        log.info("parse unpaid msg: {}", JsonUtil.toJson(orderUnpaidMsg.getDistributionOrderDTO()));
        return orderUnpaidMsg;
    }
    
    private int addDistErrorMsg(Long buyerId,Long orderId,DistributionException e) throws DistributionException{
		
    	try {
    		int result=0;
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);

			result = distErrorLogManager.addDistErrorLog(orderId, buyerId, "下单生成分佣记录", sw.toString());

	    	return result;
		} catch (DistributionException e2) {
			log.error(e.getMessage());
			throw new DistributionException(e2);
		}
		
    }
    
//    public OrderUnpaidMsg parse(Message msg) {
//        String msgStr = new String(msg.getBody(), Charset.forName("UTF-8"));
//        JSONObject json = JSONObject.parseObject(msgStr);
//        Long orderId = json.getLong("id");
//        Long buyerId = json.getLong("userId");
//        String bizCode = json.getString("bizCode");
//        String appKey;
//        try {
//            appKey = appManager.getAppKeyByBizCode(bizCode);
//        } catch (DistributionException e) {
//            log.error("get appKey error, bizCode: {}", bizCode);
//            return null;
//        }
//
//        //暂停一秒
//        try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e1) {
//			log.error("get order item error:",orderId);
//			return null;
//		}
//        
//        OrderDTO orderDTO;
//        try {
//            orderDTO = tradeManager.getOrder(orderId, buyerId, appKey);
//        } catch (DistributionException e) {
//            log.error("get order by orderId: {}, buyerId: {} error", orderId, buyerId);
//            return null;
//        }
//
//        OrderUnpaidMsg orderUnpaidMsg = new OrderUnpaidMsg();
//        orderUnpaidMsg.setMsg(msg);
//        orderUnpaidMsg.setAppKey(appKey);
//        // 通过分销商ID进行拆单
//        // 获得订单中的商品
//        Map<Long, DistributionOrderDTO> map = new HashMap<Long, DistributionOrderDTO>();
//        for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
//            DistributionOrderDTO distributionOrderDTO = map.get(orderItemDTO.getDistributorId());
//            if (distributionOrderDTO == null) {
//                distributionOrderDTO = new DistributionOrderDTO();
//                distributionOrderDTO.setOrderId(orderId);
//                distributionOrderDTO.setOrderSn(orderDTO.getOrderSn());
//                distributionOrderDTO.setSellerId(orderItemDTO.getDistributorId());
//                try {
//                    DistShopDTO shopDTO = shopManager.getBySellerId(distributionOrderDTO.getSellerId());
//                    distributionOrderDTO.setShopId(shopDTO.getId());
//                } catch (DistributionException e) {
//                    log.error("parse unpaid msg - get shop by sellerId: {}, error: {}", distributionOrderDTO.getSellerId(), e.getMessage());
//                    return null;
//                }
//                distributionOrderDTO.setAppKey(appKey);
//                distributionOrderDTO.setUserId(buyerId);
//                distributionOrderDTO.setItemDTOs(new ArrayList<DistributionItemDTO>());
//                map.put(orderItemDTO.getDistributorId(), distributionOrderDTO);
//                // 设置分拥来源：正常购买{包括：销售、团队}、开店
//                if (orderDTO.getType().equals(EnumOrderType.GIFT_PACK.getCode())) {
//                    distributionOrderDTO.setSource(DistributeSource.OPEN_SHOP_DIST.getSource());
//                } else {
//                    distributionOrderDTO.setSource(DistributeSource.SALE_DIST.getSource());
//                }
//            }
//
//            DistributionItemDTO distributionItemDTO = new DistributionItemDTO();
//            distributionItemDTO.setItemId(orderItemDTO.getItemId());
//            distributionItemDTO.setItemSkuId(orderItemDTO.getItemSkuId());
//            distributionItemDTO.setNumber(orderItemDTO.getNumber());
//            distributionItemDTO.setUnitPrice(orderItemDTO.getUnitPrice());
//            distributionOrderDTO.getItemDTOs().add(distributionItemDTO);
//        }
//
//        orderUnpaidMsg.setDistributionOrderDTOs(new ArrayList<DistributionOrderDTO>(map.values()));
//        
//        orderUnpaidMsg.setFilter(true);
//        //首单立减 设置是否分拥
//        List<OrderDiscountInfoDTO> list = orderDTO.getOrderDiscountInfoDTOs();
//        for (OrderDiscountInfoDTO orderDiscountInfoDTO : list) {
//        	   if(orderDiscountInfoDTO.getDiscountCode().equals("FirstOrderDiscount")){
//        		   Long marketActivityId =  orderDiscountInfoDTO.getMarketActivityId();
//        		   log.info("queryHeadSingleSubById HeadSingleSubDTO , marketActivityId: {}", marketActivityId);
//        		   HeadSingleSubDTO  HeadSingleSubDTO = headsingleManager.queryHeadSingleSubById(marketActivityId, appKey);
//        		   log.info("queryHeadSingleSubById HeadSingleSubDTO: {}", JsonUtil.toJson(HeadSingleSubDTO));
//        		   if(HeadSingleSubDTO != null){
//        			   if(HeadSingleSubDTO.getDiscomStatus() != 1){
//        				   orderUnpaidMsg.setFilter(false);
//        			   }
//        		   }
//        	   }
//		}
//        
//        log.info("parse unpaid msg: {}", JsonUtil.toJson(orderUnpaidMsg.getDistributionOrderDTOs()));
//        return orderUnpaidMsg;
//    }
}
