package com.mockuai.itemcenter.core.service.action.itemsku;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuRecommendationDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.manager.ItemSkuRecommendationManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yindingyu on 16/1/11.
 */
@Service
public class AddItemSkuRecommendationAction extends TransAction {

    @Resource
    private ItemSkuRecommendationManager itemSkuRecommendationManager;

    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {

        String bizCode = (String) context.get("bizCode");

        ItemSkuRecommendationDTO itemSkuRecommendationDTO = context.getRequest().getObject("itemSkuRecommendationDTO", ItemSkuRecommendationDTO.class);

        if(itemSkuRecommendationDTO==null){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"itemSkuRecommendationDTO不能为空");
        }

        itemSkuRecommendationDTO.setBizCode(bizCode);

        Long result =  itemSkuRecommendationManager.addItemSkuRecommendation(itemSkuRecommendationDTO);

        return ResponseUtil.getSuccessResponse(result);
    }

    @Override
    public String getName() {
        return ActionEnum.ADD_ITEM_SKU_RECOMMENDATION.getActionName();
    }
}
