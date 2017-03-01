package com.mockuai.tradecenter.mop.api.util;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import com.mockuai.tradecenter.mop.api.domain.*;

import org.apache.commons.lang3.StringUtils;

import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.CartItemDTO;
import com.mockuai.tradecenter.common.domain.CartItemServiceDTO;
import com.mockuai.tradecenter.common.domain.CommentImageDTO;
import com.mockuai.tradecenter.common.domain.CouponUidDTO;
import com.mockuai.tradecenter.common.domain.DeliveryDetailDTO;
import com.mockuai.tradecenter.common.domain.HigoExtraInfoDTO;
import com.mockuai.tradecenter.common.domain.ItemCommentDTO;
import com.mockuai.tradecenter.common.domain.OrderConsigneeDTO;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderDeliveryInfoDTO;
import com.mockuai.tradecenter.common.domain.OrderDiscountInfoDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.common.domain.OrderPaymentDTO;
import com.mockuai.tradecenter.common.domain.OrderServiceDTO;
import com.mockuai.tradecenter.common.domain.OrderStoreDTO;
import com.mockuai.tradecenter.common.domain.OrderTrackDTO;
import com.mockuai.tradecenter.common.domain.OrderUidDTO;
import com.mockuai.tradecenter.common.domain.UsedCouponDTO;
import com.mockuai.tradecenter.common.domain.UsedWealthDTO;
import com.mockuai.tradecenter.common.domain.WealthAccountUidDTO;
import com.mockuai.tradecenter.common.domain.distributor.DistributorOrderItemDTO;
import com.mockuai.tradecenter.common.domain.refund.RefundImageDTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;
import com.mockuai.tradecenter.common.util.ModelUtil;
import com.mockuai.tradecenter.common.util.StringUtil;


public class MopApiUtil {
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static MopOrderDTO parseOrder(String orderStr) {
        MopOrderDTO orderDTO = (MopOrderDTO) JsonUtil.parseJson(orderStr, MopOrderDTO.class);
        return orderDTO;
    }
    

    public static List<OrderDeliveryInfoDTO> genOrderDeliveryInfoDTOList(
            List<MopDeliveryInfoDTO> mopDeliveryInfoDTOs){
        if(mopDeliveryInfoDTOs == null){
            return null;
        }
        List<OrderDeliveryInfoDTO> orderDeliveryInfoDTOs = new ArrayList<OrderDeliveryInfoDTO>();
        for(MopDeliveryInfoDTO mopDeliveryInfoDTO: mopDeliveryInfoDTOs){
            orderDeliveryInfoDTOs.add(genOrderDeliveryInfoDTO(mopDeliveryInfoDTO));
        }

        return orderDeliveryInfoDTOs;
    }

    public static OrderDeliveryInfoDTO genOrderDeliveryInfoDTO(MopDeliveryInfoDTO mopDeliveryInfoDTO){
        OrderDeliveryInfoDTO orderDeliveryInfoDTO = new OrderDeliveryInfoDTO();
        orderDeliveryInfoDTO.setDeliveryCode(mopDeliveryInfoDTO.getDeliveryCode());
        orderDeliveryInfoDTO.setDeliveryCompany(mopDeliveryInfoDTO.getDeliveryCompany());
        orderDeliveryInfoDTO.setDeliveryType(mopDeliveryInfoDTO.getDeliveryType());
        orderDeliveryInfoDTO.setDeliveryFee(mopDeliveryInfoDTO.getDeliveryFee());
        orderDeliveryInfoDTO.setDeliveryDetailDTOs(
                genDeliveryDetailDTOList(mopDeliveryInfoDTO.getDeliveryDetailList()));
        return orderDeliveryInfoDTO;
    }

    public static List<DeliveryDetailDTO> genDeliveryDetailDTOList(List<MopDeliveryDetailDTO> mopDeliveryDetailDTOs){
        if(mopDeliveryDetailDTOs == null){
            return null;
        }
        List<DeliveryDetailDTO> deliveryDetailDTOs = new ArrayList<DeliveryDetailDTO>();
        for(MopDeliveryDetailDTO mopDeliveryDetailDTO: mopDeliveryDetailDTOs){
            deliveryDetailDTOs.add(genDeliveryDetailDTO(mopDeliveryDetailDTO));
        }
        return deliveryDetailDTOs;
    }

    public static DeliveryDetailDTO genDeliveryDetailDTO(MopDeliveryDetailDTO mopDeliveryDetailDTO){
        DeliveryDetailDTO deliveryDetailDTO = new DeliveryDetailDTO();
        if(mopDeliveryDetailDTO.getOpTime() != null){
            try{
                deliveryDetailDTO.setOpTime(dateFormat.parse(mopDeliveryDetailDTO.getOpTime()));
            }catch(Exception e){
                //TODO error handle
            }
        }
        deliveryDetailDTO.setContent(mopDeliveryDetailDTO.getContent());
        return deliveryDetailDTO;
    }

    public static List<UsedCouponDTO> genUsedCouponDTOList(List<MopUsedCouponDTO> mopUsedCouponDTOs){
        if(mopUsedCouponDTOs == null){
            return null;
        }
        List<UsedCouponDTO> usedCouponDTOs = new ArrayList<UsedCouponDTO>();
        for(MopUsedCouponDTO mopUsedCouponDTO: mopUsedCouponDTOs){
            usedCouponDTOs.add(genUsedCouponDTO(mopUsedCouponDTO));
        }
        return usedCouponDTOs;
    }

    public static List<UsedWealthDTO> genUsedWealthDTOList(List<MopUsedWealthDTO> mopUsedWealthDTOs){
        if(mopUsedWealthDTOs == null){
            return null;
        }
        List<UsedWealthDTO> usedWealthDTOs = new ArrayList<UsedWealthDTO>();
        for(MopUsedWealthDTO mopUsedWealthDTO: mopUsedWealthDTOs){
            usedWealthDTOs.add(genUsedWealthDTO(mopUsedWealthDTO));
        }
        return usedWealthDTOs;
    }

    public static UsedCouponDTO genUsedCouponDTO(MopUsedCouponDTO mopUsedCouponDTO){
        UsedCouponDTO usedCouponDTO = new UsedCouponDTO();
        String couponUid = mopUsedCouponDTO.getCouponUid();
        CouponUidDTO couponUidDTO = ModelUtil.parseCouponUid(couponUid);
        if(couponUidDTO == null){
            return null;
        }

        usedCouponDTO.setUserId(couponUidDTO.getUserId());
        usedCouponDTO.setCouponId(couponUidDTO.getCouponId());
        return usedCouponDTO;
    }

    public static UsedWealthDTO genUsedWealthDTO(MopUsedWealthDTO mopUsedWealthDTO){
        UsedWealthDTO usedWealthDTO = new UsedWealthDTO();
        String wealthAccountUid = mopUsedWealthDTO.getWealthAccountUid();
        WealthAccountUidDTO wealthAccountUidDTO = ModelUtil.parseWealthAccountUid(wealthAccountUid);
        if(wealthAccountUidDTO == null){
            return null;
        }

        usedWealthDTO.setUserId(wealthAccountUidDTO.getUserId());
        usedWealthDTO.setWealthAccountId(wealthAccountUidDTO.getWealthAccountId());
        usedWealthDTO.setWealthType(mopUsedWealthDTO.getWealthType());
        usedWealthDTO.setAmount(mopUsedWealthDTO.getAmount());
//        usedWealthDTO.setExchangeRate(mopUsedWealthDTO.getExchangeRate());
        
        
        return usedWealthDTO;
    }
    
    
    public static MopOrderTrackDTO genMopOrderTrack(OrderTrackDTO orderTrackDTO){
    	MopOrderTrackDTO mopOrderTrackDTO = new MopOrderTrackDTO();
    	mopOrderTrackDTO.setTrackInfo(orderTrackDTO.getTrackInfo());
    	mopOrderTrackDTO.setOperator(orderTrackDTO.getOperator());
    	if(null!=orderTrackDTO.getOperateTime()){
    		mopOrderTrackDTO.setOperateTime(dateFormat.format(orderTrackDTO.getOperateTime()));
    	}
    	
    	return mopOrderTrackDTO;
    }
    

    public static MopOrderDTO genMopOrder(OrderDTO orderDTO) {
        MopOrderDTO mopOrderDTO = new MopOrderDTO();
        mopOrderDTO.setOrderUid(orderDTO.getSellerId() + "_" + orderDTO.getUserId() + "_" + orderDTO.getId());
        mopOrderDTO.setOrderSn(orderDTO.getOrderSn());
        if(orderDTO.getInvoiceMark() != null){
        	mopOrderDTO.setInvoice(orderDTO.getInvoiceMark() == 0 ? false : true);
        }
        if(orderDTO.getUserMemo()!=null){
            mopOrderDTO.setUserMemo(orderDTO.getUserMemo());
        }
        if(orderDTO.getAttachInfo()!=null){
        	mopOrderDTO.setAttachInfo(orderDTO.getAttachInfo());
        }
        
        mopOrderDTO.setOrderStatus(orderDTO.getOrderStatus());
        mopOrderDTO.setType(orderDTO.getType());
        mopOrderDTO.setTotalPrice(orderDTO.getTotalPrice());
        mopOrderDTO.setTotalAmount(orderDTO.getTotalAmount());
        if( orderDTO.getDiscountAmount()!=null && orderDTO.getDiscountAmount() != 0 ){
            mopOrderDTO.setDiscountAmount(orderDTO.getDiscountAmount());        	
        }
        if(orderDTO.getDeliveryFee()!=null ){
            mopOrderDTO.setDeliveryFee(orderDTO.getDeliveryFee());
        }
        mopOrderDTO.setDeliveryId(orderDTO.getDeliveryId());
        mopOrderDTO.setPayTimeout(orderDTO.getPayTimeout());
        
        if (mopOrderDTO.isInvoice()) {
            mopOrderDTO.setInvoice(genMopInvoice(orderDTO));
        }
        mopOrderDTO.setDeliveryInfoList(genMopOrderDeliveryInfoList(orderDTO.getOrderDeliveryInfoDTOs()));
        mopOrderDTO.setConsignee(genMopConsignee(orderDTO));
        mopOrderDTO.setOrderPayment(genMopOrderPaymentDTO(orderDTO.getOrderPaymentDTO()));
        mopOrderDTO.setOrderItemList(genMopOrderItemList(orderDTO.getOrderItems(), orderDTO));
        mopOrderDTO.setOrderDiscountList(genMopOrderDiscountInfoList(orderDTO.getOrderDiscountInfoDTOs()));
        
        /*新增*/
//        mopOrderDTO.setDistributorOrderItemList(genMopDistributorOrderItemList(orderDTO.getDistributorOrderItemList(), orderDTO));
        /*订单取消时间点*/
        if(orderDTO.getCancelOrderTime() != null){
            mopOrderDTO.setCancelOrderTime(dateFormat.format(orderDTO.getCancelOrderTime()));
        }
        if(orderDTO.getOrderTime()!=null){
        	mopOrderDTO.setOrderTime(dateFormat.format(orderDTO.getOrderTime()));
        }
        if(orderDTO.getCancelTime()!=null){
        	mopOrderDTO.setCancelTime(dateFormat.format(orderDTO.getCancelTime()));
        }
        if(orderDTO.getConsignTime() != null){
            mopOrderDTO.setConsignTime(dateFormat.format(orderDTO.getConsignTime()));
        }
        if(orderDTO.getPayTime() != null){
            mopOrderDTO.setPayTime(dateFormat.format(orderDTO.getPayTime()));
        }
        if(orderDTO.getReceiptTime() != null){
            mopOrderDTO.setReceiptTime(dateFormat.format(orderDTO.getReceiptTime()));
        }
        if( orderDTO.getOrderStoreDTO() != null ){
        	mopOrderDTO.setOrderStore(genMopOrderStoreDTO(orderDTO.getOrderStoreDTO()));
        }
        /*跨境商品才返回税费*/
        if(orderDTO.getTaxFee()!=null && orderDTO.getHigoMark()==1){
        	mopOrderDTO.setTaxFee(orderDTO.getTaxFee());
        }
        
        mopOrderDTO.setHigoExtraInfo(genMopHigoExtraInfo(orderDTO.getHigoExtraInfoDTO()));
        
        //add sellerMemo refundMark返回
        mopOrderDTO.setSellerMemo(orderDTO.getSellerMemo());
        mopOrderDTO.setRefundMark(orderDTO.getRefundMark());
        
        return mopOrderDTO;
    }

    private static MopOrderPaymentDTO genMopOrderPaymentDTO(OrderPaymentDTO orderPaymentDTO){
        if(orderPaymentDTO == null){
            return null;
        }
        MopOrderPaymentDTO mopOrderPaymentDTO = new MopOrderPaymentDTO();
        mopOrderPaymentDTO.setPaymentId(orderPaymentDTO.getPaymentId());
        mopOrderPaymentDTO.setOutTradeNo(orderPaymentDTO.getOutTradeNo());
        mopOrderPaymentDTO.setPayAmount(orderPaymentDTO.getPayAmount());
        mopOrderPaymentDTO.setPayStatus(orderPaymentDTO.getPayStatus());
        if(orderPaymentDTO.getPayTime() != null){
            mopOrderPaymentDTO.setPayTime(dateFormat.format(orderPaymentDTO.getPayTime()));
        }
        return mopOrderPaymentDTO;
    }
    
    private static MopOrderStoreDTO genMopOrderStoreDTO(OrderStoreDTO orderStoreDTO){
    	if(null==orderStoreDTO){
    		return null;
    	}
    	MopOrderStoreDTO mopOrderStoreDTO = new MopOrderStoreDTO();
    	mopOrderStoreDTO.setStoreId(orderStoreDTO.getStoreId());
    	mopOrderStoreDTO.setStoreName(orderStoreDTO.getStoreName());
    	mopOrderStoreDTO.setStoreAddress(orderStoreDTO.getStoreAddress());
    	mopOrderStoreDTO.setStoreMobile(orderStoreDTO.getStoreMobile());
    	if(null!=orderStoreDTO.getStoreNumber()){
    		orderStoreDTO.setStoreNumber(orderStoreDTO.getStoreNumber());
    	}
    	return mopOrderStoreDTO;
    }
    

    private static List<MopOrderDiscountInfoDTO> genMopOrderDiscountInfoList(
            List<OrderDiscountInfoDTO> orderDiscountInfoDTOs){
        if(orderDiscountInfoDTOs == null){
            return null;
        }

        List<MopOrderDiscountInfoDTO> mopOrderDiscountInfoDTOs = new ArrayList<MopOrderDiscountInfoDTO>();
        for(OrderDiscountInfoDTO orderDiscountInfoDTO: orderDiscountInfoDTOs){
            mopOrderDiscountInfoDTOs.add(genMopOrderDiscountInfo(orderDiscountInfoDTO));
        }
        return mopOrderDiscountInfoDTOs;
    }
    
    //TODO
    /**
     * List<MopDistributorOrderItemDTO> 组装
     * @param distributorOrderItemDTOs
     * @param orderDTO
     * @return
     */
    private static List<MopDistributorOrderItemDTO> genMopDistributorOrderItemList(
    		List<DistributorOrderItemDTO>   distributorOrderItemDTOs,OrderDTO orderDTO){
    	
        if(distributorOrderItemDTOs == null){
            return null;
        }
        
        List<MopDistributorOrderItemDTO> mopDistributorOrderItemDTOs = new ArrayList<MopDistributorOrderItemDTO>();
        for(DistributorOrderItemDTO distributorOrderItemDTO: distributorOrderItemDTOs){        	
            MopDistributorOrderItemDTO mopDistributorOrderItemDTO = new MopDistributorOrderItemDTO();
        	mopDistributorOrderItemDTO.setDistributorId(distributorOrderItemDTO.getDistributorId());
        	mopDistributorOrderItemDTO.setDistributorName(distributorOrderItemDTO.getDistributorName());
        	mopDistributorOrderItemDTO.setOrderItemList(genMopOrderItemList(distributorOrderItemDTO.getOrderItemList(), orderDTO));
        	mopDistributorOrderItemDTOs.add(mopDistributorOrderItemDTO);
        }
        return mopDistributorOrderItemDTOs;
    }

    private static MopOrderDiscountInfoDTO genMopOrderDiscountInfo(OrderDiscountInfoDTO orderDiscountInfoDTO){
        MopOrderDiscountInfoDTO mopOrderDiscountInfoDTO = new MopOrderDiscountInfoDTO();
        mopOrderDiscountInfoDTO.setItemSkuId(orderDiscountInfoDTO.getItemSkuId());
        mopOrderDiscountInfoDTO.setDiscountType(orderDiscountInfoDTO.getDiscountType());
        mopOrderDiscountInfoDTO.setDiscountCode(orderDiscountInfoDTO.getDiscountCode());
        mopOrderDiscountInfoDTO.setDiscountDesc(orderDiscountInfoDTO.getDiscountDesc());
        mopOrderDiscountInfoDTO.setDiscountAmount(orderDiscountInfoDTO.getDiscountAmount());

        return mopOrderDiscountInfoDTO;
    }

    private static List<MopDeliveryInfoDTO> genMopOrderDeliveryInfoList(
            List<OrderDeliveryInfoDTO> orderDeliveryInfoDTOs){
        if(orderDeliveryInfoDTOs == null){
            return null;
        }
        List<MopDeliveryInfoDTO> mopDeliveryInfoDTOs = new ArrayList<MopDeliveryInfoDTO>();
        for(OrderDeliveryInfoDTO orderDeliveryInfoDTO: orderDeliveryInfoDTOs){
            mopDeliveryInfoDTOs.add(genMopOrderDeliveryInfo(orderDeliveryInfoDTO));
        }

        return mopDeliveryInfoDTOs;
    }

    private static MopDeliveryInfoDTO genMopOrderDeliveryInfo(OrderDeliveryInfoDTO orderDeliveryInfoDTO){
        MopDeliveryInfoDTO mopDeliveryInfoDTO = new MopDeliveryInfoDTO();
        mopDeliveryInfoDTO.setDeliveryInfoUid(
                orderDeliveryInfoDTO.getUserId()+"_"+orderDeliveryInfoDTO.getDeliveryInfoId());//TODO genUid逻辑封装
        mopDeliveryInfoDTO.setDeliveryCode(orderDeliveryInfoDTO.getDeliveryCode());
        mopDeliveryInfoDTO.setDeliveryCompany(orderDeliveryInfoDTO.getDeliveryCompany());
        mopDeliveryInfoDTO.setDeliveryType(orderDeliveryInfoDTO.getDeliveryType());
        mopDeliveryInfoDTO.setDeliveryFee(orderDeliveryInfoDTO.getDeliveryFee());
        mopDeliveryInfoDTO.setDeliveryDetailList(genMopDeliveryDetailList(orderDeliveryInfoDTO.getDeliveryDetailDTOs()));
        return mopDeliveryInfoDTO;
    }

    private static List<MopDeliveryDetailDTO> genMopDeliveryDetailList(List<DeliveryDetailDTO> deliveryDetailDTOs){
        if(deliveryDetailDTOs == null){
            return null;
        }
        List<MopDeliveryDetailDTO> mopDeliveryDetailDTOs = new ArrayList<MopDeliveryDetailDTO>();
        for(DeliveryDetailDTO deliveryDetailDTO: deliveryDetailDTOs){
            mopDeliveryDetailDTOs.add(genMopDeliveryDetail(deliveryDetailDTO));
        }
        return mopDeliveryDetailDTOs;
    }

    private static MopDeliveryDetailDTO genMopDeliveryDetail(DeliveryDetailDTO deliveryDetailDTO){
        MopDeliveryDetailDTO mopDeliveryDetailDTO = new MopDeliveryDetailDTO();
        mopDeliveryDetailDTO.setDeliveryDetailUid(deliveryDetailDTO.getUserId()+"_"+deliveryDetailDTO.getDetailId());
        mopDeliveryDetailDTO.setOpTime(dateFormat.format(deliveryDetailDTO.getOpTime()));
        mopDeliveryDetailDTO.setContent(deliveryDetailDTO.getContent());

        return mopDeliveryDetailDTO;
    }

    private static List<MopOrderItemDTO> genMopOrderItemList(List<OrderItemDTO> orderItemDTOs, OrderDTO orderDTO) {
        if (orderItemDTOs == null) {
            return null;
        }

        List mopOrderItemDTOs = new ArrayList();
        for (OrderItemDTO orderItemDTO : orderItemDTOs) {
            mopOrderItemDTOs.add(genMopOrderItem(orderItemDTO, orderDTO));
        }
        return mopOrderItemDTOs;
    }

    private static MopOrderItemDTO genMopOrderItem(OrderItemDTO orderItemDTO, OrderDTO orderDTO) {
        MopOrderItemDTO mopOrderItemDTO = new MopOrderItemDTO();
        mopOrderItemDTO.setOrderItemUid(orderDTO.getSellerId() + "_" +orderItemDTO.getUserId()+ "_" + orderItemDTO.getId());
        mopOrderItemDTO.setSkuUid(orderDTO.getSellerId() + "_" + orderItemDTO.getItemSkuId());

        mopOrderItemDTO.setItemSkuId(orderItemDTO.getItemSkuId());
        
        mopOrderItemDTO.setItemUid(orderDTO.getSellerId() + "_" + orderItemDTO.getItemId());
        mopOrderItemDTO.setItemName(orderItemDTO.getItemName());

        mopOrderItemDTO.setIconUrl(orderItemDTO.getItemImageUrl());
        mopOrderItemDTO.setDeliveryType(orderItemDTO.getDeliveryType());
        mopOrderItemDTO.setPrice(orderItemDTO.getUnitPrice());
        mopOrderItemDTO.setNumber(orderItemDTO.getNumber());
        mopOrderItemDTO.setSeller(genMopSeller(orderItemDTO));
        mopOrderItemDTO.setItemType(orderItemDTO.getItemType());
        
        mopOrderItemDTO.setPaymentAmount(orderItemDTO.getPaymentAmount());
        mopOrderItemDTO.setRefundAmount(orderItemDTO.getRefundAmount());
        mopOrderItemDTO.setRefundStatus(orderItemDTO.getRefundStatus());
        mopOrderItemDTO.setDiscountAmount(orderItemDTO.getDiscountAmount());
        mopOrderItemDTO.setPoint(orderItemDTO.getPoint());
        mopOrderItemDTO.setPointAmount(orderItemDTO.getPointAmount());
        mopOrderItemDTO.setDeliveryMark(orderItemDTO.getDeliveryMark());
        /*新增*/
        mopOrderItemDTO.setHigoMark(orderItemDTO.getHigoMark());
        
        // 分享人id
        mopOrderItemDTO.setShareUserId(orderItemDTO.getShareUserId());
        
        //TODO ....
        if(orderItemDTO.getItemType()!=null&&orderItemDTO.getItemType().intValue()==11 || orderItemDTO.getActivityId()!=null){
        	MopActivityInfoDTO mopActivityInfoDTO = new MopActivityInfoDTO();
        	mopActivityInfoDTO.setItemList(genSubMopOrderItem(orderItemDTO,orderDTO));
        	if(orderItemDTO.getActivityId()!=null){
        		mopActivityInfoDTO.setActivityUid(orderItemDTO.getSellerId()+"_"+orderItemDTO.getActivityId());
        	}
        	mopOrderItemDTO.setActivityInfo(mopActivityInfoDTO);
        }
        mopOrderItemDTO.setSkuSnapshot(orderItemDTO.getItemSkuDesc());
        
        //服务列表
        if(orderItemDTO.getOrderServiceList()!=null&&orderItemDTO.getOrderServiceList().size()>0){
        	mopOrderItemDTO.setServiceList(genMopItemServiceList(orderItemDTO,orderDTO));
        }
        
        if(orderItemDTO.getSku()!=null){
        	mopOrderItemDTO.setSkuBarcode(orderItemDTO.getSku().getBarCode());
        }
        //能否退款标识
        if(orderItemDTO.getCanRefundMark()!=null){
        	mopOrderItemDTO.setCanRefundMark(orderItemDTO.getCanRefundMark());
        }

        return mopOrderItemDTO;
    }
    
    private static List<MopItemServiceDTO> genMopItemServiceList(OrderItemDTO orderItemDTO,OrderDTO orderDTO){
    	if(null==orderItemDTO.getOrderServiceList()){
    		return null;
    	}
    	List<MopItemServiceDTO> mopItemServiceList = new ArrayList<MopItemServiceDTO>();
    	for(OrderServiceDTO orderServiceDTO : orderItemDTO.getOrderServiceList()){
    		MopItemServiceDTO mopItemServiceDTO = new MopItemServiceDTO();
    		mopItemServiceDTO.setServiceUid(orderDTO.getSellerId()+"_"+orderServiceDTO.getServiceId());
    		mopItemServiceDTO.setServiceName(orderServiceDTO.getServiceName());
    		mopItemServiceDTO.setServicePrice(orderServiceDTO.getPrice());
    		mopItemServiceDTO.setServiceImageUrl(orderServiceDTO.getServiceImageUrl());
    		mopItemServiceList.add(mopItemServiceDTO);
    	}
    	return mopItemServiceList;
    }
    
    public static List<MopItemServiceDTO> genMopItemServiceList(CartItemDTO cartItemDTO){
    	if(cartItemDTO.getCartItemServiceList()==null||cartItemDTO.getCartItemServiceList().size()==0){
    		return null;
        }
    	List<MopItemServiceDTO> mopItemServiceList = new ArrayList<MopItemServiceDTO>();
    	for(CartItemServiceDTO cartItemServiceDTO : cartItemDTO.getCartItemServiceList()){
    		MopItemServiceDTO mopItemServiceDTO = new MopItemServiceDTO();
    		mopItemServiceDTO.setServiceUid(cartItemDTO.getSellerId()+"_"+cartItemServiceDTO.getServiceId());
    		mopItemServiceDTO.setServiceName(cartItemServiceDTO.getServiceName());
    		mopItemServiceDTO.setServicePrice(cartItemServiceDTO.getPrice());
    		mopItemServiceDTO.setServiceImageUrl(cartItemServiceDTO.getServiceImageUrl());
    		mopItemServiceList.add(mopItemServiceDTO);
    	}
    	return mopItemServiceList;
    }
    
    private static List<MopOrderItemDTO> genSubMopOrderItem(OrderItemDTO orderItemDTO, OrderDTO orderDTO){
    	if(orderItemDTO.getItemList()==null){
    		return null;
    	}
    	List<MopOrderItemDTO> subMopOrderItemList = new ArrayList<MopOrderItemDTO>();
    	for(OrderItemDTO subOrderItemDTO:orderItemDTO.getItemList()){
    		  	MopOrderItemDTO mopOrderItemDTO = new MopOrderItemDTO();
    	        mopOrderItemDTO.setOrderItemUid(orderDTO.getSellerId() + "_" +subOrderItemDTO.getUserId()+"_"+ subOrderItemDTO.getId());
    	        mopOrderItemDTO.setSkuUid(orderDTO.getSellerId() + "_" + subOrderItemDTO.getItemSkuId());

    	        mopOrderItemDTO.setItemUid(orderDTO.getSellerId() + "_" + subOrderItemDTO.getItemId());
    	        mopOrderItemDTO.setItemName(subOrderItemDTO.getItemName());

    	        mopOrderItemDTO.setIconUrl(subOrderItemDTO.getItemImageUrl());
    	        mopOrderItemDTO.setDeliveryType(subOrderItemDTO.getDeliveryType());
    	        mopOrderItemDTO.setPrice(subOrderItemDTO.getUnitPrice());
    	        mopOrderItemDTO.setNumber(subOrderItemDTO.getNumber());
    	        mopOrderItemDTO.setSeller(genMopSeller(subOrderItemDTO));
    	        mopOrderItemDTO.setItemType(subOrderItemDTO.getItemType());
    	        
    	        mopOrderItemDTO.setPaymentAmount(subOrderItemDTO.getPaymentAmount());
    	        mopOrderItemDTO.setRefundAmount(subOrderItemDTO.getRefundAmount());
    	        mopOrderItemDTO.setRefundStatus(subOrderItemDTO.getRefundStatus());
    	        mopOrderItemDTO.setDiscountAmount(subOrderItemDTO.getDiscountAmount());
    	        mopOrderItemDTO.setPoint(subOrderItemDTO.getPoint());
    	        mopOrderItemDTO.setPointAmount(subOrderItemDTO.getPointAmount());
    	        mopOrderItemDTO.setDeliveryMark(subOrderItemDTO.getDeliveryMark());
    	        
    	        subMopOrderItemList.add(mopOrderItemDTO);
    	}
    	return  subMopOrderItemList;
    }
    

    private static MopSellerDTO genMopSeller(OrderItemDTO orderItemDTO) {
        MopSellerDTO mopSellerDTO = new MopSellerDTO();
        mopSellerDTO.setSellerId(orderItemDTO.getSellerId());
        return mopSellerDTO;
    }

    private static MopInvoiceDTO genMopInvoice(OrderDTO orderDTO) {
        if(orderDTO.getInvoiceMark() == null || orderDTO.getInvoiceMark()==0){
            return null;
        }
        MopInvoiceDTO mopInvoiceDTO = new MopInvoiceDTO();
        mopInvoiceDTO.setInvoiceType(orderDTO.getOrderInvoiceDTO().getInvoiceType());
        mopInvoiceDTO.setInvoiceTitle(orderDTO.getOrderInvoiceDTO().getInvoiceTitle());
        return mopInvoiceDTO;
    }


    private static MopConsigneeDTO genMopConsignee(OrderDTO orderDTO) {
        if(orderDTO.getOrderConsigneeDTO() == null){
            return null;
        }
        MopConsigneeDTO mopConsigneeDTO = new MopConsigneeDTO();
        OrderConsigneeDTO orderConsigneeDTO = orderDTO.getOrderConsigneeDTO();
        mopConsigneeDTO.setCountryCode(orderConsigneeDTO.getCountryCode());
        mopConsigneeDTO.setProvinceCode(orderConsigneeDTO.getProvinceCode());
        mopConsigneeDTO.setCityCode(orderConsigneeDTO.getCityCode());
        mopConsigneeDTO.setAreaCode(orderConsigneeDTO.getAreaCode());
        mopConsigneeDTO.setTownCode(orderConsigneeDTO.getTownCode());
        mopConsigneeDTO.setCountry(orderConsigneeDTO.getCountry());
        mopConsigneeDTO.setProvince(orderConsigneeDTO.getProvince());
        mopConsigneeDTO.setCity(orderConsigneeDTO.getCity());
        mopConsigneeDTO.setArea(orderConsigneeDTO.getArea());
        mopConsigneeDTO.setTown(orderConsigneeDTO.getTown());
        mopConsigneeDTO.setConsignee(orderConsigneeDTO.getConsignee());
        mopConsigneeDTO.setAddress(orderConsigneeDTO.getAddress());
        mopConsigneeDTO.setMobile(orderConsigneeDTO.getMobile());
        mopConsigneeDTO.setPhoneNo(orderConsigneeDTO.getPhone());
        if(!StringUtils.isBlank(orderConsigneeDTO.getIdCardNo())){
        	
        	/*身份证号处理，中间*号替代前一后一*/
			if( orderConsigneeDTO.getIdCardNo().length()>=3){
				String idCardNo = orderConsigneeDTO.getIdCardNo();
				String first = idCardNo.substring(0, 1);
				String last = idCardNo.substring(idCardNo.length()-1,idCardNo.length());
				String middle = idCardNo.substring(1,idCardNo.length()-1);
				String newMiddle = middle.replaceAll("[a-z0-9A-Z]", "*");
				orderConsigneeDTO.setIdCardNo(first+newMiddle+last);
			}
        	
        	mopConsigneeDTO.setIdCardNo(orderConsigneeDTO.getIdCardNo());
        }        
        if( orderDTO.getOrderStoreDTO() != null ){
        	mopConsigneeDTO.setPickupCode(orderDTO.getOrderStoreDTO().getPickupCode());
        	mopConsigneeDTO.setPickupTime(orderDTO.getOrderStoreDTO().getPickupTime());
        }
        return mopConsigneeDTO;
    }

    public static List<ItemCommentDTO> genItemCommentList(List<MopItemCommentDTO> mopItemCommentDTOs){
        List<ItemCommentDTO> itemCommentDTOs = new ArrayList<ItemCommentDTO>();
        for(MopItemCommentDTO mopItemCommentDTO: mopItemCommentDTOs){
            itemCommentDTOs.add(genItemComment(mopItemCommentDTO));
        }
        return itemCommentDTOs;
    }

    public static <T> MopResponse<T> transferResp(Response<T> tradeResp) {
        if (ResponseCode.RESPONSE_SUCCESS.getCode() != tradeResp.getCode()) {
            return new MopResponse(tradeResp.getCode(), tradeResp.getMessage());
        }
        if ((tradeResp.getModule() != null) && (!(tradeResp.getModule() instanceof Boolean))) {
            if ((tradeResp.getModule() instanceof OrderDTO)) {
                OrderDTO orderDTO = (OrderDTO) tradeResp.getModule();
                Map data = new HashMap();
                data.put("order", genMopOrder(orderDTO));
                return new MopResponse(data);
            }
            return new MopResponse(tradeResp.getModule());
        }

        return getResponse(MopRespCode.REQUEST_SUCESS);
    }

    public static ItemCommentDTO genItemComment(MopItemCommentDTO mopItemCommentDTO){

        ItemUidDTO itemUidDTO = parseItemUid(mopItemCommentDTO.getItemUid());
        OrderUidDTO orderUidDTO = ModelUtil.parseOrderUid(mopItemCommentDTO.getOrderUid());
        SkuUidDTO skuUidDTO = parseSkuUid(mopItemCommentDTO.getSkuUid());
        ItemCommentDTO itemCommentDTO = new ItemCommentDTO();
        itemCommentDTO.setItemId(itemUidDTO.getItemId());
        itemCommentDTO.setSellerId(itemUidDTO.getSellerId());
        itemCommentDTO.setUserId(orderUidDTO.getUserId());
        itemCommentDTO.setOrderId(orderUidDTO.getOrderId());
        itemCommentDTO.setTitle(mopItemCommentDTO.getTitle());
        itemCommentDTO.setContent(mopItemCommentDTO.getContent());
        itemCommentDTO.setScore(mopItemCommentDTO.getScore());
        if(null!=skuUidDTO){
        	itemCommentDTO.setSkuId(skuUidDTO.getSkuId());
        }
        
      //添加商品评论图片列表
        itemCommentDTO.setCommentImageDTOList(genCommentImageList(mopItemCommentDTO.getCommentImageList()));
        return itemCommentDTO;
    }
    
    public static SkuUidDTO parseSkuUid(String skuUid){
        SkuUidDTO skuUidDTO = new SkuUidDTO();
        if(null==skuUid){
        	return null;
        }
        String[] strs = skuUid.split("_");
        if(strs.length != 2){
            //TODO error handle
        }

        long sellerId = Long.parseLong(strs[0]);
        long skuId = Long.parseLong(strs[1]);
        skuUidDTO.setSellerId(sellerId);
        skuUidDTO.setSkuId(skuId);

        return skuUidDTO;
    }

    public static OrderItemUidDTO parseOrderItemUid(String orderItemUid){
        OrderItemUidDTO orderItemUidDTO = new OrderItemUidDTO();
        if(null==orderItemUid){
            return null;
        }
        String[] strs = orderItemUid.split("_");
        if(strs.length != 3) {
            //TODO error handle
            throw new IllegalArgumentException("orderItemUid is invalid, orderItemUid=" + orderItemUid);
        }

        long sellerId = Long.parseLong(strs[0]);
        long userId = Long.parseLong(strs[1]);
        long orderItemId = Long.parseLong(strs[2]);
        orderItemUidDTO.setSellerId(sellerId);
        orderItemUidDTO.setUserId(userId);
        orderItemUidDTO.setOrderItemId(orderItemId);

        return orderItemUidDTO;
    }
    
    public static List<CommentImageDTO> genCommentImageList(List<MopCommentImageDTO> mopCommentImageDTOs){
        if(mopCommentImageDTOs == null){
            return null;
        }

        List<CommentImageDTO> commentImageDTOs = new CopyOnWriteArrayList<CommentImageDTO>();
        for(MopCommentImageDTO mopCommentImageDTO: mopCommentImageDTOs){
            CommentImageDTO commentImageDTO = genCommentImage(mopCommentImageDTO);
            commentImageDTOs.add(commentImageDTO);
        }

        return commentImageDTOs;
    }
    
    public static List<RefundImageDTO> genRefundImageList(List<MopRefundItemImageDTO> mopImageDTOs){
        if(mopImageDTOs == null){
            return null;
        }

        List<RefundImageDTO> refundImageDTOs = new CopyOnWriteArrayList<RefundImageDTO>();
        for(MopRefundItemImageDTO mopRefundImageDTO: mopImageDTOs){
        	RefundImageDTO commentImageDTO = genRefundImage(mopRefundImageDTO);
        	refundImageDTOs.add(commentImageDTO);
        }

        return refundImageDTOs;
    }
    
    public static CommentImageDTO genCommentImage(MopCommentImageDTO mopCommentImageDTO){
        if(mopCommentImageDTO == null){
            return null;
        }

        CommentImageDTO commentImageDTO = new CommentImageDTO();
        commentImageDTO.setImageUrl(mopCommentImageDTO.getImageUrl());
        return commentImageDTO;
    }
    
    public static RefundImageDTO genRefundImage(MopRefundItemImageDTO mopRefundImageDTO){
        if(mopRefundImageDTO == null){
            return null;
        }

        RefundImageDTO refundImageDTO = new RefundImageDTO();
        refundImageDTO.setImageUrl(mopRefundImageDTO.getImageUrl());
        return refundImageDTO;
    }

    public static ItemUidDTO parseItemUid(String itemUid){
        ItemUidDTO itemUidDTO = new ItemUidDTO();
        String[] strs = itemUid.split("_");
        if(strs.length != 2){
            //TODO error handle
        }

        long sellerId = Long.parseLong(strs[0]);
        long itemId = Long.parseLong(strs[1]);
        itemUidDTO.setSellerId(sellerId);
        itemUidDTO.setItemId(itemId);

        return itemUidDTO;
    }
    
    public static RefundOrderItemDTO genRefundItem(MopRefundOrderItemDTO mopDTO) {

        OrderItemUidDTO orderItemUidDTO = parseOrderItemUid(mopDTO.getOrderItemUid());

        RefundOrderItemDTO refundItem = new RefundOrderItemDTO();

        refundItem.setOrderItemId(orderItemUidDTO.getOrderItemId());
        refundItem.setUserId(orderItemUidDTO.getUserId());
        refundItem.setSellerId(orderItemUidDTO.getSellerId());
        refundItem.setRefundAmount(mopDTO.getRefundAmount());
        refundItem.setRefundDesc(mopDTO.getRefundDesc());
        refundItem.setRefundReasonId(mopDTO.getRefundReasonId());

        refundItem.setRefundImageList(genRefundImageList(mopDTO.getRefundImageList()));

        return refundItem;
    }
    
    public static List<RefundOrderItemDTO> genRefundItemList(List<MopRefundOrderItemDTO> mopRefundItemDTOs){
        List<RefundOrderItemDTO> refundItemDTOs = new ArrayList<RefundOrderItemDTO>();
        for(MopRefundOrderItemDTO mopRefundItemDTO: mopRefundItemDTOs){
        	refundItemDTOs.add(genRefundItem(mopRefundItemDTO));
        }
        return refundItemDTOs;
    }

    public static MopHigoExtraInfoDTO genMopHigoExtraInfo(HigoExtraInfoDTO higoExtraInfoDTO){
        if(higoExtraInfoDTO == null){
            return null;
        }

        MopHigoExtraInfoDTO mopHigoExtraInfoDTO = new MopHigoExtraInfoDTO();
        mopHigoExtraInfoDTO.setOriginalTaxFee(higoExtraInfoDTO.getOriginalTaxFee());
        mopHigoExtraInfoDTO.setFinalTaxFee(higoExtraInfoDTO.getFinalTaxFee());
        mopHigoExtraInfoDTO.setSupplyBase(higoExtraInfoDTO.getSupplyBase());
        mopHigoExtraInfoDTO.setDeliveryType(higoExtraInfoDTO.getDeliveryType());
        mopHigoExtraInfoDTO.setTaxRate(higoExtraInfoDTO.getTaxRate());

        return mopHigoExtraInfoDTO;
    }


    public static MopResponse getResponse(MopRespCode mopRespCode) {
        return new MopResponse(mopRespCode);
    }

    public static void main(String[] args){
        List<Character> characters = new ArrayList<Character>();
        for(char i='0'; i<='9'; i++){
            characters.add(i);
        }

        for(char i='a'; i<='z'; i++){
            characters.add(i);
        }

        for(char i='A'; i<='Z'; i++){
            characters.add(i);
        }

        System.out.println("size:"+characters.size());

        StringBuilder sb = new StringBuilder();
        long timestamp = System.currentTimeMillis();
        for(int i=0; i<8; i++){
            System.out.println("c="+characters.get((int) ((timestamp>>6*i)&0x3f)%characters.size()));
            sb.append(characters.get((int) ((timestamp>>6*i)&0x3f)%characters.size()));
        }
        System.out.println(sb.reverse().toString());
    }
}