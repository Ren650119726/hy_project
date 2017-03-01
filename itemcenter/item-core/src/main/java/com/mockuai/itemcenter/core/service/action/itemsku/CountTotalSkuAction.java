package com.mockuai.itemcenter.core.service.action.itemsku;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemSkuManager;
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
public class CountTotalSkuAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(CountTotalSkuAction.class);

    @Resource
    private ItemSkuManager itemSkuManager;

    @Override
    public ItemResponse execute(RequestContext context) throws ItemException {
        ItemDTO itemDTO = null;
        ItemResponse response = null;
        ItemRequest request = context.getRequest();
        String bizCode = (String)context.get("bizCode");
        ItemSkuQTO itemSkuQTO = (ItemSkuQTO)request.getParam("itemSkuQTO");

        Long totalCount = null;
        try {
            itemSkuQTO.setBizCode(bizCode);//填充bizCode
            totalCount = itemSkuManager.countItemSku(itemSkuQTO);
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
        return ActionEnum.COUNT_TOTAL_SKU_ACTION.getActionName();
    }
}
