package com.mockuai.itemcenter.core.service.action.item;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.manager.ItemSearchManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by jiguansheng on 16/10/17.
 */
@Service
public class UpdateSearchItemAction extends TransAction {

    private static final Logger log = LoggerFactory.getLogger(FreezeItemAction.class);
    @Resource
    private ItemManager itemManager;

    @Resource
    private ItemSearchManager itemSearchManager;



    private Long sellerId = 1841254L;

    @Override
    public ItemResponse doTransaction(RequestContext context) throws ItemException {
        try {
            ItemRequest request = context.getRequest();
            String bizCode = (String) context.get("bizCode");

            Long itemId = request.getLong("itemId");// 商品品牌ID
            Long  stockNum = request.getLong("stockNum");//库存
            Long frozenStockNum = request.getLong("frozenStockNum");

            // 验证ID
            if (itemId == null) {
                return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "ItemId is missing");
            }
            // 验证stockNum
            if (stockNum == null) {
                return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "stockNum is missing");
            }            // 验证ID
            if (frozenStockNum == null) {
                return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "frozenStockNum is missing");
            }
            log.info("更新solr索引数据   itemId:{},stockNum:{},frozenStockNum:{}",itemId,stockNum,frozenStockNum);
            ItemDTO result = itemManager.getItem(itemId, sellerId, bizCode);
            result.setStockNum(stockNum);
            result.setFrozenStockNum(frozenStockNum);
            //TODO 商品索引更新逻辑异步化
            //更新商品索引
            itemSearchManager.setItemIndex(result);
            return ResponseUtil.getSuccessResponse();

        } catch (ItemException e) {
            log.error(e.toString());
            return ResponseUtil.getErrorResponse(e.getResponseCode(), e.getMessage());
        }
    }

    @Override
    public String getName() {
        return ActionEnum.UPDATE_SEARCH_INDEX.getActionName();
    }
}
