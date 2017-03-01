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
import com.mockuai.tradecenter.common.util.StringUtil;
import com.mockuai.tradecenter.mop.api.domain.MopItemServiceDTO;
import com.mockuai.tradecenter.mop.api.util.JsonUtil;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class AddCartItem extends BaseAction {
    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
        String skuUid = (String) request.getParam("sku_uid");
        String numberStr = (String) request.getParam("number");
        //String distributorIdStr = (String) request.getParam("distributor_id");
        Long userId = (Long) request.getAttribute("user_id");
        String appKey = (String)request.getParam("app_key");
        String shareUserId = (String) request.getParam("share_user_id");
        
        String serviceListStr = (String) request.getParam("service_list");
        java.lang.reflect.Type type = new TypeToken<List<MopItemServiceDTO>>() {}.getType();
        List<MopItemServiceDTO> mopItemServiceDTOList = JsonUtil.parseJson(serviceListStr, type);
        
//        List<ItemServiceDTO> itemServiceDTOList = null;
//        if(mopItemServiceDTOList!=null&&mopItemServiceDTOList.size()>0){
//        	itemServiceDTOList = new ArrayList<ItemServiceDTO>();
//        	for(MopItemServiceDTO mopItemService:mopItemServiceDTOList){
//        		ItemServiceDTO itemServiceDTO = new ItemServiceDTO();
//        		itemServiceDTO.setServiceId(serviceId);
//        	}
//        }
        
        if(StringUtils.isBlank(skuUid)){
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "sku_uid is null");
        }

        if(StringUtils.isBlank(numberStr)){
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "number is null");
        }

      /*  if (StringUtils.isBlank(distributorIdStr)) {
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "distributor_id is null");
        }*/

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
        cartItemDTO.setItemSkuId(skuUidDTO.getSkuId());
        cartItemDTO.setSellerId(skuUidDTO.getSellerId());
        cartItemDTO.setNumber(Integer.valueOf(numberStr));
        if(StringUtil.isNotBlank(shareUserId))
            cartItemDTO.setShareUserId(Long.parseLong(shareUserId));
        //cartItemDTO.setDistributorId(Long.valueOf(distributorIdStr));
        cartItemDTO.setServiceList(genItemServiceList(mopItemServiceDTOList));
        com.mockuai.tradecenter.common.api.Request tradeReq = new BaseRequest();
        tradeReq.setCommand(ActionEnum.ADD_USER_CART_ITEM.getActionName());
        tradeReq.setParam("cartItemDTO", cartItemDTO);
        tradeReq.setParam("appKey", appKey);

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
        	String[] strs = mopItemServiceDTO.getServiceUid().split("_");
        	long sellerId = Long.parseLong(strs[0]);
            long serviceId = Long.parseLong(strs[1]);
        	itemServiceDTO.setServiceId(serviceId);
        	itemServiceDTOs.add(itemServiceDTO);
        }

        return itemServiceDTOs;
    }

    public String getName() {
        return "/trade/cart/item/add";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}