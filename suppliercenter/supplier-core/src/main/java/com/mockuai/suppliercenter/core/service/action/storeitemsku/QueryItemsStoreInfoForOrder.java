package com.mockuai.suppliercenter.core.service.action.storeitemsku;

import com.mockuai.suppliercenter.common.action.ActionEnum;
import com.mockuai.suppliercenter.common.api.SupplierResponse;
import com.mockuai.suppliercenter.common.constant.ResponseCode;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuForOrderQTO;
import com.mockuai.suppliercenter.core.exception.SupplierException;
import com.mockuai.suppliercenter.core.manager.StoreItemSkuManager;
import com.mockuai.suppliercenter.core.service.RequestContext;
import com.mockuai.suppliercenter.core.service.SupplierRequest;
import com.mockuai.suppliercenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QueryItemsStoreInfoForOrder implements Action {
    private static final Logger log = LoggerFactory.getLogger(QueryItemsStoreInfoForOrder.class);

    @Resource
    private StoreItemSkuManager storeItemSkuManager;

    @Override
    public SupplierResponse execute(RequestContext context) throws SupplierException {
        log.info("Enter Action [{}]", getName());
        SupplierRequest supplierRequest = context.getRequest();
        StoreItemSkuForOrderQTO storeItemSkuQTO = (StoreItemSkuForOrderQTO) supplierRequest.getParam("storeItemSkuForOrderQTO");

        String bizCode = (String) context.get("bizCode");

        // TODO 入参校验
        if (bizCode == null) {
            bizCode = "hanshu";
//            log.error("bizCode is null");
//            return new SupplierResponse(ResponseCode.P_PARAM_NULL,
//                    "bizCode is null");
        }
        storeItemSkuQTO.setBizCode(bizCode);
        if (storeItemSkuQTO == null) {
            log.error("storeItemSku info qto is null when query storeItemSku by storeItemSkuQTO");
        }


        try {

            List<StoreItemSkuDTO> stores = storeItemSkuManager.queryItemsStoresInfForOrder(storeItemSkuQTO);

            log.info("cancle storeItem Sku success,Exit Action [{}]", getName());
            return new SupplierResponse(stores, Long.valueOf(stores.size()));

        } catch (SupplierException e) {
            log.error("do action:" + supplierRequest.getCommand()
                    + " occur Exception:" + e.getMessage(), e);
            return new SupplierResponse(e.getResponseCode(), e.getMessage());
        } catch (Exception e) {
            log.error("do action:" + supplierRequest.getCommand()
                    + " occur Exception:" + e.getMessage(), e);
            return new SupplierResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION,
                    e.getMessage());

        }
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return ActionEnum.QUERY_ITEMSSTOREINFFORORDER.getActionName();
    }
}
