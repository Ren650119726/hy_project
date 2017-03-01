package com.mockuai.shopcenter.core.service.action.shop;

import com.mockuai.shopcenter.api.ShopResponse;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.ShopItemGroupManager;
import com.mockuai.shopcenter.core.service.RequestContext;
import com.mockuai.shopcenter.core.service.ShopRequest;
import com.mockuai.shopcenter.core.service.action.Action;
import com.mockuai.shopcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 删除分组;
 * Created by luliang on 15/7/28.
 */
@Service
public class DeleteShopItemGroupAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(DeleteShopItemGroupAction.class);
    @Resource
    private ShopItemGroupManager shopItemGroupManager;

    @Override
    public ShopResponse execute(RequestContext context) throws ShopException {
        ShopResponse response = null;
        ShopRequest request = context.getRequest();
        Long id = (Long)request.getParam("id");
        Long sellerId = (Long)request.getParam("sellerId");

        if(id == null || sellerId == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "id is null");
        }
        try {
            Boolean result = shopItemGroupManager.deleteShopItemGroup(id, sellerId);
            if(result) {
                response = ResponseUtil.getSuccessResponse(true);
            } else {
                return ResponseUtil.getErrorResponse(ResponseCode.SYS_E_DB_UPDATE);
            }
            return response;
        } catch (ShopException e) {
            response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
            return response;
        }
    }

    @Override
    public String getName() {
        return ActionEnum.DELETE_SHOP_ITEM_GROUP.getActionName();
    }
}
