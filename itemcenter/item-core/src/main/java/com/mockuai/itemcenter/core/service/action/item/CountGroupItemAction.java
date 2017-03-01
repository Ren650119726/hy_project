package com.mockuai.itemcenter.core.service.action.item;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by luliang on 15/8/1.
 */
@Service
public class CountGroupItemAction implements Action {

    @Resource
    private ItemManager itemManager;


    @Override
    public ItemResponse execute(RequestContext context) throws ItemException {
        ItemResponse response = null;
        ItemRequest request = context.getRequest();
        String bizCode = (String)context.get("bizCode");
        ItemQTO itemQTO = (ItemQTO) request.getParam("itemQTO");
        if (itemQTO == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemQTO is missing");
        }
        try {
            itemQTO.setBizCode(bizCode);//填充bizCode
            int count = (int)itemManager.countGroupItem(itemQTO).longValue();
            response = ResponseUtil.getSuccessResponse(count);
            return response;
        } catch (ItemException e) {
            response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            return response;
        }
    }

    @Override
    public String getName() {
        return ActionEnum.COUNT_GROUP_ITEM.getActionName();
    }
}
