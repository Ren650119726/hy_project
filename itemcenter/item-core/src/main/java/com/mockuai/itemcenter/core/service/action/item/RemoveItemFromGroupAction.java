package com.mockuai.itemcenter.core.service.action.item;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by luliang on 15/7/28.
 */
@Service
public class RemoveItemFromGroupAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(RemoveItemFromGroupAction.class);

    @Resource
    private ItemManager itemManager;

    @Override
    public ItemResponse execute(RequestContext context) throws ItemException {
        ItemResponse response = null;
        ItemRequest request = context.getRequest();
        Long sellerId = (Long) request.getParam("sellerId");
        Long itemId = (Long) request.getParam("itemId");
        if(sellerId == null || itemId == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "param is null selerId=" + sellerId
                    + " itemid=" + itemId);
        }

        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(itemId);
        itemDTO.setSellerId(sellerId);
        itemDTO.setGroupId(null);
        try {
            Boolean res = itemManager.removeItemFromGroup(itemDTO);
            response = ResponseUtil.getSuccessResponse(res);
            return response;
        } catch (ItemException e) {
            response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
            return response;
        }
    }

    @Override
    public String getName() {
        return ActionEnum.REMOVE_ITEM_GROUP_ACTION.getActionName();
    }
}
