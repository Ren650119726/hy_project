package com.mockuai.itemcenter.core.service.action.itemsuit;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.manager.ItemSuitManager;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 15/12/4.
 */
@Service
public class QuerySuitsByItemAction extends TransAction{

    @Resource
    private ItemManager itemManager;

    @Resource
    private ItemSuitManager itemSuitManager;

    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {

        final String bizCode = (String)context.get("bizCode");
        final Request request = context.getRequest();
        final String appKey = (String) request.getParam("appKey");
        ItemQTO itemQTO = (ItemQTO)request.getParam("itemQTO");


        itemQTO.setBizCode(bizCode);

        List<ItemDTO> itemDTOList = itemSuitManager.querySuitOfItem(itemQTO);

        return ResponseUtil.getSuccessResponse(itemDTOList);
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_SUITS_BY_ITEM.getActionName();
    }
}
