package com.mockuai.shopcenter.core.service.action.store;

import com.google.common.base.Strings;
import com.mockuai.shopcenter.api.ShopResponse;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.constant.PropertyConsts;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.StoreManager;
import com.mockuai.shopcenter.core.manager.AddressManager;
import com.mockuai.shopcenter.core.service.RequestContext;
import com.mockuai.shopcenter.core.service.ShopRequest;
import com.mockuai.shopcenter.core.service.action.TransAction;
import com.mockuai.shopcenter.core.util.ExceptionUtil;
import com.mockuai.shopcenter.core.util.ResponseUtil;
import com.mockuai.shopcenter.domain.dto.StoreDTO;
import com.mockuai.usercenter.common.dto.UserConsigneeDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yindingyu on 15/11/3.
 */
@Service
public class GetStoreByAddressAction extends TransAction {

    @Resource
    private StoreManager storeManager;

    @Resource
    private AddressManager addressManager;

    private static final double MAX_LONGITUDE = 180;
    private static final double MIN_LONGITUDE = -180;
    private static final double MAX_LATITUDE = 90;
    private static final double MIN_LATITUDE = -90;

    @Override
    protected ShopResponse doTransaction(RequestContext context) throws ShopException {
        String bizCode = (String) context.get("bizCode");

        ShopRequest request = context.getRequest();

        Long sellerId = request.getLong("sellerId");
        Long addressId = request.getLong("addressId");
        Long userId = request.getLong("userId");

        String appKey = request.getString("appKey");

        String condition = request.getString("condition");

        if (sellerId == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "selleId不能为空");
        }

        if (userId == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "userId不能为空");
        }

        if (addressId == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "addressId不能为空");
        }

        if (Strings.isNullOrEmpty(condition)) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "condition不能为空");
        }

        if (!condition.equals(PropertyConsts.SUPPORT_DELIVERY)) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "不支持的condition类型");
        }


        UserConsigneeDTO userConsigneeDTO = addressManager.getAddress(userId, addressId, appKey);

        if (userConsigneeDTO == null) {
            throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "查询不到对应的地址记录");
        }

        if (Strings.isNullOrEmpty(userConsigneeDTO.getLongitude()) || Strings.isNullOrEmpty(userConsigneeDTO.getLatitude())) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "该条地址没有经纬度信息");
        }


        double longitude = 0L;
        double latitude = 0L;


        try {


            longitude = Double.parseDouble(userConsigneeDTO.getLongitude());

            if (longitude > MAX_LONGITUDE || longitude < MIN_LONGITUDE) {
                throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "longitude的值非法");
            }

        } catch (Exception e) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "longitude的值非法");
        }


        try {
            latitude = Double.parseDouble(userConsigneeDTO.getLatitude());

            if (latitude > MAX_LATITUDE || latitude < MIN_LATITUDE) {
                throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "latitude的值非法");
            }

        } catch (Exception e) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "longitude的值非法");
        }


        StoreDTO storeDTO = storeManager.getStoreByCoordinates(sellerId, longitude, latitude, bizCode);
        return ResponseUtil.getSuccessResponse(storeDTO);


    }

    @Override
    public String getName() {
        return ActionEnum.GET_STORE_BY_ADDRESS.getActionName();
    }
}
