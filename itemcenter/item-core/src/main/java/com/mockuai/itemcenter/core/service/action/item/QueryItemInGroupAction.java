package com.mockuai.itemcenter.core.service.action.item;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.GroupItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemCategoryDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemCategoryManager;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luliang on 15/7/28.
 */
@Service
public class QueryItemInGroupAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(QueryItemInGroupAction.class);
    @Resource
    private ItemManager itemManager;

    @Resource
    private ItemCategoryManager itemCategoryManager;

    @Override
    public ItemResponse execute(RequestContext context) throws ItemException {
        ItemResponse response = null;
        ItemRequest request = context.getRequest();
        ItemQTO itemQTO = (ItemQTO) request.getParam("itemQTO");
        if(itemQTO == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "param is null");
        }

        try {
            List<ItemDTO> itemDTOs = itemManager.queryItem(itemQTO);

            List<GroupItemDTO> groupItemDTOs = new ArrayList<GroupItemDTO>();
            if(itemDTOs != null && itemDTOs.size() != 0) {
                // 查询商品
                for(ItemDTO itemDTO: itemDTOs) {
                    GroupItemDTO groupItemDTO = new GroupItemDTO();
                    groupItemDTO.setItemUid(itemDTO.getItemUid());
                    groupItemDTO.setSellerId(itemDTO.getSellerId());
                    groupItemDTO.setBizCode(itemDTO.getBizCode());
                    groupItemDTO.setBrandId(itemDTO.getItemBrandId());
                    groupItemDTO.setCategoryId(itemDTO.getCategoryId());
                    groupItemDTO.setMarketPrice(itemDTO.getMarketPrice());
                    groupItemDTO.setItemName(itemDTO.getItemName());
                    groupItemDTO.setPromotionPrice(itemDTO.getPromotionPrice());
                    groupItemDTO.setWirelessPrice(itemDTO.getWirelessPrice());
                    groupItemDTO.setSaleBegin(itemDTO.getSaleBegin());
                    groupItemDTO.setIconUrl(itemDTO.getIconUrl());
                    // 查询类目名
                    ItemCategoryDTO itemCategoryDTO = itemCategoryManager.getItemCategory(itemDTO.getCategoryId());
                    if(itemCategoryDTO != null) {
                        groupItemDTO.setCategoryName(itemCategoryDTO.getCateName());
                    }
                    groupItemDTOs.add(groupItemDTO);
                }
            }

            response = ResponseUtil.getSuccessResponse(groupItemDTOs, itemQTO.getTotalCount());
            return response;
        } catch (ItemException e) {
            response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
            return response;
        }
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_ITEM_GROUP_ACTION.getActionName();
    }
}
