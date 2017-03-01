package com.mockuai.itemcenter.core.service.action.itemsku;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuPropertyRecommendationDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.manager.ItemSkuPropertyRecommendationManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yindingyu on 16/1/11.
 */
@Service
public class AddItemSkuPropertyRecommendationAction extends TransAction {

    @Resource
    private ItemSkuPropertyRecommendationManager itemSkuPropertyRecommendationManager;

    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {

        String bizCode = (String) context.get("bizCode");

        ItemSkuPropertyRecommendationDTO itemSkuPropertyRecommendationDTO = context.getRequest().getObject("itemSkuPropertyRecommendationDTO", ItemSkuPropertyRecommendationDTO.class);

        if(itemSkuPropertyRecommendationDTO==null){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"itemSkuPropertyRecommendationDTO不能为空");
        }

        itemSkuPropertyRecommendationDTO.setBizCode(bizCode);

        Long result = itemSkuPropertyRecommendationManager.addItemSkuPropertyRecommendation(itemSkuPropertyRecommendationDTO);


        return ResponseUtil.getSuccessResponse(result);
    }

    @Override
    public String getName() {
        return ActionEnum.ADD_ITEM_SKU_PROPERTY_RECOMMENDATION.getActionName();
    }
}
