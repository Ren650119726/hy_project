package com.mockuai.itemcenter.core.service.action.item;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.*;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

/**
 * 将商品移入回收站
 *
 * @author chen.huang
 */
@Service
public class EmptyRecycleBinItemAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(EmptyRecycleBinItemAction.class);
    @Resource
    private ItemManager itemManager;

    @Resource
    TransactionTemplate transactionTemplate;

    @Resource
    private ItemSkuManager itemSkuManager;

    @Resource
    private ItemImageManager itemImageManager;

    @Resource
    private ItemPropertyManager itemPropertyManager;

    @Resource
    private SkuPropertyManager skuPropertyManager;

    @Resource
    private ItemSearchManager itemSearchManager;

    @Resource
    private ItemBuyLimitManager itemBuyLimitManager;


    public ItemResponse doTransaction(final RequestContext context) throws ItemException {

        ItemResponse response = null;
        ItemRequest request = context.getRequest();
        String appKey = (String) request.getParam("appKey");
        String bizCode = (String) context.get("bizCode");

        if (request.getLong("sellerId") == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "sellerId is missing");
        }
        Long sellerId = request.getLong("sellerId");// 供应商ID

        Long result = itemSkuManager.emptyRecycleBin(sellerId, bizCode);//删除对应的item_sku
        Long result2 = itemPropertyManager.emptyRecycleBin(sellerId, bizCode);//删除对应的基本属性
        Long result3 = skuPropertyManager.emptyRecycleBin(sellerId, bizCode);//删除对应的销售属性

        // 删除限购;
        itemBuyLimitManager.emptyRecycleBin(sellerId, bizCode);

        // 删除副图列表
        itemImageManager.emptyRecycleBin(sellerId, bizCode);
        itemManager.emptyRecycleBin(sellerId, bizCode);

        response = ResponseUtil.getSuccessResponse(true);
        return response;
    }

    @Override
    public String getName() {
        return ActionEnum.EMPTY_ITEM_RECYCLE_BIN.getActionName();
    }
}
