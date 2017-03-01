package com.mockuai.shopcenter.mop.api.action;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.shopcenter.api.BaseRequest;
import com.mockuai.shopcenter.api.Response;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.domain.dto.StoreDTO;
import com.mockuai.shopcenter.domain.qto.StoreQTO;
import com.mockuai.shopcenter.mop.api.util.MopApiUtil;
import com.mockuai.shopcenter.mop.api.util.StoreUtil;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luliang on 15/7/27.
 */
public class ListStore extends BaseAction {

    public MopResponse execute(Request request) {

        String appKey = (String) request.getParam("app_key");
        String sellerIdStr = (String) request.getParam("seller_id");
        String offsetStr = (String) request.getParam("offset");
        String countStr = (String) request.getParam("count");

        String longitude = (String) request.getParam("longitude");
        String latitude = (String) request.getParam("latitude");


        String supportPickUpStr = (String)request.getParam("support_pick_up");

        StoreQTO storeQTO = new StoreQTO();

        if(supportPickUpStr!=null&&supportPickUpStr.equals("1")){
            storeQTO.setSupportPickUp(1);
        }



        if(sellerIdStr == null){
            return new MopResponse(ResponseCode.PARAM_E_MISSING.getCode(),"seller_id为空");
        }

        if(offsetStr == null){
            return new MopResponse(ResponseCode.PARAM_E_MISSING.getCode(),"offset为空");
        }

        if(countStr == null){
            return new MopResponse(ResponseCode.PARAM_E_MISSING.getCode(),"count为空");
        }


        long sellerId = 0L;
        int count = 0;
        int offset = 0;

        try{
            sellerId = Long.parseLong(sellerIdStr);
        }catch (Exception e){
            return new MopResponse(ResponseCode.PARAM_E_MISSING.getCode(),"sellerId格式错误");
        }

        try{
            count = Integer.parseInt(countStr);
        }catch (Exception e){
            return new MopResponse(ResponseCode.PARAM_E_MISSING.getCode(),"count格式错误");
        }

        try{
            offset = Integer.parseInt(offsetStr);
        }catch (Exception e){
            return new MopResponse(ResponseCode.PARAM_E_MISSING.getCode(),"offset格式错误");
        }

        com.mockuai.shopcenter.api.Request shopRequest = new BaseRequest();


        storeQTO.setSellerId(sellerId);
        storeQTO.setNeedPaging(true);
        storeQTO.setPageSize(count);
        storeQTO.setOffset(offset);
        storeQTO.setLatitude(latitude);
        storeQTO.setLongitude(longitude);
        shopRequest.setParam("appKey", appKey);
        shopRequest.setParam("storeQTO", storeQTO);

        if(request.getParam("support_recovery")!=null&&request.getParam("support_recovery").equals("1")){
            shopRequest.setParam("supportRecovery", "1");
        }

        if(request.getParam("support_repair")!=null&&request.getParam("support_repair").equals("1")){
            shopRequest.setParam("supportRepair","1");
        }


        shopRequest.setCommand(ActionEnum.QUERY_STORE.getActionName());

        Response<List<StoreDTO>> shopResponse = getShopService().execute(shopRequest);


        if(shopResponse.getCode()==ResponseCode.SUCCESS.getCode()){

            Map result = new HashMap<String,Object>();
            result.put("store_list",MopApiUtil.genMopStoreDTOList(shopResponse.getModule()));
            result.put("totalcount",shopResponse.getTotalCount());
            result.put("total_count",shopResponse.getTotalCount());
            return new MopResponse(result);

        }else{
            return new MopResponse(shopResponse.getCode(),shopResponse.getMessage());
        }
    }

    public String getName() {
        return "/store/list";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.NO_LIMIT;
    }
}
