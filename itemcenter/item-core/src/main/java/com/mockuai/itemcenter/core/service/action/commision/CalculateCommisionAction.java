package com.mockuai.itemcenter.core.service.action.commision;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.CommissionUnitDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemSettlementConfigManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 15/10/12.
 */
@Service
public class CalculateCommisionAction implements Action{


    @Resource
    private ItemSettlementConfigManager itemSettlementConfigManager;


    @Override
    public ItemResponse execute(RequestContext context) throws ItemException {

        String bizCode = (String) context.get("biz_Code");

        ItemRequest request = context.getRequest();


        String appKey = (String) request.getParam("appKey");

        List<CommissionUnitDTO> commissionUnitDTOList = request.getObject("commissionUnitDTOList", List.class);

        if (commissionUnitDTOList == null || commissionUnitDTOList.size() == 0) {

            //为空则返回佣金为0
            return ResponseUtil.getSuccessResponse(0L);
        }


        Long result = itemSettlementConfigManager.calculateCommission(commissionUnitDTOList,bizCode);


        return ResponseUtil.getSuccessResponse(result);

    }

    @Override
    public String getName() {
        return ActionEnum.CALCULATE_COMMISSION.getActionName();
    }



}
