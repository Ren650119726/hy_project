package com.mockuai.shopcenter.core.service.action.shop;

import com.mockuai.shopcenter.api.ShopResponse;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.RItemGroupManager;
import com.mockuai.shopcenter.core.manager.ShopItemGroupManager;
import com.mockuai.shopcenter.core.service.RequestContext;
import com.mockuai.shopcenter.core.service.ShopRequest;
import com.mockuai.shopcenter.core.service.action.Action;
import com.mockuai.shopcenter.core.util.ResponseUtil;
import com.mockuai.shopcenter.domain.dto.ShopItemGroupDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by luliang on 15/7/31.
 */
@Service
public class GetShopItemGroup implements Action {

    private static final Logger log = LoggerFactory.getLogger(GetShopItemGroup.class);

    @Resource
    private ShopItemGroupManager shopItemGroupManager;

    @Resource
    private RItemGroupManager rItemGroupManager;

    @Override
    public ShopResponse execute(RequestContext context) throws ShopException {
        ShopResponse shopResponse = null;
        ShopRequest shopRequest = context.getRequest();
        Long groupId = (Long)shopRequest.getParam("groupId");
        Long sellerId= (Long)shopRequest.getParam("sellerId");
        String bizCode = (String) context.get("bizCode");
        String needItems = (String) shopRequest.getParam("needItems");
        if(groupId == null || sellerId == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "param is null");
        }

        ShopItemGroupDTO shopItemGroupDTO = null;
        try {
            shopItemGroupDTO = shopItemGroupManager.getShopItemGroup(groupId, sellerId);

            if(shopItemGroupDTO!=null&&needItems!=null&&needItems.equals("1")){
                List<Long> itemIdList = rItemGroupManager.queryItemIdList(groupId,sellerId,bizCode);

                shopItemGroupDTO.setItemIdList(itemIdList);
            }



        } catch (ShopException e) {
            shopResponse = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            log.error("do action:" + shopRequest.getCommand() + " occur Exception:" + e.getMessage(), e);
            return shopResponse;
        }
        shopResponse = ResponseUtil.getSuccessResponse(shopItemGroupDTO);
        return shopResponse;
    }

    @Override
    public String getName() {
        return ActionEnum.GET_SHOP_ITEM_GROUP.getActionName();
    }
}
