package com.mockuai.shopcenter.core.service.action.shop;

import com.mockuai.shopcenter.api.ShopResponse;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.ShopItemGroupManager;
import com.mockuai.shopcenter.core.manager.ShopManager;
import com.mockuai.shopcenter.core.service.RequestContext;
import com.mockuai.shopcenter.core.service.ShopRequest;
import com.mockuai.shopcenter.core.service.action.Action;
import com.mockuai.shopcenter.core.service.action.TransAction;
import com.mockuai.shopcenter.core.util.ResponseUtil;
import com.mockuai.shopcenter.domain.dto.ShopDTO;
import com.mockuai.shopcenter.domain.dto.ShopItemGroupDTO;
import com.mockuai.shopcenter.domain.qto.ShopItemGroupQTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by luliang on 15/7/31.
 */
@Service
public class QueryShopItemGroupByShopAction extends TransAction {

    private static final Logger log = LoggerFactory.getLogger(QueryShopItemGroupByShopAction.class);

    @Resource
    private ShopItemGroupManager shopItemGroupManager;

    @Resource
    private ShopManager shopManager;

    @Override
    protected ShopResponse doTransaction(RequestContext context) throws ShopException {
        ShopResponse shopResponse = null;
        ShopRequest shopRequest = context.getRequest();
        String bizCode = (String) context.get("bizCode");

        Long shopId = shopRequest.getLong("shopId");
        if (shopId == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "shopId is null");
        }

        List<ShopItemGroupDTO> shopItemGroupDTOList = null;
        try {

            ShopItemGroupQTO shopItemGroupQTO = new ShopItemGroupQTO();
            shopItemGroupQTO.setShopId(shopId);
            shopItemGroupQTO.setBizCode(bizCode);
            shopItemGroupDTOList = shopItemGroupManager.queryShopItemGroup(shopItemGroupQTO);

            return ResponseUtil.getSuccessResponse(shopItemGroupDTOList,shopItemGroupDTOList.size());

        } catch (ShopException e) {
            shopResponse = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            log.error("do action:" + shopRequest.getCommand() + " occur Exception:" + e.getMessage(), e);
            return shopResponse;
        }
    }


    @Override
    public String getName() {
        return ActionEnum.QUERY_SHOP_ITEM_GROUP_BY_SHOP.getActionName();
    }
}
