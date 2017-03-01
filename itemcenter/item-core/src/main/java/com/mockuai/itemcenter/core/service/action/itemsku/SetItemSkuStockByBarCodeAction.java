package com.mockuai.itemcenter.core.service.action.itemsku;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.*;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemSkuManager;
import com.mockuai.itemcenter.core.message.producer.Producer;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 更新商品销售属性(ItemSku) Action
 *
 * @author chen.huang
 */

@Service
public class SetItemSkuStockByBarCodeAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(SetItemSkuStockByBarCodeAction.class);
    @Resource
    private ItemSkuManager itemSkuManager;

    @Resource
    private Producer producer;

    @Override
    public ItemResponse execute(RequestContext context) throws ItemException {
        ItemResponse response = null;
        ItemRequest request = context.getRequest();
        if (request.getString("barCode") == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "ItemSkuID is missing");
        }
        if (request.getLong("sellerId") == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "sellerId is missing");
        }
        if (request.getLong("number") == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "decreasedNumber is missing");
        }
        String barCode = request.getString("barCode");
        long sellerId = request.getLong("sellerId");
        long number = request.getLong("number");
        String bizCode = (String) context.get("bizCode");
        try {

            ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
            itemSkuQTO.setBarCode(barCode);
            itemSkuQTO.setSellerId(sellerId);
            itemSkuQTO.setBizCode(bizCode);

            List<ItemSkuDTO> itemSkuDTOList = itemSkuManager.queryItemSku(itemSkuQTO);

            if (itemSkuDTOList.size() == 0) {
                throw ExceptionUtil.getException(ResponseCode.BASE_STATE_E_BARCODE_NO_EXIST, "查询不到对应的SKU");
            } else if (itemSkuDTOList.size() > 1) {
                throw ExceptionUtil.getException(ResponseCode.BASE_STATE_E_BARCODE_DUPLICATE, "该条形码对应多个SKU");
            }

            itemSkuManager.updateItemSkuStock(itemSkuDTOList.get(0).getId(), sellerId, number, bizCode);

            ItemSkuDTO itemSkuDTO = itemSkuManager.getItemSku(itemSkuDTOList.get(0).getId(), sellerId, bizCode);

            context.put(HookEnum.STOCK_CHANGE_HOOK.getHookName(), "");
            context.put("skuId", itemSkuDTOList.get(0).getId());
            context.put("sellerId", sellerId);

            producer.send(
                    MessageTopicEnum.ITEM_SKU_UPDATE.getTopic(),
                    MessageTagEnum.STOCK.getTag(),
                    itemSkuDTO);


            response = ResponseUtil.getSuccessResponse(true);
            return response;
        } catch (ItemException e) {
            response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
            return response;
        }

    }

    @Override
    public String getName() {
        return ActionEnum.SET_ITEM_SKU_STOCK_BY_BARCODE.getActionName();
    }
}
