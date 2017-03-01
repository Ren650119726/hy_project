package com.mockuai.itemcenter.core.service.action.brand;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.SellerBrandDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.SellerBrandManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2016/8/31.
 */
@Service
public class GetBrandAction implements Action {
    @Resource
    private SellerBrandManager sellerBrandManager;

    @Override
    public ItemResponse execute(RequestContext context) throws ItemException {
        ItemResponse response ;
        ItemRequest request = context.getRequest();

        Long id = (Long) request.getParam("brandId");

        SellerBrandDTO sellerBrandDTO = sellerBrandManager.getSellerBrand(id);
        response = ResponseUtil.getSuccessResponse(sellerBrandDTO);
        return response;
    }

    @Override
    public String getName() {
        return ActionEnum.GET_BRAND.getActionName();
    }
}
