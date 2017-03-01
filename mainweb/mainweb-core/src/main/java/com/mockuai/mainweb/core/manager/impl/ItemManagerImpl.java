package com.mockuai.mainweb.core.manager.impl;

import com.mockuai.itemcenter.client.ItemClient;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemImageDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.mainweb.common.constant.ResponseCode;
import com.mockuai.mainweb.core.exception.MainWebException;
import com.mockuai.mainweb.core.manager.ItemManager;
import com.mockuai.mainweb.core.util.ExceptionUtil;
import com.mockuai.mainweb.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/9/23.
 */
@Service
public class ItemManagerImpl implements ItemManager {


    @Resource
    ItemClient itemClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemManagerImpl.class);


    @Override
    public List<ItemDTO> queryItem(ItemQTO itemQTO, String appKey) throws MainWebException {
        Response<List<ItemDTO>> response ;
        try {
            response = itemClient.queryItem(itemQTO, appKey);
        }catch (Exception e) {
            LOGGER.error("error to queryItem, itemQTO : {}",
                    JsonUtil.toJson(itemQTO),e);
            throw  new MainWebException(ResponseCode.SYS_E_SERVICE_EXCEPTION,
                    String.format("error to queryItem, itemQTO : %s, appKey :%s"
                            ,JsonUtil.toJson(itemQTO),appKey));
        }
        if (response.isSuccess()) {
            return response.getModule();
        }
        LOGGER.error("error to queryItem, itemQTO : {}, appKey, resCode : {}, resMsg : {}",
                JsonUtil.toJson(itemQTO),response.getCode(),response.getMessage());

        throw ExceptionUtil.getException(ResponseCode.SYS_E_DEFAULT_ERROR,
                String.format("error to queryItem itemId:%s,appKey:%s",JsonUtil.toJson(itemQTO),appKey));
    }


    @Override
    public ItemDTO getItemById(Long itemId,Long sellerId,Boolean needDetail,String appKey) throws MainWebException {

        needDetail = true;
        Response<ItemDTO> itemDTOResponse = null;
        try {
            itemDTOResponse =  itemClient.getItem(itemId,sellerId,needDetail,appKey);

            if(itemDTOResponse.isSuccess()){
                ItemDTO itemDTO = itemDTOResponse.getModule();
                if(itemDTO != null && itemDTO.getIconUrl() == null){
                    itemDTO.setIconUrl(findPrimaryImage(itemDTO));
                }
                return  itemDTO;
            }else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.error("商品获取失败, itemId : {} 检查商品有效性",
                    itemId,appKey,e);
//            throw  new MainWebException(ResponseCode.SYS_E_SERVICE_EXCEPTION,  e);
            return null;
        }

//        LOGGER.error("商品获取失败, itemId : {} 检查商品有效性",
//                itemId,appKey,itemDTOResponse.getCode(),itemDTOResponse.getMessage());
//        throw ExceptionUtil.getException(ResponseCode.SYS_E_DEFAULT_ERROR,
//                String.format("error to getItemById itemId:%s,appKey:%s",itemId,appKey));
    }


    private String findPrimaryImage(ItemDTO itemDTO){
        String imgUrl = null;
        if (itemDTO.getItemImageDTOList()!=null) {
            List<ItemImageDTO> itemImageDTOs = itemDTO.getItemImageDTOList();
            for (ItemImageDTO itemImage:itemImageDTOs){
                if (itemImage.getImageType() == 1){
                    imgUrl = itemImage.getImageUrl();
                }
            }
        }
        return imgUrl;
    }



}
