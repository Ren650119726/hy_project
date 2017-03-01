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
import com.mockuai.shopcenter.domain.dto.StoreDTO;
import com.mockuai.shopcenter.domain.qto.StoreQTO;
import com.mockuai.shopcenter.mop.api.util.MopApiUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luliang on 15/7/27.
 */
public class GetStoreByCoordinates extends BaseAction {

    public MopResponse execute(Request request) {

        String appKey = (String) request.getParam("app_key");
        String sellerIdStr = (String) request.getParam("seller_id");
        String longitude = (String) request.getParam("longitude");
        String latitude = (String) request.getParam("latitude");


        if(sellerIdStr == null){
            return new MopResponse(ResponseCode.PARAM_E_MISSING.getCode(),"seller_id为空");
        }

        long sellerId = 0L;

        try{
            sellerId = Long.parseLong(sellerIdStr);
        }catch (Exception e){
            return new MopResponse(ResponseCode.PARAM_E_INVALID.getCode(),"seller_id格式错误");
        }

        if(longitude == null){
            return new MopResponse(ResponseCode.PARAM_E_MISSING.getCode(),"longitude为空");
        }

        if(latitude == null){
            return new MopResponse(ResponseCode.PARAM_E_MISSING.getCode(),"latitude为空");
        }

        com.mockuai.shopcenter.api.Request shopRequest = new BaseRequest();

        shopRequest.setParam("longitude", longitude);
        shopRequest.setParam("latitude", latitude);
        shopRequest.setParam("sellerId", sellerId);
        shopRequest.setParam("appKey", appKey);

        if(request.getParam("support_recovery")!=null&&request.getParam("support_recovery").equals("1")){
            shopRequest.setParam("supportRecovery", "1");
        }

        if(request.getParam("support_repair")!=null&&request.getParam("support_repair").equals("1")){
            shopRequest.setParam("supportRepair","1");
        }


        shopRequest.setCommand(ActionEnum.GET_STORE_BY_COORDINATES.getActionName());

        Response<StoreDTO> shopResponse = getShopService().execute(shopRequest);


        if(shopResponse.getCode()==ResponseCode.SUCCESS.getCode()){

            Map result = new HashMap<String,Object>();
            result.put("store",MopApiUtil.genMopStoreDTO(shopResponse.getModule()));
            return new MopResponse(result);
        }else{
            return new MopResponse(shopResponse.getCode(),shopResponse.getMessage());
        }
    }
    public String getName() {
        return "/store/by_coordinates/get";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
