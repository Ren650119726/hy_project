package com.mockuai.itemcenter.core.manager.impl;

import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ShopPropertyManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.shopcenter.ShopPropertyClient;
import com.mockuai.shopcenter.api.Response;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yindingyu on 15/11/13.
 */
@Service
public class ShopPropertyManagerImpl implements ShopPropertyManager{

    @Resource
    private ShopPropertyClient shopPropertyClient;

    public String getShopConfig(String key, Long sellerId, String appKey) throws ItemException {

        Response<String> response;

        try{
            response = shopPropertyClient.getShopProperty(sellerId,key,appKey);
        }catch (Exception e){
            throw ExceptionUtil.getException(ResponseCode.SYS_E_SERVICE_EXCEPTION,"查询门店功能开关时出现问题，请稍后重试");
        }

        if(response.getCode()== ResponseCode.SUCCESS.getCode()){
            return response.getModule();
        }else if(response.getCode()==ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST.getCode()){
            return "0";
        }else{
            throw ExceptionUtil.getException(ResponseCode.SYS_E_SERVICE_EXCEPTION,response.getMessage());
        }
    }
}
