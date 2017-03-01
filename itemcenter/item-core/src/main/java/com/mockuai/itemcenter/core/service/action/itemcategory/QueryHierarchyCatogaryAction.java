package com.mockuai.itemcenter.core.service.action.itemcategory;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemCategoryDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemCategoryManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 15/9/10.
 */
@Service
public class QueryHierarchyCatogaryAction implements Action{

    @Resource
    private ItemCategoryManager itemCategoryManager;


    @Override
    public ItemResponse execute(RequestContext context) throws ItemException {
        ItemRequest itemRequest = context.getRequest();

        try {

            String bizCode = (String)context.get("bizCode");

            List<ItemCategoryDTO> catogaryList = itemCategoryManager.queryHierachyCatogaty(bizCode);


            return ResponseUtil.getSuccessResponse(catogaryList);

        }catch (ItemException e){


            return ResponseUtil.getErrorResponse(e.getResponseCode(),e.getMessage());
        }

    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_HIERARCHY_CATOGARY.getActionName();
    }
}
