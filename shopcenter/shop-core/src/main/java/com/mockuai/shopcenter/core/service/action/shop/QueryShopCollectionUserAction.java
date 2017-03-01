package com.mockuai.shopcenter.core.service.action.shop;

import com.mockuai.shopcenter.api.ShopResponse;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.ShopCollectionManager;
import com.mockuai.shopcenter.core.service.RequestContext;
import com.mockuai.shopcenter.core.service.ShopRequest;
import com.mockuai.shopcenter.core.service.action.Action;
import com.mockuai.shopcenter.core.util.ResponseUtil;
import com.mockuai.shopcenter.domain.dto.ShopCollectionDTO;
import com.mockuai.shopcenter.domain.qto.ShopCollectionQTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 店铺查询收藏店铺的人
 * Created by luliang on 15/8/7.
 */
@Service
public class QueryShopCollectionUserAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(AddShopCollectionAction.class);

    @Resource
    private ShopCollectionManager shopCollectionManager;

    @Override
    public ShopResponse execute(RequestContext context) throws ShopException {
        ShopResponse shopResponse = null;
        ShopRequest shopRequest = context.getRequest();
        String bizCode = (String) context.get("bizCode");
        ShopCollectionQTO shopCollectionQTO = (ShopCollectionQTO)shopRequest.getParam("shopCollectionQTO");
        if(shopCollectionQTO == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "shopCollectionQTO param is null");
        }

        try {
            shopCollectionQTO.setBizCode(bizCode);
            List<ShopCollectionDTO> shopCollectionDTOList = shopCollectionManager.queryShopCollection(shopCollectionQTO);
            shopResponse = ResponseUtil.getSuccessResponse(shopCollectionDTOList);
        } catch (ShopException e) {
            shopResponse = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            log.error("do action:" + shopRequest.getCommand() + " occur Exception:" + e.getMessage(), e);
            return shopResponse;
        }
        return shopResponse;
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_SHOP_COLLECTION_USER.getActionName();
    }
}
