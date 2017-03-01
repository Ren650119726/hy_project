package com.mockuai.itemcenter.core.service.action.itemsuit;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemSuitDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemSuitManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yindingyu on 15/12/11.
 */
@Service
public class GetSuitExtraInfoAction extends TransAction{

    @Resource
    private ItemSuitManager itemSuitManager;

    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {

        String bizCode = (String) context.get("bizCode");
        ItemRequest request = context.getRequest();

        Long itemId = request.getLong("itemId");

        Long sellerId = request.getLong("sellerId");

        ItemSuitDTO itemSuitDTO = itemSuitManager.getSuitExtraInfo(itemId, sellerId, bizCode);

        return ResponseUtil.getSuccessResponse(itemSuitDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.GET_SUIT_EXTRA_INFO.getActionName();
    }
}
