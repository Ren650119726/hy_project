package com.mockuai.itemcenter.client.impl;

import com.mockuai.itemcenter.client.SupplierClient;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.SupplierDTO;
import com.mockuai.itemcenter.common.domain.qto.SupplierQTO;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 16/3/21.
 */
public class SupplierClientImpl implements SupplierClient {

    @Resource
    ItemService itemService;

    public Response<Long> addSupplier(SupplierDTO supplierDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("supplierDTO", supplierDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.ADD_SUPPLIER.getActionName());
        return itemService.execute(request);
    }

    public Response<Long> updateSupplier(SupplierDTO supplierDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("supplierDTO", supplierDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.UPDATE_SUPPLIER.getActionName());
        return itemService.execute(request);
    }

    public Response<Long> deleteSupplier(Long id, Long sellerId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("id", id);
        request.setParam("sellerId", sellerId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.DELETE_SUPPLIER.getActionName());
        return itemService.execute(request);
    }

    public Response<SupplierDTO> getSupplier(Long id, Long sellerId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("id", id);
        request.setParam("sellerId", sellerId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_SUPPLIER.getActionName());
        return itemService.execute(request);
    }

    public Response<List<SupplierDTO>> querySupplier(SupplierQTO supplierQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("supplierQTO", supplierQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_SUPPLIER.getActionName());
        return itemService.execute(request);
    }
}
