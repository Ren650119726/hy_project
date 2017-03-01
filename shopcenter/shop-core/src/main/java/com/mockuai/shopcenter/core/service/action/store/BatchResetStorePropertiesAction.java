package com.mockuai.shopcenter.core.service.action.store;

import com.google.common.base.Strings;
import com.mockuai.shopcenter.api.ShopResponse;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.ShopPropertyManager;
import com.mockuai.shopcenter.core.manager.StorePropertyManager;
import com.mockuai.shopcenter.core.service.RequestContext;
import com.mockuai.shopcenter.core.service.action.TransAction;
import com.mockuai.shopcenter.core.util.ExceptionUtil;
import com.mockuai.shopcenter.core.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 15/11/3.
 */
@Service
public class BatchResetStorePropertiesAction extends TransAction{

    @Resource
    private StorePropertyManager storePropertyManager;

    @Override
    protected ShopResponse doTransaction(RequestContext context) throws ShopException {

        String bizCode = (String) context.get("bizCode");
        Long sellerId = context.getRequest().getLong("sellerId");
        String key = context.getRequest().getString("key");
        String value = context.getRequest().getString("value");
        List<Long> storeIds = (List<Long>) context.getRequest().getObject("storeIds");

        if(sellerId==null){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"sellerId不能为空");
        }

        if(Strings.isNullOrEmpty(key)){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"key不能为空");
        }

        if(Strings.isNullOrEmpty(value)){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"value不能为空");
        }

        if(storeIds==null){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"storeIds不能为空");
        }


        Integer result1 = storePropertyManager.batchDeleteProperty(sellerId, key, value, bizCode);

        Integer result2 = storePropertyManager.batchSetProperty(storeIds, sellerId, key, value, bizCode);

        return ResponseUtil.getSuccessResponse(result2);
    }

    @Override
    public String getName() {
        return ActionEnum.BATCH_RESET_STORE_PROPERTY.getActionName();
    }
}
