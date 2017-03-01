package com.mockuai.itemcenter.core.service.action.specialitem;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.manager.SpecialItemExtraInfoManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yindingyu on 15/12/12.
 */
@Service
public class GetSpecialItemExtraInfoAction extends TransAction{


    @Resource
    private SpecialItemExtraInfoManager specialItemExtraInfoManager;


    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {
        ItemRequest request = context.getRequest();
        String bizCode = (String) context.get("bizCode");

        if(request.getLong("skuId")==null){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "skuId不能为空");
        }

        Long skuId = request.getLong("skuId");


//        if(request.getLong("userId")==null){
//            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "userId不能为空");
//        }

        Long userId = request.getLong("userId");

        if(request.getLong("sellerId")==null){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"sellerId不能为空");
        }

        Long sellerId = request.getLong("sellerId");

        if(request.getInteger("itemType")==null){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"itemType");
        }

        Integer itemType = request.getInteger("itemType");


        String appKey = request.getString("appKey");


        Object obj = specialItemExtraInfoManager.getSpecialItemExtraInfo(skuId, sellerId,userId, itemType,appKey);


        return ResponseUtil.getSuccessResponse(obj);
    }

    @Override
    public String getName() {
        return ActionEnum.GET_SPECIAL_ITEM_EXTRA_INFO.getActionName();
    }
}
