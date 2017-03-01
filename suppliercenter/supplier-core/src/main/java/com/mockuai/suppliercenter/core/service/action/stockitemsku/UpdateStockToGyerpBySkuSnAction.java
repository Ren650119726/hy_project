package com.mockuai.suppliercenter.core.service.action.stockitemsku;
import com.google.common.base.Strings;
import com.mockuai.suppliercenter.common.action.ActionEnum;
import com.mockuai.suppliercenter.common.api.SupplierResponse;
import com.mockuai.suppliercenter.common.constant.ResponseCode;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuQTO;
import com.mockuai.suppliercenter.core.exception.SupplierException;
import com.mockuai.suppliercenter.core.manager.StoreItemSkuManager;
import com.mockuai.suppliercenter.core.service.RequestContext;
import com.mockuai.suppliercenter.core.service.SupplierRequest;
import com.mockuai.suppliercenter.core.service.action.TransAction;
import com.mockuai.suppliercenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * Created by lizg on 2016/9/25.
 */

@Service
public class UpdateStockToGyerpBySkuSnAction extends TransAction {

    @Resource
    private StoreItemSkuManager storeItemSkuManager;

    private static final Logger log = LoggerFactory.getLogger(UpdateStockToGyerpBySkuSnAction.class);

    @Override
    protected SupplierResponse doTransaction(RequestContext context) throws SupplierException {
        SupplierRequest supplierRequest = context.getRequest();

        StoreItemSkuDTO storeItemSkuDTO = (StoreItemSkuDTO)supplierRequest.getParam("storeItemSkuDTO");
        if (null == storeItemSkuDTO) {
            return new SupplierResponse(ResponseCode.P_PARAM_NULL, "storeItemSkuDTO is null");
        }
        log.info("[{}] UpdateStockToGyerpBySkuSnAction storeItemSkuDTO:{}", JsonUtil.toJson(storeItemSkuDTO));
        String skuItemSn = storeItemSkuDTO.getSupplierItmeSkuSn();
        if (Strings.isNullOrEmpty(skuItemSn)) {
            return new SupplierResponse(ResponseCode.P_PARAM_NULL,"skuItemSn is null");
        }

        Long storeId = storeItemSkuDTO.getStoreId();
        if (null == storeId) {
            return new SupplierResponse(ResponseCode.P_PARAM_NULL,"storeId is null");
        }
        StoreItemSkuQTO  storeItemSkuQTO = new StoreItemSkuQTO();
        storeItemSkuQTO.setStoreId(storeId);
        storeItemSkuQTO.setItemSkuId(storeItemSkuDTO.getItemSkuId());
        storeItemSkuQTO.setSupplierItmeSkuSn(skuItemSn);
        StoreItemSkuDTO itemSkuDTO = storeItemSkuManager.getStoreItemSku(storeItemSkuQTO);
        if (null == itemSkuDTO) {
            return new SupplierResponse(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST,"该商品编码不存在对应的库存记录!");
        }

        log.info("[{}] gyerpStockNum:{}",storeItemSkuDTO.getGyerpStockNum());

        itemSkuDTO.setGyerpStockNum(storeItemSkuDTO.getGyerpStockNum());
        storeItemSkuManager.updateStockToGyerpBySkuSn(itemSkuDTO);

        return new SupplierResponse(true);
    }

    @Override
    public String getName() {
        return ActionEnum.STOCK_TO_GYERP_BY_SKUSN.getActionName();
    }
}
