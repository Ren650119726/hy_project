package com.mockuai.itemcenter.core.service.action.itemsku;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuPropertyRecommendationDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuPropertyRecommendationQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.manager.ItemSkuPropertyRecommendationManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 16/1/11.
 */
@Service
public class QueryItemSkuPropertyRecommendationAction extends TransAction {

    @Resource
    private ItemSkuPropertyRecommendationManager itemSkuPropertyRecommendationManager;

    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {

        String bizCode = (String) context.get("bizCode");

        ItemRequest request = context.getRequest();

        ItemSkuPropertyRecommendationQTO itemSkuPropertyRecommendationQTO = request.getObject("itemSkuPropertyRecommendationQTO", ItemSkuPropertyRecommendationQTO.class);

        if (itemSkuPropertyRecommendationQTO == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemSkuPropertyRecommendationQTO不能为空");
        }

        List<ItemSkuPropertyRecommendationDTO> itemSkuRecommendationDTOList = itemSkuPropertyRecommendationManager.queryItemSkuPropertyRecommendation(itemSkuPropertyRecommendationQTO);

        if (itemSkuPropertyRecommendationQTO.getNeedPaging() != null && itemSkuPropertyRecommendationQTO.getNeedPaging() == true) {
            return ResponseUtil.getSuccessResponse(itemSkuRecommendationDTOList, itemSkuPropertyRecommendationQTO.getTotalCount());
        } else {
            return ResponseUtil.getSuccessResponse(itemSkuRecommendationDTOList, itemSkuRecommendationDTOList.size());
        }
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_ITEM_SKU_PROPERTY_RECOMMENDATION.getActionName();
    }
}
