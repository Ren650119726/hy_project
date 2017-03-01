package com.mockuai.shopcenter.core.service.action.shop;

import com.mockuai.shopcenter.api.ShopResponse;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.ShopCollectionManager;
import com.mockuai.shopcenter.core.manager.ShopManager;
import com.mockuai.shopcenter.core.service.RequestContext;
import com.mockuai.shopcenter.core.service.ShopRequest;
import com.mockuai.shopcenter.core.service.action.Action;
import com.mockuai.shopcenter.core.service.action.TransAction;
import com.mockuai.shopcenter.core.util.ExceptionUtil;
import com.mockuai.shopcenter.core.util.ResponseUtil;
import com.mockuai.shopcenter.domain.dto.ShopDTO;
import com.mockuai.shopcenter.domain.qto.ShopQTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by luliang on 15/8/7.
 */
@Service
public class DeleteShopCollectionAction extends TransAction {

    private static final Logger log = LoggerFactory.getLogger(DeleteShopCollectionAction.class);

    @Resource
    private ShopManager shopManager;

    @Resource
    private ShopCollectionManager shopCollectionManager;

    @Override
    protected ShopResponse doTransaction(RequestContext context) throws ShopException {

        ShopResponse shopResponse = null;
        ShopRequest shopRequest = context.getRequest();

        Long userId = shopRequest.getLong("userId");
        List<Long> shopIdList = shopRequest.getObject("shopIdList", List.class);
        String bizCode = (String) context.get("bizCode");

        if (userId == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "userId is null");
        }

        if (CollectionUtils.isEmpty(shopIdList)) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "shopIdList is null");
        }

        ShopQTO shopQTO = new ShopQTO();
        shopQTO.setIdList(shopIdList);
        shopQTO.setBizCode(bizCode);

        List<ShopDTO> shopDTOList = shopManager.queryShop(shopQTO);

        if (CollectionUtils.isEmpty(shopDTOList)) {
            throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "查询不到对应的门店");
        }

        Boolean result = true;

        for (ShopDTO shopDTO : shopDTOList) {
            result = shopCollectionManager.deleteShopCollection(shopDTO.getSellerId(), userId)&&result;
        }

        shopResponse = ResponseUtil.getSuccessResponse(result);

        return shopResponse;
    }

    @Override
    public String getName() {
        return ActionEnum.DELETE_SHOP_COLLECTION.getActionName();
    }
}
