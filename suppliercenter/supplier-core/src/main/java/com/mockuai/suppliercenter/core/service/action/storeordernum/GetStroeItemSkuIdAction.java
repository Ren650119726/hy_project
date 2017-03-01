package com.mockuai.suppliercenter.core.service.action.storeordernum;

import com.mockuai.suppliercenter.common.action.ActionEnum;
import com.mockuai.suppliercenter.common.api.SupplierResponse;
import com.mockuai.suppliercenter.common.constant.ResponseCode;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.core.exception.SupplierException;
import com.mockuai.suppliercenter.core.manager.SupplierOrderStockManager;
import com.mockuai.suppliercenter.core.service.RequestContext;
import com.mockuai.suppliercenter.core.service.SupplierRequest;
import com.mockuai.suppliercenter.core.service.action.TransAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lizg on 2016/8/1.
 */

@Service
public class GetStroeItemSkuIdAction extends TransAction {


    private static final Logger log = LoggerFactory.getLogger(GetStroeItemSkuIdAction.class);

    @Resource
    private SupplierOrderStockManager supplierOrderStockManager;


    @Override
    protected SupplierResponse doTransaction(RequestContext context) throws SupplierException {
        SupplierRequest supplierRequest = context.getRequest();

        Long itemSkuId = (Long) supplierRequest.getParam("itemSkuId");
        Long number = (Long) supplierRequest.getParam("number");
        String appKey = (String) supplierRequest.getParam("appKey");
        log.info("[{}] appKey:{}", appKey);

        // TODO 入参校验
        if (itemSkuId == null) {
            log.error("itemSkuId is null");
            return new SupplierResponse(ResponseCode.P_PARAM_NULL,
                    "itemSkuId is null");
        }

        if (number == null) {
            log.error("number is null");
            return new SupplierResponse(ResponseCode.P_PARAM_NULL,
                    "number is null");
        }


        try {
            List<StoreItemSkuDTO> storeItemSkuDOList = supplierOrderStockManager.getStroeItemSkuListByItemSkuId(itemSkuId, number);

            return new SupplierResponse(storeItemSkuDOList);

        } catch (SupplierException e) {
            // TODO Auto-generated catch block
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
        return ActionEnum.GET_STOREITEMSKU.getActionName();
    }
}
