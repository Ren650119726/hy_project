package com.mockuai.itemcenter.client.impl;

import com.mockuai.itemcenter.client.CommissionClient;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.CommissionUnitDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSettlementConfigDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSettlementConfigQTO;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 16/1/18.
 */
public class CommissionClientImpl implements CommissionClient {

    @Resource
    private ItemService itemService;

    public Response<Long> calculateCommission(List<CommissionUnitDTO> commissionUnitDTOList, String appKey) {
        Request request = new BaseRequest();

        request.setParam("commissionUnitDTOList",commissionUnitDTOList);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.CALCULATE_COMMISSION.getActionName());
        return  itemService.execute(request);
    }

    public Response<ItemSettlementConfigDTO> getItemSettlementConfig(Long id, String appKey) {
        Request request = new BaseRequest();

        request.setParam("id",id);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_ITEM_SETTLEMENT_CONFIG.getActionName());
        return  itemService.execute(request);
    }

    public Response<Long> deleteItemSettlementConfig(Long id, String appKey) {
        Request request = new BaseRequest();

        request.setParam("id",id);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.DELETE_ITEM_SETTLEMENT_CONFIG.getActionName());
        return  itemService.execute(request);
    }

    public Response<Long> enableItemSettlementConfig(Long id, String appKey) {
        Request request = new BaseRequest();

        request.setParam("id",id);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.ENABLE_ITEM_SETTLEMENT_CONFIG.getActionName());
        return  itemService.execute(request);
    }

    public Response<Long> disableItemSettlementConfig(Long id, String appKey) {
        Request request = new BaseRequest();

        request.setParam("id",id);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.DISABLE_ITEM_SETTLEMENT_CONFIG.getActionName());
        return  itemService.execute(request);
    }

    public Response<Long> addItemSettlementConfig(ItemSettlementConfigDTO itemSettlementConfigDTO, String appKey) {

        Request request = new BaseRequest();

        request.setParam("itemSettlementConfigDTO",itemSettlementConfigDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.ADD_ITEM_SETTLEMENT_CONFIG.getActionName());
        return  itemService.execute(request);

    }

    public Response<Long> updateItemSettlementConfig(ItemSettlementConfigDTO itemSettlementConfigDTO, String appKey) {
        Request request = new BaseRequest();

        request.setParam("itemSettlementConfigDTO",itemSettlementConfigDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.UPDATE_ITEM_SETTLEMENT_CONFIG.getActionName());
        return  itemService.execute(request);
    }

    public Response<List<ItemSettlementConfigDTO>> queryItemSettlementConfig(ItemSettlementConfigQTO itemSettlementConfigQTO, String appKey) {
        Request request = new BaseRequest();

        request.setParam("itemSettlementConfigQTO",itemSettlementConfigQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_ITEM_SETTLEMENT_CONFIG.getActionName());
        return  itemService.execute(request);
    }
}
