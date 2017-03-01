package com.mockuai.suppliercenter.core.service.action.stockitemsku;

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
public class QueryStoreItemSkuByItemIdAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(QueryStoreItemSkuByItemIdAction.class);

    @Resource
    private StoreItemSkuManager storeItemSkuManager;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public SupplierResponse execute(RequestContext context) throws SupplierException {
        SupplierRequest supplierRequest = context.getRequest();
        Long itemId = (Long) supplierRequest.getParam("itemId");
        Integer offset = (Integer) supplierRequest.getParam("offset");
        Integer pageSize = (Integer) supplierRequest.getParam("pageSize");
        String appKey = (String) supplierRequest.getParam("appKey");
        
        if (itemId == null) {
            log.error("storeItemSku info qto is null when query storeItemSku by itemId");
            return new SupplierResponse(ResponseCode.P_PARAM_NULL, "itemId is null");
        }

        try {
        	//重组参数
        	StoreItemSkuQTO storeItemSkuQTO = new StoreItemSkuQTO();
        	storeItemSkuQTO.setItemId(itemId);
        	storeItemSkuQTO.setOffset((offset * pageSize));
        	storeItemSkuQTO.setPageSize(pageSize);
        	
            List<StoreItemSkuDTO> stores = storeItemSkuManager.queryStoreItemSkuByItemId(storeItemSkuQTO, appKey);
            Long total = storeItemSkuManager.getTotalCount(storeItemSkuQTO);
            
            return new SupplierResponse(stores, total);
        } catch (Exception e) {
            log.error("do action:" + supplierRequest.getCommand() + " occur Exception:" + e.getMessage(), e);
            return new SupplierResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION, e.getMessage());

        }       
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_STOREITEMSKUBYITEMID.getActionName();
    }
}
