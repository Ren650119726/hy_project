package com.mockuai.itemcenter.core.service.action.settlement;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemSettlementConfigDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSettlementConfigQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemSettlementConfigManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 15/9/23.
 */
@Service
public class QueryItemSettlementConfigAction extends TransAction{

    @Resource
    private ItemSettlementConfigManager itemSettlementConfigManager;

    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {

        ItemRequest request = context.getRequest();
        String bizCode = (String) context.get("bizCode");


        ItemSettlementConfigQTO itemSettlementConfigQTO = request.getObject("itemSettlementConfigQTO", ItemSettlementConfigQTO.class);
        itemSettlementConfigQTO.setBizCode(bizCode);

        if (itemSettlementConfigQTO == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemSettlementConfigQTO is missing");
        }

        List<ItemSettlementConfigDTO> itemSettlementConfigDTOList = itemSettlementConfigManager.queryItemSettlementConfig(itemSettlementConfigQTO);


        return ResponseUtil.getSuccessResponse(itemSettlementConfigDTOList,itemSettlementConfigQTO.getTotalCount());
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_ITEM_SETTLEMENT_CONFIG.getActionName();
    }
}
