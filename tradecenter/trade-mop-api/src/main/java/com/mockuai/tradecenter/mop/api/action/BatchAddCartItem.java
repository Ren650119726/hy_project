package com.mockuai.tradecenter.mop.api.action;


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
import com.mockuai.tradecenter.common.domain.CartItemDTO;
import com.mockuai.tradecenter.common.domain.ItemServiceDTO;
import com.mockuai.tradecenter.common.domain.SkuUidDTO;
import com.mockuai.tradecenter.common.util.ModelUtil;
import com.mockuai.tradecenter.mop.api.domain.MopCartItemDTO;
import com.mockuai.tradecenter.mop.api.domain.MopItemServiceDTO;
import com.mockuai.tradecenter.mop.api.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class BatchAddCartItem extends BaseAction {
    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
      
        Long userId = (Long) request.getAttribute("user_id");
        String appKey = (String)request.getParam("app_key");

        /**
         * 前端过来的购物车商品信息是JSON形式的字符串
         */
        String cartListStr = (String) request.getParam("cart_item_list");
        if(StringUtils.isBlank(cartListStr)){
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "cart_item_list is null");
        }

        /**
         * typeToken保留泛型的类信息，能够保证正确序列化为JSON
         */
        java.lang.reflect.Type type = new TypeToken<List<MopCartItemDTO>>() {}.getType();
        List<MopCartItemDTO> mopCartItemDTOList = JsonUtil.parseJson(cartListStr, type);
        
        if(mopCartItemDTOList==null||mopCartItemDTOList.isEmpty()){
        	  return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "cart_item_list is empty");
        }

        /**
         * 此处将MopCartItemDTO 转化为 CartItemDTO
         */
        List<CartItemDTO> cartItemDTOs = new ArrayList<CartItemDTO>();
        for(MopCartItemDTO mopCartItemDTO:mopCartItemDTOList){
        	String skuUid = mopCartItemDTO.getSkuUid();
        	if(StringUtils.isBlank(skuUid)){
                return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "sku_uid is null");
            }
        	Integer number = mopCartItemDTO.getNumber();

            if(null==number){
                return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "number is null");
            }
            
            CartItemDTO cartItemDTO = new CartItemDTO();
            cartItemDTO.setUserId(userId);

            SkuUidDTO skuUidDTO = null;
            try{
                skuUidDTO = ModelUtil.parseSkuUid(skuUid);
            }catch(Exception e){
                return new MopResponse(MopRespCode.P_E_PARAM_FORMAT_INVALID, "sku_uid's format is invalid");
            }
            if(null==skuUidDTO||null==skuUidDTO.getSkuId()){
            	return new MopResponse(MopRespCode.P_E_PARAM_FORMAT_INVALID, "sku_uid's format is invalid");
            }
            if(mopCartItemDTO.getShareUserId() != null){
                cartItemDTO.setShareUserId(mopCartItemDTO.getShareUserId());
            }
            cartItemDTO.setItemSkuId(skuUidDTO.getSkuId());
            cartItemDTO.setSellerId(skuUidDTO.getSellerId());
            cartItemDTO.setNumber(number);
            //TODO 这里的setServiceList是否要保留
//            cartItemDTO.setServiceList(genItemServiceList(mopCartItemDTO.getServiceList()));
            cartItemDTOs.add(cartItemDTO);
            
        }

        /**
         * 把Request做出来
         */
        com.mockuai.tradecenter.common.api.Request tradeReq = new BaseRequest();
        tradeReq.setCommand(ActionEnum.BATCH_ADD_USER_CARTITEM_J.getActionName());
        tradeReq.setParam("cartItemDTOList", cartItemDTOs);
        tradeReq.setParam("appKey", appKey);

        /**
         * execute并判断返回的状态吗
         */
        Response tradeResp = getTradeService().execute(tradeReq);
        if (tradeResp.getCode() == ResponseCode.RESPONSE_SUCCESS.getCode()) {
            return new MopResponse(MopRespCode.REQUEST_SUCESS);
        }

        return new MopResponse(tradeResp.getCode(), tradeResp.getMessage());
    }
    
    public static List<ItemServiceDTO> genItemServiceList(List<MopItemServiceDTO> mopItemServiceDTOs){
        if(mopItemServiceDTOs == null){
            return null;
        }
        List<ItemServiceDTO> itemServiceDTOs = new CopyOnWriteArrayList<ItemServiceDTO>();
        for(MopItemServiceDTO mopItemServiceDTO: mopItemServiceDTOs){
        	ItemServiceDTO itemServiceDTO = new ItemServiceDTO();
            /**
             * 约定了ServiceUid是以下划线隔开
             */
        	String[] strs = mopItemServiceDTO.getServiceUid().split("_");
        	long sellerId = Long.parseLong(strs[0]);
            long serviceId = Long.parseLong(strs[1]);
        	itemServiceDTO.setServiceId(serviceId);
        	itemServiceDTOs.add(itemServiceDTO);
        }

        return itemServiceDTOs;
    }

    public String getName() {
        return "/trade/cart/batch/add";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}

/* Location:           /work/tmp/trade-mop-api-0.0.1-20150519.033139-22.jar
* Qualified Name:     com.mockuai.tradecenter.mop.api.action.AddCartItem
* JD-Core Version:    0.6.2
*/