package com.mockuai.itemcenter.core.service.action.itemdesctmpl;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDescTmplDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemDescTmplManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class GetItemDescTemplateAction extends TransAction {

    private static final Logger log = LoggerFactory.getLogger(GetItemDescTemplateAction.class);

    @Resource
    private ItemDescTmplManager itemDescTmplManager;

    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {
        ItemResponse response = null;
        ItemRequest request = context.getRequest();
        String bizCode = (String) context.get("bizCode");
        Long itemDescTmplId = (Long) request.getParam("itemDescTmplId");
        Long sellerId = (Long) request.getParam("sellerId");
        // 验证DTO是否为空
        if (itemDescTmplId == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemDescTmplId is null");
        }

        if (sellerId == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "sellerId 不能为空");
        }
        ;

        ItemDescTmplDTO itemDescTmplDTO = itemDescTmplManager.getItemDescTmpl(itemDescTmplId, sellerId, bizCode);
        response = ResponseUtil.getSuccessResponse(itemDescTmplDTO);
        return response;

    }

    @Override
    public String getName() {
        return ActionEnum.GET_ITEM_DESC_TMPL.getActionName();
    }
}
