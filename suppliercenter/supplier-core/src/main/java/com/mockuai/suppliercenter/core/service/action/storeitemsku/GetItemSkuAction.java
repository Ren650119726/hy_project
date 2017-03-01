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
import com.mockuai.suppliercenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 根据itemId查询itemSku
 */
@Service
public class GetItemSkuAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(GetItemSkuAction.class);


    @Resource
    private StoreItemSkuManager storeItemSkuManager;

    @Override
    public SupplierResponse execute(RequestContext context)
            throws SupplierException {

        SupplierRequest supplierRequest = context.getRequest();
        StoreItemSkuQTO storeItemSkuQTO = (StoreItemSkuQTO) supplierRequest
                .getParam("storeItemSkuQTO");
        String appKey = (String) supplierRequest.getParam("appKey");


        String bizCode = (String) context.get("bizCode");
        if (bizCode == null) {
            bizCode = "hanshu";
        }

        if (storeItemSkuQTO == null) {
            log.error("get ItemSku info， storeItemSkuQTO is null ");
        }

           log.info("[{}] storeItemSkuQTO:{}", JsonUtil.toJson(storeItemSkuQTO));
        try {
            StoreItemSkuDTO storeItemSkuDTO = storeItemSkuManager.getStoreItemSku(storeItemSkuQTO);
            log.info("Exit Action [{}]", getName());
            return new SupplierResponse(storeItemSkuDTO);

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
        return ActionEnum.GET_ITEMSKU.getActionName();
    }

}
