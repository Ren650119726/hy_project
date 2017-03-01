package com.mockuai.itemcenter.client.impl;

import com.mockuai.itemcenter.client.ValueAddedServiceClient;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ValueAddedServiceTypeDTO;
import com.mockuai.itemcenter.common.domain.qto.ValueAddedServiceTypeQTO;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 15/12/23.
 */
public class ValueAddedServiceClientImpl implements ValueAddedServiceClient{
    @Resource
    private ItemService itemService;

    public Response<ValueAddedServiceTypeDTO> getValueAddedService(Long id, Long sellerId, String appKey) {

        Request request = new BaseRequest();
        request.setCommand(ActionEnum.GET_VALUE_ADDED_SERVICE.getActionName());

        request.setParam("serviceId", id);
        request.setParam("sellerId", sellerId);
        request.setParam("appKey",appKey);

        return itemService.execute(request);
    }

    public Response<List<ValueAddedServiceTypeDTO>> queryValueAddedService(ValueAddedServiceTypeQTO valueAddedServiceQTO, String appKey) {

        Request request = new BaseRequest();
        request.setCommand(ActionEnum.QUERY_VALUE_ADDED_SERVICE.getActionName());

        request.setParam("valueAddedServiceTypeQTO", valueAddedServiceQTO);
        request.setParam("appKey",appKey);

        return itemService.execute(request);

    }

    public Response<Long> addValueAddedService(ValueAddedServiceTypeDTO valueAddedServiceDTO, String appKey) {
        Request request = new BaseRequest();
        request.setCommand(ActionEnum.ADD_VALUE_ADDED_SERVICE.getActionName());

        request.setParam("valueAddedServiceTypeDTO", valueAddedServiceDTO);
        request.setParam("appKey",appKey);

        return itemService.execute(request);
    }

    public Response<Long> deleteValueAddedService(Long id, Long sellerId, String appKey) {

        Request request = new BaseRequest();
        request.setCommand(ActionEnum.DELETE_VALUE_ADDED_SERVICE.getActionName());

        request.setParam("serviceId",id);
        request.setParam("sellerId",sellerId);
        request.setParam("appKey",appKey);

        return itemService.execute(request);
    }

    public Response<Long> updateValueAddedService(ValueAddedServiceTypeDTO valueAddedServiceDTO, String appKey) {
        Request request = new BaseRequest();
        request.setCommand(ActionEnum.UPDATE_VALUE_ADDED_SERVICE.getActionName());

        request.setParam("valueAddedServiceTypeDTO", valueAddedServiceDTO);
        request.setParam("appKey",appKey);

        return itemService.execute(request);
    }
}
