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
import java.util.List;

/**
 * 删除商品Action
 *
 * @author chen.huang
 */
@Service
public class BatchRecoveryItemAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(BatchRecoveryItemAction.class);
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
        // 验证ID
        if (request.getObject("itemIdList") == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "ItemIdList is missing");
        }

        if (request.getLong("sellerId") == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "sellerId is missing");
        }

        List<Long> itemIdList = (List<Long>) request.getObject("itemIdList");// 商品品牌ID
        Long sellerId = request.getLong("sellerId");// 供应商ID

        for (Long itemId : itemIdList) {
            Long result = itemSkuManager.recoveryByItemId(itemId, sellerId, bizCode);//删除对应的item_sku
            Long result2 = itemPropertyManager.recoveryByItemId(itemId, sellerId);//删除对应的基本属性
            Long result3 = skuPropertyManager.recoveryByItemId(itemId, sellerId);//删除对应的销售属性
            // 删除限购;
            itemBuyLimitManager.recoveryItemBuyLimit(sellerId, itemId);
            // 删除副图列表
            itemImageManager.recoveryItemImageListByItemId(itemId, sellerId);

            itemManager.recoveryItem(itemId, sellerId, bizCode);

            //恢复的商品处于下架状态
            itemManager.withdrawItem(itemId, sellerId, bizCode);

        }

        return ResponseUtil.getSuccessResponse(1L);
    }

    @Override
    public String getName() {
        return ActionEnum.BATCH_RECOVERY_ITEM.getActionName();
    }
}
