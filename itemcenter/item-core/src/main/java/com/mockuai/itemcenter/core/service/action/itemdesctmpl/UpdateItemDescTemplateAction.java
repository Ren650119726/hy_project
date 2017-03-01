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
public class UpdateItemDescTemplateAction extends TransAction {

    private static final Logger log = LoggerFactory.getLogger(UpdateItemDescTemplateAction.class);

    @Resource
    private ItemDescTmplManager itemDescTmplManager;

    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {
        ItemResponse response = null;
        ItemRequest request = context.getRequest();
        String bizCode = (String) context.get("bizCode");
        ItemDescTmplDTO itemDescTmplDTO = (ItemDescTmplDTO) request.getParam("itemDescTmplDTO");
        // 验证DTO是否为空
        if (itemDescTmplDTO == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemDescTmplDTO is null");
        }


        itemDescTmplDTO.setBizCode(bizCode);//填充bizCode
        Long id = itemDescTmplManager.updateItemDescTmpl(itemDescTmplDTO);
        response = ResponseUtil.getSuccessResponse(id);
        return response;

    }

    @Override
    public String getName() {
        return ActionEnum.UPDATE_ITEM_DESC_TMPL.getActionName();
    }
}
