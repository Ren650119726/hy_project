package com.mockuai.itemcenter.core.service.action.support;

import com.google.common.base.Strings;
import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.manager.ShopPropertyManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yindingyu on 15/11/13.
 */
@Service
public class GetShopSupportConfigAction extends TransAction{

    @Resource
    private ShopPropertyManager shopPropertyManager;

    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {

        String bizCode = (String) context.get("bizode");

        Long sellerId = context.getRequest().getLong("sellerId");
        String key = context.getRequest().getString("key");
        String appKey = context.getRequest().getString("appKey");

        if(sellerId == null){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"查询门店功能开关时，seller_id不能为空");
        }

        if(Strings.isNullOrEmpty(key)){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"查询店铺配置项时，key不能为空");
        }

        String value = shopPropertyManager.getShopConfig(key,sellerId,appKey);

        return ResponseUtil.getSuccessResponse(value);

    }

    @Override
    public String getName() {
        return ActionEnum.GET_STORE_SUPPORT_CONFIG.getActionName();
    }
}
