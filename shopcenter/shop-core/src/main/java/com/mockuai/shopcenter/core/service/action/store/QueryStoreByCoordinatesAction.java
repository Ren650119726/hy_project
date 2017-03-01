package com.mockuai.shopcenter.core.service.action.store;

import com.google.common.base.Strings;
import com.mockuai.shopcenter.api.ShopResponse;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.StoreManager;
import com.mockuai.shopcenter.core.service.RequestContext;
import com.mockuai.shopcenter.core.service.ShopRequest;
import com.mockuai.shopcenter.core.service.action.TransAction;
import com.mockuai.shopcenter.core.util.ExceptionUtil;
import com.mockuai.shopcenter.core.util.ResponseUtil;
import com.mockuai.shopcenter.domain.dto.StoreDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 15/11/3.
 */
@Service
public class QueryStoreByCoordinatesAction extends TransAction{

    @Resource
    private StoreManager storeManager;

    private static final double MAX_LONGITUDE = 180;
    private static final double MIN_LONGITUDE = -180;
    private static final double MAX_LATITUDE = 90;
    private static final double MIN_LATITUDE = -90;

    @Override
    protected ShopResponse doTransaction(RequestContext context) throws ShopException {
        String bizCode = (String) context.get("bizCode");

        ShopRequest request = context.getRequest();

        Long sellerId  = request.getLong("sellerId");
        String longitudeStr = request.getString("longitude");
        String latitudeStr = request.getString("latitude");

        String supportRecovery = request.getString("supportRecovery");

        String supportRepair = request.getString("supportRepair");

        if(sellerId==null){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"seller_id不能为空");
        }

        if(longitudeStr==null){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"longitude不能为空");
        }

        if(latitudeStr==null){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"latitude不能为空");
        }

        if(longitudeStr==null){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"longitude不能为空");
        }

        double longitude = 0d;
        double latitude = 0d;

        try {

            longitude = Double.parseDouble(longitudeStr);

            if (longitude> MAX_LONGITUDE || longitude < MIN_LONGITUDE) {
                throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "longitude的值非法");
            }

        }catch (Exception e){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "longitude的值非法");
        }


        if(latitudeStr==null){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"latitude不能为空");
        }

        try {
            latitude =  Double.parseDouble(latitudeStr);

            if(latitude>MAX_LATITUDE||latitude<MIN_LATITUDE){
                throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID,"latitude的值非法");
            }

        }catch (Exception e){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "longitude的值非法");
        }

        if(!Strings.isNullOrEmpty(supportRecovery)&&supportRecovery.equals("1")){
            List<StoreDTO> storeDTOList = storeManager.queryRecoveryStoreByCoordinates(sellerId, longitude, latitude, bizCode);
            return ResponseUtil.getSuccessResponse(storeDTOList);
        }else if(!Strings.isNullOrEmpty(supportRepair)&&supportRepair.equals("1")){
            List<StoreDTO> storeDTOList = storeManager.queryRepairStoreByCoordinates(sellerId, longitude, latitude, bizCode);
            return ResponseUtil.getSuccessResponse(storeDTOList);
        }else {
            List<StoreDTO> storeDTOList = storeManager.queryStoreByCoordinates(sellerId, longitude, latitude, bizCode);
            return ResponseUtil.getSuccessResponse(storeDTOList);
        }


    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_STORE_BY_COORDINATES.getActionName();
    }
}
