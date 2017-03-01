package com.mockuai.tradecenter.mop.api.action;


import com.google.common.collect.Lists;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.BizMarkDTO;
import com.mockuai.tradecenter.common.domain.CartItemDTO;
import com.mockuai.tradecenter.common.domain.distributor.DistributorCartDTO;
import com.mockuai.tradecenter.mop.api.domain.MopBizMarkDTO;
import com.mockuai.tradecenter.mop.api.domain.MopCartActivityInfoDTO;
import com.mockuai.tradecenter.mop.api.domain.MopCartItemDTO;
import com.mockuai.tradecenter.mop.api.domain.MopDistributorCartDTO;
import com.mockuai.tradecenter.mop.api.util.MopApiUtil;
import org.springframework.beans.BeanUtils;

import java.util.*;

public class CartItemListForDistributor extends BaseAction {
    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
        Long userId = (Long) request.getAttribute("user_id");
        String appKey = (String)request.getParam("app_key");

        com.mockuai.tradecenter.common.api.Request tradeReq = new BaseRequest();
        tradeReq.setCommand(ActionEnum.QUERY_USERCART_ITEMS.getActionName());
        tradeReq.setParam("userId", userId);
        tradeReq.setParam("appKey", appKey);

        Response tradeResp = getTradeService().execute(tradeReq);
        if (tradeResp.getCode() == ResponseCode.RESPONSE_SUCCESS.getCode()) {
            Map data = new HashMap();
            List<DistributorCartDTO> distributorCartDTOs = (List<DistributorCartDTO>) tradeResp.getModule();
            data.put("distributor_cart_list", genMopCartItemList(distributorCartDTOs));
            data.put("total_count",tradeResp.getTotalCount());
            return new MopResponse(data);
        }

        return new MopResponse(tradeResp.getCode(), tradeResp.getMessage());
    }

    private List<MopDistributorCartDTO> genMopCartItemList(List<DistributorCartDTO> distributorCartDTOs) {
        if (distributorCartDTOs == null) {
            return Collections.emptyList();
        }

        List mopSupplierCartDTOs = new ArrayList();
        for (DistributorCartDTO distributorCartDTO : distributorCartDTOs) {
            MopDistributorCartDTO mopDistributorCartDTO = new MopDistributorCartDTO();
            //mopDistributorCartDTO.setDistributorId(distributorCartDTO.getDistributorId());
            //mopDistributorCartDTO.setDistributorName(distributorCartDTO.getDistributorName());

            List mopCartItemDTOs = new ArrayList();
            for(CartItemDTO cartItemDTO:distributorCartDTO.getCartItems()){
                mopCartItemDTOs.add(genMopCartItem(cartItemDTO));
            }

            mopDistributorCartDTO.setItemList(mopCartItemDTOs);

            mopDistributorCartDTO.setDiscountInfoList(distributorCartDTO.getDiscountInfoList());

            mopSupplierCartDTOs.add(mopDistributorCartDTO);
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
        //购物车商品sku快照信息
        mopCartItemDTO.setSkuSnapshot(cartItemDTO.getItemSkuDesc());
        mopCartItemDTO.setIconUrl(cartItemDTO.getItemImageUrl());
        mopCartItemDTO.setSellerId(cartItemDTO.getSellerId());
        mopCartItemDTO.setIconUrl(cartItemDTO.getItemImageUrl());
        mopCartItemDTO.setDeliveryType(cartItemDTO.getDeliveryType());
        mopCartItemDTO.setMarketPrice(cartItemDTO.getMarketPrice());
        mopCartItemDTO.setPromotionPrice(cartItemDTO.getPromotionPrice());
        mopCartItemDTO.setWirelessPrice(cartItemDTO.getWirelessPrice());
        mopCartItemDTO.setNumber(cartItemDTO.getNumber());
        if(cartItemDTO.getBizMarkList() != null){
            List<MopBizMarkDTO>  mopBizMarkDTOList = Lists.newArrayList();
            for(BizMarkDTO bizMarkDTO : cartItemDTO.getBizMarkList() ){
                MopBizMarkDTO item = new MopBizMarkDTO();
                BeanUtils.copyProperties(bizMarkDTO,item);
                mopBizMarkDTOList.add(item);
            }
            mopCartItemDTO.setBizMarkList(mopBizMarkDTOList);
        }
        if(cartItemDTO.getItemType()!=null&&cartItemDTO.getItemType().intValue()==11){
        	MopCartActivityInfoDTO mopActivityInfoDTO = new MopCartActivityInfoDTO();
        	mopActivityInfoDTO.setItemList(genSubMopCartItemList(cartItemDTO));
        	mopCartItemDTO.setActivityInfo(mopActivityInfoDTO);
        }
        
        //增值服务
        mopCartItemDTO.setServiceList(MopApiUtil.genMopItemServiceList(cartItemDTO));
        //商品购买限制
        mopCartItemDTO.setSaleMinNum(cartItemDTO.getSaleMinNum());
        mopCartItemDTO.setSaleMaxNum(cartItemDTO.getSaleMaxNum());
        //商品状态
        mopCartItemDTO.setStatus(cartItemDTO.getStatus());
        //商品库存
        mopCartItemDTO.setStockNum(cartItemDTO.getStockNum());
        mopCartItemDTO.setShareUserId(cartItemDTO.getShareUserId());
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
            //购物车商品sku快照信息
            mopCartItemDTO.setSkuSnapshot(cartItemDTO.getItemSkuDesc());
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
        return "/trade/distributor/cart/item/list";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}