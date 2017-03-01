package com.mockuai.itemcenter.core.service.action.item;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 从ssd磁盘读取商品
 * Created by lizg on 2016/10/31.
 */

@Service
public class ReadPublishItemAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(ReadPublishItemAction.class);


    @Override
    public ItemResponse execute(RequestContext context) throws ItemException {
        ItemRequest request = context.getRequest();
        String appKey = (String) context.get("appKey");
        Long itemId = request.getLong("id");


        return null;
    }

    @Override
    public String getName() {
        return ActionEnum.READ_PUBLISH_ITEM.getActionName();
    }
}
