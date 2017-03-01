package com.mockuai.itemcenter.mop.api.action;

import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.mop.common.constant.ResponseFormat;
import com.mockuai.mop.common.service.action.Action;

/**
 * Created by zengzhangqiang on 4/27/15.
 */
public abstract class BaseAction implements Action{
    private ItemService itemService;

    public ItemService getItemService() {
        return itemService;
    }

    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    public ResponseFormat getResponseFormat() {
        return ResponseFormat.STANDARD;
    }
}
