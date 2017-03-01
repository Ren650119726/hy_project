package com.mockuai.tradecenter.mop.api.action;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.CartItemDTO;
import com.mockuai.tradecenter.common.domain.supplier.SupplierCartDTO;
import com.mockuai.tradecenter.mop.api.domain.MopCartActivityInfoDTO;
import com.mockuai.tradecenter.mop.api.domain.MopCartItemDTO;
import com.mockuai.tradecenter.mop.api.domain.MopSupplierCartDTO;
import com.mockuai.tradecenter.mop.api.util.MopApiUtil;

public class CartItemListForSupplier extends BaseAction {
    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
        Long userId = (Long) request.getAttribute("user_id");
        String appKey = (String)request.getParam("app_key");

        com.mockuai.tradecenter.common.api.Request tradeReq = new BaseRequest();
        tradeReq.setCommand(ActionEnum.QUERY_USERCART_ITEMS_FOR_SUPPLIER.getActionName());
        tradeReq.setParam("userId", userId);
        tradeReq.setParam("appKey", appKey);

        Response tradeResp = getTradeService().execute(tradeReq);
        if (tradeResp.getCode() == ResponseCode.RESPONSE_SUCCESS.getCode()) {
            Map data = new HashMap();
            List<SupplierCartDTO> supplierCartDTOs = (List<SupplierCartDTO>) tradeResp.getModule();
            data.put("supplier_cart_list", genMopCartItemList(supplierCartDTOs));
            data.put("total_count",tradeResp.getTotalCount());
            return new MopResponse(data);
        }

        return new MopResponse(tradeResp.getCode(), tradeResp.getMessage());
    }

    private List<MopSupplierCartDTO> genMopCartItemList(List<SupplierCartDTO> supplierCartDTOs) {
        if (supplierCartDTOs == null) {
            return Collections.emptyList();
        }

        List mopSupplierCartDTOs = new ArrayList();  
        for (SupplierCartDTO supplierCartDTO : supplierCartDTOs) {
        	MopSupplierCartDTO mopSupplierCartDTO = new MopSupplierCartDTO();
        	mopSupplierCartDTO.setSupplierId(supplierCartDTO.getSupplierId());
        	mopSupplierCartDTO.setSupplierName(supplierCartDTO.getSupplierName());
        
        	List mopCartItemDTOs = new ArrayList();
        	for(CartItemDTO cartItemDTO:supplierCartDTO.getCartItems()){
        		 mopCartItemDTOs.add(genMopCartItem(cartItemDTO));
        	}
        	
        	mopSupplierCartDTO.setItemList(mopCartItemDTOs);
        	
        	mopSupplierCartDTO.setDiscountInfoList(supplierCartDTO.getDiscountInfoList());
        	
        	mopSupplierCartDTOs.add(mopSupplierCartDTO);
        }

        return mopSupplierCartDTOs;
    }

    private MopCartItemDTO genMopCartItem(CartItemDTO cartItemDTO) {
        if (cartItemDTO == null) {
            return null;
        }

        MopCartItemDTO mopCartItemDTO = new MopCartItemDTO();

        mopCartItemDTO.setCartItemUid(cartItemDTO.getUserId() + "_" + cartItemDTO.getId());
        mopCartItemDTO.setSkuUid(cartItemDTO.getSellerId() + "_" + cartItemDTO.getItemSkuId());
        mopCartItemDTO.setItemUid(cartItemDTO.getSellerId() + "_" + cartItemDTO.getItemId());
        mopCartItemDTO.setItemType(cartItemDTO.getItemType());
        mopCartItemDTO.setItemName(cartItemDTO.getItemName());
        mopCartItemDTO.setItemSkuDesc(cartItemDTO.getItemSkuDesc());
        mopCartItemDTO.setIconUrl(cartItemDTO.getItemImageUrl());
        mopCartItemDTO.setSellerId(cartItemDTO.getSellerId());
        mopCartItemDTO.setIconUrl(cartItemDTO.getItemImageUrl());
        mopCartItemDTO.setDeliveryType(cartItemDTO.getDeliveryType());
        mopCartItemDTO.setMarketPrice(cartItemDTO.getMarketPrice());
        mopCartItemDTO.setPromotionPrice(cartItemDTO.getPromotionPrice());
        mopCartItemDTO.setWirelessPrice(cartItemDTO.getWirelessPrice());
        mopCartItemDTO.setNumber(cartItemDTO.getNumber());
        
        if(cartItemDTO.getItemType()!=null&&cartItemDTO.getItemType().intValue()==11){
        	MopCartActivityInfoDTO mopActivityInfoDTO = new MopCartActivityInfoDTO();
        	mopActivityInfoDTO.setItemList(genSubMopCartItemList(cartItemDTO));
        	mopCartItemDTO.setActivityInfo(mopActivityInfoDTO);
        }
        
        //增值服务
        mopCartItemDTO.setServiceList(MopApiUtil.genMopItemServiceList(cartItemDTO));
        
        return mopCartItemDTO;
    }
    
    public List<MopCartItemDTO> genSubMopCartItemList(CartItemDTO cartItemDTO){
    	if(cartItemDTO.getSubCartList()==null){
    		return null;
    	}
    	List<MopCartItemDTO> subCartItemList = new ArrayList<MopCartItemDTO>();
    	
    	for(CartItemDTO subCartItem:cartItemDTO.getSubCartList()){
    		MopCartItemDTO mopCartItemDTO = new MopCartItemDTO();

            mopCartItemDTO.setCartItemUid(subCartItem.getUserId() + "_" + subCartItem.getId());
            mopCartItemDTO.setSkuUid(subCartItem.getSellerId() + "_" + subCartItem.getItemSkuId());
            mopCartItemDTO.setItemUid(subCartItem.getSellerId() + "_" + subCartItem.getItemId());
            mopCartItemDTO.setItemType(subCartItem.getItemType());
            mopCartItemDTO.setItemName(subCartItem.getItemName());
            mopCartItemDTO.setItemSkuDesc(subCartItem.getItemSkuDesc());
            mopCartItemDTO.setIconUrl(subCartItem.getItemImageUrl());
            mopCartItemDTO.setSellerId(subCartItem.getSellerId());
            mopCartItemDTO.setIconUrl(subCartItem.getItemImageUrl());
            mopCartItemDTO.setDeliveryType(subCartItem.getDeliveryType());
            mopCartItemDTO.setMarketPrice(subCartItem.getMarketPrice());
            mopCartItemDTO.setPromotionPrice(subCartItem.getPromotionPrice());
            mopCartItemDTO.setWirelessPrice(subCartItem.getWirelessPrice());
            mopCartItemDTO.setNumber(subCartItem.getNumber());
            mopCartItemDTO.setItemUrl(subCartItem.getItemUrl());
            subCartItemList.add(mopCartItemDTO);
    	}
    	return subCartItemList;
    	
    }

    public String getName() {
        return "/trade/supplier/cart/item/list";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}

/* Location:           /work/tmp/trade-mop-api-0.0.1-20150519.033139-22.jar
 * Qualified Name:     com.mockuai.tradecenter.mop.api.action.CartItemList
 * JD-Core Version:    0.6.2
 */