package com.mockuai.itemcenter.core.manager.impl;

import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ShopManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.shopcenter.ShopClient;
import com.mockuai.shopcenter.api.Response;
import com.mockuai.shopcenter.domain.dto.ShopDTO;
import com.mockuai.shopcenter.domain.dto.ShopItemGroupDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yindingyu on 16/1/18.
 */
@Service
public class ShopManagerImpl implements ShopManager {

    @Resource
    private ShopClient shopClient;


    @Override
    public ShopDTO getShop(Long sellerId, String appKey) throws ItemException {


        Response<ShopDTO> response;

        try {
            response = shopClient.getShop(sellerId,appKey);
        }catch (Exception e){

            return null;
            //throw ExceptionUtil.getException(ResponseCode.SYS_E_DEFAULT_ERROR,"查询店铺信息时出现问题");
        }

        if(response.getCode()== ResponseCode.SUCCESS.getCode())
        {
            return response.getModule();
        }else {
            return null;
        }

    }

    @Override
    public ShopItemGroupDTO getShopItemGroup(Long sellerId,Long groupId,String appKey) throws ItemException {
        Response<ShopItemGroupDTO> response;

        try {
            response = shopClient.getShopItemGroup(sellerId,groupId,"1",appKey);
        }catch (Exception e){

            return null;
            //throw ExceptionUtil.getException(ResponseCode.SYS_E_DEFAULT_ERROR,"查询店铺信息时出现问题");
        }

        if(response.getCode()== ResponseCode.SUCCESS.getCode())
        {
            return response.getModule();
        }else {
            return null;
        }
    }
}
