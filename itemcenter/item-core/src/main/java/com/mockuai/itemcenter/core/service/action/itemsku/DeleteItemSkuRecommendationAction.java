package com.mockuai.itemcenter.core.service.action.itemsku;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.service.ItemRequest;
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
public class DeleteItemSkuRecommendationAction extends TransAction {

    @Resource
    private ItemSkuRecommendationManager itemSkuRecommendationManager;

    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {

        String bizCode = (String) context.get("bizCode");
        ItemRequest request = context.getRequest();

        Long id = request.getLong("id");

        if (id == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "id不能为空");
        }
        Long sellerId = request.getLong("sellerId");

        if (sellerId == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "sellerId不能为空");
        }

        Long result =  itemSkuRecommendationManager.deleteItemSkuRecommendation(id, sellerId, bizCode);

        return ResponseUtil.getSuccessResponse(result);
    }

    @Override
    public String getName() {
        return ActionEnum.DELETE_ITEM_SKU_RECOMMENDATION.getActionName();
    }
}
