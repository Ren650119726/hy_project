package com.mockuai.itemcenter.core.service.action.item;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
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
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by luliang on 15/8/12.
 */
@Service
public class CountTotalItemAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(CountTotalItemAction.class);

    @Resource
    private ItemManager itemManager;


    @Override
    public ItemResponse execute(RequestContext context) throws ItemException {
        ItemDTO itemDTO = null;
        ItemResponse response = null;
        ItemRequest request = context.getRequest();
        String bizCode = (String)context.get("bizCode");
        ItemQTO itemQTO = (ItemQTO)request.getParam("itemQTO");

        Long totalCount = null;
        try {
            itemQTO.setBizCode(bizCode);//填充bizCode
            totalCount = itemManager.countItem(itemQTO);
        } catch (ItemException e) {
            response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
            return response;
        }
        response = ResponseUtil.getSuccessResponse(totalCount);
        return response;
    }

    @Override
    public String getName() {
        return ActionEnum.COUNT_TOTAL_ITEM_ACTION.getActionName();
    }
}
