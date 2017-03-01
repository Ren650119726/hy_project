package com.mockuai.itemcenter.core.service.action.unsafe;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 15/9/25.
 */
public class QueryItemActionUnsafe implements Action{

    private static final Logger log = LoggerFactory.getLogger(QueryItemActionUnsafe.class);
    @Resource
    private ItemManager itemManager;

    @Override
    public ItemResponse execute(RequestContext context) throws ItemException {
        ItemResponse response = null;
        ItemRequest request = context.getRequest();

        if (request.getParam("itemQTO") == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemQTO is null");
        }
        ItemQTO itemQTO = (ItemQTO) request.getParam("itemQTO");
        try {

            List<ItemDTO> itemDTOList = itemManager.queryItemContainsUrl(itemQTO);
            //TODO 这里有BUG，manager应该增加一个查询总量的接口
            //response = ResponseUtil.getSuccessResponse(itemDTOList, itemDTOList.size());
            // bug 修复
            response = ResponseUtil.getSuccessResponse(itemDTOList, itemQTO.getTotalCount());

            return response;
        } catch (ItemException e) {
            response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
            return response;
        }

    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_ITEM_UNSAFE.getActionName();
    }
}
