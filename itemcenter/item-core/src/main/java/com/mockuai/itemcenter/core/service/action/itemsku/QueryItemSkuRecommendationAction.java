package com.mockuai.itemcenter.core.service.action.itemsku;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuRecommendationDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuRecommendationQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.manager.ItemSkuRecommendationManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 16/1/11.
 */
@Service
public class QueryItemSkuRecommendationAction extends TransAction {

    @Resource
    private ItemSkuRecommendationManager itemSkuRecommendationManager;

    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {

        String bizCode = (String) context.get("bizCode");

        ItemRequest request = context.getRequest();

        ItemSkuRecommendationQTO itemSkuRecommendationQTO = request.getObject("itemSkuRecommendationQTO", ItemSkuRecommendationQTO.class);

        if (itemSkuRecommendationQTO == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemSkuRecommendationQTO不能为空");
        }

        List<ItemSkuRecommendationDTO> itemSkuRecommendationDTOList = itemSkuRecommendationManager.queryItemSkuRecommendation(itemSkuRecommendationQTO);

        if (itemSkuRecommendationQTO.getNeedPaging() != null && itemSkuRecommendationQTO.getNeedPaging() == true) {
            return ResponseUtil.getSuccessResponse(itemSkuRecommendationDTOList, itemSkuRecommendationQTO.getTotalCount());
        } else {
            return ResponseUtil.getSuccessResponse(itemSkuRecommendationDTOList, itemSkuRecommendationDTOList.size());
        }
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_ITEM_SKU_RECOMMENDATION.getActionName();
    }
}
