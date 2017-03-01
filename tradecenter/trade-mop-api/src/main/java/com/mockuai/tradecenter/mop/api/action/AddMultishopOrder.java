package com.mockuai.tradecenter.mop.api.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.gson.reflect.TypeToken;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.CommentImageDTO;
import com.mockuai.tradecenter.common.domain.ConsigneeUidDTO;
import com.mockuai.tradecenter.common.domain.ItemServiceDTO;
import com.mockuai.tradecenter.common.domain.OrderConsigneeDTO;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.common.domain.OrderStoreDTO;
import com.mockuai.tradecenter.common.domain.OrderViewDTO;
import com.mockuai.tradecenter.common.domain.SkuUidDTO;
import com.mockuai.tradecenter.common.domain.StoreUidDTO;
import com.mockuai.tradecenter.common.domain.UsedWealthDTO;
import com.mockuai.tradecenter.common.util.ModelUtil;
import com.mockuai.tradecenter.mop.api.domain.MopActivityInfoDTO;
import com.mockuai.tradecenter.mop.api.domain.MopCommentImageDTO;
import com.mockuai.tradecenter.mop.api.domain.MopItemCommentDTO;
import com.mockuai.tradecenter.mop.api.domain.MopItemServiceDTO;
import com.mockuai.tradecenter.mop.api.domain.MopOrderDTO;
import com.mockuai.tradecenter.mop.api.domain.MopOrderItemDTO;
import com.mockuai.tradecenter.mop.api.util.JsonUtil;
import com.mockuai.tradecenter.mop.api.util.MopApiUtil;
/**
 * @author hzmk
 *
 */
public class AddMultishopOrder extends BaseAction {
    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
        String orderListStr = (String) request.getParam("order_list");
        Long userId = (Long) request.getAttribute("user_id");
        String appKey = (String)request.getParam("app_key");

        if(StringUtils.isBlank(orderListStr)){
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "order_list is null");
        }
        if(StringUtils.isBlank((String)request.getParam("payment_id"))){
        	 return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "payment_id is null");
        }
        java.lang.reflect.Type type = new TypeToken<List<MopOrderDTO>>() {}.getType();
        List<MopOrderDTO> mopOrderList = JsonUtil.parseJson(orderListStr, type);
        if(null==mopOrderList||mopOrderList.isEmpty()==true){
        	return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "mopOrderList is empty");
        }
        List<OrderDTO> orderDTOs = new ArrayList<OrderDTO>();
        for(MopOrderDTO mopOrderDTO:mopOrderList){
        	OrderDTO orderDTO = genOrder(mopOrderDTO);
        	//优惠券
        	orderDTO.setUsedCouponDTOs(MopApiUtil.genUsedCouponDTOList(mopOrderDTO.getCouponList()));
        	
        	 String consigneeUid = mopOrderDTO.getConsignee().getConsigneeUid();
             ConsigneeUidDTO consigneeUidDTO = ModelUtil.parseConsigneeUid(consigneeUid);
             
             if(null==orderDTO.getOrderStoreDTO()&&null==consigneeUidDTO){
             	return new MopResponse(MopRespCode.P_E_PARAM_FORMAT_INVALID, "consigneeUid is invalid");
             }
             OrderConsigneeDTO orderConsigneeDTO = new OrderConsigneeDTO();
             if(null!=consigneeUidDTO){
             	   orderConsigneeDTO.setConsigneeId(consigneeUidDTO.getConsigneeId());
             }
             String consignee = mopOrderDTO.getConsignee().getConsignee();
             orderConsigneeDTO.setConsignee(consignee);
             String mobile = mopOrderDTO.getConsignee().getMobile();
             orderConsigneeDTO.setMobile(mobile);
             orderDTO.setOrderConsigneeDTO(orderConsigneeDTO);
             
             
             
        	orderDTOs.add(orderDTO);
        }
        
        Long useBalanceAmt = 0L;
        if(StringUtils.isNotEmpty((String)request.getParam("use_balance_amount"))	){
        	useBalanceAmt = Long.parseLong((String)request.getParam("use_balance_amount"));
        }
        
        Long usePointAmt = 0L;
        if(StringUtils.isNotEmpty((String)request.getParam("use_point_amount"))	){
        	usePointAmt = Long.parseLong((String)request.getParam("use_point_amount"));
        }
        
        com.mockuai.tradecenter.common.api.Request tradeReq = new BaseRequest();
        tradeReq.setCommand(ActionEnum.ADD_MULTISHOP_ORDER.getActionName());
        
       
//        if(orderDTOs.size()==1){
//        	OrderDTO orderDTO = orderDTOs.get(0);
//        	List<UsedWealthDTO> usedWealthDTOs = new ArrayList<UsedWealthDTO>();
//        	if(useBalanceAmt>0){
//        		UsedWealthDTO usedWealthDTO  = new UsedWealthDTO();
//        		usedWealthDTO.setWealthType(1);
//        		usedWealthDTO.setAmount(useBalanceAmt);
//        		usedWealthDTO.setUserId(orderDTO.getUserId());
//        		usedWealthDTOs.add(usedWealthDTO);
//        	}
//        	if(usePointAmt>0){
//        		UsedWealthDTO usedWealthDTO  = new UsedWealthDTO();
//        		usedWealthDTO.setWealthType(2);
//        		usedWealthDTO.setPoint(usePointAmt);
//        		usedWealthDTO.setUserId(orderDTO.getUserId());
//        		usedWealthDTOs.add(usedWealthDTO);
//        		usedWealthDTO.setAmount(usePointAmt);
//        	}
//        	if(usedWealthDTOs.isEmpty()==false){
//        		orderDTO.setUsedWealthDTOs(usedWealthDTOs);
//        	}
////        	orderDTO.setUsedCouponDTOs(MopApiUtil.genUsedCouponDTOList(mopOrderList.get(0).getCouponList()));
//        	tradeReq.setCommand(ActionEnum.ADD_ORDER.getActionName());
//        	
//        	
//        	  orderDTO.setUserId(userId);
//              orderDTO.setPaymentId(Integer.parseInt((String)request.getParam("payment_id")));
//              
////              String consigneeUid = mopOrderList.get(0).getConsignee().getConsigneeUid();
////              ConsigneeUidDTO consigneeUidDTO = ModelUtil.parseConsigneeUid(consigneeUid);
////              
////              if(null==orderDTO.getOrderStoreDTO()&&null==consigneeUidDTO){
////              	return new MopResponse(MopRespCode.P_E_PARAM_FORMAT_INVALID, "consigneeUid is invalid");
////              }
////              OrderConsigneeDTO orderConsigneeDTO = new OrderConsigneeDTO();
////              if(null!=consigneeUidDTO){
////              	   orderConsigneeDTO.setConsigneeId(consigneeUidDTO.getConsigneeId());
////              }
////              String consignee = mopOrderList.get(0).getConsignee().getConsignee();
////              orderConsigneeDTO.setConsignee(consignee);
////              String mobile = mopOrderList.get(0).getConsignee().getMobile();
////              orderConsigneeDTO.setMobile(mobile);
////              orderDTO.setOrderConsigneeDTO(orderConsigneeDTO);
//              
//              
//              tradeReq.setParam("orderDTO", orderDTO);
//        }
        
        
        tradeReq.setParam("orderDTOList", orderDTOs);
        tradeReq.setParam("userId", userId);
        tradeReq.setParam("paymentId", Integer.parseInt((String)request.getParam("payment_id")));
        //TODO ...
        tradeReq.setParam("appKey", appKey);
        
        if(StringUtils.isNotEmpty((String)request.getParam("use_balance_amount"))	){
        	tradeReq.setParam("useBalanceAmount", Long.parseLong((String)request.getParam("use_balance_amount")));
        }
        //
        if(StringUtils.isNotEmpty((String)request.getParam("use_point_amount"))	){
        	tradeReq.setParam("usePointAmount", Long.parseLong((String)request.getParam("use_point_amount")));
        }
        
        
        //增加 orderviewDTO
        OrderViewDTO orderViewDTO = new OrderViewDTO();
        
        orderViewDTO.setDeviceType(request.getAttribute("app_type").toString());
        
        tradeReq.setParam("orderViewDTO", orderViewDTO);
        
        
     

        
        Response tradeResp = getTradeService().execute(tradeReq);
        MopResponse response = null;
        if (tradeResp.getCode() == ResponseCode.RESPONSE_SUCCESS.getCode()) {
            OrderDTO order = (OrderDTO) tradeResp.getModule();
            Map data = new HashMap();
            data.put("order_uid", ModelUtil.genOrderUid(order.getSellerId().longValue(),
                    order.getUserId().longValue(), order.getId().longValue()));
            //返回订单状态，方便调用方决定是否需要支付
            data.put("order_status", order.getOrderStatus().intValue());
            //订单号
            data.put("order_sn", order.getOrderSn());

            response = new MopResponse(data);
        } else {
            response = MopApiUtil.transferResp(tradeResp);
        }

        return response;
    }
    
 

    private OrderDTO genOrder(MopOrderDTO mopOrderDTO) {
        OrderDTO orderDTO = new OrderDTO();
        List orderItemDTOList = new ArrayList();
        orderDTO.setOrderItems(orderItemDTOList);
        if ((mopOrderDTO.getOrderItemList() != null) && (!mopOrderDTO.getOrderItemList().isEmpty())) {
            for (MopOrderItemDTO mopOrderItemDTO : mopOrderDTO.getOrderItemList()) {
                OrderItemDTO orderItemDTO = new OrderItemDTO();
                SkuUidDTO skuUidDTO = ModelUtil.parseSkuUid(mopOrderItemDTO.getSkuUid());
                orderItemDTO.setItemSkuId(skuUidDTO.getSkuId());
                orderItemDTO.setSellerId(skuUidDTO.getSellerId());
                orderItemDTO.setNumber(mopOrderItemDTO.getNumber());
               
                orderItemDTO.setServiceList(genItemServiceList(mopOrderItemDTO.getServiceList()));
                
                orderItemDTO.setItemType(mopOrderItemDTO.getItemType());
                
                MopActivityInfoDTO mopActivityInfoDTO = mopOrderItemDTO.getActivityInfo();
                if( null != mopActivityInfoDTO ){
                	String activityUid = mopActivityInfoDTO.getActivityUid();
                	
                	String[] strs = activityUid.split("_");
                    if(strs.length == 2){
                    	long sellerId = Long.parseLong(strs[0]);
                        long activityId = Long.parseLong(strs[1]);
                        orderItemDTO.setActivityId(activityId);
                    }

                    List<MopOrderItemDTO> subMopItemDTOList = mopActivityInfoDTO.getItemList();
                    if( null!=subMopItemDTOList&&subMopItemDTOList.size()>0){
                    	List<OrderItemDTO> subOrderItemList = new ArrayList<OrderItemDTO>();
                    	for (MopOrderItemDTO subMopOrderItemDTO : subMopItemDTOList) {
                    		OrderItemDTO subOrderItemDTO = new OrderItemDTO();
                            SkuUidDTO subSkuUidDTO = ModelUtil.parseSkuUid(subMopOrderItemDTO.getSkuUid());
                            subOrderItemDTO.setItemSkuId(subSkuUidDTO.getSkuId());
                            subOrderItemDTO.setSellerId(subSkuUidDTO.getSellerId());
                            subOrderItemDTO.setNumber(subMopOrderItemDTO.getNumber());
                            subOrderItemDTO.setUnitPrice(subMopOrderItemDTO.getPrice());
                            subOrderItemList.add(subOrderItemDTO);
                        }
                    	orderItemDTO.setItemList(subOrderItemList);
                    }
                    
                }
                
                orderItemDTOList.add(orderItemDTO);
            }
        }

        orderDTO.setPaymentId(mopOrderDTO.getPaymentId());
        orderDTO.setDeliveryId(mopOrderDTO.getDeliveryId());
        orderDTO.setSourceType(mopOrderDTO.getSourceType());
        orderDTO.setAttachInfo(mopOrderDTO.getAttachInfo());
        orderDTO.setUserMemo(mopOrderDTO.getUserMemo());
        if (mopOrderDTO.getConsignee() != null) {
            String consigneeUid = mopOrderDTO.getConsignee().getConsigneeUid();
            ConsigneeUidDTO consigneeUidDTO = ModelUtil.parseConsigneeUid(consigneeUid);
            OrderConsigneeDTO orderConsigneeDTO = new OrderConsigneeDTO();
            if(null!=consigneeUidDTO){
                  orderConsigneeDTO.setConsigneeId(consigneeUidDTO.getConsigneeId());
            }
            String consignee = mopOrderDTO.getConsignee().getConsignee();
            orderConsigneeDTO.setConsignee(consignee);
            orderDTO.setOrderConsigneeDTO(orderConsigneeDTO);
        }
        
        if(mopOrderDTO.getStoreUid() != null){
        	String storeUid = mopOrderDTO.getStoreUid();
        	StoreUidDTO storeUidDTO = ModelUtil.parseStoreUid(storeUid);
        	OrderStoreDTO orderStoreDTO  = new OrderStoreDTO();
        	orderStoreDTO.setStoreId(storeUidDTO.getStoreId());
        	orderDTO.setOrderStoreDTO(orderStoreDTO);
        }
        orderDTO.setPickupTime(mopOrderDTO.getPickupTime());

        return orderDTO;
    }
    
    
    public static List<ItemServiceDTO> genItemServiceList(List<MopItemServiceDTO> mopItemServiceDTOs){
        if(mopItemServiceDTOs == null){
            return null;
        }

        List<ItemServiceDTO> itemServiceDTOs = new CopyOnWriteArrayList<ItemServiceDTO>();
        for(MopItemServiceDTO mopItemServiceDTO: mopItemServiceDTOs){
        	ItemServiceDTO itemServiceDTO = new ItemServiceDTO();
        	String[] strs = mopItemServiceDTO.getServiceUid().split("_");
        	long sellerId = Long.parseLong(strs[0]);
            long serviceId = Long.parseLong(strs[1]);
        	itemServiceDTO.setServiceId(serviceId);
        	itemServiceDTOs.add(itemServiceDTO);
        }

        return itemServiceDTOs;
    }

    public String getName() {
        return "/trade/multishop/order/add";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}

