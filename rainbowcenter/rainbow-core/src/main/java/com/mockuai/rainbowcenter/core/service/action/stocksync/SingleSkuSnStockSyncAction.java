package com.mockuai.rainbowcenter.core.service.action.stocksync;
import com.google.common.base.Strings;
import com.mockuai.itemcenter.client.ItemClient;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.rainbowcenter.common.api.RainbowResponse;
import com.mockuai.rainbowcenter.common.api.Request;
import com.mockuai.rainbowcenter.common.constant.ActionEnum;
import com.mockuai.rainbowcenter.common.constant.ResponseCode;
import com.mockuai.rainbowcenter.common.dto.ErpStockDTO;
import com.mockuai.rainbowcenter.common.dto.SingleSkuSnStockSyncDTO;
import com.mockuai.rainbowcenter.common.dto.SyncStockItemSkuDTO;
import com.mockuai.rainbowcenter.core.exception.RainbowException;
import com.mockuai.rainbowcenter.core.manager.GyErpManage;
import com.mockuai.rainbowcenter.core.manager.ItemSkuSnManager;
import com.mockuai.rainbowcenter.core.service.RequestContext;
import com.mockuai.rainbowcenter.core.service.action.Action;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuQTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;


/**
 * Created by lizg on 2016/9/25.
 */
@Controller
public class SingleSkuSnStockSyncAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(SingleSkuSnStockSyncAction.class);

    @Resource
    private GyErpManage gyErpManage;

    @Resource
    private ItemSkuSnManager itemSkuSnManager;

    @Resource
    private ItemClient itemClient;

    @Override
    public RainbowResponse execute(RequestContext context) {

        Request request = context.getRequest();

        String appKey = (String) request.getParam("appKey");

        SingleSkuSnStockSyncDTO singleSkuSnStockSyncDTO = (SingleSkuSnStockSyncDTO) request.getParam("singleSkuSnStockSyncDTO");

        SyncStockItemSkuDTO syncStockItemSkuDTO = new SyncStockItemSkuDTO();

        try {
            if (Strings.isNullOrEmpty(appKey)) {
                log.error("appKey is null");
                throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL, "appKey is null");
            }

            if (null == singleSkuSnStockSyncDTO) {
                log.error("singleSkuSnStockSyncDTO is null");
                throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL, "singleSkuSnStockSyncDTO is null");
            }

            String skuItemSn = singleSkuSnStockSyncDTO.getItemSkuSn();
            if (Strings.isNullOrEmpty(skuItemSn)) {
                log.error("skuItemSn is null");
                throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL, "skuItemSn is null");
            }

            Long storeId = singleSkuSnStockSyncDTO.getStoreId();
            if (null == storeId) {
                log.error("storeId is null");
                throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL, "storeId is null");
            }

            Long itemSkuId = singleSkuSnStockSyncDTO.getItemSkuId();

            if (null == itemSkuId) {
                log.error("itemSkuId is null");
                throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL, "itemSkuId is null");
            }

            ErpStockDTO erpStockDTO = gyErpManage.getStock(skuItemSn,storeId);
            if (null == erpStockDTO) {
                throw new RainbowException(ResponseCode.GYERP_ITEM_CODE_SERVICE_EXCEPTION,"erpStockDTO is null");
            }

            String salableQty = erpStockDTO.getSalableQty();
            log.info("[{}] salableQty:{}",salableQty);
            String salableNum = salableQty.substring(0, salableQty.lastIndexOf("."));
             Long stockNum = Long.parseLong(salableNum);
            log.info("[{}] stockNum:{}",stockNum);
            StoreItemSkuDTO storeItemSkuDTO = new StoreItemSkuDTO();
            storeItemSkuDTO.setSupplierItmeSkuSn(skuItemSn);
            storeItemSkuDTO.setStoreId(storeId);
            storeItemSkuDTO.setItemSkuId(itemSkuId);
            storeItemSkuDTO.setGyerpStockNum(stockNum);
            itemSkuSnManager.updateSingStockToGyerpBySkuSn(storeItemSkuDTO,appKey);

            StoreItemSkuQTO storeItemSkuQTO = new StoreItemSkuQTO();
            storeItemSkuQTO.setItemSkuId(itemSkuId);
            storeItemSkuQTO.setStoreId(storeId);
            storeItemSkuDTO = itemSkuSnManager.getStoreItemSku(storeItemSkuQTO, appKey);
              syncStockItemSkuDTO.setStoreId(storeId);
              syncStockItemSkuDTO.setItemSkuId(itemSkuId);
              syncStockItemSkuDTO.setSupplierItmeSkuSn(storeItemSkuDTO.getSupplierItmeSkuSn());
              syncStockItemSkuDTO.setFrozenStockNum(storeItemSkuDTO.getFrozenStockNum());
              syncStockItemSkuDTO.setSalesNum(storeItemSkuDTO.getSalesNum());
              syncStockItemSkuDTO.setSoldNum(storeItemSkuDTO.getSoldNum());
              syncStockItemSkuDTO.setStockNum(storeItemSkuDTO.getStockNum());

        } catch (RainbowException e) {
            return new RainbowResponse(e.getResponseCode(), e.getMessage());
        }

        return new RainbowResponse(syncStockItemSkuDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.SINGLE_SKU_SN_STOCK_SYNC.getActionName();
    }
}
