package com.mockuai.itemcenter.mop.api.action;


import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.mop.api.domain.SkuUidDTO;
import com.mockuai.itemcenter.mop.api.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;

/**
 * 提供对外的接口用于更新库存
 * @author cwr
 *
 */
public class updateItemStock extends BaseAction{
	
    public MopResponse execute(Request request) {

		String skuUidStr = (String)request.getParam("sku_uid");
		String numberStr = (String)request.getParam("number");
        String appKey = (String)request.getParam("app_key");

        SkuUidDTO skuUidDTO = MopApiUtil.parseSkuUid(skuUidStr);
		Long number = Long.valueOf(numberStr);
		
        com.mockuai.itemcenter.common.api.Request itemRequest = new BaseRequest();
        ItemSkuDTO itemSkuDTO =new ItemSkuDTO();
        
        itemSkuDTO.setId(skuUidDTO.getSkuId());
        itemSkuDTO.setSellerId(skuUidDTO.getSellerId());
        itemSkuDTO.setStockNum(number);
        
        itemRequest.setParam("itemSkuDTO", itemSkuDTO);
        itemRequest.setParam("appKey", appKey);
        itemRequest.setCommand(ActionEnum.UPDATE_ITEM_SKU.getActionName());
        
        Response<Boolean> response = this.getItemService().execute(itemRequest);
        if(response.getCode() == ResponseCode.SUCCESS.getCode()){
            return new MopResponse(response.getModule());
        }else{
            return new MopResponse(response.getCode(), response.getMessage());
        }
    }

    public String getName() {
        return "/item/sku/stock_number/update";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
		
}
