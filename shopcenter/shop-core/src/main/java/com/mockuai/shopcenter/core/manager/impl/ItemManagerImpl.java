package com.mockuai.shopcenter.core.manager.impl;

import com.mockuai.itemcenter.client.ItemClient;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSearchDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSearchQTO;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.ItemManager;
import com.mockuai.shopcenter.core.util.ExceptionUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 16/1/15.
 */
@Service
public class ItemManagerImpl implements ItemManager {

    @Resource
    private ItemClient itemClient;

    @Override
    public List<ItemDTO> queryItem(ItemQTO itemQTO,String appKey) throws ShopException{

        Response<List<ItemDTO>> response;

        try {

            response = itemClient.queryItem(itemQTO,appKey);
        }catch (Exception e){
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEFAULT_ERROR,"查询商品时出现问题");
        }

        if(response.getCode()== ResponseCode.SUCCESS.getCode()){
            return response.getModule();
        }else{
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEFAULT_ERROR,response.getCode()+response.getMessage());
        }

    }

    @Override
    public List<ItemSearchDTO> queryItem(ItemSearchQTO itemSearchQTO, String appKey) throws ShopException {
        Response<List<ItemSearchDTO>> response;

        try {

            response = itemClient.searchItem(itemSearchQTO,appKey);
        }catch (Exception e){
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEFAULT_ERROR,"查询商品时出现问题");
        }

        if(response.getCode()== ResponseCode.SUCCESS.getCode()){
            return response.getModule();
        }else{
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEFAULT_ERROR,response.getCode()+response.getMessage());
        }
    }
}
