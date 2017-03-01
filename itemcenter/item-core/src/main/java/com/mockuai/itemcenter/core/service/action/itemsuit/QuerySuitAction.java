package com.mockuai.itemcenter.core.service.action.itemsuit;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.manager.ItemSuitManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
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
public class QuerySuitAction extends TransAction{

    @Resource
    private ItemManager itemManager;

    @Resource
    private ItemSuitManager itemSuitManager;

    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {
        ItemRequest request = context.getRequest();

        String bizCode = (String)context.get("bizCode");

        ItemQTO itemQTO = (ItemQTO) request.getObject("itemQTO");

        itemQTO.setBizCode(bizCode);
        itemQTO.setItemType(DBConst.SUIT_ITEM.getCode());

        List<ItemDTO> itemDTOList = itemSuitManager.querySuit(itemQTO);

        return ResponseUtil.getSuccessResponse(itemDTOList,itemQTO.getTotalCount());
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_SUIT.getActionName();
    }
}
