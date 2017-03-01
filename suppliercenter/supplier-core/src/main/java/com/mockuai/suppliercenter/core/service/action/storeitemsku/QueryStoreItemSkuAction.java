package com.mockuai.suppliercenter.core.service.action.storeitemsku;

import com.mockuai.suppliercenter.common.action.ActionEnum;
import com.mockuai.suppliercenter.common.api.SupplierResponse;
import com.mockuai.suppliercenter.common.constant.ResponseCode;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuQTO;
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
public class QueryStoreItemSkuAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(QueryStoreItemSkuAction.class);

    @Resource
    private StoreItemSkuManager storeItemSkuManager;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public SupplierResponse execute(RequestContext context) throws SupplierException {

        SupplierRequest supplierRequest = context.getRequest();
        StoreItemSkuQTO storeItemSkuQTO = (StoreItemSkuQTO) supplierRequest.getParam("storeItemSkuQTO");
        String appKey = (String) supplierRequest.getParam("appKey");
        
        if (storeItemSkuQTO == null) {
            log.error("storeItemSku info qto is null when query storeItemSku by storeItemSkuQTO");
            return new SupplierResponse(ResponseCode.P_PARAM_NULL, "storeItemSkuQTO is null");
        }

        try {

            List<StoreItemSkuDTO> stores = storeItemSkuManager.queryStoreItemSku(storeItemSkuQTO, appKey);
            Long total = storeItemSkuManager.getTotalCount(storeItemSkuQTO);
            
            return new SupplierResponse(stores, total);
        } catch (Exception e) {
            log.error("do action:" + supplierRequest.getCommand() + " occur Exception:" + e.getMessage(), e);
            return new SupplierResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION, e.getMessage());

        }       
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_STOREITEMSKU.getActionName();
    }
}
