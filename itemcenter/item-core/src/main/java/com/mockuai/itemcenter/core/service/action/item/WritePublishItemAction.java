package com.mockuai.itemcenter.core.service.action.item;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.mop.MopBaseItemDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.PublishItemManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lizg on 2016/10/30.
 */

@Service
public class WritePublishItemAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(GetItemAction.class);

    @Resource
    private PublishItemManager publishItemManager;


    @Override
    public ItemResponse execute(RequestContext context) throws ItemException {

        ItemRequest request = context.getRequest();
        String appKey = (String) context.get("appKey");
        String bizCode = (String) context.get("bizCode");
        //  Integer type = request.getInteger("type");
        Long itemId = request.getLong("itemId");// 商品ID
        try {
            MopBaseItemDTO mopBaseItemDTO = publishItemManager.publish(itemId,appKey,bizCode,false);
            return ResponseUtil.getSuccessResponse(mopBaseItemDTO);
        }catch (ItemException e){
            throw  new ItemException(e.getMessage(),e);
        }

    }


    @Override
    public String getName() {
        return ActionEnum.WRITER_PUBLISH_ITEM.getActionName();
    }
}
