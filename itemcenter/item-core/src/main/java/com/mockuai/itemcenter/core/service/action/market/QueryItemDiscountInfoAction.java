package com.mockuai.itemcenter.core.service.action.market;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.manager.MarketingManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.MopCombineDiscountDTO;
import com.mockuai.marketingcenter.common.domain.dto.mop.MopDiscountInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 15/12/10.
 */
@Service
public class QueryItemDiscountInfoAction extends TransAction{

    @Resource
    private MarketingManager marketingManager;


    @Resource
    private ItemManager itemManager;

    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {

        String bizCode = (String) context.get("bizCode");
        Long userId = context.getRequest().getLong("userId");




        ItemRequest request = context.getRequest();

        String appKey = request.getString("appKey");

        ItemDTO itemDTO = request.getObject("itemDTO", ItemDTO.class);


        ItemDTO ret = itemManager.getItem(itemDTO.getId(),itemDTO.getSellerId(),bizCode);

        MopCombineDiscountDTO discountInfo = marketingManager.getItemDiscountInfo(ret, userId, appKey);

        return ResponseUtil.getSuccessResponse(discountInfo);

    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_ITEM_DISCOUNT_INFO.getActionName();
    }
}
