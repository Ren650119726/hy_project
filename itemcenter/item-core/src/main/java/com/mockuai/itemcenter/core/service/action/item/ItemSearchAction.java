package com.mockuai.itemcenter.core.service.action.item;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemSearchManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lizg on 2016/11/8.
 */

@Service
public class ItemSearchAction extends TransAction {

    private static final Logger log = LoggerFactory.getLogger(ItemSearchAction.class);

    @Resource
    private ItemSearchManager itemSearchManager;

    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {

        ItemRequest request = context.getRequest();
        String appKey = (String) request.getParam("appKey");
        // 验证DTO是否为空
        if (request.getParam("itemDTO") == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemDTO is null");
        }
        ItemDTO itemDTO = (ItemDTO) request.getParam("itemDTO");
        try {
            itemSearchManager.setItemIndex(itemDTO);
            return  ResponseUtil.getSuccessResponse(true);
        }catch (ItemException e) {
            log.error(e.toString());
            return ResponseUtil.getErrorResponse(e.getResponseCode(), e.getMessage());
        }
    }

    @Override
    public String getName() {
        return ActionEnum.ITEM_SERACH.getActionName();
    }
}
