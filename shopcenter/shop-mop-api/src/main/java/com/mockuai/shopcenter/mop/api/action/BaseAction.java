package com.mockuai.shopcenter.mop.api.action;

import com.mockuai.mop.common.constant.ResponseFormat;
import com.mockuai.mop.common.service.action.Action;
import com.mockuai.shopcenter.api.ShopService;

/**
 * Created by zengzhangqiang on 4/27/15.
 */
public abstract class BaseAction implements Action{
    private ShopService shopService;

    public ShopService getShopService() {
        return shopService;
    }

    public void setShopService(ShopService shopService) {
        this.shopService = shopService;
    }

    public ResponseFormat getResponseFormat() {
        return ResponseFormat.STANDARD;
    }
}
