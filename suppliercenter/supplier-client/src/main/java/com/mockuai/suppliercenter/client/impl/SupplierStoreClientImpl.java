package com.mockuai.suppliercenter.client.impl;

import com.mockuai.suppliercenter.client.SupplierStoreClient;
import com.mockuai.suppliercenter.common.action.ActionEnum;
import com.mockuai.suppliercenter.common.api.BaseRequest;
import com.mockuai.suppliercenter.common.api.Request;
import com.mockuai.suppliercenter.common.api.Response;
import com.mockuai.suppliercenter.common.api.SupplierDispatchService;
import com.mockuai.suppliercenter.common.dto.StoreDTO;
import com.mockuai.suppliercenter.common.qto.StoreQTO;

import javax.annotation.Resource;
import java.util.List;

public class SupplierStoreClientImpl implements SupplierStoreClient {

    @Resource
    private SupplierDispatchService supplierDispatchService;

    /**
     * 添加仓库
     */
    public Response<StoreDTO> addStore(StoreDTO storeDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("storeDTO", storeDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.ADD_STORE.getActionName());
        Response<StoreDTO> response = supplierDispatchService.execute(request);
        return response;
    }

    /**
     * 查询符合查询条件的仓库
     */
    public Response<List<StoreDTO>> queryStore(StoreQTO storeQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("storeQTO", storeQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_STORE.getActionName());
        Response<List<StoreDTO>> response = supplierDispatchService.execute(request);
        return response;
    }


    /**
     * 给订单提供使用
     */

    public Response<List<StoreDTO>> queryStoreForOrder(StoreQTO storeQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("storeQTO", storeQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_STOREFORORDER.getActionName());
        Response<List<StoreDTO>> response = supplierDispatchService.execute(request);
        return response;
    }

    /**
     * 编辑仓库
     */
    public Response<Boolean> updateStore(StoreDTO storeDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("storeDTO", storeDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.UPDATE_STORE.getActionName());
        Response<Boolean> response = supplierDispatchService.execute(request);
        return response;
    }


    /**
     * 禁用仓库
     */
    public Response<Boolean> forbiddenStore(Long id, String appKey) {
        Request request = new BaseRequest();
        request.setParam("id", id);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.FORBIDDEN_STORE.getActionName());
        Response<Boolean> response = supplierDispatchService.execute(request);
        return response;
    }

    /**
     * 启用仓库
     */
    public Response<Boolean> enableStore(Long id, String appKey) {
        Request request = new BaseRequest();
        request.setParam("id", id);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.ENABLE_STORE.getActionName());
        Response<Boolean> response = supplierDispatchService.execute(request);
        return response;
    }

    /**
     * 刪除仓库
     */
    public Response<Boolean> deleteStore(StoreDTO storeDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("storeDTO", storeDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.DELETE_STORE.getActionName());
        Response<Boolean> response = supplierDispatchService.execute(request);
        return response;
    }


}
