package com.mockuai.shopcenter.core.service.action.shop;

import com.mockuai.shopcenter.api.ShopResponse;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.ShopManager;
import com.mockuai.shopcenter.core.service.RequestContext;
import com.mockuai.shopcenter.core.service.ShopRequest;
import com.mockuai.shopcenter.core.service.action.Action;
import com.mockuai.shopcenter.core.util.ResponseUtil;
import com.mockuai.shopcenter.domain.qto.ShopQTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by luliang on 15/8/12.
 */
@Service
public class CountShopAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(CountShopAction.class);

    @Resource
    private ShopManager shopManager;

    @Override
    public ShopResponse execute(RequestContext context) throws ShopException {
        ShopResponse shopResponse = null;
        ShopRequest shopRequest = context.getRequest();
        String bizCode = (String)context.get("bizCode");
        ShopQTO shopQTO = (ShopQTO) shopRequest.getParam("shopQTO");
        try {
            shopQTO.setBizCode(bizCode);//填充bizCode
            Integer count = shopManager.countShop(shopQTO);
            shopResponse = ResponseUtil.getSuccessResponse(count);
            return shopResponse;
        } catch (ShopException e) {
            shopResponse = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            log.error("do action:" + shopRequest.getCommand() + " occur Exception:" + e.getMessage(), e);
            return shopResponse;
        }
    }

    @Override
    public String getName() {
        return ActionEnum.COUNT_ALL_SHOP.getActionName();
    }
}
