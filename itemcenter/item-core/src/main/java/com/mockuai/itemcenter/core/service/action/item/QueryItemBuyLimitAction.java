package com.mockuai.itemcenter.core.service.action.item;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemBuyLimitDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemBuyLimitManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 限购;
 * Created by luliang on 15/7/17.
 */
@Service
public class QueryItemBuyLimitAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(QueryItemBuyLimitAction.class);

    @Resource
    private ItemBuyLimitManager itemBuyLimitManager;

    @Override
    public ItemResponse execute(RequestContext context) {
        ItemResponse response = null;
        ItemRequest request = context.getRequest();
        Long sellerId = request.getLong("sellerId");
        List<Long> itemIdList = (List<Long>) request.getObject("itemIdList");
        if(sellerId == null || itemIdList == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "sellerId and itemIdList could not be null.");
        }
        List<ItemBuyLimitDTO> itemBuyLimitCount;
        try {
            itemBuyLimitCount = itemBuyLimitManager.queryItemBuyLimitRecord(sellerId, itemIdList);
        } catch (ItemException e) {
            response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
            return response;
        }
        response = ResponseUtil.getSuccessResponse(itemBuyLimitCount);
        return response;
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_ITEM_BUY_LIMIT.getActionName();
    }
}
