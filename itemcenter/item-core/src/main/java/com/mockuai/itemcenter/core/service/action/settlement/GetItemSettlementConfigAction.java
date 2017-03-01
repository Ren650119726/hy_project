package com.mockuai.itemcenter.core.service.action.settlement;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.FreightTemplateDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSettlementConfigDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.FreightTemplateManager;
import com.mockuai.itemcenter.core.manager.ItemSettlementConfigManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yindingyu on 15/9/23.
 */
@Service
public class GetItemSettlementConfigAction extends TransAction{

    @Resource
    private ItemSettlementConfigManager itemSettlementConfigManager;

    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {

        ItemRequest request = context.getRequest();
        String bizCode = (String) context.get("bizCode");

        Long id = request.getLong("id");

        if (id == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "id is missing");
        }

        ItemSettlementConfigDTO itemSettlementConfigDTO = itemSettlementConfigManager.getItemSettlementConfig(id,bizCode);


        return ResponseUtil.getSuccessResponse(itemSettlementConfigDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.GET_ITEM_SETTLEMENT_CONFIG.getActionName();
    }
}
