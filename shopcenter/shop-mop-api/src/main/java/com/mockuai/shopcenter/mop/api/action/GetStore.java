package com.mockuai.shopcenter.mop.api.action;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.shopcenter.api.BaseRequest;
import com.mockuai.shopcenter.api.Response;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.domain.dto.ShopDTO;
import com.mockuai.shopcenter.domain.dto.StoreDTO;
import com.mockuai.shopcenter.mop.api.util.MopApiUtil;
import com.mockuai.shopcenter.mop.api.util.StoreUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by luliang on 15/7/27.
 */
public class GetStore extends BaseAction {

    public MopResponse execute(Request request) {

        String appKey = (String) request.getParam("app_key");
        String storeUid = (String) request.getParam("store_uid");

        if(storeUid == null){
            return new MopResponse(ResponseCode.PARAM_E_MISSING.getCode(),"store_uid为空");
        }

        Long id = StoreUtil.getId(storeUid);
        Long sellerId = StoreUtil.getSellerId(storeUid);

        if( id == null || sellerId == null ){
            return new MopResponse(ResponseCode.PARAM_E_INVALID.getCode(),"store_uid格式不正确");
        }


        com.mockuai.shopcenter.api.Request shopRequest = new BaseRequest();

        shopRequest.setParam("id", id);
        shopRequest.setParam("sellerId", sellerId);
        shopRequest.setParam("appKey", appKey);

        Response<StoreDTO> shopResponse = getShopService().execute(shopRequest);

        shopRequest.setCommand(ActionEnum.GET_STORE.getActionName());

        if(shopResponse.getCode()==ResponseCode.SUCCESS.getCode()){

            Map result = new HashMap<String,Object>();
            result.put("store",MopApiUtil.genMopStoreDTO(shopResponse.getModule()));

            return new MopResponse(result);
        }else{
            return new MopResponse(shopResponse.getCode(),shopResponse.getMessage());
        }
    }

    public String getName() {
        return "/store/get";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.NO_LIMIT;
    }
}
