package com.mockuai.tradecenter.mop.api.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import com.alibaba.dubbo.common.utils.StringUtils;
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
import com.mockuai.tradecenter.common.domain.distributor.DistributorOrderItemDTO;
import com.mockuai.tradecenter.common.util.ModelUtil;
import com.mockuai.tradecenter.mop.api.domain.*;
import com.mockuai.tradecenter.mop.api.util.MopApiUtil;
import org.springframework.beans.BeanUtils;

public class AddOrder extends BaseAction {
    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
        String orderStr = (String) request.getParam("order");
        Long userId = (Long) request.getAttribute("user_id");
        String appKey = (String)request.getParam("app_key");

        if(StringUtils.isBlank(orderStr)){
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "order is null");
        }

        MopOrderDTO mopOrderDTO = MopApiUtil.parseOrder(orderStr);

        com.mockuai.tradecenter.common.api.Request tradeReq = new BaseRequest();
        tradeReq.setCommand(ActionEnum.ADD_ORDER.getActionName());

        OrderDTO orderDTO = genOrder(mopOrderDTO);

        //竞拍保证金订单和充值订单不需要检查收货地址
        boolean isPickup = (mopOrderDTO.getDeliveryId()!=null) && (mopOrderDTO.getDeliveryId().intValue()==2);


        orderDTO.setUserId(userId);
              
        orderDTO.setPaymentId(mopOrderDTO.getPaymentId());
        orderDTO.setDeliveryId(mopOrderDTO.getDeliveryId());
        orderDTO.setUsedCouponDTOs(MopApiUtil.genUsedCouponDTOList(mopOrderDTO.getCouponList()));
        orderDTO.setUsedWealthDTOs(MopApiUtil.genUsedWealthDTOList(mopOrderDTO.getWealthAccountList()));
        tradeReq.setParam("orderDTO", orderDTO);
        tradeReq.setParam("appKey", appKey);
        //增加 orderviewDTO
        OrderViewDTO orderViewDTO = new OrderViewDTO();
        
        orderViewDTO.setDeviceType(request.getAttribute("app_type").toString());
        
        tradeReq.setParam("orderViewDTO", orderViewDTO);

        /*boolean isSeckill = checkIsSeckill(mopOrderDTO);
        if(isSeckill){
        	orderDTO.setType(3);
        	tradeReq.setCommand(ActionEnum.ADD_ACTIVITY_ORDER.getActionName());
        }*/

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
            data.put("pay_timeout", order.getPayTimeout());

            response = new MopResponse(data);
        } else {
            response = MopApiUtil.transferResp(tradeResp);
        }

        return response;
    }
    
    private boolean checkIsSeckill(MopOrderDTO mopOrderDTO){
    	List<MopOrderItemDTO> orderItemList = getOrderItemList(mopOrderDTO);
    	if(orderItemList.size()==1&&orderItemList.get(0).getItemType()!=null&&orderItemList.get(0).getItemType()==13){
    		return true;
    	}
    	return false;
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

    private OrderDTO genOrder(MopOrderDTO mopOrderDTO) {
        OrderDTO orderDTO = new OrderDTO();
        List<OrderItemDTO> orderItemDTOs = new ArrayList<OrderItemDTO>();
        orderDTO.setOrderItems(orderItemDTOs);
        if ((mopOrderDTO.getOrderItemList() != null)
                && (!mopOrderDTO.getOrderItemList().isEmpty())) {
            for (MopOrderItemDTO mopOrderItemDTO : mopOrderDTO.getOrderItemList()) {
                OrderItemDTO orderItemDTO = genOrderItem(mopOrderItemDTO);
                orderItemDTOs.add(orderItemDTO);
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
            String mobile = mopOrderDTO.getConsignee().getMobile();
            orderConsigneeDTO.setConsignee(consignee);
            orderConsigneeDTO.setMobile(mobile);
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

    private OrderItemDTO genOrderItem(MopOrderItemDTO mopOrderItemDTO) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        SkuUidDTO skuUidDTO = ModelUtil.parseSkuUid(mopOrderItemDTO.getSkuUid());
        orderItemDTO.setItemSkuId(skuUidDTO.getSkuId());
        orderItemDTO.setSellerId(skuUidDTO.getSellerId());
        orderItemDTO.setNumber(mopOrderItemDTO.getNumber());
        orderItemDTO.setDistributorId(mopOrderItemDTO.getDistributorId());
        //增加分享人id
        orderItemDTO.setShareUserId(mopOrderItemDTO.getShareUserId());

        orderItemDTO.setServiceList(genItemServiceList(mopOrderItemDTO.getServiceList()));

        MopActivityInfoDTO mopActivityInfoDTO = mopOrderItemDTO.getActivityInfo();
        if( null != mopActivityInfoDTO ){
            String activityUid = mopActivityInfoDTO.getActivityUid();
            if(StringUtils.isNotEmpty(activityUid)){
                String[] strs = activityUid.split("_");
                if(strs.length == 2){
                    long sellerId = Long.parseLong(strs[0]);
                    long activityId = Long.parseLong(strs[1]);
                    orderItemDTO.setActivityId(activityId);
                }
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

        return orderItemDTO;
    }

    private List<MopOrderItemDTO> getOrderItemList(MopOrderDTO mopOrderDTO) {
        return mopOrderDTO.getOrderItemList();
    }

    public String getName() {
        return "/trade/order/add";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}
