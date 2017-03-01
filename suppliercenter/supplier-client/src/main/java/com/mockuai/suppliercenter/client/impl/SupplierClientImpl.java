package com.mockuai.suppliercenter.client.impl;

import com.mockuai.suppliercenter.client.SupplierClient;
import com.mockuai.suppliercenter.common.action.ActionEnum;
import com.mockuai.suppliercenter.common.api.BaseRequest;
import com.mockuai.suppliercenter.common.api.Request;
import com.mockuai.suppliercenter.common.api.Response;
import com.mockuai.suppliercenter.common.api.SupplierDispatchService;
import com.mockuai.suppliercenter.common.dto.SupplierDTO;
import com.mockuai.suppliercenter.common.qto.SupplierQTO;

import javax.annotation.Resource;
import java.util.List;

public class SupplierClientImpl implements SupplierClient {


    @Resource
    private SupplierDispatchService supplierDispatchService;


    public Response<SupplierDTO> addSupplier(SupplierDTO supplierDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("supplierDTO", supplierDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.ADD_SUPPLIER.getActionName());
        Response<SupplierDTO> response = supplierDispatchService.execute(request);
        return response;
    }

    /**
     * 根据id获取供应商信息
     */
    public Response<SupplierDTO> getSupplierInf(Long id, String appKey) {
        Request request = new BaseRequest();
        request.setParam("id", id);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_SUPPLIER.getActionName());
        Response<SupplierDTO> response = supplierDispatchService.execute(request);
        return response;

    }


    /**
     * 查询供应商
     */
    public Response<List<SupplierDTO>> querySupplier(SupplierQTO supplierQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("supplierQTO", supplierQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_SUPPLIER.getActionName());
        Response<List<SupplierDTO>> response = supplierDispatchService.execute(request);
        return response;
    }

    /**
     * 编辑供应商
     */
    public Response<Boolean> updateSupplier(SupplierDTO supplierDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("supplierDTO", supplierDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.UPDATE_SUPPLIER.getActionName());
        Response<Boolean> response = supplierDispatchService.execute(request);
        return response;
    }

    /**
     * 提供订单使用,查询供应商
     */
    public Response<List<SupplierDTO>> querySupplierInfForOrder(SupplierQTO supplierQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("supplierQTO", supplierQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_SUPPLIERFORORDER.getActionName());
        Response<List<SupplierDTO>> response = supplierDispatchService.execute(request);
        return response;
    }

}
