package com.mockuai.suppliercenter.client.impl;

import com.mockuai.suppliercenter.client.StoreItemSkuClient;
import com.mockuai.suppliercenter.common.action.ActionEnum;
import com.mockuai.suppliercenter.common.api.BaseRequest;
import com.mockuai.suppliercenter.common.api.Request;
import com.mockuai.suppliercenter.common.api.Response;
import com.mockuai.suppliercenter.common.api.SupplierDispatchService;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuForOrderQTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuQTO;

import javax.annotation.Resource;
import java.util.List;

public class StoreItemSkuClientImpl implements StoreItemSkuClient {
    @Resource
    private SupplierDispatchService supplierDispatchService;


    /**
     * 根据仓库编号、itemSkuId查询skuSn， 返回给管易erp使用
     */

    public Response<StoreItemSkuDTO> getItemSku(StoreItemSkuQTO storeItemSkuQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("storeItemSkuQTO", storeItemSkuQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_ITEMSKU
                .getActionName());
        Response<StoreItemSkuDTO> response = supplierDispatchService
                .execute(request);
        return response;
    }

    /**
     * 查询符合查询条件的仓库
     */
    public Response<List<StoreItemSkuDTO>> queryStoreItemSku(StoreItemSkuQTO storeItemSkuQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("storeItemSkuQTO", storeItemSkuQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_STOREITEMSKU.getActionName());
        Response<List<StoreItemSkuDTO>> response = supplierDispatchService.execute(request);
        return response;
    }
    
    /**
     * 
     *根据itemList查询skuList数据
     *
     */
    public Response<List<StoreItemSkuDTO>> storeItemSkusByItemIdList(StoreItemSkuQTO storeItemSkuQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("storeItemSkuQTO", storeItemSkuQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_STOREITEMSKULIST.getActionName());
        Response<List<StoreItemSkuDTO>> response = supplierDispatchService.execute(request);
        return response;
    }

    /**
     * 给订单提供使用
     */

    public Response<List<StoreItemSkuDTO>> queryItemStoreNumForOrder(
            StoreItemSkuQTO storeItemSkuQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("storeItemSkuQTO", storeItemSkuQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_ITEMSTORENUMFORORDER
                .getActionName());
        Response<List<StoreItemSkuDTO>> response = supplierDispatchService
                .execute(request);
        return response;
    }

    /**
     * 给订单提供使用
     */

    public Response<List<StoreItemSkuDTO>> queryItemsStoreInfoForOrder(
            StoreItemSkuForOrderQTO storeItemSkuForOrderQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("storeItemSkuForOrderQTO", storeItemSkuForOrderQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_ITEMSSTOREINFFORORDER
                .getActionName());
        Response<List<StoreItemSkuDTO>> response = supplierDispatchService
                .execute(request);
        return response;
    }

    /**
     * 根据skuId和仓库id增加库存的接口
     */
    public Response<Boolean> increaseStoreNumAction(
            Long storeId, Long itemSkuId, Long storeNum, String appKey) {
        Request request = new BaseRequest();
        request.setParam("storeId", storeId);
        request.setParam("itemSkuId", itemSkuId);
        request.setParam("storeNum", storeNum);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.INCREASE_STORENUM.getActionName());
        Response<Boolean> response = supplierDispatchService.execute(request);
        return response;
    }

    /**
     * 根据skuId和仓库id降低库存的接口
     */
    public Response<Boolean> reduceStoreNumAction(
            Long storeId, Long itemSkuId, Long storeNum, String appKey) {
        Request request = new BaseRequest();
        request.setParam("storeId", storeId);
        request.setParam("itemSkuId", itemSkuId);
        request.setParam("storeNum", storeNum);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.REDUCE_STORENUM.getActionName());
        Response<Boolean> response = supplierDispatchService.execute(request);
        return response;
    }

    /**
     * 添加Store ItemSku关联
     */
    public Response<StoreItemSkuDTO> addStoreItemSku(StoreItemSkuDTO storeItemSkuDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("storeItemSkuDTO", storeItemSkuDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.ADD_STOREITEMSKU
                .getActionName());
        Response<StoreItemSkuDTO> response = supplierDispatchService
                .execute(request);
        return response;
    }

    /**
     * 取消Store ItemSku关联
     */
    public Response<Boolean> cancleStoreItemSku(StoreItemSkuDTO storeItemSkuDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("storeItemSkuDTO", storeItemSkuDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.CANCLE_STOREITEMSKU
                .getActionName());
        Response<Boolean> response = supplierDispatchService
                .execute(request);
        return response;
    }

    /**
     * 取消多个Store ItemSku关联，List
     */
    public Response<Boolean> cancleStoreItemSkuList(List<Long> skuIdList, String appKey) {

        Request request = new BaseRequest();
        request.setParam("skuIdList", skuIdList);
        request.setParam("appKey", appKey);

        request.setCommand(ActionEnum.CANCLE_STOREITEMSKULIST.getActionName());
        Response<Boolean> response = supplierDispatchService.execute(request);
        return response;

    }

    /**
     * 编辑Store ItemSku关联
     */
    public Response<Boolean> updateStoreItemSku(StoreItemSkuDTO storeItemSkuDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("storeItemSkuDTO", storeItemSkuDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.UPDATE_STOREITEMSKU
                .getActionName());
        Response<Boolean> response = supplierDispatchService
                .execute(request);
        return response;
    }

    /**
     * 从现有sku的库存复制为新的sku的库存
     */
    public Response<Boolean> copySkuStock(Long itemSkuId, Long itemSkuIdNew, Long stock, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemSkuId", itemSkuId);
        request.setParam("itemSkuIdNew", itemSkuIdNew);
        request.setParam("stock", stock);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.COPY_SKUSTOCK.getActionName());
        Response<Boolean> response = supplierDispatchService.execute(request);
        return response;
    }

    /**
     * 返还从现有sku的库存复制为新的sku的库存，与上面相反的操作
     */
    public Response<Boolean> copySkuStockReturn(Long itemSkuId, Long itemSkuIdNew, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemSkuId", itemSkuId);
        request.setParam("itemSkuIdNew", itemSkuIdNew);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.COPY_SKUSTOCKRETURN.getActionName());
        Response<Boolean> response = supplierDispatchService.execute(request);
        return response;
    }

}
