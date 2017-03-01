package com.mockuai.itemcenter.core.service.action.specialitem;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.manager.SpecialItemExtraInfoManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by yindingyu on 15/12/12.
 */
@Service
public class QuerySpecialItemAction extends TransAction{


    @Resource
    private SpecialItemExtraInfoManager specialItemExtraInfoManager;

    @Resource
    private ItemManager itemManager;


    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {
        ItemRequest request = context.getRequest();
        String bizCode = (String) context.get("bizCode");

        if(request.getObject("itemQTO")==null){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "skuId不能为空");
        }

        ItemQTO itemQTO = request.getObject("itemQTO", ItemQTO.class);

        Long sellerId = request.getLong("sellerId");


        String appKey = request.getString("appKey");


        Map<Long,Object> itemExtraInfoMap = specialItemExtraInfoManager.querySpecialItemExtraInfo(itemQTO, appKey);

        if (itemExtraInfoMap.isEmpty()) {
            return ResponseUtil.getSuccessResponse(Collections.EMPTY_LIST, 0);
        }


        List<Long> itemIdlist = Lists.newArrayList(itemExtraInfoMap.keySet());

        if (itemIdlist.size() == 0) {
            return ResponseUtil.getSuccessResponse(Collections.EMPTY_LIST, 0);
        }

        ItemQTO itemQTO1 = new ItemQTO();
        itemQTO1.setBizCode(bizCode);
        itemQTO1.setIdList(itemIdlist);
        itemQTO1.setSellerId(0L);

        List<ItemDTO> itemDTOList = itemManager.queryItem(itemQTO1);

        for(ItemDTO itemDTO :itemDTOList){
            itemDTO.setItemExtraInfo(itemExtraInfoMap.get(itemDTO.getId()));
        }


        return ResponseUtil.getSuccessResponse(itemDTOList,itemQTO.getTotalCount());
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_SPECIAL_ITEM.getActionName();
    }
}
