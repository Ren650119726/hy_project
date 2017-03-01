package com.mockuai.shopcenter.core.service.action.config;

import com.google.common.base.Strings;
import com.mockuai.shopcenter.api.ShopResponse;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.ShopPropertyManager;
import com.mockuai.shopcenter.core.service.RequestContext;
import com.mockuai.shopcenter.core.service.action.TransAction;
import com.mockuai.shopcenter.core.util.ExceptionUtil;
import com.mockuai.shopcenter.core.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by yindingyu on 15/11/3.
 */
@Service
public class GetShopPropertiesAction extends TransAction{

    @Resource
    private ShopPropertyManager shopPropertyManager;

    @Override
    protected ShopResponse doTransaction(RequestContext context) throws ShopException {

        String bizCode = (String) context.get("bizCode");
        Long sellerId = context.getRequest().getLong("sellerId");
        List<String> keys = (List<String>) context.getRequest().getObject("keys");

        if(sellerId==null){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"sellerId不能为空");
        }

        if(null==keys){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"keys不能为空");
        }

        Map<String,String> props = shopPropertyManager.getProperties(keys,sellerId,bizCode);

        return ResponseUtil.getSuccessResponse(props);
    }

    @Override
    public String getName() {
        return ActionEnum.GET_SHOP_PROPERTIES.getActionName();
    }
}
