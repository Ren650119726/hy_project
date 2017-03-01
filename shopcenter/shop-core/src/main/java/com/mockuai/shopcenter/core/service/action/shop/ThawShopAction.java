package com.mockuai.shopcenter.core.service.action.shop;

import com.mockuai.shopcenter.api.ShopResponse;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.constant.ShopStatusEnum;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.ShopManager;
import com.mockuai.shopcenter.core.service.RequestContext;
import com.mockuai.shopcenter.core.service.ShopRequest;
import com.mockuai.shopcenter.core.service.action.Action;
import com.mockuai.shopcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by luliang on 15/7/28.
 */
@Service
public class ThawShopAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(FreezeShopAction.class);

    @Resource
    private ShopManager shopManager;

    @Override
    public ShopResponse execute(RequestContext context) throws ShopException {
        ShopResponse response = null;
        ShopRequest request = context.getRequest();
        Long sellerId = (Long)request.getParam("sellerId");
        if(sellerId == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "sellerId is null");
        }
        try {
            Boolean result = shopManager.updateShopStatus(sellerId, ShopStatusEnum.NORMAL_BUSSINESS.getStatus());
            if(result) {
                response = ResponseUtil.getSuccessResponse(true);
                return response;
            } else {
                return ResponseUtil.getErrorResponse(ResponseCode.SYS_E_DB_UPDATE);
            }

        } catch (ShopException e) {
            response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
            return response;
        }
    }

    @Override
    public String getName() {
        return ActionEnum.THAW_SHOP.getActionName();
    }
}
