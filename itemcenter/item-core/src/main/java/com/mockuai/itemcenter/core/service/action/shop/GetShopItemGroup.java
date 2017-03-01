package com.mockuai.itemcenter.core.service.action.shop;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ShopManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import com.mockuai.shopcenter.domain.dto.ShopItemGroupDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by luliang on 15/7/31.
 */
@Service
public class GetShopItemGroup extends TransAction {

    private static final Logger log = LoggerFactory.getLogger(GetShopItemGroup.class);

    @Resource
    private ShopManager shopManager;

    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {
        ItemResponse shopResponse = null;
        ItemRequest shopRequest = context.getRequest();
        Long groupId = (Long) shopRequest.getParam("groupId");
        Long sellerId = (Long) shopRequest.getParam("sellerId");
        String appKey = (String) shopRequest.getParam("appKey");
        if (groupId == null || sellerId == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "param is null");
        }

        ShopItemGroupDTO shopItemGroupDTO = null;

        shopItemGroupDTO = shopManager.getShopItemGroup(sellerId, groupId, appKey);

        if (shopItemGroupDTO != null) {
            shopResponse = ResponseUtil.getSuccessResponse(shopItemGroupDTO.getItemIdList());
        }else {
            shopResponse = ResponseUtil.getSuccessResponse(null);
        }


        return shopResponse;
    }

    @Override
    public String getName() {
        return ActionEnum.GET_ITEM_GROUP.getActionName();
    }
}
