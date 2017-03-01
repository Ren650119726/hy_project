package com.mockuai.shopcenter.core.service.action.shop;

import com.google.common.base.Strings;
import com.mockuai.shopcenter.api.ShopResponse;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.core.dao.RItemGroupDAO;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.RItemGroupManager;
import com.mockuai.shopcenter.core.manager.ShopItemGroupManager;
import com.mockuai.shopcenter.core.service.RequestContext;
import com.mockuai.shopcenter.core.service.ShopRequest;
import com.mockuai.shopcenter.core.service.action.Action;
import com.mockuai.shopcenter.core.util.ResponseUtil;
import com.mockuai.shopcenter.domain.dto.RItemGroupDTO;
import com.mockuai.shopcenter.domain.dto.ShopItemGroupDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by luliang on 15/8/1.
 */
@Service
public class UpdateShopItemGroupAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(UpdateShopItemGroupAction.class);

    @Resource
    private ShopItemGroupManager shopItemGroupManager;

    @Resource
    private RItemGroupManager rItemGroupManager;

    @Override
    public ShopResponse execute(RequestContext context) throws ShopException {
        ShopResponse response = null;
        ShopRequest request = context.getRequest();
        String bizCode = (String) context.get("bizCode");
        ShopItemGroupDTO shopItemGroupDTO = (ShopItemGroupDTO)request.getParam("shopItemGroupDTO");
        String updateItems = (String) request.getParam("updateItems");
        if(shopItemGroupDTO == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "shopItemGroupDTO is null");
        }
        try {
            shopItemGroupDTO.setBizCode(bizCode);
            Boolean result = shopItemGroupManager.updateShopItemGroup(shopItemGroupDTO);

            Long groupId = shopItemGroupDTO.getId();
            Long sellerId = shopItemGroupDTO.getSellerId();


            if(updateItems!=null && updateItems.equals("1")){


                rItemGroupManager.deleteItemGroupByGroupId(groupId,sellerId,bizCode);

                if(shopItemGroupDTO.getItemIdList()!=null){
                    for(Long itemId : shopItemGroupDTO.getItemIdList()){
                        RItemGroupDTO rItemGroupDTO = new RItemGroupDTO();
                        rItemGroupDTO.setItemId(itemId);
                        rItemGroupDTO.setSellerId(sellerId);
                        rItemGroupDTO.setGroupId(groupId);
                        rItemGroupDTO.setBizCode(bizCode);

                        rItemGroupManager.addRItemGroup(rItemGroupDTO);
                    }
                }
            }


            response = ResponseUtil.getSuccessResponse(true);
        } catch (ShopException e) {
            response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
            return response;
        }

        return response;
    }

    @Override
    public String getName() {
        return ActionEnum.UPDATE_SHOP_ITEM_GROUP.getActionName();
    }
}
