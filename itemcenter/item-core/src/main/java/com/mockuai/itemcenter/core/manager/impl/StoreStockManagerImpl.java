package com.mockuai.itemcenter.core.manager.impl;

import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.StoreStockManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.JsonUtil;
import com.mockuai.suppliercenter.client.StoreItemSkuClient;
import com.mockuai.suppliercenter.client.StoreOrderNumClient;
import com.mockuai.suppliercenter.common.api.Response;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.common.dto.SupplierOrderStockDTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuQTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 16/5/21.
 */
@Service
public class StoreStockManagerImpl implements StoreStockManager {

    @Resource
    private StoreItemSkuClient storeItemSkuClient;

    @Resource
    private StoreOrderNumClient storeOrderNumClient;

    private static final Logger log = LoggerFactory.getLogger(StoreStockManagerImpl.class);


    @Override
    public List<StoreItemSkuDTO> queryItemStock(StoreItemSkuQTO storeItemSkuQTO, String appKey) throws ItemException {


        Response<List<StoreItemSkuDTO>> response;
        try {
            response = storeItemSkuClient.storeItemSkusByItemIdList(storeItemSkuQTO, appKey);

        } catch (Exception e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "查询供应商平台时出现问题"+e.getMessage());
        }

        if (response.isSuccess()) {


            return response.getModule();
        } else {
            log.info("cause {},param:{}","supplier storeItemSkusByItemIdList", JsonUtil.toJson(storeItemSkuQTO));
            log.info("cause {},response:{}","supplier storeItemSkusByItemIdList", JsonUtil.toJson(response.getModule()));
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEPENDENT_SERVICE_EXCEPTION, response.getMessage());
        }
    }




    @Override
    public List<StoreItemSkuDTO> queryStoreItemSku(StoreItemSkuQTO storeItemSkuQTO, String appKey) throws ItemException {

        Response<List<StoreItemSkuDTO>> response;
        try {
            response = storeItemSkuClient.queryStoreItemSku(storeItemSkuQTO, appKey);

        } catch (Exception e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "查询供应商平台时出现问题");
        }

        if (response.isSuccess()) {

            return response.getModule();
        } else {
            log.info("cause {},param:{}","queryStoreItemSku", JsonUtil.toJson(storeItemSkuQTO));
            log.info("cause {},response:{}","queryStoreItemSku", JsonUtil.toJson(response.getModule()));
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEPENDENT_SERVICE_EXCEPTION, response.getMessage());
        }
    }


    /**
     * 根据skuId和仓库id增加库存的接口
     */
    @Override
    public Boolean increaseStoreNumAction(Long  storeId,Long  itemSkuId,Long storeNum, String appKey) throws ItemException{
        Response<Boolean> response;
        try {
            response = storeItemSkuClient.increaseStoreNumAction(storeId, itemSkuId, storeNum, appKey);

        } catch (Exception e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "查询供应商平台时出现问题");
        }

        if (response.isSuccess()) {
            return response.getModule();
        } else {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEPENDENT_SERVICE_EXCEPTION, response.getMessage());
        }
    }

    /**
     * 根据skuId和仓库id增加库存的接口
     */
    @Override
    public Boolean reduceStoreNumAction(Long  storeId,Long  itemSkuId,Long storeNum, String appKey) throws ItemException{
        Response<Boolean> response;
        try {
            response = storeItemSkuClient.reduceStoreNumAction(storeId, itemSkuId, storeNum, appKey);

        } catch (Exception e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "查询供应商平台时出现问题");
        }

        if (response.isSuccess()) {
            return response.getModule();
        } else {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEPENDENT_SERVICE_EXCEPTION, response.getMessage());
        }
    }

    /**
     * 根据skuId和仓库id增加库存的接口
     */
    @Override
    public Response lockStoreSkuStock(SupplierOrderStockDTO supplierOrderStockDTO, String appKey) throws ItemException{
        Response response;
        try {
            response = storeOrderNumClient.lockStoreSkuStock(supplierOrderStockDTO, appKey);

        } catch (Exception e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "查询供应商平台时出现问题");
        }

        return  response;
   }

    @Override
    public Boolean unlockStoreSkuStock(SupplierOrderStockDTO supplierOrderStockDTO, String appKey) throws ItemException{
        Response<Boolean> response;
        try {
            response = storeOrderNumClient.unlockStoreSkuStock(supplierOrderStockDTO, appKey);

        } catch (Exception e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "查询供应商平台时出现问题");
        }

        if (response.isSuccess()) {
            return response.getModule();
        } else {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEPENDENT_SERVICE_EXCEPTION, response.getMessage());
        }
    }

    @Override
    public Boolean reomveStoreSkuStock(SupplierOrderStockDTO supplierOrderStockDTO, String appKey) throws ItemException{
        Response<Boolean> response;
        try {
            response = storeOrderNumClient.removeStoreSkuStock(supplierOrderStockDTO, appKey);

        } catch (Exception e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "查询供应商平台时出现问题");
        }

        if (response.isSuccess()) {
            return response.getModule();
        } else {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEPENDENT_SERVICE_EXCEPTION, response.getMessage());
        }
    }

    @Override
    public Boolean resumeStoreSkuStock(SupplierOrderStockDTO supplierOrderStockDTO, String appKey) throws ItemException {
        Response<Boolean> response;
        try {
            response = storeOrderNumClient.returnStoreSkuStock(supplierOrderStockDTO, appKey);

        } catch (Exception e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "查询供应商平台时出现问题");
        }

        if (response.isSuccess()) {
            return response.getModule();
        } else {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEPENDENT_SERVICE_EXCEPTION, response.getMessage());
        }
    }

    @Override
    public Boolean resumeStoreSkuStockBySku(SupplierOrderStockDTO supplierOrderStockDTO, String appKey) throws ItemException {
        Response<Boolean> response;
        try {
            response = storeOrderNumClient.returnStoreSkuStockBySku(supplierOrderStockDTO, appKey);

        } catch (Exception e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "查询供应商平台时出现问题");
        }

        if (response.isSuccess()) {
            return response.getModule();
        } else {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEPENDENT_SERVICE_EXCEPTION, response.getMessage());
        }
    }

    @Override
    public Boolean deleteStoreSkuStock(List<Long> skuIdList, String appKey) throws ItemException {
        Response<Boolean> response;
        try {
            response = storeItemSkuClient.cancleStoreItemSkuList(skuIdList, appKey);

        } catch (Exception e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "查询供应商平台时出现问题");
        }

        if (response.isSuccess()) {
            return response.getModule();
        } else {
            log.error("删除仓库信息时供应商平台报错 skuIdList {} code {} message {}",skuIdList,response.getCode(),response.getMessage());
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEPENDENT_SERVICE_EXCEPTION, response.getMessage());
        }
    }
}
