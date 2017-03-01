package com.mockuai.itemcenter.core.service.action.itemsuit;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.RItemSuitDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemSuitManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 15/12/7.
 */
@Service
public class AddRItemSuitAction extends TransAction{

    @Resource
    private ItemSuitManager itemSuitManager;


    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {
        ItemRequest request = context.getRequest();

        String bizCode = (String)context.get("bizCode");

        List<RItemSuitDTO> rItemSuitDTOList = (List<RItemSuitDTO>) request.getObject("rItemSuitDTOList");

        for(RItemSuitDTO rItemSuitDTO : rItemSuitDTOList){
            rItemSuitDTO.setBizCode(bizCode);
        }

        Long result = itemSuitManager.addItemSuit(rItemSuitDTOList);

        return ResponseUtil.getSuccessResponse(result);

    }

    @Override
    public String getName() {
        return ActionEnum.ADD_R_ITEM_SUIT.getActionName();
    }
}
